package com.demo.Model;

public class Password {
	
	private String newPassword;

	public Password() {
		super();
	}

	public Password(String newPassword) {
		super();
		this.newPassword = newPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "Password [newPassword=" + newPassword + "]";
	}
	
	
}
