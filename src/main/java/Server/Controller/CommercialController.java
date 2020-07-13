package Server.Controller;

import Server.Model.Users.Seller;
import Server.Model.Users.User;

import java.io.*;
import java.util.ArrayList;

public class CommercialController implements Serializable{
   private ArrayList<String> commercialItemRequest;
   private ArrayList<String> acceptedItemId;
   private String itemCommercial;
   private static  CommercialController commercialController;

   public CommercialController(){
      commercialItemRequest=new ArrayList<>();
      acceptedItemId=new ArrayList<>();
   }

   public static CommercialController getInstance() {
      if (commercialController == null){
         try {
            File file = new File("Resource/Commercials/commercial.txt");
            if(file.exists()==false) {
               return new CommercialController();
            }
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
            commercialController=(CommercialController) stream.readObject();
         }catch (IOException | ClassNotFoundException  e){
            commercialController=new CommercialController();
         }
      }
      return commercialController;
   }

   public String addCommercialRequest(String id){
      if(acceptedItemId.contains(id)) return "Error:Item is already in commercial";
      if(commercialItemRequest.contains(id)) return "Error:you have already requested commercial for this item";
      commercialItemRequest.add(id);
      saveCommercials();
      User seller=UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(id).getSellerName());
      seller.addRequest(id,"id: "+id+" state:pending "+" info:item with id "+id+" commercial request has been sent to the admin");
      return "Successful: your request has been sent to the admin";
   }

   public String acceptCommercial(String id){
      System.out.println(ItemAndCategoryController.getInstance().getItemById(id).getSellerName());
     Seller seller=(Seller) UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(id).getSellerName());
     double money=seller.getMoney();
     if(money>50){
        seller.setMoney(money-50);
        acceptedItemId.add(id);
        commercialItemRequest.remove(id);
        saveCommercials();
        seller.addRequest(id,"id: "+id+" state:accepted "+" info:item with id "+id+" will be shown in commercials");
        return "Successful:";
     }
     acceptedItemId.remove(id);
      seller.addRequest(id,"id: "+id+" state:declined "+" info:item with id "+id+" cannot be shown in commercials because you dont have sufficient money");
     saveCommercials();
     return "Error: item's seller doesnt have sufficient money";
   }

   public void declineCommercial(String id){
      commercialItemRequest.remove(id);
      User seller=UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(id).getSellerName());
      seller.addRequest(id,"id: "+id+" state:declined "+" info:item with id "+id+" will not be shown in commercials");
      saveCommercials();
   }

   public void removeCommercial(String id){
      acceptedItemId.remove(id);
      saveCommercials();
   }


   public ArrayList<String> getAcceptedItemId() {
      return acceptedItemId;
   }

   public ArrayList<String> getCommercialItemRequest() {
      return commercialItemRequest;
   }

   private void saveCommercials(){
      try {
         File file=new File("Resource/Commercials/commercial.txt");
         if(file.exists()==false) file.createNewFile();
         ObjectOutputStream stream=new ObjectOutputStream(new FileOutputStream(file));
         stream.writeObject(getInstance());
         stream.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void deleteItem(String id){
      acceptedItemId.remove(id);
      commercialItemRequest.remove(id);
      saveCommercials();
   }
}
