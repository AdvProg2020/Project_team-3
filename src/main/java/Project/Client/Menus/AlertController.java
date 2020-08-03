package Project.Client.Menus;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class AlertController {
    @FXML private Text message;
    @FXML private Button button;
    private static String text;

    @FXML private void initialize(){
        message.setText(text);
        button.setOnAction(e -> {
            SceneSwitcher.getInstance().closeAlert();
        });
    }

    public static void setText(String newText){
        text = newText;
    }
}
