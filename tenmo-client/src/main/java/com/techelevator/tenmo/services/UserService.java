package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class UserService {
    private final String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken;

    /**
     * Constructs a new UserService with the specified base URL.
     * @param BASE_URL the base URL of the user service
     */
    public UserService(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    /**
     * Retrieves the list of users from the user service.
     * @param currentUser the authenticated user
     * @return an array of Users representing the list of users
     */
    public User[] listOfUsers(AuthenticatedUser currentUser) {
        User[] users = null;
        authToken = currentUser.getToken();
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(
                    BASE_URL + "users", HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    /**
     * Retrieves a specific user from the user service.
     * @param currentUser the authenticated user
     * @param userId      the ID of the user to retrieve
     * @return a User object representing the requested user, or null if not found
     */
    public User getUser(AuthenticatedUser currentUser, int userId) {
        User user = null;
        authToken = currentUser.getToken();
        try {
            ResponseEntity<User> response = restTemplate.exchange(
                    BASE_URL + "users/" + userId, HttpMethod.GET, makeAuthEntity(), User.class);
            user = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return user;
    }

    /**
     * Creates an HTTP entity with authentication headers and no request body.
     * @return the HTTP entity with authentication headers
     */
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
