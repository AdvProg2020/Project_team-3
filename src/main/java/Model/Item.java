package Model;

import Controller.Controller;
import Controller.Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Item {
    private String id;
    private String state;
    private String description;
    private String name;
    private String brand;
    private int timesBought;
    private double price;
    private int inStock;
    private int viewCount;
    private HashMap<String,String > attributes;
    private ArrayList<String> attributesKey;
    private String sellerName;
    private String categoryName;
    private ArrayList<String>buyerUserName;
    private ArrayList<Rating>allRatings;
    private ArrayList<Comment>allComments;
    private Sale sale;
    //constructor
    public Item(String name , String brand , String description , String state, double price , String sellerName , String categoryName , HashMap<String,String> attributes,ArrayList<String> attributesKey,int inStock){
        this.name=name;
        this.brand=brand;
        this.description=description;
        this.state=state;
        this.price=price;
        this.timesBought=0;
        this.sellerName=sellerName;
        this.categoryName=categoryName;
        this.attributes=attributes;
        this.attributesKey=attributesKey;
        this.inStock=inStock;
        this.id= Controller.getInstance().getAlphaNumericString(Controller.getInstance().getIdSize(),"Items");
        timesBought=0;
        allRatings=new ArrayList<>();
        allComments=new ArrayList<>();
        buyerUserName=new ArrayList<>();
    }
    //getters

    public ArrayList<String> getAttributesKey() {
        return attributesKey;
    }

    public boolean isInStock(){
        if(inStock==0)
            return false;
        return true;
    }

    public double getPrice() {
        return price;
    }

    public double getPriceWithSale(){
        if(sale==null){
            return price;
        }else{
            return price*sale.getOffPercentage()/100;
        }
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getTimesBought() {
        return timesBought;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public boolean hasUserRated(String username){
        for (Rating rating : allRatings) {
            if(rating.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    public double getRating(){
        double ratingSum=0;
        if(allRatings.size()==0){
            return 0;
        } else{
        for (Rating rating : allRatings) {
            ratingSum+=rating.getScore();
        }
        return ratingSum/allRatings.size();}
    }
    //setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTimesBought(int timesBought) {
        this.timesBought = timesBought;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public String getCategoryName() {
        return categoryName;
    }



    public String getSellerName() {
        return sellerName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }


    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public ArrayList<Comment> getAllComments() {
        return allComments;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void addAttribute(String name,String value){}

    public void editAttribute(String name,String newValue){}

    public void addComment(Comment newComment){
        this.allComments.add(newComment);
        try {
            Database.getInstance().saveItem(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRating(Rating newRating){
        this.allRatings.add(newRating);
        try {
            Database.getInstance().saveItem(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getBuyerUserName() {
        return buyerUserName;
    }

    public boolean isInSale(){
        if(sale==null){
            return false;
        }
            return true;
    }

    public void addBuyerUserName(String userName){
        buyerUserName.add(userName);
    }

    public boolean isBuyerWithUserName(String userName){
        if(buyerUserName.contains(userName)) return true;
        return false;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public String showAttributes(){
        String string="";
        for (String key : attributesKey) {
            string+=key+":"+attributes.get(key)+"/n";
        }
        return string;
    }

    @Override
    public String toString() {
       String string=name+"\nID: "+id+  "\nSeller:"+sellerName+"\nStock:"+ isInStock()+"\nPrice:"+price;
       if(isInSale()){
           string+="\nprice after sale: " +price*sale.getOffPercentage()/100;
       }
       string+="\nRating= "+getRating();
       return string;
    }

    public String toSimpleString(){
        return"ID:"+id+"   Name"+name+"   Price:"+price;
    }

    public void addViewsBy(int count){
        this.viewCount+=count;
    }

    public void addTimesBoughtBy(int count){
        this.timesBought += count;
    }
}
