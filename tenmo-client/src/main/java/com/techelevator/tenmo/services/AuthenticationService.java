package com.techelevator.tenmo.services;

import com.techelevator.util.BasicLogger;
import com.techelevator.util.BasicLoggerException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.UserCredentials;

public class AuthenticationService {

    private final String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Creates an instance of the AuthenticationService class.
     * @param url the base URL for the API
     */
    public AuthenticationService(String url) {
        this.BASE_URL = url;
    }

    /**
     * Logs in a user with the provided credentials.
     * @param credentials the user credentials
     * @return an AuthenticatedUser object representing the authenticated user
     */
    public AuthenticatedUser login(UserCredentials credentials) {
        HttpEntity<UserCredentials> entity = createCredentialsEntity(credentials);
        AuthenticatedUser user = null;
        try {
            ResponseEntity<AuthenticatedUser> response =
                    restTemplate.exchange(BASE_URL + "login", HttpMethod.POST, entity, AuthenticatedUser.class);
            user = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return user;
    }

    /**
     * Registers a new user with the provided credentials.
     * @param credentials the user credentials
     * @return true if the registration was successful, false otherwise
     */
    public boolean register(UserCredentials credentials) {
        HttpEntity<UserCredentials> entity = createCredentialsEntity(credentials);
        boolean success = false;
        try {
            restTemplate.exchange(BASE_URL + "register", HttpMethod.POST, entity, Void.class);
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    /**
     * Creates an HttpEntity with the user credentials and headers.
     * @param credentials the user credentials
     * @return an HttpEntity object containing the credentials and headers
     */
    private HttpEntity<UserCredentials> createCredentialsEntity(UserCredentials credentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(credentials, headers);
    }
}
