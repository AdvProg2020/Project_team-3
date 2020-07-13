package Server.Controller;

import Server.Model.Item;
import Server.Model.Requests.Request;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommercialControllerTest {


   private  void registerSellerAndAddItem() {
      deleteJunk();
      UserController.getInstance().registerSeller(500,"Alireza","alireza79",
              "reza","pishro","alireza@gmail.com","33824264","benz");
      ArrayList<Request> allRequests= RequestController.getInstance().getAllRequestFromDataBase();
      for(Request request:allRequests){
         RequestController.getInstance().acceptRequest(request.getRequestId());
      }
      UserController.getInstance().login("Alireza","alireza79");
      for (Item item : ItemAndCategoryController.getInstance().getAllItemFromDataBase()) {
         ItemAndCategoryController.getInstance().deleteItem(item.getId());
      }
      ArrayList<Request>allRequest=RequestController.getInstance().getAllRequestFromDataBase();
      for(Request request:allRequest){
         RequestController.getInstance().acceptRequest(request.getRequestId());
      }
      HashMap<String,String> attributes=new HashMap<>();
      System.out.println(ItemAndCategoryController.getInstance().addItem("Vacuum345","Benz","this is vaccum",500,10,"Project.Main",
              attributes));
      ArrayList<Request>allRequess=RequestController.getInstance().getAllRequestFromDataBase();
      System.out.println(allRequess.size());
      for(Request request:allRequess){
         System.out.println(request.toString());
         RequestController.getInstance().acceptRequest(request.getRequestId());
      }
   }

   private void deleteJunk(){
      UserController.getInstance().logout();
      UserController.getInstance().login("admin","12345");
      //for (User user : UserController.getInstance().getAllUserFromDataBase()) {
      //   UserController.getInstance().deleteUser(user.getUsername());
      //}
      UserController.getInstance().logout();
   }

   @Test
   public void getInstance() {
      Database.getInstance().initiate();
      CommercialController commercialController=CommercialController.getInstance();
      assertNotNull(commercialController);
   }


   @Test
   public void addCommercial(){
      registerSellerAndAddItem();
      assertEquals(CommercialController.getInstance().addCommercialRequest(ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId()),"Successful: your request has been sent to the admin");
      assertEquals(CommercialController.getInstance().addCommercialRequest(ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId()),"Error:you have already requested commercial for this item");
      assertEquals(CommercialController.getInstance().acceptCommercial(CommercialController.getInstance().getCommercialItemRequest().get(0)),"Successful:");
      assertEquals(CommercialController.getInstance().addCommercialRequest(ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId()),"Error:Item is already in commercial");
      CommercialController.getInstance().removeCommercial(ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId());
      assertEquals(CommercialController.getInstance().addCommercialRequest(ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId()),"Successful: your request has been sent to the admin");
      CommercialController.getInstance().declineCommercial(CommercialController.getInstance().getCommercialItemRequest().get(0));
      deleteJunk();
   }
}
