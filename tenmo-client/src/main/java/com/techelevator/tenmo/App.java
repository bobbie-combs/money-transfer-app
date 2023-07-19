package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;


public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private static AuthenticatedUser currentUser;
    private TenmoCLI tenmoCLI = new TenmoCLI();

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    /**
     * Runs the Tenmo application
     */
    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    /**
     * Displays the login menu and handles user selection
     */
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    /**
     * Handles the user registration process
     */
    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }



    /**
     * Handles the user login process
     */
    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);

        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            tenmoCLI.setCurrentUser(currentUser);
        }

    }

    /**
     * Displays the main menu and handles user selection
     */
    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                tenmoCLI.viewCurrentBalance();
            } else if (menuSelection == 2) {
                tenmoCLI.viewTransferHistory();
            } else if (menuSelection == 3) {
                tenmoCLI.viewPendingRequests();
            } else if (menuSelection == 4) {
                tenmoCLI.sendBucks();
            } else if (menuSelection == 5) {
                tenmoCLI.requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }
}
