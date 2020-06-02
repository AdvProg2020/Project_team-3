import Controller.Database;
import Controller.ItemAndCategoryController;
import View.Menus.ShopAndDiscountMenu.ShopMenu;
import View.Menus.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
        Database.getInstance().initiate();
        View.run();

    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        stage.setTitle("paniz");
        stage.setScene(new Scene(anchorPane,400,400));
        stage.show();
    }
}

//edit category : add attribute [attribute e space dar]
//dar akhar: bayad enumerate beshe status ha
//status haye enumerate shode bayad ezafe shan be classa
//sort bar asas zaman
//time bought item hichja estefade nemishe
//matcher attribute
//pole seller bedun discount (dar dakhel buylog)