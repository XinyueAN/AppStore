import controllers.AppStoreAPI;
import models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class AppStoreAPITest {

    private EducationApp edAppBelowBoundary, edAppOnBoundary, edAppAboveBoundary, edAppInvalidData;
    private ProductivityApp prodAppBelowBoundary, prodAppOnBoundary, prodAppAboveBoundary, prodAppInvalidData;
    private GameApp gameAppBelowBoundary, gameAppOnBoundary, gameAppAboveBoundary, gameAppInvalidData;

    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");
    private Developer developerEAGames = new Developer("EA Games", "www.eagames.com");
    private Developer developerKoolGames = new Developer("Kool Games", "www.koolgames.com");
    private Developer developerApple = new Developer("Apple", "www.apple.com");
    private Developer developerMicrosoft = new Developer("Microsoft", "www.microsoft.com");

    private AppStoreAPI appStore = new AppStoreAPI();
    private AppStoreAPI emptyAppStore = new AppStoreAPI();

    @BeforeEach
    void setUp() {

        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        edAppBelowBoundary = new EducationApp(developerLego, "WeDo", 1, 1.0, 0,  1);

        edAppOnBoundary = new EducationApp(developerLego, "Spike", 1000, 2.0,
                1.99, 10);

        edAppAboveBoundary = new EducationApp(developerLego, "EV3", 1001, 3.5,  2.99,  11);

        edAppInvalidData = new EducationApp(developerLego, "", -1, 0, -1.00,  0);


        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0),
        prodAppBelowBoundary = new ProductivityApp(developerApple, "NoteKeeper", 1, 1.0, 0.0);

        prodAppOnBoundary = new ProductivityApp(developerMicrosoft, "Outlook", 1000, 2.0, 1.99);

        prodAppAboveBoundary = new ProductivityApp(developerApple, "Pages", 1001, 3.5, 2.99);

        prodAppInvalidData = new ProductivityApp(developerMicrosoft, "", -1, 0, -1.00);


        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0),
        gameAppBelowBoundary = new GameApp(developerEAGames, "Tetris", 1, 1.0, 0.0,  false);

        gameAppOnBoundary = new GameApp(developerKoolGames, "CookOff", 1000, 2.0, 1.99,  true);

        gameAppAboveBoundary = new GameApp(developerEAGames, "Empires", 1001, 3.5,  2.99, false);

        gameAppInvalidData = new GameApp(developerKoolGames, "", -1, 0,  -1.00,  true);

        //not included - edAppOnBoundary, edAppInvalidData, prodAppBelowBoundary, gameAppBelowBoundary, gameAppInvalidData.
        appStore.addApp(edAppBelowBoundary);
        appStore.addApp(prodAppOnBoundary);
        appStore.addApp(gameAppAboveBoundary);
        appStore.addApp(prodAppBelowBoundary);
        appStore.addApp(edAppAboveBoundary);
        appStore.addApp(prodAppInvalidData);
        appStore.addApp(gameAppOnBoundary);
    }

    @AfterEach
    void tearDown() {
        edAppBelowBoundary = edAppOnBoundary = edAppAboveBoundary = edAppInvalidData = null;
        gameAppBelowBoundary = gameAppOnBoundary = gameAppAboveBoundary = gameAppInvalidData = null;
        prodAppBelowBoundary = prodAppOnBoundary = prodAppAboveBoundary = prodAppInvalidData = null;
        developerApple = developerEAGames = developerKoolGames = developerLego = developerMicrosoft = null;
        appStore = emptyAppStore = null;
    }

    @Nested
    class GettersAndSetters {
        @Test
        void getApps() {
            assertEquals(developerLego, edAppBelowBoundary.getDeveloper());
            assertEquals(developerLego, edAppOnBoundary.getDeveloper());
            assertEquals(developerLego, edAppAboveBoundary.getDeveloper());
            assertEquals(developerLego, edAppInvalidData.getDeveloper());

            assertEquals(developerApple, prodAppBelowBoundary.getDeveloper());
            assertEquals(developerMicrosoft, prodAppOnBoundary.getDeveloper());
            assertEquals(developerApple, prodAppAboveBoundary.getDeveloper());
            assertEquals(developerMicrosoft, prodAppInvalidData.getDeveloper());

            assertEquals(developerEAGames, gameAppBelowBoundary.getDeveloper());
            assertEquals(developerKoolGames, gameAppOnBoundary.getDeveloper());
            assertEquals(developerEAGames, gameAppAboveBoundary.getDeveloper());
            assertEquals(developerKoolGames, gameAppInvalidData.getDeveloper());
        }

        @Test
        void getAppName() {
            assertEquals("WeDo", edAppBelowBoundary.getAppName());
            assertEquals("Spike", edAppOnBoundary.getAppName());
            assertEquals("EV3", edAppAboveBoundary.getAppName());
            assertEquals("", edAppInvalidData.getAppName());

            assertEquals("NoteKeeper", prodAppBelowBoundary.getAppName());
            assertEquals("Outlook", prodAppOnBoundary.getAppName());
            assertEquals("Pages", prodAppAboveBoundary.getAppName());
            assertEquals("", prodAppInvalidData.getAppName());

            assertEquals("Tetris", gameAppBelowBoundary.getAppName());
            assertEquals("CookOff", gameAppOnBoundary.getAppName());
            assertEquals("Empires", gameAppAboveBoundary.getAppName());
            assertEquals("", gameAppInvalidData.getAppName());
        }

        @Test
        void getAppSize() {
            assertEquals(1, edAppBelowBoundary.getAppSize());
            assertEquals(1000, edAppOnBoundary.getAppSize());
            assertEquals(0, edAppAboveBoundary.getAppSize());
            assertEquals(0, edAppInvalidData.getAppSize());
        }

        @Test
        void getAppVersion() {
            assertEquals(1.0, edAppBelowBoundary.getAppVersion());
            assertEquals(2.0, edAppOnBoundary.getAppVersion());
            assertEquals(3.5, edAppAboveBoundary.getAppVersion());
            assertEquals(1.0, edAppInvalidData.getAppVersion());
        }

        @Test
        void getAppCost() {
            assertEquals(0, edAppBelowBoundary.getAppCost());
            assertEquals(1.99, edAppOnBoundary.getAppCost());
            assertEquals(2.99, edAppAboveBoundary.getAppCost());
            assertEquals(0, edAppInvalidData.getAppCost());
        }

        @Test
        void getLevel() {
            assertEquals(1, edAppBelowBoundary.getLevel());
            assertEquals(10, edAppOnBoundary.getLevel());
            assertEquals(0, edAppAboveBoundary.getLevel());
            assertEquals(0, edAppInvalidData.getLevel());
        }
    }

    @Nested
    class CRUDMethods {
        @Test
        void addApp() {
            App app = setupProductivityAppWithRating(2,4); // index 12
            assertEquals(7, appStore.numberOfApps());
            assertTrue(appStore.addApp(app));
            assertEquals(8, appStore.numberOfApps());
        }

        @Test
        void getAppByName() {
            App app = setupEducationAppWithRating(2,4); // index 12
            appStore.addApp(app);
            assertNull(appStore.getAppByName("test"));
            assertEquals("WeDo", appStore.getAppByName("WeDo").getAppName());
            assertEquals("WeDo", appStore.getAppByName("wedo").getAppName());
        }

        @Test
        void getAppByIndex() {
            assertNull(appStore.getAppByIndex(-1));
            assertNull(appStore.getAppByIndex(100));
            assertEquals(edAppBelowBoundary,appStore.getAppByIndex(0));
            assertEquals(prodAppInvalidData,appStore.getAppByIndex(5));
        }

        @Test
        void removingAnAppThatDoesNotExistReturnsNull() {
            assertNull(appStore.deleteAppByIndex(100));
            assertNull(appStore.deleteAppByIndex(-1));
        }

        @Test
        void deleteAppByIndex() {
            App app = setupGameAppWithRating(2,4); // index 12
            appStore.addApp(app);
            assertEquals(8, appStore.numberOfApps());
            assertEquals(app, appStore.deleteAppByIndex(7));
            assertEquals(7, appStore.numberOfApps());
        }
    }

    @Nested
    class ListingMethods {

        @Test
        void listAllAppsReturnsNoAppssStoredWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllApps().toLowerCase().contains("no apps"));
        }

        @Test
        void listAllAppsReturnsAppsStoredWhenArrayListHasAppsStored() {
            assertEquals(7, appStore.numberOfApps());
            String apps = appStore.listAllApps();
            //checks for objects in the string
            assertTrue(apps.contains("WeDo"));
            assertTrue(apps.contains("Outlook"));
            assertTrue(apps.contains("Empires"));
            assertTrue(apps.contains("NoteKeeper"));
            assertTrue(apps.contains("EV3"));
            assertTrue(apps.contains("CookOff"));
        }

        @Test
        void listRecommendedAppsReturnsNoAppsWhenRecommendedAppsDoNotExist() {
            assertEquals(7, appStore.numberOfApps());

            String apps = appStore.listAllRecommendedApps();
            //checks for the three objects in the string
            assertTrue(apps.contains("No recommended apps"));
        }

        @Test
        void listRecommendedAppsReturnsRecommendedAppsWhenTheyExist() {
            assertEquals(7, appStore.numberOfApps());

            //adding recommended apps to the list
            appStore.addApp(setupGameAppWithRating(5,4));
            appStore.addApp(setupEducationAppWithRating(3,4));
            appStore.addApp(setupProductivityAppWithRating(3,4));
            assertEquals(10, appStore.numberOfApps());

            String apps = appStore.listAllRecommendedApps();
            System.out.println(apps);
            //checks for the three objects in the string
            assertFalse(apps.contains("MazeRunner"));
            assertTrue(apps.contains("Evernote"));
            assertTrue(apps.contains("WeDo"));
        }

        @Test
        void listSummaryOfAllApps1() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listSummaryOfAllApps().toLowerCase().contains("no apps"));
        }

        @Test
        void listSummaryOfAllApps2() {
            assertEquals(7, appStore.numberOfApps());
            String summary = appStore.listSummaryOfAllApps();
            assertTrue(summary.contains("Outlook"));
        }

        @Test
        void listAllGameApps() {
            assertEquals(7, appStore.numberOfApps());
            String summary = appStore.listAllGameApps();
            assertTrue(summary.contains("Empires"));
            assertTrue(summary.contains("CookOff"));
            assertFalse(summary.contains("Tetris"));
        }

        @Test
        void listAllGameAppsReturnsNoGameAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllGameApps().toLowerCase().contains("no game apps"));
        }

        @Test
        void listAllEducationApps() {
            assertEquals(7, appStore.numberOfApps());
            String summary = appStore.listAllEducationApps();
            assertTrue(summary.contains("WeDo"));
            assertTrue(summary.contains("EV3"));
            assertFalse(summary.contains("Spike"));
        }

        @Test
        void listAllProductivityAppsReturnsNoProductivityAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllProductivityApps().toLowerCase().contains("no productivity apps"));
        }

        @Test
        void listAllProductivityApps() {
            assertEquals(7, appStore.numberOfApps());
            String summary = appStore.listAllProductivityApps();
            assertTrue(summary.contains("Outlook"));
            assertTrue(summary.contains("NoteKeeper"));
            assertTrue(summary.contains(""));
            assertFalse(summary.contains("MazeRunner"));
        }

    }

    @Nested
    class ReportingMethods {

    }

    @Nested
    class SearchingMethods {
        @Test
        void listAllAppsByNameWhenArrayListHasAppsStored() {
            assertEquals(7, appStore.numberOfApps());
            String list = appStore.listAllAppsByName("Outlook");
            assertTrue(list.contains("Outlook"));
            assertTrue(list.contains("2.0"));
            assertTrue(list.contains("1.99"));
        }

        @Test
        void listAllAppsAboveOrEqualAGivenStarRating() {
            assertEquals(7, appStore.numberOfApps());
            assertTrue(appStore.listAllAppsAboveOrEqualAGivenStarRating(5).toLowerCase().contains("no apps"));
            assertTrue(appStore.listAllAppsAboveOrEqualAGivenStarRating(2).toLowerCase().contains("no apps"));
            appStore.addApp(setupProductivityAppWithRating(2,3));
            String list = appStore.listAllAppsAboveOrEqualAGivenStarRating(2);
            assertTrue(list.contains("Evernote"));
        }

        @Test
        void listAllAppsByChosenDeveloper() {
            assertTrue(appStore.listAllAppsByChosenDeveloper(developerSphero).toLowerCase().contains("no apps for developer"));
        }

        @Test
        void listAllAppsByChosenDeveloperReturnsNoAppsWhenArrayListIsEmpty() {
            assertEquals(0, emptyAppStore.numberOfApps());
            assertTrue(emptyAppStore.listAllAppsByChosenDeveloper(developerSphero).toLowerCase().contains("no apps for developer"));
        }

        @Test
        void listAllAppsByChosenDeveloperReturnsAppsMatchingDeveloperWhenArrayListHasAppsStored() {
            assertEquals(7, appStore.numberOfApps());
            String list = appStore.listAllAppsByChosenDeveloper(developerLego);
            assertTrue(list.contains("WeDo"));
            assertTrue(list.contains("EV3"));
            assertFalse(list.contains("CookOff"));
        }

        @Test
        void numberOfAppsByChosenDeveloper() {
            assertEquals(7, appStore.numberOfApps());
            assertEquals(2, appStore.numberOfAppsByChosenDeveloper(developerLego));
            assertEquals(0, emptyAppStore.numberOfAppsByChosenDeveloper(developerLego));
            assertEquals(0, appStore.numberOfAppsByChosenDeveloper(developerSphero));
        }
    }

    @Nested
    class SortingMethods {

        @Test
        void sortByNameAscendingReOrdersList() {
            assertEquals(7, appStore.numberOfApps());
            //checks the order of the objects in the list
            assertEquals(edAppBelowBoundary, appStore.getAppByIndex(0));
            assertEquals(prodAppOnBoundary, appStore.getAppByIndex(1));
            assertEquals(gameAppAboveBoundary, appStore.getAppByIndex(2));
            assertEquals(prodAppBelowBoundary, appStore.getAppByIndex(3));
            assertEquals(edAppAboveBoundary, appStore.getAppByIndex(4));
            assertEquals(prodAppInvalidData, appStore.getAppByIndex(5));
            assertEquals(gameAppOnBoundary, appStore.getAppByIndex(6));

            appStore.sortAppsByNameAscending();
            assertEquals(prodAppInvalidData, appStore.getAppByIndex(0));
            assertEquals(gameAppOnBoundary, appStore.getAppByIndex(1));
            assertEquals(edAppAboveBoundary, appStore.getAppByIndex(2));
            assertEquals(gameAppAboveBoundary, appStore.getAppByIndex(3));
            assertEquals(prodAppBelowBoundary, appStore.getAppByIndex(4));
            assertEquals(prodAppOnBoundary, appStore.getAppByIndex(5));
            assertEquals(edAppBelowBoundary, appStore.getAppByIndex(6));
        }

        @Test
        void sortByNameAscendingDoesntCrashWhenListIsEmpty() {
            assertEquals(0,emptyAppStore.numberOfApps());
            emptyAppStore.sortAppsByNameAscending();
        }

    }

    @Nested
    class ValidationMethods {
        @Test
        void isValidAppName() {
            assertTrue(appStore.isValidAppName("Empires"));
            assertTrue(appStore.isValidAppName("CookOff"));
            assertTrue(appStore.isValidAppName("Outlook"));
            assertFalse(emptyAppStore.isValidAppName("Outlook"));
        }
    }

    @Test
    void checkRandomApp() {
        assertNull(emptyAppStore.randomApp());
        assertNotNull(appStore.randomApp());
    }

    @Test
    void checkSimulateRatings() {
        appStore.simulateRatings();
        for (int i = 0; i < appStore.numberOfApps(); ++i) {
            assertFalse(appStore.getAppByIndex(i).getRatings().isEmpty());
        }
    }

    @Test
    void getFileName() {
        assertEquals("apps.xml", appStore.fileName());
    }

    //--------------------------------------------
    // Helper Methods
    //--------------------------------------------
    EducationApp setupEducationAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        EducationApp edApp = new EducationApp(developerLego, "WeDo", 1,
                1.0, 1.00, 3);
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        return edApp;
    }

    GameApp setupGameAppWithRating(int rating1, int rating2) {
        GameApp gameApp = new GameApp(developerEAGames, "MazeRunner", 1,
                1.0, 1.00, false);
        gameApp.addRating(new Rating(rating1, "John Soap", "Exciting Game"));
        gameApp.addRating(new Rating(rating2, "Jane Soap", "Nice Game"));
        return gameApp;
    }

    ProductivityApp setupProductivityAppWithRating(int rating1, int rating2) {
        ProductivityApp productivityApp = new ProductivityApp(developerApple, "Evernote", 1,
                1.0, 1.99);

        productivityApp.addRating(new Rating(rating1, "John101", "So easy to add a note"));
        productivityApp.addRating(new Rating(rating2, "Jane202", "So useful"));
        return productivityApp;
    }

}
