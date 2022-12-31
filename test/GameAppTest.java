import models.Developer;
import models.GameApp;
import models.Rating;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameAppTest {

    private GameApp edAppBelowBoundary, edAppOnBoundary, edAppAboveBoundary, edAppInvalidData;
    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");

    // TODO Nothing!  This class is complete
    
    @BeforeEach
    void setUp() {
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0), level(1-10).
        edAppBelowBoundary = new GameApp(developerLego, "WeDo", 1, 1.0, 0, true);
        edAppOnBoundary = new GameApp(developerLego, "Spike", 1000, 2.0, 1.99,  true);
        edAppAboveBoundary = new GameApp(developerLego, "EV3", 1001, 3.5, 2.99, false);
        edAppInvalidData = new GameApp(developerLego, "", -1, 0, -1.00, false);
    }

    @AfterEach
    void tearDown() {
        edAppBelowBoundary = edAppOnBoundary = edAppAboveBoundary = edAppInvalidData = null;
        developerLego = developerSphero = null;
    }

    @Nested
    class Getters {

        @Test
        void getDeveloper() {
            assertEquals(developerLego, edAppBelowBoundary.getDeveloper());
            assertEquals(developerLego, edAppOnBoundary.getDeveloper());
            assertEquals(developerLego, edAppAboveBoundary.getDeveloper());
            assertEquals(developerLego, edAppInvalidData.getDeveloper());
        }

        @Test
        void getAppName() {
            assertEquals("WeDo", edAppBelowBoundary.getAppName());
            assertEquals("Spike", edAppOnBoundary.getAppName());
            assertEquals("EV3", edAppAboveBoundary.getAppName());
            assertEquals("", edAppInvalidData.getAppName());
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
        void getIsMultiplayer() {
            assertEquals(true, edAppBelowBoundary.isMultiplayer());
            assertEquals(true, edAppOnBoundary.isMultiplayer());
            assertEquals(false, edAppAboveBoundary.isMultiplayer());
            assertEquals(false, edAppInvalidData.isMultiplayer());
        }

    }

    @Nested
    class Setters {

        @Test
        void setDeveloper() {
            //no validation in models
            assertEquals(developerLego, edAppBelowBoundary.getDeveloper());
            edAppBelowBoundary.setDeveloper(developerSphero);
            assertEquals(developerSphero, edAppBelowBoundary.getDeveloper());
        }

        @Test
        void setAppName() {
            //no validation in models
            assertEquals("WeDo", edAppBelowBoundary.getAppName());
            edAppBelowBoundary.setAppName("Mindstorms");
            assertEquals("Mindstorms", edAppBelowBoundary.getAppName());
        }

        @Test
        void setAppSize() {
            //Validation: appSize(1-1000)
            assertEquals(1, edAppBelowBoundary.getAppSize());

            edAppBelowBoundary.setAppSize(1000);
            assertEquals(1000, edAppBelowBoundary.getAppSize()); //update

            edAppBelowBoundary.setAppSize(1001);
            assertEquals(1000, edAppBelowBoundary.getAppSize()); //no update

            edAppBelowBoundary.setAppSize(2);
            assertEquals(2, edAppBelowBoundary.getAppSize()); //update

            edAppBelowBoundary.setAppSize(0);
            assertEquals(2, edAppBelowBoundary.getAppSize()); //no update
        }

        @Test
        void setAppVersion() {
            //Validation: appVersion(>=1.0)
            assertEquals(1.0, edAppBelowBoundary.getAppVersion());

            edAppBelowBoundary.setAppVersion(2.0);
            assertEquals(2.0, edAppBelowBoundary.getAppVersion()); //update

            edAppBelowBoundary.setAppVersion(0.0);
            assertEquals(2.0, edAppBelowBoundary.getAppVersion()); //no update

            edAppBelowBoundary.setAppVersion(1.0);
            assertEquals(1.0, edAppBelowBoundary.getAppVersion()); //update
        }

        @Test
        void setAppCost() {
            //Validation: appCost(>=0)
            assertEquals(0.0, edAppBelowBoundary.getAppCost());

            edAppBelowBoundary.setAppCost(1.0);
            assertEquals(1.0, edAppBelowBoundary.getAppCost()); //update

            edAppBelowBoundary.setAppCost(-1);
            assertEquals(1.0, edAppBelowBoundary.getAppCost()); //no update

            edAppBelowBoundary.setAppCost(0.0);
            assertEquals(0.0, edAppBelowBoundary.getAppCost()); //update
        }

        @Test
        void setMultiplayer() {
            //Validation:
            assertEquals(true, edAppBelowBoundary.isMultiplayer());

            edAppBelowBoundary.setMultiplayer(false);
            assertEquals(false, edAppBelowBoundary.isMultiplayer()); //update
        }

    }

    @Nested
    class ObjectStateMethods {

        @Test
        void appSummaryReturnsCorrectString() {
            GameApp edApp = setupEducationAppWithRating(true, 3, 4);
            String stringContents = edApp.appSummary();

            assertTrue(stringContents.contains("isMultiplayer " + edApp.isMultiplayer()));
            assertTrue(stringContents.contains(edApp.getAppName() + "(V" + edApp.getAppVersion()));
            assertTrue(stringContents.contains(edApp.getDeveloper().toString()));
            assertTrue(stringContents.contains("€" + edApp.getAppCost()));
            assertTrue(stringContents.contains("Rating: " + edApp.calculateRating()));
        }

        @Test
        void toStringReturnsCorrectString() {
            GameApp edApp = setupEducationAppWithRating(true, 3, 4);
            String stringContents = edApp.toString();

            assertTrue(stringContents.contains(edApp.getAppName()));
            assertTrue(stringContents.contains("(Version " + edApp.getAppVersion()));
            assertTrue(stringContents.contains(edApp.getDeveloper().toString()));
            assertTrue(stringContents.contains(edApp.getAppSize() + "MB"));
            assertTrue(stringContents.contains("Cost: " + edApp.getAppCost()));
            assertTrue(stringContents.contains("IsMultiplayer: " + edApp.isMultiplayer()));
            assertTrue(stringContents.contains("Ratings (" + edApp.calculateRating()));

            //contains list of ratings too
            assertTrue(stringContents.contains("John Doe"));
            assertTrue(stringContents.contains("Very Good"));
            assertTrue(stringContents.contains("Jane Doe"));
            assertTrue(stringContents.contains("Excellent"));
        }

    }

    @Nested
    class RecommendedApp {

        @Test
        void appIsNotRecommendedWhenMultiplayerIsFalse() {
            GameApp edApp = setupEducationAppWithRating(false, 3, 4);
            assertFalse(edApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenRatingIsLessThan4() {
            GameApp edApp = setupEducationAppWithRating(true, 3, 3);
            assertFalse(edApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenNoRatingsExist() {
            //setting all conditions to true with no ratings
            GameApp edApp = new GameApp(developerLego, "WeDo", 1,
                    1.0, 1.00,  true);
            //verifying recommended app returns true
            assertFalse(edApp.isRecommendedApp());
        }

        @Test
        void appIsRecommendedWhenAllOfTheConditionsAreTrue() {
            GameApp edApp = setupEducationAppWithRating(true, 5, 4);

            //verifying recommended app returns true
            assertTrue(edApp.isRecommendedApp());
        }

    }

    GameApp setupEducationAppWithRating(boolean isMultiplayer, int rating1, int rating2) {
        //setting all conditions to true
        GameApp edApp = new GameApp(developerLego, "WeDo", 1,
                1.0, 1.00,  isMultiplayer);
        edApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        edApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        //verifying all conditions are true for a recommended educational app]
        assertEquals(2, edApp.getRatings().size());  //two ratings are added
        assertEquals(1.0, edApp.getAppCost(), 0.01);
        assertEquals(((rating1 + rating2) / 2.0), edApp.calculateRating(), 0.01);

        return edApp;
    }
}
