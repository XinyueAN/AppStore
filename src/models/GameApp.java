package models;

public class GameApp extends App {
    private boolean isMultiplayer = false;

    /**
     * constructor
     *
     * @param developer
     * @param appName
     * @param appSize
     * @param appVersion
     * @param appCost
     */
    public GameApp(Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer) {
        super(developer, appName, appSize, appVersion, appCost);
        setMultiplayer(isMultiplayer);
    }

    /**
     * is Multiplayer
     * @return
     */
    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    /**
     * set Multiplayer
     * @param multiplayer
     */
    public void setMultiplayer(boolean multiplayer) {
        this.isMultiplayer = multiplayer;
    }

    /**
     * get app Summary
     * @return
     */
    public String appSummary() {
        return super.appSummary() + ". isMultiplayer " + isMultiplayer;
    }

    /**
     * is Recommended App
     * @return
     */
    @Override
    public boolean isRecommendedApp() {
        if (isMultiplayer && calculateRating() >= 4.0) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        String retStr =  super.toString();
        retStr += ("IsMultiplayer: " + isMultiplayer + "\n");

        return retStr;
    }
}
