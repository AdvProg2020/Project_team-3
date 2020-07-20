package Project.Client.Menus.MenuController;

import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import javafx.event.ActionEvent;

public class AllAuctionsMenu {

    public void back(ActionEvent actionEvent)
    {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().back();
    }
}
