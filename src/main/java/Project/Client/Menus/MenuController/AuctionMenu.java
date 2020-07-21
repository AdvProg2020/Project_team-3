package Project.Client.Menus.MenuController;

import Project.Client.CLI.ItemMenu;
import Project.Client.CLI.View;
import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Auction;
import Project.Client.Model.Item;
import Project.Client.Model.Users.Seller;
import Project.Client.Model.Users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class AuctionMenu {
    private static Auction auction;

    @FXML private AnchorPane pane;
    @FXML private TextArea chatBox;
    @FXML private ListView listView;
    @FXML private Label errorLabel;
    @FXML private Label bidLabel;
    @FXML private TextArea description;
    @FXML private Label nameLabel;
    @FXML private Label brandLabel;

    protected static void setAuction(Auction newAuction){
        auction = newAuction;
    }

    @FXML  private void back() {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("AllAuctionsMenu");
    }

    @FXML private void initialize(){
        View.setFonts(pane);
        initializeChat();
        errorLabel.setTextFill(Color.RED);
        Item item= MakeRequest.getItem(auction.getItemID());
        nameLabel.setText(item.getName()+"        Sold by:  "+item.getSellerName());
        description.setText(item.getDescription());
        brandLabel.setText(item.getBrand());

        //bayad biaim menush ro update konim

        //ghabeliat chat beine hamme (login shode bashe!)

        //ghabeliat bid baraye buyer ha
    }

    private void initializeChat(){
        listView.getItems().clear();
        chatBox.clear();
        HashMap<String,String> chat = auction.getChat();
        for(String sender:chat.keySet()){
            listView.getItems().add(sender+": "+chat.get(sender));
        }
    }

    @FXML private void clear(){
        chatBox.clear();
    }

    @FXML private void send(){
        if((Client.getInstance().getToken()==null)||(MakeRequest.isTokenValid()==false)){
            errorLabel.setText("You must be logged in to send a message");
            return;
        }
        if(chatBox.getText().isEmpty()){
            errorLabel.setText("Type something to send");
            return;
        }
        MakeRequest.addAuctionChat(auction.getId(),chatBox.getText());
        String message = chatBox.getText();
        initializeChat();
        listView.getItems().add("You just said: "+message);
        errorLabel.setText("Message sent!");
    }

    @FXML private void learnMore(){
        ItemMenuController.setItemID(auction.getItemID());
        SceneSwitcher.getInstance().saveScene("AuctionMenu");
        SceneSwitcher.getInstance().setSceneTo("ItemMenu");
    }

}
