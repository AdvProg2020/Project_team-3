import Controller.Database;
import Controller.SceneSwitcher;
import View.Menus.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
        Database.getInstance().initiate();
        View.run();

    }

    @Override
    public void start(Stage stage) throws Exception {
        SceneSwitcher.getInstance().setStage(stage);
        stage.setTitle("paniz");
        initializeScreens();
        SceneSwitcher.getInstance().setSceneTo("FelanMenu");
        stage.show();
    }

    public void initializeScreens() throws IOException {
        //screen haro az fxml mikhone pass mide SceneSwitcher
        Parent parent;
        //parent = FXMLLoader.load(getClass().getResource("main/java/View/Menus/Markups/FelanMenu.fxml"));
        parent = FXMLLoader.load(getClass().getResource("fxml/asd.fxml")); //<= kar nemikone kiri!
        SceneSwitcher.getInstance().addScene("FelanMenu",new Scene(parent,400,600));
    }

}

//edit category : add attribute [attribute e space dar]
//dar akhar: bayad enumerate beshe status ha
//status haye enumerate shode bayad ezafe shan be classa
//sort bar asas zaman
//time bought item hichja estefade nemishe
//matcher attribute
//pole seller bedun discount (dar dakhel buylog)