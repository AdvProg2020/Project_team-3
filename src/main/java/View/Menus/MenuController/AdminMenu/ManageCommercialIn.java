package View.Menus.MenuController.AdminMenu;

import Controller.CommercialController;
import Controller.RequestController;
import View.Menus.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ManageCommercialIn {

   private static String requestId;

   public static void setRequestId(String requestId) {
      ManageCommercialIn.requestId = requestId;
   }


   public void accept(MouseEvent mouseEvent) {
     String message= CommercialController.getInstance().acceptCommercial(requestId);
     showAlertBox(message);
     SceneSwitcher.getInstance().setSceneTo("ManageRequests");
   }

   public void decline(MouseEvent mouseEvent) {
      CommercialController.getInstance().declineCommercial(requestId);
      showAlertBox("Successful: request declined");
      SceneSwitcher.getInstance().setSceneTo("ManageRequests");
   }

   public void back(ActionEvent actionEvent) {
      SceneSwitcher.getInstance().setSceneTo("ManageRequests");
   }

   private void showAlertBox(String message){
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setContentText(message);
      alert.showAndWait();
   }
}
