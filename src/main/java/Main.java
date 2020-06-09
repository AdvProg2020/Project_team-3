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
        SceneSwitcher.getInstance().setSceneTo("BuyerRegister");
        stage.setMaximized(true);
        stage.show();
    }

    public void initializeScreens() throws IOException {
        Parent parent;
        URL [] urls=new URL[5];
        urls[0] = new File("src/main/resources/fxml/MainMenu.fxml").toURI().toURL();
        parent = FXMLLoader.load(urls[0]);
        SceneSwitcher.getInstance().addScene("MainMenu",new Scene(parent,1280,600));
        urls[1]=new File("src/main/resources/fxml/SellerRegisterMenu.fxml").toURI().toURL();
        parent=FXMLLoader.load(urls[1]);
        SceneSwitcher.getInstance().addScene("SellerRegister",new Scene(parent,1280,600));
        urls[2]=new File("src/main/resources/fxml/AdminRegisterMenu.fxml").toURI().toURL();
        parent=FXMLLoader.load(urls[2]);
        SceneSwitcher.getInstance().addScene("AdminRegister",new Scene(parent,1280,600));
        urls[3]=new File("src/main/resources/fxml/BuyerRegisterMenu.fxml").toURI().toURL();
        parent=FXMLLoader.load(urls[3]);
        SceneSwitcher.getInstance().addScene("BuyerRegister",new Scene(parent,1280,600));

    }

}

//edit category : add attribute [attribute e space dar]
//dar akhar: bayad enumerate beshe status ha
//status haye enumerate shode bayad ezafe shan be classa
//sort bar asas zaman
//time bought item hichja estefade nemishe
//matcher attribute
//pole seller bedun discount (dar dakhel buylog)