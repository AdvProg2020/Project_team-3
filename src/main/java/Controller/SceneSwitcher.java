package Controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class SceneSwitcher {
    private static SceneSwitcher sceneSwitcher;
    private HashMap<String, Scene> allScenes = new HashMap<>();
    private ArrayList<String> recentScenes=new ArrayList<>();
    private Stage stage;
    private SceneSwitcher(){

    }
    public static SceneSwitcher getInstance(){
        if(sceneSwitcher==null){
            sceneSwitcher = new SceneSwitcher();
        }
        return sceneSwitcher;
    }

    public void addScene(String name,Scene scene){
        allScenes.put(name,scene);
    }

    public void setStage(Stage passedStage){
        this.stage = passedStage;
    }

    public void closeWindow(){
        stage.close();
    }

    public void setSceneTo(String name){
        recentScenes.add(name);
        stage.setScene(allScenes.get(name));
        System.out.println(recentScenes);
    }

    public  Stage getStage(){return stage;}

    public void back(){
        if(recentScenes.size()<2) return;
        String name=recentScenes.get(recentScenes.size()-2);
        recentScenes.remove(recentScenes.size()-1);
        recentScenes.remove(recentScenes.size()-1);
        setSceneTo(name);
    }

    public ArrayList<String> getRecentScenes() {
        return recentScenes;
    }
}
