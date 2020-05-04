package Model.Users;

import Model.Cart;
import Model.Logs.BuyLog;

import java.util.ArrayList;

public class Buyer extends User {

    private double money;
    private ArrayList<BuyLog> buyLogs;
    private Cart cart;
    private ArrayList<String>allDiscounts;
    public Buyer(double money,String username, String password, String name, String lastName, String email, String number) {
        super(username,password,name,lastName,email,number,"Buyer");
        this.money=money;
        buyLogs=new ArrayList<>();
        allDiscounts=new ArrayList<>();
        cart=new Cart(username);
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

    public ArrayList<BuyLog> getBuyLogs() {
        return buyLogs;
    }

    public void addBuyLog(BuyLog buyLog){buyLogs.add(buyLog);}

    public void assignCart(Cart cart){
        this.cart=cart;
    }

    public Cart getCart() {
        return cart;
    }

    public boolean doesHaveEnoughMoneyToBuyCart(){
        return false;
    }

    public String buy(){
    if(doesHaveEnoughMoneyToBuyCart()==false){
        return "Error: dont have enough money";
    }
        return "bayad buylog sakhte shavad";
    }

    public void addDiscount(String discountId){
        this.allDiscounts.add(discountId);
    }

    public void removeDiscount(String discountID){
        if(this.allDiscounts.contains(discountID)) this.allDiscounts.remove(discountID);
    }

    public boolean hasBoughtInPast(){
        return false;
    }

    public boolean hasDiscountID(String id){
        return  this.allDiscounts.contains(id);
    }

    public String getDiscountById(String id){
        if(this.hasDiscountID(id)){
            for(String iterateID:allDiscounts){
                if(iterateID.equals(id)) return iterateID;
            }

        }
        return null;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }


}
