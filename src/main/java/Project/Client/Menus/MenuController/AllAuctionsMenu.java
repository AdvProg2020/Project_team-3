package Project.Client.Menus.MenuController;

import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Auction;
import Project.Client.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class AllAuctionsMenu {

    @FXML private ListView listView;

    public void back(ActionEvent actionEvent)
    {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().back();
    }

    @FXML private void initialize(){
        //miad list ro update mikone
        //on click : berim auction menu
        ArrayList<Auction> allAuctions = MakeRequest.getAllAuctions();

        for(Auction auction:allAuctions){
            listView.getItems().add(auction.getId()+"   for item "+auction.getItemID()+"  current bid:"+auction.getHighestBid()+"    ends:"+auction.getEndTime());
        }

    }

    @FXML private void select(){
        //berim
    }
}
