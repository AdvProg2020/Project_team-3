package Model.Users;

import Model.Sale;
import Model.Logs.SaleLog;

import java.util.ArrayList;

public class Seller extends User {
    private String companyName;
    private ArrayList<SaleLog> sellLogs;
    private ArrayList<String> soldItemsId;
    private ArrayList<String> allItemsId;
    private ArrayList<String> allSaleId;
    private double money;
    public Seller( double money,String username,String password, String name, String lastName, String email, String number,String companyName) {
        super(username,password,name,lastName,email,"Model.Users.Seller",number);
        this.companyName=companyName;
        sellLogs=new ArrayList<>();
        soldItemsId=new ArrayList<>();
        allItemsId=new ArrayList<>();
        allSaleId=new ArrayList<>();
        this.money=money;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ArrayList<SaleLog> getSellLogs(){
        return sellLogs;
    }

    public ArrayList<String> getAllItemsId() {
        return allItemsId;
    }

    public ArrayList<String> getAllSaleId() {
        return allSaleId;
    }

    public ArrayList<String> getSoldItemsId() {
        return soldItemsId;
    }

    public void addSaleLog(SaleLog saleLog){}

    public void addSoldItemID(String id){}

    public boolean hasSoldItem(String id){
        return soldItemsId.contains(id);
    }

    public void addAllItemID(String id){}

    public boolean hasItem(String id){
        return allItemsId.contains(id);
    }

    public void addAllSaleId(String id){}

    public boolean hasSale(String id){
        return allSaleId.contains(id);
    }
    
    public void addSale(double amount) {

    }

    public void addItemToSale(String itemid){

    }


    public String showAllSale() {
       return "hello";
    }

    public String printItems(){
       return "hello";
    }


    public void editSale(Sale sale, Sale updatedSale){

      }

     public void removeItem(String itemId){

      }

      public boolean isSaleInPast(){
        return false;
      }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney(){
        return this.money;
    }

    public boolean isSoldInPast(){
        return false;
    }


}