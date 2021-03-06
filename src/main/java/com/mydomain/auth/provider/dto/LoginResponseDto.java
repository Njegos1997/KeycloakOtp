package com.mydomain.auth.provider.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseDto {

  private String buildBranch;
  private String tokenId;
  private boolean success;
  private String redirectTo;
  private String redirectToNewUI;
  private String buildNumber;
  private boolean needValidation;

  public String getBuildNumber() {
    return buildNumber;
  }

  public void setBuildNumber(String buildNumber) {
    this.buildNumber = buildNumber;
  }

  public String getBuildBranch() {
    return buildBranch;
  }

  public void setBuildBranch(String buildBranch) {
    this.buildBranch = buildBranch;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getRedirectTo() {
    return redirectTo;
  }

  public void setRedirectTo(String redirectTo) {
    this.redirectTo = redirectTo;
  }

  public String getRedirectToNewUI() {
    return redirectToNewUI;
  }

  public void setRedirectToNewUI(String redirectToNewUI) {
    this.redirectToNewUI = redirectToNewUI;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

public boolean isNeedValidation() {
	return needValidation;
}

public void setNeedValidation(boolean needValidation) {
	this.needValidation = needValidation;
}

}
