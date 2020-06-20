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
      return "Successful:";
   }

   public void acceptCommercial(String id){
     Seller seller=(Seller) UserController.getInstance().getUserByUsername(ItemAndCategoryController.getInstance().getItemById(id).getSellerName());
     double money=seller.getMoney();
     if(money>50){
        seller.setMoney(money-50);
        acceptedItemId.add(id);
     }
   }

   public void declineCommercial(String id){
      commercialItemRequest.remove(id);
   }

   public String getRandomItemId(){
    return acceptedItemId.get((int)Math.random()*acceptedItemId.size());
   }


}
