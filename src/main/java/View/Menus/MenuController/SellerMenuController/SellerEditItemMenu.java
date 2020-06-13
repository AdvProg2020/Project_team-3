package View.Menus.MenuController.SellerMenuController;

import Controller.ItemAndCategoryController;
import Controller.UserController;
import Model.Item;
import Model.Users.Seller;
import View.Menus.MenuController.ItemMenuController;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Optional;

public class SellerEditItemMenu {
    private static String itemID;
    //
    //
    // bayad in controller ye fielde static dashte bashe , itemi ke gharare edit beshe , chizmiz haro tu initialize bar
    // asase inke on iteme attribute hash chie neshon bedim
    //
    //
    @FXML
    private ListView listView;
    @FXML
    private Label label;
    @FXML
    private Button removeItem;
    @FXML
    private Button viewBuyers;

    private TextInputDialog dialog = new TextInputDialog("");

    @FXML
    private void initialize(){
        label.setText("You are editing "+itemID);
        updateList();

    }

    private void updateList(){
        Item item = ItemAndCategoryController.getInstance().getItemById(itemID);
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
        result.ifPresent(s -> ItemAndCategoryController.getInstance().editItem(key,s,itemID));

        sendAlert("Editing request has been sent.","SellerEditItemMenu");
    }

    private void sendAlert(String text,String nextScene){
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
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    @FXML
    private void viewBuyers(){

    }

    @FXML
    private void removeItem(){
        sendAlert(ItemAndCategoryController.getInstance().deleteItem(itemID),"SellerManageProductsMenu");
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


}
