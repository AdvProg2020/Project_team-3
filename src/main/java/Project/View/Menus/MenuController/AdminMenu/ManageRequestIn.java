package Project.View.Menus.MenuController.AdminMenu;

import Project.Controller.RequestController;
import Project.View.Menus.MusicManager;
import Project.View.Menus.SceneSwitcher;
import Project.View.CLI.View;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManageRequestIn {
   private static String requestId;
   @FXML private TextField detail;
   @FXML private AnchorPane pane;
   @FXML public void initialize() {
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");
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
