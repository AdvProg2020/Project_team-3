package Project.Client.Menus.MenuController.SellerMenuController;

import Server.Controller.SaleAndDiscountCodeController;
import Server.Controller.UserController;
import Server.Model.Sale;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SellerEditOff {
    private static String offID;
    @FXML private AnchorPane pane;
    @FXML
    private void back(){
        reset();
        SceneSwitcher.getInstance().setSceneTo("SellerManageOffs");
    }
    @FXML
    private void logout(){
        MusicManager.getInstance().playSound("Button");
        UserController.getInstance().logout();
        SceneSwitcher.getInstance().clearRecentScene();
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    @FXML
    private void initialize(){
        View.setFonts(pane);
        MusicManager.getInstance().setSongName("first.wav");
        reset();
        Sale sale = SaleAndDiscountCodeController.getInstance().getSaleById(offID);
        currentOffPercent.setText(Integer.toString(sale.getOffPercentage()));
        currentStartDate.setText(sale.getStartTime().toString());
        currentEndDate.setText(sale.getEndTime().toString());
        setFonts();
    }

    private void setFonts(){
        currentOffPercent.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        currentStartDate.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        currentEndDate.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));

        errorLabel.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
        newOffPercent.setFont(Font.loadFont("file:src/main/resources/fonts/O.ttf", 12));
    }

    @FXML private Text currentOffPercent;

    @FXML private Text currentStartDate;

    @FXML private Text currentEndDate;

    @FXML private Text errorLabel;

    @FXML private TextField newOffPercent;
    @FXML private DatePicker newStartDate;
    @FXML private DatePicker newEndDate;


    @FXML
    private void editOffPercent(){
        if(newOffPercent.getStyle().contains("red")){
            errorLabel.setText("Enter a valid off percentage.");
            return;
        }
        try {
            String message= SaleAndDiscountCodeController.getInstance().editSale(offID,"off percentage",newOffPercent.getText());
            SellerAddOff.showAlertBox(message,"INFORMATION");

        }catch (Exception e){

        }
        reset();
    }
    @FXML
    private void editStartDate(){
        try {
            LocalDateTime start=getDate(newStartDate.getValue().toString());
            String message= SaleAndDiscountCodeController.getInstance().editSale(offID,"start time",start.toString());
            SellerAddOff.showAlertBox(message,"INFORMATION");
        }catch (Exception e){

        }
        reset();
    }
    @FXML
    private void editEndDate(){
        try {
            LocalDateTime end=getDate(newEndDate.getValue().toString());
            String message= SaleAndDiscountCodeController.getInstance().editSale(offID,"end time",end.toString());
            SellerAddOff.showAlertBox(message,"INFORMATION");
        }catch (Exception e){

        }
        reset();
    }

    public void updatePercent(KeyEvent keyEvent) {
        String text=newOffPercent.getText();
        if(text.isEmpty())  return;
        try {
            int per = Integer.parseInt(text);
            if ((per > 100) || (per <= 0)) {
                newOffPercent.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
            } else{
                newOffPercent.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
            }
        }catch (Exception e){
            newOffPercent.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        }
    }

    @FXML
    private void reset(){
        errorLabel.setText("");
        newOffPercent.setText("");
    }

    public static void setOffID(String newID) {
        offID = newID;
    }

    public static String getOffID() {
        return offID;
    }

    private LocalDateTime getDate(String dateString){
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
