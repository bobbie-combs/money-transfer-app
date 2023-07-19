/**
 The AuthenticatedUser class represents an authenticated user in the Tenmo application.
 It contains the user's authentication token and user information.
 */
package com.techelevator.tenmo.model;
public class AuthenticatedUser {

	private String token; // The authentication token of the user
	private User user; // The user information

	/**
	 * Retrieves the authentication token of the user.
	 * @return The authentication token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the authentication token of the user.
	 * @param token The authentication token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Retrieves the user information.
	 * @return The user information
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user information.
	 * @param user The user information to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}