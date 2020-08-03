package Project.Client.Menus.MenuController.AdminMenu;


import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.CLI.View;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ManageCommercialIn {

   private static String requestId;
   @FXML private AnchorPane pane;
   public static void setRequestId(String requestId) {
      ManageCommercialIn.requestId = requestId;
   }

   public void initialize(){
      View.setFonts(pane);
      MusicManager.getInstance().setSongName("first.wav");}

   public void accept(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      String message=" ";
     // String message = CommercialController.getInstance().acceptCommercial(requestId);
      showAlertBox(message);
      SceneSwitcher.getInstance().closeSecondStage();
      SceneSwitcher.getInstance().closeSecondStage();
      SceneSwitcher.getInstance().setSceneTo("ManageRequests");
   }

   public void decline(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      //CommercialController.getInstance().declineCommercial(requestId);
      showAlertBox("Successful: request declined");
      SceneSwitcher.getInstance().closeSecondStage();
      SceneSwitcher.getInstance().closeSecondStage();
      SceneSwitcher.getInstance().setSceneTo("ManageRequests");
   }


   private void showAlertBox(String message) {
      MusicManager.getInstance().playSound("notify");
      SceneSwitcher.getInstance().sendAlert(false,message);
   }
}
