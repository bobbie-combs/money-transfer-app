package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user for a menu selection.
     * @param prompt the prompt to display to the user
     * @return the menu selection as an integer
     */
    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    /**
     * Prints the greeting message.
     */
    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    /**
     * Prints the login menu options.
     */
    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    /**
     * Prints the main menu options.
     */
    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    /**
     * Prompts the user for login credentials.
     * @return a UserCredentials object with the entered username and password
     */
    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    /**
     * Prompts the user for a string input.
     * @param prompt the prompt to display to the user
     * @return the entered string
     */
    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Prompts the user for an integer input.
     * @param prompt the prompt to display to the user
     * @return the entered integer
     */
    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    /**
     * Prints a list of users.
     * @param users the list of users to print
     */
    public void printUsers(List<User> users) {
        System.out.println("*********************************************");
        System.out.println("Users");
        System.out.printf("%-30s %s %n", "ID", "Name");
        System.out.println("*********************************************");
        for (User user : users) {
            System.out.printf("%-30d %s %n", user.getId(), user.getUsername());
        }
        System.out.println("********");
    }

    /**
     * Prints the details of a transfer.
     * @param transfer the transfer object
     * @param accounts the array of accounts
     * @param users    the array of users
     * @param type     the array of transfer types
     * @param status   the array of transfer statuses
     */
    public void printTransferDetails(Transfer transfer, Account[] accounts, User[] users, String[] type, String[] status) {
        boolean isAccountFound = false;
        int count = 0;
        System.out.println("*********************************************");
        System.out.println("Transfer Details");
        System.out.printf("%s: %d %n", "Id", transfer.getTransferId());
        for (Account account : accounts) {
            if (account.getAccountId() == transfer.getAccountFrom()) {
                for (User user : users) {
                    if (user.getId() == account.getUserId()) {
                        String accountFrom = user.getUsername();
                        System.out.printf("%s: %s %n", "From", accountFrom);
                        count++;
                        break;
                    }
                }
            }

            if (account.getAccountId() == transfer.getAccountTo())
                for (User user : users) {
                    if (user.getId() == account.getUserId()) {
                        String accountTo = user.getUsername();
                        System.out.printf("%s: %s %n", "To", accountTo);
                        count++;
                        break;
                    }
                }
            if (count == 2) {
                break;
            }
        }
        System.out.printf("%s: %s %n", "Type", type[transfer.getTransferTypeId() - 1]);
        System.out.printf("%s: %s %n", "Status", status[transfer.getTransferStatusId() - 1]);
        System.out.printf("%s: $%.2f %n", "Amount", transfer.getAmount().doubleValue());
    }

    /**
     * Prints a list of transfers.
     * @param transfers the list of transfers
     * @param accountId the account ID
     * @param accounts  the array of accounts
     * @param users     the array of users
     */
    public void printTransfers(List<Transfer> transfers, int accountId, Account[] accounts, User[] users) {
        Transfer tr = null;
        System.out.println("***********************************************************************************");
        System.out.println("Transfers");
        System.out.printf("%-20s %-30s %s %n", "ID", "From/To", "Amount");
        System.out.println("***********************************************************************************");
        for (Transfer transfer : transfers) {
            for (Account account : accounts) {
                if (accountId != transfer.getAccountFrom()) {
                    if (transfer.getAccountFrom() == account.getAccountId()) {
                        for (User user : users) {
                            if (user.getId() == account.getUserId()) {
                                System.out.printf("%-20d From: %-30s $%10.2f %n", transfer.getTransferId(),
                                        user.getUsername(), transfer.getAmount().doubleValue());
                                break;
                            }
                        }
                    }
                } else {
                    if (transfer.getAccountTo() == account.getAccountId()) {
                        for (User user : users) {
                            if (user.getId() == account.getUserId()) {
                                System.out.printf("%-20d To:   %-30s $%10.2f %n", transfer.getTransferId(),
                                        user.getUsername(), transfer.getAmount().doubleValue());
                                break;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("********");
    }

    /**
     * Prompts the user for a BigDecimal input.
     * @param prompt the prompt to display to the user
     * @return the entered BigDecimal
     */
    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }
    /**
     * Pauses the program and waits for the user to press Enter.
     */
    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Prints an error message.
     */
    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    /**
     * Prints a list of pending transfers.
     * @param transfers  the list of transfers
     * @param accountId  the account ID
     * @param accounts   the array of accounts
     * @param users      the array of users
     */
    public void printPendingTransfers(List<Transfer> transfers, int accountId, Account[] accounts, User[] users) {
        System.out.println("***********************************************************************************");
        System.out.println("Transfers");
        System.out.printf("%-20s %-30s %s %n", "ID", "To", "Amount");
        System.out.println("***********************************************************************************");
        for (Transfer transfer : transfers) {
            if (transfer.getTransferStatusId() == 1) {
                for (Account account : accounts) {
                    if (transfer.getAccountTo() == account.getAccountId()) {
                        for (User user : users) {
                            if (user.getId() == account.getUserId()) {
                                System.out.printf("%-20d To:   %-28s $%10.2f %n", transfer.getTransferId(),
                                        user.getUsername(), transfer.getAmount().doubleValue());
                                break;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("********");
    }

    /**
     * Prints the transfer choices and prompts the user to choose an option.
     * @return the selected transfer status option
     */
    public int printTransferChoice() {
        System.out.println("1: Approve");
        System.out.println("2: Reject");
        System.out.println("0: Don't approve or reject");
        System.out.println("---------");
        int transferStatusOption = promptForInt("Please choose an option:");
        return transferStatusOption;
    }
}
