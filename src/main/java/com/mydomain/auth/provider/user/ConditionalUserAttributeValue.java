package com.mydomain.auth.provider.user;

import java.util.Map;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.AuthenticationFlowException;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionalUserAttributeValue implements ConditionalAuthenticator {

	static final ConditionalUserAttributeValue SINGLETON = new ConditionalUserAttributeValue();
	private static final Logger log = LoggerFactory.getLogger(ConditionalUserAttributeValue.class);

	@Override
	public void action(AuthenticationFlowContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean requiresUser() {
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

	@Override
	public boolean matchCondition(AuthenticationFlowContext context) {
		Map<String, String> config = context.getAuthenticatorConfig().getConfig();
		String attributeName = config.get(ConditionalUserAttributeValueFactory.CONF_ATTRIBUTE_NAME);
		log.info(attributeName);
		String attributeValue = config.get(ConditionalUserAttributeValueFactory.CONF_ATTRIBUTE_EXPECTED_VALUE);
		log.info(attributeValue);

		UserModel user = context.getUser();
		if (user == null) {
			throw new AuthenticationFlowException(
					"Cannot find user for obtaining particular user attributes. Authenticator: "
							+ ConditionalUserAttributeValueFactory.PROVIDER_ID,
					AuthenticationFlowError.UNKNOWN_USER);
		}

		log.info(attributeName);
		boolean result = user.getAttributeStream(attributeName).anyMatch(attr -> {
			log.info("Attr in stream : " + attr);
			log.info("value" + attributeValue);
			return Objects.equals(attr, attributeValue);
		});
		log.info(user.getEmail());
		log.info("RESUTL: " + result);
		return result;
	}

}
