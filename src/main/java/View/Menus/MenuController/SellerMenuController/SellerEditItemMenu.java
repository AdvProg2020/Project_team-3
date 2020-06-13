package View.Menus.MenuController.SellerMenuController;

import Controller.ItemAndCategoryController;
import Controller.UserController;
import Model.Item;
import Model.Users.Seller;
import View.Menus.MenuController.ItemMenuController;
import View.Menus.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.HashMap;

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

        listView.getSelectionModel().clearSelection();
        if(ItemAndCategoryController.getInstance().isThereItemWithId(itemID)) {
            ItemMenuController.setItemID(itemID);
            SceneSwitcher.getInstance().setSceneTo("ItemMenu", 1280, 720);
        }
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

    }

    public static String getItemID() {
        return itemID;
    }

    public static void setItemID(String itemID) {
        SellerEditItemMenu.itemID = itemID;
    }


}
