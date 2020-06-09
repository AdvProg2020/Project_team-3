package Controller;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class SceneSwitcher {
    private static SceneSwitcher sceneSwitcher;
    private HashMap<String, Scene> allScenes = new HashMap<>();
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
        stage.setScene(allScenes.get(name));
    }

    public  Stage getStage(){return stage;}

}
