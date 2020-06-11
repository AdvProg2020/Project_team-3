package View.Menus.MenuController.AdminMenu;

import Controller.RequestController;
import View.Menus.SceneSwitcher;
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
      String message= RequestController.getInstance().declineRequest(requestId);
      if(message.startsWith("Successful")){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setContentText(message);
         alert.showAndWait();
         exit(null);
      }
      if(message.startsWith("Error")){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setContentText(message);
         alert.showAndWait();
         exit(null);
      }
   }

   public void accept(MouseEvent mouseEvent) {
      String message= RequestController.getInstance().acceptRequest(requestId);
      if(message.startsWith("Successful")){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setContentText(message);
         alert.showAndWait();
         exit(null);
      }
      if(message.startsWith("Error")){
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setContentText(message);
         alert.showAndWait();
         exit(null);
      }
   }

   public void exit(MouseEvent mouseEvent) {
      SceneSwitcher.getInstance().setSceneTo("ManageRequests");
   }
}
