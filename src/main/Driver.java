package main;

import controllers.AppStoreAPI;
import controllers.DeveloperAPI;
import models.*;
import utils.ScannerInput;
import utils.Utilities;

public class Driver {

    //TODO Some skeleton code has been given to you.
    //     Familiarise yourself with the skeleton code...run the menu and then review the skeleton code.
    //     Then start working through the spec.

    private DeveloperAPI developerAPI = new DeveloperAPI();
    private AppStoreAPI appStoreAPI = new AppStoreAPI();

    public static void main(String[] args) {
        new Driver().start();
    }

    public void start() {
        loadAllData();
        runMainMenu();
    }

    private int mainMenu() {
        System.out.println("""
                 -------------App Store------------
                |  1) Developer - Management MENU  |
                |  2) App - Management MENU        |
                |  3) Reports MENU                 |
                |----------------------------------|
                |  4) Search                       |
                |  5) Sort                         |
                |----------------------------------|
                |  6) Recommended Apps             |
                |  7) Random App of the Day        |
                |  8) Simulate ratings             |
                |----------------------------------|
                |  20) Save all                    |
                |  21) Load all                    |
                |----------------------------------|
                |  0) Exit                         |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runDeveloperMenu();
                case 2 -> runAppMenu(); // TODO run the App Store Menu and the associated methods (your design here)
                case 3 -> runReport(); // TODO run the Reports Menu and the associated methods (your design here)
                case 4 -> searchAppsBySpecificCriteria();
                case 5 -> sortAppsByName(); // TODO Sort Apps by Name
                case 6 -> printRecommendedApps(); // TODO print the recommended apps
                case 7 -> printRandomApp(); // TODO print the random app of the day
                case 8 -> simulateRatings();
                case 20 -> saveAllData();
                case 21 -> loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }

    private void exitApp() {
        saveAllData();
        System.out.println("Exiting....");
        System.exit(0);
    }

    //--------------------------------------------------
    //  Developer Management - Menu Items
    //--------------------------------------------------
    private int developerMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Add a developer       |
                |   2) List developer        |
                |   3) Update developer      |
                |   4) Delete developer      |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private int appMenu() {
        System.out.println("""
                 -------App Store Menu-------
                |   1) Add an app            |
                |   2) Update app            |
                |   3) Delete app            |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private int reportMenu() {
        System.out.println("""
                 ---------Reports Menu-------
                |   1) Apps Overview         |
                |   2) Developers Overview   |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void sortAppsByName() {
        appStoreAPI.sortAppsByNameAscending();
        System.out.println("After sorting by namg: ");
        appStoreAPI.listAllApps();
    }

    private void printRecommendedApps() {
        appStoreAPI.listAllRecommendedApps();
    }

    private void printRandomApp() {
        App ranApp = appStoreAPI.randomApp();
        System.out.println(ranApp);
    }

    private void runDeveloperMenu() {
        int option = developerMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addDeveloper();
                case 2 -> System.out.println(developerAPI.listDevelopers());
                case 3 -> updateDeveloper();
                case 4 -> deleteDeveloper();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerMenu();
        }
    }

    private void runAppMenu() {
        int option = appMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addApp();
                case 2 -> updateApp();
                case 4 -> deleteApp();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = appMenu();
        }
    }

    private void runReport() {
        int option = reportMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> appView();
                case 2 -> developerView();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = reportMenu();
        }
    }

    private void appView() {
        System.out.println(String.format("%d app(s): ", appStoreAPI.numberOfApps()));
        System.out.println(appStoreAPI.listAllApps());
    }

    private void developerView() {
        System.out.println(developerAPI.listDevelopers());
    }

    private void addDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");

        if (developerAPI.addDeveloper(new Developer(developerName, developerWebsite))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void addApp() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String appName = ScannerInput.validNextLine("Please enter the appName: ");
        double appSize = ScannerInput.validNextDouble("Please enter the appSize: ");
        double appVersion = ScannerInput.validNextDouble("Please enter the appVersion: ");
        double appCost = ScannerInput.validNextDouble("Please enter the appCost: ");
        int type = ScannerInput.validNextInt("Please enter the app type(1:Edu, 2:Pro, 3:Game): ");

        App app = null;
        if (type == 1) {
            int level = ScannerInput.validNextInt("Please enter the level: ");
            app = new EducationApp(developerAPI.getDeveloperByName(developerName), appName,
                    appSize, appVersion, appCost, level);
        } else if (type == 2) {
            app = new ProductivityApp(developerAPI.getDeveloperByName(developerName), appName,
                    appSize, appVersion, appCost);
        } else if (type == 3) {
            char status = ScannerInput.validNextChar("Enter isMultiplayer (y/n): ");
            boolean isMultiplayer = Utilities.YNtoBoolean(status);
            app = new GameApp(developerAPI.getDeveloperByName(developerName), appName,
                    appSize, appVersion, appCost, isMultiplayer);
        }

        if (appStoreAPI.addApp(app)) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void updateDeveloper() {
        System.out.println(developerAPI.listDevelopers());
        Developer developer = readValidDeveloperByName();
        if (developer != null) {
            String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
            if (developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite))
                System.out.println("Developer Website Updated");
            else
                System.out.println("Developer Website NOT Updated");
        } else
            System.out.println("Developer name is NOT valid");
    }

    private void updateApp() {
        System.out.println(appStoreAPI.listAllApps());
        App app = readValidAppByName();
        if (app != null) {
            double appSize = ScannerInput.validNextDouble("Please enter the appSize: ");
            double appVersion = ScannerInput.validNextDouble("Please enter the appVersion: ");
            double appCost = ScannerInput.validNextDouble("Please enter the appCost: ");

            app.setAppSize(appSize);
            app.setAppVersion(appVersion);
            app.setAppCost(appCost);
            System.out.println("App updated");
        } else
            System.out.println("App name is NOT valid");
    }

    private void deleteDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        if (developerAPI.removeDeveloper(developerName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private void deleteApp() {
        appStoreAPI.listAllApps();
        if (appStoreAPI.numberOfApps() > 0){
            int indexToDelete = ScannerInput.validNextInt("Enter the index of the App to delete ==> ");
            App appToDelete = appStoreAPI.deleteAppByIndex(indexToDelete);
            if (appToDelete != null){
                System.out.println("Delete Successful!");
            } else {
                System.out.println("Delete NOT Successful");
            }
        }
    }

    private Developer readValidDeveloperByName() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            return developerAPI.getDeveloperByName(developerName);
        } else {
            return null;
        }
    }

    private App readValidAppByName() {
        String appName = ScannerInput.validNextLine("Please enter the app's name: ");
        if (appStoreAPI.isValidAppName(appName)) {
            return appStoreAPI.getAppByName(appName);
        } else {
            return null;
        }
    }



    //--------------------------------------------------
    // TODO UNCOMMENT THIS CODE as you start working through this class
    //--------------------------------------------------
    private void searchAppsBySpecificCriteria() {
        System.out.println("""
                What criteria would you like to search apps by:
                  1) App Name
                  2) Developer Name
                  3) Rating (all apps of that rating or above)""");
        int option = ScannerInput.validNextInt("==>> ");
        switch (option) {
            // TODO Search methods below
             case 1 -> searchAppsByName();
             case 2 -> searchAppsByDeveloper(readValidDeveloperByName());
             case 3 -> searchAppsEqualOrAboveAStarRating();
             default -> System.out.println("Invalid option");
        }
    }

    private void searchAppsByName() {
        String appName = ScannerInput.validNextLine("Please enter the app's name: ");
        System.out.println(appStoreAPI.listAllAppsByName(appName));
    }

    private void searchAppsByDeveloper(Developer developer) {
        if (developer == null) {
            System.out.println("Has no this developer");
            return;
        }

        System.out.println(appStoreAPI.listAllAppsByChosenDeveloper(developer));
    }

    private void searchAppsEqualOrAboveAStarRating() {
        int rating = ScannerInput.validNextInt("Please enter the rating: ");
        System.out.println(appStoreAPI.listAllAppsAboveOrEqualAGivenStarRating(rating));
    }

    //--------------------------------------------------
    // TODO UNCOMMENT THIS COMPLETED CODE as you start working through this class
    //--------------------------------------------------
    private void simulateRatings() {
        // simulate random ratings for all apps (to give data for recommended apps and reports etc).
        if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("Simulating ratings...");
            appStoreAPI.simulateRatings();
            System.out.println(appStoreAPI.listSummaryOfAllApps());
        } else {
            System.out.println("No apps");
        }
    }

    //--------------------------------------------------
    //  Persistence Menu Items
    //--------------------------------------------------

    private void saveAllData() {
        // TODO try-catch to save the developers to XML file
        // TODO try-catch to save the apps in the store to XML file
        try {
            developerAPI.save();
            appStoreAPI.save();
        } catch (Exception e) {
            //
        }
    }

    private void loadAllData() {
        // TODO try-catch to load the developers from XML file
        // TODO try-catch to load the apps in the store from XML file
        try {
            developerAPI.load();
            appStoreAPI.load();
        } catch (Exception e) {
            //
        }
    }

}
