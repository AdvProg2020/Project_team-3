package Project.Client.Menus.MenuController;

import Project.Client.CLI.View;
import Project.Client.MakeRequest;
import Project.Client.Menus.MusicManager;
import Project.Client.Menus.SceneSwitcher;
import Project.Client.Model.Auction;
import Project.Client.Model.SortAndFilter;
import Project.Client.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;


import java.util.ArrayList;

public class AllAuctionsMenu {

    @FXML private ListView listView;
    @FXML private AnchorPane pane;
    private ArrayList<Auction> allAuctions;

    public void back(ActionEvent actionEvent)
    {
        MusicManager.getInstance().playSound("Button");
        SceneSwitcher.getInstance().setSceneTo("MainMenu");
    }

    @FXML private void initialize(){
        //miad list ro update mikone
        //on click : berim auction menu
        SortAndFilter.getInstance().reset();
        MakeRequest.resetFilter();
        View.setFonts(pane);
        listView.getItems().clear();
        String str = MakeRequest.getAllAuctions();
        allAuctions = ObjectMapper.jsonToAuction(str);
        for(Auction auction:allAuctions){
            listView.getItems().add(auction.getId()+"   for item "+auction.getItemID()+"  current bid:"+auction.getHighestBid()+"    ends:"+auction.getEndTime());
        }

    }

    @FXML private void select(){
        int index=listView.getSelectionModel().getSelectedIndex();
        if(index==-1)
            return;
        String id=listView.getItems().get(index).toString();
        System.out.println(id);
        id = id.substring(0,5);
        listView.getSelectionModel().clearSelection();
        if(getSelected(id)==null){
            initialize();
            return;
        }
        AuctionMenu.setAuction(getSelected(id));
        SceneSwitcher.getInstance().setSceneTo("AuctionMenu");
    }

    private Auction getSelected(String id){
        for(Auction auction:allAuctions){
            if(auction.getId().equals(id)){
                return auction;
            }
        }
        return null;
    }
}
