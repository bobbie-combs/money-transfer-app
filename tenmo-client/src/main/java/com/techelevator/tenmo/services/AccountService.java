package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class AccountService {
    private final String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken;

    /**
     * Creates an instance of the AccountService class.
     * @param BASE_URL the base URL for the API
     */
    public AccountService(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    /**
     * Retrieves the current balance for a user.
     * @param currentUser the authenticated user
     * @param userId      the ID of the user
     * @return an Account object representing the user's account
     */
    public Account viewCurrentBalance(AuthenticatedUser currentUser, int userId) {
        Account accounts = null;
        authToken = currentUser.getToken();
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(BASE_URL + "accounts/findByUserId/" + userId, HttpMethod.GET, makeAuthEntity(), Account.class);
            accounts = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }

    /**
     * Retrieves the account details for a specific user.
     * @param currentUser the authenticated user
     * @param user        the user for which to retrieve the account details
     * @return an Account object representing the user's account
     */
    public Account getAccount(AuthenticatedUser currentUser, User user) {
        Account accounts = null;
        authToken = currentUser.getToken();
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(BASE_URL + "accounts/findByUserId/" + user.getId(), HttpMethod.GET, makeAuthEntity(), Account.class);
            accounts = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }

    /**
     * Retrieves all accounts.
     * @param currentUser the authenticated user
     * @return an array of Account objects representing all accounts
     */
    public Account[] getAllAccounts(AuthenticatedUser currentUser) {
        Account[] accounts = null;
        authToken = currentUser.getToken();
        try {
            ResponseEntity<Account[]> response =
                    restTemplate.exchange(BASE_URL + "accounts", HttpMethod.GET, makeAuthEntity(), Account[].class);
            accounts = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }

    /**
     * Sets the authentication token for the current user.
     * @param currentUser the authenticated user
     */
    public void setAuthToken(AuthenticatedUser currentUser) {
        authToken = currentUser.getToken();
        System.out.println("good");
    }
/**
 Creates an HttpEntity with empty body and authentication headers.
 This method is used for making authenticated requests that do not require a request body.
 @return an HttpEntity object with empty body and authentication headers
 */
private HttpEntity<Void> makeAuthEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(authToken);
    return new HttpEntity<>(headers);
}



}
