package View.Menus.MenuController.SellerMenuController;

import Controller.*;
import View.Menus.SceneSwitcher;
import View.Menus.View;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SellerAddOff {


    @FXML
    private void back(){
        SceneSwitcher.getInstance().setSceneTo("SellerMenu");
    }
    @FXML
    private void logout(){
        UserController.getInstance().logout();
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
        errorLabel.setText("");
        allItems.getItems().clear();
        ArrayList<String> availableItems = SortAndFilterController.getInstance().show(UserController.getInstance().getSellerItems());
        ArrayList<String> toBeRemoved = new ArrayList<>();
        for(String item:availableItems){
            String id = item.substring(4,9);
            if(ItemAndCategoryController.getInstance().getItemById(id).isInSale()){
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
        //age darsad va dota tarikh valid bodan
        //miad sale request mide be yaro
        if(offPercentage.getStyle().contains("red")){
            errorLabel.setText("Enter a valid off percentage.");
            errorLabel.setTextFill(Color.rgb(255,0,0));
            return;
        }
        try{
            int percent = Integer.parseInt(offPercentage.getText());
            LocalDateTime start=getDate(startDate.getValue().toString());
            LocalDateTime end=getDate(endDate.getValue().toString());
            String message= SaleAndDiscountCodeController.getInstance().addSale(start,end,percent,selectedItemsID);
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
        Alert alert = new Alert(Alert.AlertType.valueOf(type));
        alert.setContentText(message);
        alert.showAndWait();
    }


    protected static LocalDateTime getDate(String dateString){
        LocalDateTime date;
        dateString=dateString.substring(8,10)+"/"+dateString.substring(5,7)+"/"+dateString.substring(0,4)+" 12:12";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try{
            date = LocalDateTime.parse(dateString,dateTimeFormatter);
            return date;
        }catch (Exception e){
            System.out.println(View.ANSI_RED+"Invalid date. Try again."+View.ANSI_RESET);
            return null;
        }
    }

}
