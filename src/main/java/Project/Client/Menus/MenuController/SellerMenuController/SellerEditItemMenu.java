package Project.Client.Menus.MenuController.SellerMenuController;

import Project.Client.Client;
import Project.Client.MakeRequest;

import Project.Client.Model.Item;

import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class SellerEditItemMenu {
    private static String itemID;
    @FXML private AnchorPane pane;
    @FXML private ListView listView;
    @FXML private Label label;
    @FXML private Button removeItem;
    @FXML private ListView buyersListView;

    private TextInputDialog dialog = new TextInputDialog("");

    @FXML
    private void initialize(){
        MakeRequest.getItem(itemID);
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("first.wav");
        label.setText("You are editing "+itemID);
        updateAttributes();
        updateBuyers();
    }


     private void updateAttributes(){
        Item item = MakeRequest.getItem(itemID);
        listView.getItems().clear();
        listView.getItems().add("Name:" +  item.getName());
        listView.getItems().add("Brand:" +item.getBrand());
        listView.getItems().add("Price:" +item.getPrice());
        listView.getItems().add("Description:"+item.getDescription());
        listView.getItems().add("inStock:"+item.getInStock());
        HashMap<String, String> attributes = item.getAttributes();
        for(String key:attributes.keySet()){
            listView.getItems().add(key+":"+attributes.get(key));
        }
    }

    private void updateBuyers(){
        buyersListView.getItems().clear();
        Item item = MakeRequest.getItem(itemID);
        ArrayList<String> buyers = item.getBuyerUserName();
        for(String buyer:buyers){
            buyersListView.getItems().add(buyer);
        }
        if(buyersListView.getItems().isEmpty()){
            buyersListView.getItems().add("This item has never been bought.");
        }
    }

    @FXML
    private void keySelect(){
        int index=listView.getSelectionModel().getSelectedIndex();
        if(index==-1)
            return;
        String string=listView.getItems().get(index).toString();
        int ind = string.indexOf(":");
        string = string.substring(0,ind);
        final String key = string.toLowerCase();
        // alert dialog miad value migire
        setDialogText("Enter a new value for "+key);
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> sendEditRequest(key,s));


    }

    private void sendEditRequest(String key,String value){
        if(isAValidValue(key,value)) {
            MakeRequest.makeEditProductRequest(itemID,key,value);
            sendAlert("Editing request has been sent.", "SellerEditItemMenu");
        }
        else {
            sendAlert("Error: New value is invalid.", "SellerEditItemMenu");
        }
    }

    private boolean isAValidValue(String key,String value){
        if(value.isEmpty()) return false;
        if(key.equalsIgnoreCase("price")){
            return SellerAddProductMenu.isAPositiveDouble(value);
        }
        if(key.equalsIgnoreCase("inStock")){
            return SellerAddProductMenu.isAPositiveInteger(value);
        }
        return true;
    }

    private void sendAlert(String text,String nextScene){
        MusicManager.getInstance().playSound("notify");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.setGraphic(null);
        alert.show();
        alert.setHeight(127);
        alert.setOnHidden(evt -> SceneSwitcher.getInstance().setSceneTo(nextScene));
    }


    @FXML
    private void back(){
        SceneSwitcher.getInstance().setSceneTo("SellerManageProductsMenu");
    }

    @FXML
    private void logout(){
        MakeRequest.makeLogoutRequest();
        SceneSwitcher.getInstance().clearRecentScene();
        Client.getInstance().setBankAccountToken("");
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }


    @FXML
    private void removeItem(){
        sendAlert(MakeRequest.makeRemoveProductSellerRequest(itemID),"SellerManageProductsMenu");
    }

    public static String getItemID() {
        return itemID;
    }

    public static void setItemID(String itemID) {
        SellerEditItemMenu.itemID = itemID;
    }

    private void setDialogText(String attributeKey){
        dialog.setTitle("Enter Attribute Value");
        dialog.setHeaderText(attributeKey);
        dialog.setContentText("Please enter a value:");
        dialog.setGraphic(null);
    }


  /*  public void commercial(MouseEvent mouseEvent) {
        MusicManager.getInstance().playSound("Button");
        String message=CommercialController.getInstance().addCommercialRequest(itemID);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    } */

}
