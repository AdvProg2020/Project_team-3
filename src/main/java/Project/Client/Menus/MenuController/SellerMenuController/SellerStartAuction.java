package Project.Client.Menus.MenuController.SellerMenuController;

import Project.Client.CLI.View;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.SortAndFilter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class SellerStartAuction {
    @FXML private void back(){
        SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }

    @FXML private ListView listView;
    @FXML private Label errorLabel;
    @FXML private TextField itemID;
    @FXML private TextField startPrice;
    @FXML private TextField duration;
    @FXML private AnchorPane pane;

    @FXML private void initialize(){
        SortAndFilter.getInstance().reset();
        MakeRequest.resetFilter();
        View.setFonts(pane);
        listView.getItems().clear();
        listView.getItems().addAll(MakeRequest.makeGetAllSellerItems());
        SortAndFilter.getInstance().disableFilterSellerName();
        errorLabel.setTextFill(Color.RED);
        if(listView.getItems().isEmpty()) {
            listView.getItems().add("there are no products right now");
            return;
        }
    }

    @FXML private void startAuction(){
        if(itemID.getText().startsWith("select ")){
            errorLabel.setText("select an item");
            return;
        }
        if(!isValidPrice(startPrice.getText())){
            errorLabel.setText("enter a valid price");
            return;
        }
        if(!isValidDuration(duration.getText())){
            errorLabel.setText("enter a valid duration");
            return;
        }
        //inja miad request e sakhtane auction ro mide
        sendAlert(MakeRequest.startAuction(Integer.parseInt(duration.getText()),Double.parseDouble(startPrice.getText()),itemID.getText()));
        itemID.clear();
        errorLabel.setText("");
        startPrice.clear();
        duration.clear();
    }

    private void sendAlert(String result){
        MusicManager.getInstance().playSound("notify");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(result);
        alert.setGraphic(null);
        alert.show();
        alert.setHeight(127);
        alert.setOnHidden(evt -> SceneSwitcher.getInstance().setSceneTo("SellerMenu"));
    }

    @FXML private void select(){
        int index=listView.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index==-1)
            return;
        String item=listView.getItems().get(index).toString();
        System.out.println(item);
        item = item.substring(4,9);
        itemID.setText(item);
        listView.getSelectionModel().clearSelection();
    }

    private boolean isValidPrice(String price){
        double d = -1;
        try {
            d = Double.parseDouble(price);
        }catch (Exception e){
            return false;
        }
        return d > 0;
    }

    private boolean isValidDuration(String dur){
        int i = -1;
        try {
            i = Integer.parseInt(dur);
        }catch (Exception e){
            return false;
        }
        return i > 0;
    }
}
