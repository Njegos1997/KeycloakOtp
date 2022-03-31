package com.mydomain.auth.provider.user;

import java.util.Arrays;
import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomAuthenticatorFactory implements AuthenticatorFactory {
	
	private static final Logger log = LoggerFactory.getLogger(CustomAuthenticatorFactory.class);

	@Override
	public Authenticator create(KeycloakSession session) {
		log.info("[I00] creating new CustomAuthenticator");
		return new CustomAuthenticator();
	}

	@Override
	public String getId() {
		log.info("[I900] getId()");
		return "custom-authenticator";
	}

	@Override
	public String getDisplayType() {
		return "Custom Authenticator";
	}

	@Override
	public String getReferenceCategory() {
		return "otp";
	}

	@Override
	public boolean isConfigurable() {
		return true;
	}

	@Override
	public Requirement[] getRequirementChoices() {
		return new AuthenticationExecutionModel.Requirement[] { AuthenticationExecutionModel.Requirement.REQUIRED};
	}

	@Override
	public boolean isUserSetupAllowed() {
		return false;
	}

	@Override
	public String getHelpText() {
		return "OTP validation";
	}

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		return Arrays.asList(
				new ProviderConfigProperty("length", "Code length", "The number of digits of the generated code.",
						ProviderConfigProperty.STRING_TYPE, 6),
				new ProviderConfigProperty("ttl", "Time-to-live",
						"The time to live in seconds for the code to be valid.", ProviderConfigProperty.STRING_TYPE,
						"300"));
	}
	
	@Override
	public void init(Scope config) {
	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {
	}

	@Override
	public void close() {
	}

}
