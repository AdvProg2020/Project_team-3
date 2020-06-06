import Controller.Database;
import Controller.SceneSwitcher;
import View.Menus.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

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
        FXMLLoader loader = new FXMLLoader();
        loader.getCharset();
        //FXMLLoader.load(getClass().getResource("fxml/asd.fxml"));
        //parent = FXMLLoader.load(getClass().getResource("main/java/View/Menus/Markups/FelanMenu.fxml"));
        URL url = new File("src/main/resources/fxml/asd.fxml").toURI().toURL();
        parent = FXMLLoader.load(url);


        getClass().getResource("fxml/asd.fxml");
        //parent = loader.load(getClass().getResource("View/Menus/Markups/FelanMenu.fxml")); //<= kar nemikone kiri!
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