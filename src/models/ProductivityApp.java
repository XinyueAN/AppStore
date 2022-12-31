package models;

public class ProductivityApp extends App {
    /**
     * constructor
     *
     * @param developer
     * @param appName
     * @param appSize
     * @param appVersion
     * @param appCost
     */
    public ProductivityApp(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        super(developer, appName, appSize, appVersion, appCost);
    }

    /**
     * is Recommended App
     * @return
     */
    @Override
    public boolean isRecommendedApp() {
        if (getAppCost() >= 1.99 && calculateRating() >= 3.0) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
