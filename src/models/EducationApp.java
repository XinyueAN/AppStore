package models;

import utils.Utilities;

public class EducationApp extends App {
    private int level = 0;

    /**
     * constructor
     *
     * @param developer
     * @param appName
     * @param appSize
     * @param appVersion
     * @param appCost
     */
    public EducationApp(Developer developer, String appName, double appSize, double appVersion, double appCost, int level) {
        super(developer, appName, appSize, appVersion, appCost);
        setLevel(level);
    }

    /**
     * get level
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     * set level
     * @param level
     */
    public void setLevel(int level) {
        if (Utilities.validRange(level, 1, 10)) {
            this.level = level;
        }
    }

    /**
     * get app Summary
     * @return
     */
    public String appSummary() {
        return super.appSummary() + ". level " + level;
    }

    /**
     * is Recommended App
     * @return
     */
    @Override
    public boolean isRecommendedApp() {
        if (getAppCost() > 0.99 && calculateRating() >= 3.5 && level >= 3) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        String retStr =  super.toString();
        retStr += ("Level: " + level + "\n");

        return retStr;
    }
}
