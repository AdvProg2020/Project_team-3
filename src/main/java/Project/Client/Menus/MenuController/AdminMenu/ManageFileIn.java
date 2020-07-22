package Project.Client.Menus.MenuController.AdminMenu;

import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;


public class ManageFileIn {
   static String fileName;

   public static void setFileName(String fileName) {
      ManageFileIn.fileName = fileName;
   }

   public void initialize(){
      MusicManager.getInstance().setSongName("first.wav");}

   public void close(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      SceneSwitcher.getInstance().setSceneTo("ManageFiles");
      SceneSwitcher.getInstance().closeSecondStage();
   }

   public void delete(MouseEvent mouseEvent) {
      MusicManager.getInstance().playSound("Button");
      String message= MakeRequest.makeDeleteProductAdminRequest(fileName);
      Alert alert=new Alert(Alert.AlertType.INFORMATION);
      alert.setContentText(message);
      alert.showAndWait();
      close(null);
   }

}
