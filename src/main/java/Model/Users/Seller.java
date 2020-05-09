package Model.Users;

import Control.ItemAndCategoryController;
import Model.Logs.SaleLog;
import Model.Sale;

import java.util.ArrayList;

public class Seller extends User {
    private String companyName;
    private ArrayList<SaleLog> sellLogs;
    private ArrayList<String> soldItemsId;
    private ArrayList<String> allItemsId;
    private ArrayList<String> allSaleId;
    private boolean valid;
    private double money;

    public Seller(double money, String username, String password, String name, String lastName, String email, String number, String companyName) {
        super(username, password, name, lastName, email, number, "Seller");
        this.companyName = companyName;
        sellLogs = new ArrayList<>();
        soldItemsId = new ArrayList<>();
        allItemsId = new ArrayList<>();
        allSaleId = new ArrayList<>();
        valid = false;
        this.money = money;
    }

    @Override
    public String getPersonalInfo() {
        String response = "";
        response += "You are a seller\n";
        response += "Name: " + getName() + "\n";
        response += "Surname: " + getLastName() + "\n";
        response += "Email: " + getEmail() + "\n";
        response += "Number: " + getNumber() + "\n";
        response += "Company: " + companyName + "\n";
        return response;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ArrayList<SaleLog> getSellLogs() {
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

    public void addSaleLog(SaleLog saleLog) {
    }

    public void addSoldItemID(String id) {
    }

    public boolean hasSoldItem(String id) {
        return soldItemsId.contains(id);
    }

    public void addAllItemID(String id) {
    }

    public boolean hasItem(String id) {
        return allItemsId.contains(id);
    }

    public void addAllSaleId(String id) {
    }

    public boolean hasSale(String id) {
        return allSaleId.contains(id);
    }

    public void addSale(double amount) {

    }

    public void addItemToSale(String itemid) {

    }

    public String printItems() {
        return "hello";
    }


    public void editSale(Sale sale, Sale updatedSale) {

    }

    public void removeItem(String itemId) {

    }

    public boolean isSaleInPast() {
        return false;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return this.money;
    }

    public boolean isSoldInPast() {
        return false;
    }

    public void Validate() {
        this.valid = true;
    }

    public boolean getValid() {
        return valid;
    }

    public String getSaleLogsString(){
        String ans = "";
        for(SaleLog saleLog:sellLogs){
            ans += saleLog.toString();
            ans += "\n";
        }
        return ans;
    }

    public String getAllItemsString(){
        String ans="";
        for(String id:allItemsId){
            ans+= ItemAndCategoryController.getInstance().getItemById(id).toSimpleString();
            ans+="\n";
        }
        return ans;
    }
}