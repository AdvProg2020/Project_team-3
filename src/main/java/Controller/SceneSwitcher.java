package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class SceneSwitcher {
    private static SceneSwitcher sceneSwitcher;
    private HashMap<String, String> allScenesFXML = new HashMap<>();
    private Stage stage;
    private SceneSwitcher(){

    }
    public static SceneSwitcher getInstance(){
        if(sceneSwitcher==null){
            sceneSwitcher = new SceneSwitcher();
        }
        return sceneSwitcher;
    }

    public void addSceneFXML (String name,String FXML){
        allScenesFXML.put(name,FXML);
    }

    public void setStage(Stage passedStage){
        this.stage = passedStage;
    }

    public void closeWindow(){
        stage.close();
    }

    public void setSceneTo(String sceneName)  {
        try {
            String path = allScenesFXML.get(sceneName);
            URL urls = new File(path).toURI().toURL();
            Parent parent = FXMLLoader.load(urls);
            stage.setScene(new Scene(parent, 1280, 720));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setSceneTo(String sceneName,int width,int height) throws IOException {
        String path=allScenesFXML.get(sceneName);
        URL urls=new File(path).toURI().toURL();
        Parent parent= FXMLLoader.load(urls);
        stage.setScene(new Scene(parent,width,height));
    }

    public  Stage getStage(){return stage;}
}
