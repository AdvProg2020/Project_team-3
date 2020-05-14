package Model.Users;

import Controller.SaleAndDiscountCodeController;
import Model.Cart;
import Model.DiscountCode;
import Model.Logs.BuyLog;

import java.util.ArrayList;

public class Buyer extends User {

    private double money;
    private ArrayList<BuyLog> buyLogs;
    private Cart cart;
    public Buyer(double money,String username, String password, String name, String lastName, String email, String number) {
        super(username,password,name,lastName,email,number,"Buyer");
        this.money=money;
        buyLogs=new ArrayList<>();
        cart=new Cart();
    }

    @Override
    public  String getPersonalInfo(){
        String response="";
        response+="You are a buyer.\n";
        response+="Name: "+getName()+"\n";
        response+="Surname: "+getLastName()+"\n";
        response+="Email: "+getEmail()+"\n";
        response+="Number: "+getNumber()+"\n";
        return response;
    }

    public String getBuyLogsString(){
        String ans = "";
        int count=1;
        for(BuyLog buyLog:buyLogs){
            ans+=count+"-";
            ans += buyLog.toSimpleString();
            count++;
        }
        return ans;
    }

    public int getBuyLogSize() {
        return buyLogs.size();
    }

    public void addBuyLog(BuyLog buyLog){buyLogs.add(buyLog);}

    /*public void addDiscount(DiscountCode discountCode){
        this.discountCodes.add(discountCode);
    }

    public void removeDiscount(String discountID){
        DiscountCode toBeRemoved = getDiscountById(discountID);
        discountCodes.remove(toBeRemoved);
    }

    public boolean hasDiscountID(String ID){
        for(DiscountCode discountCode: discountCodes){
            if(discountCode.getDiscountId().equals(ID)) return true;
        }
        return false;
    }

    public DiscountCode getDiscountById(String ID){
        for(DiscountCode discountCode:discountCodes){
            if(discountCode.getDiscountId().equals(ID)) return discountCode;
        }
        return null;
    }*/

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDiscountCodes() {
        String ans = "";
        for(DiscountCode discountCode: SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase()){
            if(discountCode.hasUser(this.getUsername())){
                ans += discountCode.toString();
                ans += "\n";
            }
        }
        return ans;
    }

    public BuyLog getBuyLogByID(int index){
      try{
          return buyLogs.get(index);
      }catch(Exception expection){
          return null;
        }
    }

}
