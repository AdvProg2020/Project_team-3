package View.Menus.MenuController;

import Controller.SortAndFilterController;
import Controller.UserController;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SalesMenuController {


    private int pageNumber=1;


    public void logout(ActionEvent actionEvent) {
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
        // SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    public void Exit(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().closeWindow();
    }


    public void back(ActionEvent actionEvent) {
        SceneSwitcher.getInstance().back();
    }


    @FXML
    private void initialize(){

    }

    private void initLists(){

    }


    private boolean isAValidPage(int num){
        return false;
    }


    @FXML
    private void increasePage(){
        if(isAValidPage(pageNumber+1)){
            //pageNum.setText((pageNumber + 1) +"/"+ (SortAndFilterController.getInstance().show(categoryName).size() / 24 + 1));
            pageNumber++;
            initLists();
        }

    }

    @FXML
    private void decreasePage(){
        if(isAValidPage(pageNumber-1)){
            //pageNum.setText((pageNumber - 1) +"/"+ (SortAndFilterController.getInstance().show(categoryName).size() / 24 + 1));
            pageNumber--;
            initLists();
        }

    }
}
