package View.Menus.ShopAndDiscountMenu;


import Controller.ItemAndCategoryController;
import Controller.RequestController;
import Controller.SortAndFilterController;
import Controller.UserController;
import Model.Requests.Request;
import Model.Users.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ShopMenuTest {

   @Test
   public void addItem() {
      UserController.getInstance().registerSeller(500,"testShop","alireza79",
              "reza","pishro","alireza@gmail.com","33824264","benz");
      acceptRequests();
      User seller =UserController.getInstance().getUserByUsername("testShop");
      System.out.println(UserController.getInstance().login(seller.getUsername(),seller.getPassword()));
      HashMap<String,String> attributes=new HashMap<>();
      attributes.put("price","cheap");
      HashMap<String , String>attributes1=new HashMap();
      attributes1.put("price","expensive");
      HashMap<String,String> attributes2=new HashMap<>();
      attributes2.put("price","cheap");
      ItemAndCategoryController.getInstance().addItem("Vacuum345search","Benz"
              ,"this is vaccum",500,10,"Main",
              attributes);
      ItemAndCategoryController.getInstance().addItem("Oven456search","Benz"
              ,"this is oven",5000,10,"Main",attributes1);
      ItemAndCategoryController.getInstance().addItem("microwave67","Benz",
              "this is microWave",600,10,"Main",attributes2);
      UserController.getInstance().logout();
      ArrayList<Request> allRequests= RequestController.getInstance().getAllRequestFromDataBase();
      for(Request request:allRequests){
         RequestController.getInstance().acceptRequest(request.getRequestId());
      }
   }

   public void acceptRequests(){
      ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
      for(Request request:allRequests){
         RequestController.getInstance().acceptRequest(request.getRequestId());
      }
   }

   public void deleteJunk(){
      UserController.getInstance().login("admin","12345");
      System.out.println(UserController.getInstance().deleteUser("testShop"));
   }

   @Test
   public void getInstance() {
      ShopMenu shopMenu=ShopMenu.getInstance();
      assertNotNull(shopMenu);
   }

   @Test
   public void execute() {
   }

   @Test
   public void help() {
   }

   @Test
   public void setCurrentCategory() {
   }

   @Test
   public void getCurrentCategory() {
   }

   @Test
   public void showAllItems() {
      addItem();
      SortAndFilterController.getInstance().activateFilterPriceRange(100,1000);
      SortAndFilterController.getInstance().activateFilterAvailability();
      SortAndFilterController.getInstance().activateFilterSellerName("testShop");
      SortAndFilterController.getInstance().activateFilterName("search");
      SortAndFilterController.getInstance().activateFilterCategoryName("Main");
      SortAndFilterController.getInstance().activateFilterBrandName("Benz");
      SortAndFilterController.getInstance().activateFilterAttribute("price","cheap");
      ShopMenu.getInstance().setCurrentCategory("Main");
      ShopMenu.getInstance().showAllItems();
      deleteJunk();
   }

   @Test
   public void showCategory() {
   }

   @Test
   public void viewSubCategories() {
   }

   @Test
   public void viewAllCategories() {
   }
}
