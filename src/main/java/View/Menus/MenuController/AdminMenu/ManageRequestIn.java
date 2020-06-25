package View.Menus.MenuController.AdminMenu;

import Controller.RequestController;
import View.Menus.MusicManager;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ManageRequestIn {
   private static String requestId;
   @FXML
   private TextField detail;

   @FXML
   public void initialize() {
      detail.setText(RequestController.getInstance().getRequestDetail(requestId));
   }

   public static void setRequestId(String requestId) {
      ManageRequestIn.requestId = requestId;
   }

   public void decline(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      String message= RequestController.getInstance().declineRequest(requestId);
      if(message.startsWith("Successful")){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setContentText(message);
         alert.showAndWait();
         back();
      }
      if(message.startsWith("Error")){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setContentText(message);
         alert.showAndWait();
         back();
      }
   }

   public void accept(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      String message= RequestController.getInstance().acceptRequest(requestId);
      if(message.startsWith("Successful")){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setContentText(message);
         alert.showAndWait();
         back();
      }
      if(message.startsWith("Error")){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setContentText(message);
         alert.showAndWait();
         back();
      }
   }

   public void back() {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("ManageRequests");
      SceneSwitcher.getInstance().closeSecondStage();
   }
}
