package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.services.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TenmoCLI {
    private static final String API_BASE_URL = "http://localhost:8080/";
    private AuthenticatedUser currentUser;
    private final ConsoleService consoleService = new ConsoleService();
    private User user;
    private final String[] transferType = {"Request","Send"};
    private final String[] transferStatus = {"Pending", "Approved", "Rejected"};
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final UserService userService = new UserService(API_BASE_URL);
    private final TransferService transferService = new TransferService(API_BASE_URL);

    /**
     * Displays the current account balance for the logged-in user
     */
    protected void viewCurrentBalance() {
        System.out.println("Your current account balance is: $" + accountService.viewCurrentBalance(currentUser,currentUser.getUser().getId()).getBalance());
    }

    /**
     * Sets the current authenticated user
     * @param currUser the authenticated user
     */
    public void setCurrentUser (AuthenticatedUser currUser) {
        currentUser = currUser;
    }

    /**
     * Displays the transfer history for the logged-in user
     */
    protected void viewTransferHistory() {
        transferService.setAuthToken(currentUser);
        Account userAccount = accountService.getAccount(currentUser, currentUser.getUser());
        Account[] accounts = accountService.getAllAccounts(currentUser);
        User[] users = userService.listOfUsers(currentUser);
        List<Transfer> transfers = Arrays.asList(transferService.listOfTransfers(currentUser,userAccount.getAccountId()));
        boolean isTfFound = false;
        while(!isTfFound) {
            consoleService.printTransfers(transfers, userAccount.getAccountId(), accounts, users);
            int tfId = consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel): ");
            if(tfId == 0) {
                return;
            }
            for (Transfer tf : transfers) {
                if (tfId == tf.getTransferId()) {
                    consoleService.printTransferDetails(tf, accounts, users, transferType, transferStatus);
                    isTfFound = true;
                    break;
                }
            }
        }
    }

    /**
     * Displays the pending transfer requests for the logged-in user and allows approving or rejecting them
     */
    protected void viewPendingRequests() {
        transferService.setAuthToken(currentUser);
        Account userAccount = accountService.getAccount(currentUser, currentUser.getUser());
        Account[] accounts = accountService.getAllAccounts(currentUser);
        User[] users = userService.listOfUsers(currentUser);
        List<Transfer> transfers = Arrays.asList(transferService.listOfTransfers(currentUser,userAccount.getAccountId()));
        boolean isTfStatusUpdated  = false;
        while(!isTfStatusUpdated) {
            consoleService.printPendingTransfers(transfers, userAccount.getAccountId(), accounts, users);
            int tfId = consoleService.promptForInt("Please enter transfer ID to approve/reject (0 to cancel): ");
            if(tfId == 0) {
                return;
            }
            for (Transfer tf : transfers) {
                if (tfId == tf.getTransferId()) {
                    if(userAccount.getAccountId() != tf.getAccountTo()) {
                        boolean isChoiceSelected = false;
                        while (!isChoiceSelected) {
                            int transferStatusChoice = consoleService.printTransferChoice();
                            if (transferStatusChoice == 1) {
                                tf.setTransferStatusId(2);
                                if (transferService.updateTransfer(tf)) {
                                    System.out.println("Transfer successful!");
                                    break;
                                } else {
                                    System.out.println("Transfer rejected. Try again");
                                }
                            } else if (transferStatusChoice == 2) {
                                tf.setTransferStatusId(3);
                                if (transferService.updateTransfer(tf)) {
                                    System.out.println("Transfer successful!");
                                    break;
                                } else {
                                    System.out.println("Transfer rejected. Try again");
                                }
                            } else if (transferStatusChoice == 0) {
                                break;
                            } else {
                                System.out.println("Please choose either 0, 1, or 2");
                            }
                        }
                    } else {
                        System.out.println("You cannot update the status of a pending transfer you sent.");
                        break;
                    }
                }
            }
        }
    }

    /**
     * Sends TE Bucks from the logged-in user to another user
     */
    protected void sendBucks() {
        boolean isTransferSent = false;
        while (!isTransferSent) {
            List<User> users = new ArrayList<>(Arrays.asList(userService.listOfUsers(currentUser)));
            users.remove(currentUser.getUser());
            consoleService.printUsers(users);
            int recipientId = consoleService.promptForInt("Enter the ID of the user you want to send TE Bucks to: ");
            boolean isUserValid = false;
            while (!isUserValid) {
                for (User recipient : users) {
                    if (recipientId == recipient.getId()) {
                        user = recipient;
                        isUserValid = true;
                        break;
                    }
                }
                if(isUserValid) {
                    break;
                }
                consoleService.printUsers(users);
                recipientId = consoleService.promptForInt("Enter the ID of the user you want to send TE Bucks to: ");
            }
            user = userService.getUser(currentUser, recipientId);
            BigDecimal amount = consoleService.promptForBigDecimal("Enter the amount of TE Bucks to send: ");
            isUserValid = false;
            Transfer transfer = new Transfer();
            while (!isUserValid) {
                if (amount.doubleValue() > 0.00) {
                    isUserValid = true;
                    break;
                }
                amount = consoleService.promptForBigDecimal("Enter the amount of TE Bucks to send: ");
            }
            accountService.setAuthToken(currentUser);
            transferService.setAuthToken(currentUser);
            int acctFrom = accountService.viewCurrentBalance(currentUser, currentUser.getUser().getId()).getAccountId();
            int acctTo = accountService.viewCurrentBalance(currentUser, user.getId()).getAccountId();

            transfer = new Transfer(2, 2, acctFrom, acctTo, amount);

            if (transferService.sendTransfer(transfer)) {
                System.out.println("Transfer successful!");
                isTransferSent = true;
            } else {
                System.out.println("Transfer rejected. Please only transfer equal to or less than" +
                        " the amount in your account.");
            }
        }
    }

    /**
     * Requests TE Bucks from another user to the logged-in user
     */
    protected void requestBucks() {
        boolean isTransferReqSent = false;
        while(!isTransferReqSent) {
            List<User> users = new ArrayList<>(Arrays.asList(userService.listOfUsers(currentUser)));
            users.remove(currentUser.getUser());
            consoleService.printUsers(users);
            int recipientId = consoleService.promptForInt("Enter the ID of the user you want to request TE Bucks from: ");
            boolean isUserValid = false;
            while (!isUserValid) {
                for (User recipient : users) {
                    if (recipientId == recipient.getId()) {
                        user = recipient;
                        isUserValid = true;
                        break;
                    }
                }
                if(isUserValid = true) {
                    break;
                }
                consoleService.printUsers(users);
                recipientId = consoleService.promptForInt("Enter the ID of the user you want to request TE Bucks from: ");
            }
            user = userService.getUser(currentUser, recipientId);
            BigDecimal amount = consoleService.promptForBigDecimal("Enter the amount of TE Bucks to request: ");
            isUserValid = false;
            Transfer transfer = new Transfer();
            while (!isUserValid) {
                if (amount.doubleValue() > 0.00) {
                    isUserValid = true;
                    break;
                }
                amount = consoleService.promptForBigDecimal("Enter the amount of TE Bucks to request: ");
            }
            accountService.setAuthToken(currentUser);
            transferService.setAuthToken(currentUser);
            int acctTo = accountService.viewCurrentBalance(currentUser, currentUser.getUser().getId()).getAccountId();
            int acctFrom = accountService.viewCurrentBalance(currentUser, user.getId()).getAccountId();
            transfer = new Transfer(1, 1, acctFrom, acctTo, amount);

            if (transferService.requestTransfer(transfer)) {
                System.out.println("Transfer request successful!");
                isTransferReqSent = true;
            } else {
                System.out.println("Try again.");
            }
        }
    }
}
