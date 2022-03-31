package com.mydomain.auth.provider.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.common.util.RandomString;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.sessions.AuthenticationSessionModel;
import org.keycloak.theme.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

public class CustomAuthenticator implements Authenticator {

	// Custom Evooq OTP theme
	private static final String TPL_CODE = "login-otp.ftl";
	private static final Logger log = LoggerFactory.getLogger(CustomAuthenticator.class);

	@Override
	public void authenticate(AuthenticationFlowContext context) {
		log.info("IN authenticate method ");
		AuthenticatorConfigModel config = context.getAuthenticatorConfig();
		KeycloakSession session = context.getSession();
		UserModel user = context.getUser();

		// We dont need this
		// String mobileNumber = user.getFirstAttribute("mobile_number");
		log.info("CHECKPOINT 1");
		log.info(context.toString());
		
		int length = Integer.parseInt(config.getConfig().get("length"));
		int ttl = Integer.parseInt(config.getConfig().get("ttl"));
		log.info("CHECKPOINT 2");		
		// We dont need code and code in authNote
		// String code = RandomString.randomCode(length);
		AuthenticationSessionModel authSession = context.getAuthenticationSession();
		// authSession.setAuthNote("code", code);
		authSession.setAuthNote("ttl", Long.toString(System.currentTimeMillis() + (ttl * 1000L)));
		log.info("CHECKPOINT 3");
		try {
			// Probably we dont need this
			log.info("CHECKPOINT 4");
			 Theme theme = session.theme().getTheme(Theme.Type.LOGIN);
			 log.info("CHECKPOINT 5");
			// Locale locale = session.getContext().resolveLocale(user);
			// String smsAuthText = theme.getMessages(locale).getProperty("smsAuthText");
			// String smsText = String.format(smsAuthText, code, Math.floorDiv(ttl, 60));

			// Use this when custom form implementation is done
			context.challenge(context.form().setAttribute("realm", context.getRealm()).createForm(TPL_CODE));
			log.info("CHECKPOINT 6");

		} catch (Exception e) {
			context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
					context.form().setError("smsAuthSmsNotSent", e.getMessage())
							.createErrorPage(Response.Status.INTERNAL_SERVER_ERROR));
		}
	}

	@Override
	public void action(AuthenticationFlowContext context) {
		String enteredCode = context.getHttpRequest().getDecodedFormParameters().getFirst("code");
		log.info("Enterd code: " + enteredCode);
		AuthenticationSessionModel authSession = context.getAuthenticationSession();
		// We dont need this
		// String code = authSession.getAuthNote("code");
		// String ttl = authSession.getAuthNote("ttl");

		try {
			boolean isValid = isCodeValid(enteredCode, context);
			if (isValid) {
				log.info("Code is valid");
				context.success();
				;
			} else {
				log.info("Code is invalid");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isCodeValid(String enteredCode, AuthenticationFlowContext context) throws IOException {
		//String url = System.getenv("IDP_BASE_URL") + "/assetmax/moik/ext/login/validatecode";
		String url = "https://demo.iam.evooq.io" + "/assetmax/moik/ext/login/validatecode";

		HttpPost post = new HttpPost(url);
		// add request parameters
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("auth", "emailpassword"));
		urlParameters.add(new BasicNameValuePair("email", context.getUser().getEmail()));
		urlParameters.add(new BasicNameValuePair("password", "Welcome_123"));
		urlParameters.add(new BasicNameValuePair("code", enteredCode));
		urlParameters.add(new BasicNameValuePair("auth_type", "TOTP"));

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {
			log.info("Validation code response " + EntityUtils.toString(response.getEntity()));

			// return mapLoginResponse(EntityUtils.toString(response.getEntity()));
		}
		return false;
	}

	@Override
	public boolean requiresUser() {
		return true;
	}

	@Override
	public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
