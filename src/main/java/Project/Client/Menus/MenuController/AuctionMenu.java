package Project.Client.Menus.MenuController;

import Project.Client.CLI.ItemMenu;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Auction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class AuctionMenu {
    private static Auction auction;

    @FXML private AnchorPane pane;

    protected static void setAuction(Auction newAuction){
        auction = newAuction;
    }

    @FXML  private void back() {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("AllAuctionsMenu");
    }

    @FXML private void initialize(){
        //bayad biaim menush ro update konim

        //ghabeliat chat beine hamme (login shode bashe!)

        //ghabeliat bid baraye buyer ha
    }

    private void initializeChat(){

    }

    @FXML private void sendChatMessage(){

    }

    @FXML private void learnMore(){
        ItemMenuController.setItemID(auction.getItemID());
        SceneSwitcher.getInstance().saveScene("AuctionMenu");
        SceneSwitcher.getInstance().setSceneTo("ItemMenu");
    }

}
