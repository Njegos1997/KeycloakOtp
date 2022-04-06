package com.mydomain.auth.provider.user;

import java.util.Arrays;
import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticator;
import org.keycloak.authentication.authenticators.conditional.ConditionalAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionalUserAttributeValueFactory implements ConditionalAuthenticatorFactory {
	
	private static final Logger log = LoggerFactory.getLogger(ConditionalUserAttributeValueFactory.class);
	public static final String PROVIDER_ID = "conditional-user-attribute";

    public static final String CONF_ATTRIBUTE_NAME = "need_validation";
    public static final String CONF_ATTRIBUTE_EXPECTED_VALUE = "true";
    
    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED, AuthenticationExecutionModel.Requirement.DISABLED
    };

	@Override
	public void init(Scope config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		return PROVIDER_ID;
	}

	@Override
	public String getDisplayType() {
		 return "Condition - user attribute";
	}

	@Override
	public String getReferenceCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConfigurable() {
		return true;
	}

	@Override
	public Requirement[] getRequirementChoices() {
		return REQUIREMENT_CHOICES;
	}

	@Override
	public boolean isUserSetupAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getHelpText() {
		// TODO Auto-generated method stub
		return "Flow is executed only if the user attribute exists and has the expected value";
	}

	@Override
	public List<ProviderConfigProperty> getConfigProperties() {
		  ProviderConfigProperty authNoteName = new ProviderConfigProperty();
	        authNoteName.setType(ProviderConfigProperty.STRING_TYPE);
	        authNoteName.setName(CONF_ATTRIBUTE_NAME);
	        authNoteName.setLabel("Attribute name");
	        authNoteName.setHelpText("Name of the attribute to check");

	        ProviderConfigProperty authNoteExpectedValue = new ProviderConfigProperty();
	        authNoteExpectedValue.setType(ProviderConfigProperty.STRING_TYPE);
	        authNoteExpectedValue.setName(CONF_ATTRIBUTE_EXPECTED_VALUE);
	        authNoteExpectedValue.setLabel("Expected attribute value");
	        authNoteExpectedValue.setHelpText("Expected value in the attribute");

	        return Arrays.asList(authNoteName, authNoteExpectedValue);
	}

	@Override
	public ConditionalAuthenticator getSingleton() {
		// TODO Auto-generated method stub
		return ConditionalUserAttributeValue.SINGLETON;
	}

}
