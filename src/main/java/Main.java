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

public class Main /*extends Application*/ {
    public static void main(String[] args) {
        //launch(args);
        Database.getInstance().initiate();
        View.run();

    }

   /* @Override
    public void start(Stage stage) throws Exception {
        SceneSwitcher.getInstance().setStage(stage);
        stage.setTitle("MVC Shop team 3");
        initializeScreens();                                       //                                                 ______
        SceneSwitcher.getInstance().setSceneTo("MainMenu");       //   /\                                             \    /
        stage.setMinWidth(1280);                                  //  /  \    note haye tahe main ro hamishe bekhonid  \  /
        stage.setMaxWidth(1280);                                  // /____\                                             \/
        stage.setMinHeight(720);
        stage.setMaxHeight(720);
        stage.setResizable(false);
        stage.show();
    }

    public void initializeScreens() throws IOException {
        Parent parent;
        URL [] urls=new URL[5];
        urls[0] = new File("src/main/resources/fxml/MainMenu.fxml").toURI().toURL();
        parent = FXMLLoader.load(urls[0]);
        SceneSwitcher.getInstance().addScene("MainMenu",new Scene(parent,1280,720));
        urls[1]=new File("src/main/resources/fxml/SellerRegisterMenu.fxml").toURI().toURL();
        parent=FXMLLoader.load(urls[1]);
        SceneSwitcher.getInstance().addScene("SellerRegister",new Scene(parent,1280,720));
        urls[2]=new File("src/main/resources/fxml/AdminRegisterMenu.fxml").toURI().toURL();
        parent=FXMLLoader.load(urls[2]);
        SceneSwitcher.getInstance().addScene("AdminRegister",new Scene(parent,1280,720));
        urls[3]=new File("src/main/resources/fxml/BuyerRegisterMenu.fxml").toURI().toURL();
        parent=FXMLLoader.load(urls[3]);
        SceneSwitcher.getInstance().addScene("BuyerRegister",new Scene(parent,1280,720));

    }*/

}
// hamme scene ha 1280 (tool) dar 720 (ertefa) bashan ke 16:9 bashe va ziadi ham bozorg nabashe
//set resizable ro false kardam ke be ham narize chizi !
// login menu tush ye ghabeliat bashe ke ba hyperlink bere register (?)
// in menu bar hammeja bashe
//havaseton bashe HAMMEJA BAYAD EMKANE LOGIN , REGISTER , LOGOUT , BACK vojod dashte bashe !!