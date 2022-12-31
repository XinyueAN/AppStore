package models;

import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public abstract class App {
    private Developer developer;
    private String appName = "No app name";
    private double appSize = 0;
    private double appVersion = 1.0;
    private double appCost = 0;
    private List<Rating> ratings = new ArrayList<>();

    /**
     * constructor
     * @param developer
     * @param appName
     * @param appSize
     * @param appVersion
     * @param appCost
     */
    public App(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        setDeveloper(developer);
        setAppName(appName);
        setAppSize(appSize);
        setAppVersion(appVersion);
        setAppCost(appCost);
    }

    public abstract boolean isRecommendedApp();

    /**
     * get app Summary
     * @return
     */
    public String appSummary() {
        return String.format("%s(V%s) by %s, €%.2f. Rating: %f", appName, ""+appVersion, developer.toString(),
                appCost, calculateRating());
    }

    /**
     * add rating
     * @param rating
     * @return
     */
    public boolean addRating(Rating rating) {
        return ratings.add(rating);
    }

    /**
     * list Ratings
     * @return
     */
    public String listRatings() {
        if (ratings.isEmpty()) {
            return "No ratings added yet";
        } else {
            String retStr = "";
            for (Rating r : ratings) {
                retStr += r.toString() + "\n";
            }

            return retStr;
        }
    }

    /**
     * calculate Rating
     * @return
     */
    public double calculateRating() {
        if (ratings.isEmpty()) {
            return 0;
        } else {
            double average = 0;
            int count = 0;

            for (Rating r : ratings) {
                if (r.getNumberOfStars() == 0) {
                    continue;
                }

                count++;
                average += r.getNumberOfStars();
            }

            return average / count;
        }
    }

    /**
     * get developer
     * @return
     */
    public Developer getDeveloper() {
        return developer;
    }

    /**
     * get appName
     * @return
     */
    public String getAppName() {
        return appName;
    }

    /**
     * set appName
     * @param appName
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * set developer
     * @param developer
     */
    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    /**
     * get appSize
     * @return
     */
    public double getAppSize() {
        return appSize;
    }

    /**
     * set appSize
     * @param appSize
     */
    public void setAppSize(double appSize) {
        if (Utilities.validRange(appSize, 1, 1000)) {
            this.appSize = appSize;
        }
    }

    /**
     * get appVersion
     * @return
     */
    public double getAppVersion() {
        return appVersion;
    }

    /**
     * set appVersion
     * @param appVersion
     */
    public void setAppVersion(double appVersion) {
        if (Utilities.greaterThanOrEqualTo(appVersion, 1.0)) {
            this.appVersion = appVersion;
        }
    }

    /**
     * get appCost
     * @return
     */
    public double getAppCost() {
        return appCost;
    }

    /**
     * set appCost
     * @param appCost
     */
    public void setAppCost(double appCost) {
        if (Utilities.greaterThanOrEqualTo(appCost, 0.0)) {
            this.appCost = appCost;
        }
    }

    /**
     * get Ratings
     * @return
     */
    public List<Rating> getRatings() {
        return ratings;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(appName + "\n")
                .append("(Version " + appVersion + ")\n")
                .append(developer.toString() + "\n")
                .append(appSize + "MB\n")
                .append("Cost: " + appCost + "\n")
                .append("Ratings (" + calculateRating() + ")\n");

        for (Rating r : ratings) {
            sb.append("\t" + r.toString() + "\n");
        }

        return sb.toString();
    }
}
