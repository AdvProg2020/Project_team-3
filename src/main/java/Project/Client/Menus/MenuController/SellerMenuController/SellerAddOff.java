package Project.Client.Menus.MenuController.SellerMenuController;

import Project.Client.Client;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SellerAddOff {

    @FXML private AnchorPane pane;
    @FXML
    private void back()
    {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }
    @FXML
    private void logout(){
        MakeRequest.makeLogoutRequest();
        SceneSwitcher.getInstance().clearRecentScene();
        Client.getInstance().setBankAccountToken("");
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private TextField offPercentage;
    @FXML private ListView allItems;
    @FXML private ListView saleItems;
    @FXML private Label errorLabel;
    private ArrayList<String> selectedItemsID=new ArrayList<>();

    public void updatePercent(KeyEvent keyEvent) {
        String text=offPercentage.getText();
        if(text.isEmpty())  return;
        try {
            int per = Integer.parseInt(text);
            if ((per > 100) || (per <= 0)) {
                offPercentage.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
            } else{
                offPercentage.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
            }
        }catch (Exception e){
            offPercentage.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        }
    }

    @FXML
    private void initialize(){
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("first.wav");
        errorLabel.setText("");
        allItems.getItems().clear();
        ArrayList<String> availableItems = MakeRequest.makeGetAllSellerItems();
        System.out.println();
        ArrayList<String> toBeRemoved = new ArrayList<>();
        for(String item:availableItems){
                String id = item.substring(4, 9);
                if (MakeRequest.isInSaleItem(id)) {
                    toBeRemoved.add(item);
                }
        }
        availableItems.removeAll(toBeRemoved);

        for (Object item : availableItems) {
            allItems.getItems().add(item);
        }
        if(allItems.getItems().isEmpty())
            allItems.getItems().add("You don't have any item that isn't on sale.");
    }

    public void selectItem() {
        int index=allItems.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index==-1)
            return;
        String id=allItems.getItems().get(index).toString().substring(4,9);
        System.out.println(id);
        if(selectedItemsID.contains(id)){
            selectedItemsID.remove(id);
            saleItems.getItems().remove(id);
        }else {
            selectedItemsID.add(id);
            saleItems.getItems().add(id);
        }
        allItems.getSelectionModel().clearSelection();
    }


    @FXML
    private void createSale(){
        if(offPercentage.getStyle().contains("red")){
            errorLabel.setText("Enter a valid off percentage.");
            errorLabel.setTextFill(Color.rgb(255,0,0));
            return;
        }
        try{
            int percent = Integer.parseInt(offPercentage.getText());
            String message=MakeRequest.makeAddSaleRequest(startDate.getValue().toString(),endDate.getValue().toString(),percent,selectedItemsID);
            showAlertBox(message,"INFORMATION");
            if(message.startsWith("Successful:")) {
                back();
            }else{
                reset();
            }
        }catch (Exception e){

        }
    }

    @FXML
    private void reset() {
        offPercentage.clear();
        saleItems.getItems().clear();
        selectedItemsID.clear();
        initialize();
    }

    protected static void showAlertBox(String message,String type){
        if(type.equalsIgnoreCase("Error")){
            MusicManager.getInstance().playSound("error");
        }else {
            MusicManager.getInstance().playSound("notify");
        }
        SceneSwitcher.getInstance().sendAlert(type.equalsIgnoreCase("error"),message);

    }




}
