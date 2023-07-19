package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public class TransferService {

    private final String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken;

    /**
     * Constructs a TransferService object with the specified base URL.
     * @param BASE_URL the base URL of the RESTful API
     */
    public TransferService(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    /**
     * Sends a transfer by making a POST request to the API.
     * @param transfer the transfer object to be sent
     * @return true if the transfer was sent successfully, false otherwise
     */
    public boolean sendTransfer(Transfer transfer) {
        Transfer newTransfer = null;
        try {
            newTransfer = restTemplate.postForObject(
                    BASE_URL + "transfers",
                    makeTransferEntity(transfer),
                    Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        if (newTransfer.getTransferStatusId() == 3) {
            return false;
        }
        return true;
    }

    /**
     * Requests a transfer by making a POST request to the API.
     * @param transfer the transfer object to be requested
     * @return true if the transfer was requested successfully, false otherwise
     */
    public boolean requestTransfer(Transfer transfer) {
        Transfer newTransfer = null;
        try {
            newTransfer = restTemplate.postForObject(
                    BASE_URL + "transfers/requests",
                    makeTransferEntity(transfer),
                    Transfer.class);
            return true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            return false;
        }
    }

    /**
     * Updates a transfer by making a PUT request to the API.
     * @param transfer the transfer object to be updated
     * @return true if the transfer was updated successfully, false otherwise
     */
    public boolean updateTransfer(Transfer transfer) {
        try {
            restTemplate.put(BASE_URL + "transfers/updates", makeTransferEntity(transfer), Transfer.class);
            return true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a list of transfers for the specified authenticated user and account ID
     * by making a GET request to the API.
     * @param user      the authenticated user
     * @param accountId the ID of the account
     * @return an array of Transfer objects representing the transfers
     */
    public Transfer[] listOfTransfers(AuthenticatedUser user, int accountId) {
        Transfer[] transfers = null;
        try {
            HttpEntity<Transfer[]> response = restTemplate.exchange(
                    BASE_URL + "transfers/accounts/" + accountId,
                    HttpMethod.GET, makeAuthEntity(),
                    Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    /**
     * Sets the authentication token for the current user.
     * @param currentUser the authenticated user
     */
    public void setAuthToken(AuthenticatedUser currentUser) {
        authToken = currentUser.getToken();
    }

    /**
     * Creates an HTTP entity with the transfer object and authentication headers.
     * @param transfer the transfer object
     * @return the HTTP entity with headers
     */
    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(authToken);
            return new HttpEntity<>(transfer, headers);
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