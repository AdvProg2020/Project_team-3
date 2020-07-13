package Project.Client.Model.Users;

import Server.Controller.SaleAndDiscountCodeController;
import Server.Model.DiscountCode;
import Server.Model.Logs.BuyLog;

import java.util.ArrayList;
import java.util.HashMap;

public class Buyer extends User {

    private double money;
    private ArrayList<BuyLog> buyLogs;

    public Buyer(double money,String username, String password, String name, String lastName, String email, String number) {
        super(username,password,name,lastName,email,number,"Buyer");
        this.money=money;
        buyLogs=new ArrayList<>();
        //cart=new Cart();
    }

    @Override
    public  String getPersonalInfo(){
        String response="";
        response+="Buyer.\n";
        response+="Name: "+getName()+"\n";
        response+="Surname: "+getLastName()+"\n";
        response+="Email: "+getEmail()+"\n";
        response+="Number: "+getNumber()+"\n";
        response += "money: " + getMoney() + "\n";
        return response;
    }

    public String getBuyLogsString(){
        String ans = "";
        int count=1;
        for(BuyLog buyLog:buyLogs){
            ans+=count+"-";
            ans += buyLog.toSimpleString();
            ans += "\n";
            count++;
        }
        return ans;
    }

    public int getBuyLogSize() {
        return buyLogs.size();
    }

    public void addBuyLog(BuyLog buyLog){buyLogs.add(buyLog);}

    public ArrayList<BuyLog> getBuyLogs() {
        return buyLogs;
    }

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
