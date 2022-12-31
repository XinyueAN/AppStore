package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.ISerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;
import static utils.RatingUtility.generateRandomRating;

public class AppStoreAPI implements ISerializer {
    private List<App> apps = new ArrayList<>();

    //TODO refer to the spec and add in the required methods here (make note of which methods are given to you first!)
    public boolean addApp(App app) {
        return apps.add(app);
    }

    /**
     * list all apps
     * @return
     */
    public String listAllApps() {
        String retStr = "";
        for (App app : apps){
            retStr += apps.indexOf(app) + ": " + app + "\n";
        }
        if (retStr.equals("")){
            return "No apps";
        }
        else {
            return retStr;
        }
    }

    /**
     * list Summary Of All Apps
     * @return
     */
    public String listSummaryOfAllApps() {
        String retStr = "";
        for (App app : apps){
            retStr += apps.indexOf(app) + ": " + app.appSummary() + "\n";
        }
        if (retStr.equals("")){
            return "No apps";
        }
        else {
            return retStr;
        }
    }

    /**
     * list All Game Apps
     * @return
     */
    public String listAllGameApps() {
        String retStr = "";
        for (App app : apps){
            if (app instanceof GameApp) {
                retStr += apps.indexOf(app) + ": " + app + "\n";
            }
        }
        if (retStr.equals("")){
            return "No Game apps";
        }
        else {
            return retStr;
        }
    }

    /**
     * list All Education Apps
     * @return
     */
    public String listAllEducationApps() {
        String retStr = "";
        for (App app : apps){
            if (app instanceof EducationApp) {
                retStr += apps.indexOf(app) + ": " + app + "\n";
            }
        }
        if (retStr.equals("")){
            return "No Education apps";
        }
        else {
            return retStr;
        }
    }

    /**
     * list All Productivity Apps
     * @return
     */
    public String listAllProductivityApps() {
        String retStr = "";
        for (App app : apps){
            if (app instanceof ProductivityApp) {
                retStr += apps.indexOf(app) + ": " + app + "\n";
            }
        }
        if (retStr.equals("")){
            return "No Productivity apps";
        }
        else {
            return retStr;
        }
    }

    /**
     * list All Apps By Name
     * @param name
     * @return
     */
    public String listAllAppsByName(String name) {
        String retStr = "";
        for (App app : apps){
            if (app.getAppName().toLowerCase().contains(name.toLowerCase())) {
                retStr += apps.indexOf(app) + ": " + app + "\n";
            }
        }
        if (retStr.equals("")){
            return "No apps for name \"" + name + "\" exists.";
        }
        else {
            return retStr;
        }
    }

    /**
     * list All Apps Above Or Equal A Given Star Rating
     * @param rate
     * @return
     */
    public String listAllAppsAboveOrEqualAGivenStarRating(int rate) {
        String retStr = "";
        for (App app : apps){
            if (app.calculateRating() >= rate) {
                retStr += apps.indexOf(app) + ": " + app + "\n";
            }
        }
        if (retStr.equals("")){
            return "No apps have a rating of" + rate + " or above";
        }
        else {
            return retStr;
        }
    }

    /**
     * list All Recommended Apps
     * @return
     */
    public String listAllRecommendedApps() {
        String retStr = "";
        for (App app : apps){
            if (app.isRecommendedApp()) {
                retStr += apps.indexOf(app) + ": " + app + "\n";
            }
        }
        if (retStr.equals("")){
            return "No recommended apps";
        }
        else {
            return retStr;
        }
    }

    /**
     * list All Apps By Chosen Developer
     * @param developer
     * @return
     */
    public String listAllAppsByChosenDeveloper(Developer developer) {
        String retStr = "";
        for (App app : apps){
            if (app.getDeveloper().equals(developer)) {
                retStr += apps.indexOf(app) + ": " + app + "\n";
            }
        }
        if (retStr.equals("")){
            return "No apps for developer: " + developer;
        }
        else {
            return retStr;
        }
    }

    /**
     * number Of Apps By Chosen Developer
     * @param developer
     * @return
     */
    public int numberOfAppsByChosenDeveloper(Developer developer) {
        int count = 0;
        for (App app : apps){
            if (app.getDeveloper().equals(developer)) {
                count++;
            }
        }
        return count;
    }

    /**
     * delete App By Index
     * @param index
     * @return
     */
    public App deleteAppByIndex(int index) {
        if (isValidIndex(index)) {
            return apps.remove(index);
        }

        return null;
    }

    /**
     * select a random app
     * @return
     */
    public App randomApp() {
        if (apps.isEmpty()) {
            return null;
        }

        return apps.get ((int) (random() * (apps.size()-1)));
    }

    /**
     * is Valid AppName
     * @param appName
     * @return
     */
    public boolean isValidAppName(String appName) {
        for (App app : apps){
            if (app.getAppName().equalsIgnoreCase(appName)){
                return true;
            }
        }
        return false;
    }

    /**
     * get App By Name
     * @param appName
     * @return
     */
    public App getAppByName(String appName) {
        if (isValidAppName(appName)) {
            for (App app : apps){
                if (app.getAppName().equalsIgnoreCase(appName)){
                    return app;
                }
            }
        }
        return null;
    }

    /**
     * get App By Index
     * @param index
     * @return
     */
    public App getAppByIndex(int index) {
        if (isValidIndex(index)) {
            return apps.get(index);
        }

        return null;
    }


    //---------------------
    // Method to simulate ratings (using the RatingUtility).
    // This will be called from the Driver (see skeleton code)
    //---------------------
    // TODO UNCOMMENT THIS COMPLETED method as you start working through this class
    //---------------------
    public void simulateRatings(){
        for (App app :apps) {
            app.addRating(generateRandomRating());
        }
    }

    /**
     * number Of Apps
     * @return
     */
    public int numberOfApps() {
        return apps.size();
    }

    /**
     * sort Apps By Name Ascending
     */
    public void sortAppsByNameAscending() {
        for(int i=0;i<apps.size()-1;i++){
            for(int j=0;j<apps.size()-i-1;j++){
                if (apps.get(j).getAppName().compareTo(apps.get(j+1).getAppName()) > 0) {
                    swapApps(apps, j,j+1);
                }
            }
        }
    }

    /**
     * swap App
     * @param list
     * @param i
     * @param j
     */
    private void swapApps(List<App> list, int i, int j) {
        App app = list.get(i);
        list.set(i, list.get(j));
        list.set(j, app);
    }

    //---------------------
    // Validation methods
    //---------------------
    // TODO UNCOMMENT THIS COMPlETED method as you start working through this class
    //---------------------
    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < apps.size());
    }

    //---------------------
    // Persistence methods
    //---------------------
    // TODO UNCOMMENT THIS COMPLETED CODE block as you start working through this class
    //---------------------
    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        apps = (List<App>) in.readObject();
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName(){
        return "apps.xml";
    }
}
