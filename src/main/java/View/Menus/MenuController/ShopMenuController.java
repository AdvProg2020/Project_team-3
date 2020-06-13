package View.Menus.MenuController;

import Controller.UserController;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class ShopMenuController {



    public void logout(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }


    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().setSceneTo("BuyerMenu");
    }


    @FXML
    GridPane gridPane;

    @FXML
    private void initialize(){
        //bayad item haye shop ro bedast biarim va chizayi mesle filter sort va category ke mitonan
        //arrayliste item haro dastkari konan ro poshesh bedim , inja miaim item haro bargozari mikonim
        //har item mishe 1 Vbox 3 ghesmate , 1 aks + 1 gheymat (agge off bashe strikethrough + gheymat jadid) + 1 esm
        //click rooye vbox mibare maro be itemMenu on item
        //catgory o filter o sort o in kosshera ro badan mizanim
    }
}
