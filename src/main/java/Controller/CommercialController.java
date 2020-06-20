package Controller;

import Model.Users.Seller;

import java.util.ArrayList;

public class CommercialController {
   private ArrayList<String> commercialItemRequest;
   private ArrayList<String> acceptedItemId;
   private String itemCommercial;
   private static  CommercialController commercialController;

   private CommercialController(){
      commercialItemRequest=new ArrayList<>();
      acceptedItemId=new ArrayList<>();
   }

   public static CommercialController getInstance() {
      if (commercialController == null)
         commercialController = new CommercialController();
      return commercialController;
   }

   public String addCommercialRequest(String id){
      if(acceptedItemId.contains(id)) return "Error:Item is already in commercial";
      if(commercialItemRequest.contains(id)) return "Error:you have already requested commercial for this item";
      commercialItemRequest.add(id);
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
        return "Successful:";
     }
     acceptedItemId.remove(id);
     return "Error: item's seller doesnt have sufficient money";
   }

   public void declineCommercial(String id){
      commercialItemRequest.remove(id);
   }

   public void removeCommercial(String id){
      acceptedItemId.remove(id);
   }

   public String getRandomItemId(){
    return acceptedItemId.get((int)Math.random()*acceptedItemId.size());
   }

   public ArrayList<String> getAcceptedItemId() {
      return acceptedItemId;
   }

   public ArrayList<String> getCommercialItemRequest() {
      return commercialItemRequest;
   }
}
