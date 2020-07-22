package Project.Client.Model;




import Project.Client.MakeRequest;
import Server.Controller.SaleAndDiscountCodeController;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Item {
    private String id;
    private String state;
    private String description;
    private String name;
    private String brand;
    private int timesBought; // <= (4Tir) ino dast nazanid shayad badan bekhaim !
    private double price;
    private int inStock;
    private int viewCount;
    private HashMap<String, String> attributes;
    private String sellerName;
    private String categoryName;
    private ArrayList<String> buyerUserName;
    private ArrayList<Rating> allRatings;
    private ArrayList<Comment> allComments;
    private String saleId;

    private String imageName;
    private String videoName;

    private String addedTime;


    public double getPrice() {
        return price;
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

    public String getImageName() {
        return imageName;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getDescription() {
        return description;
    }

    public double getRating() {
        double ratingSum = 0;
        if (allRatings.size() == 0) {
            return 0;
        } else {
            for (Rating rating : allRatings) {
                ratingSum += rating.getScore();
            }
            return ratingSum / allRatings.size();
        }
    }

    //setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
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

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public ArrayList<Comment> getAllComments() {
        return allComments;
    }

    public int getViewCount() {
        return viewCount;
    }

    public ArrayList<String> getBuyerUserName() {
        return buyerUserName;
    }

    public boolean isBuyerWithUserName(String userName) {
        if (buyerUserName.contains(userName)) return true;
        return false;
    }




    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    public void setAttribute(String attributeName, String value) {
        if (!attributes.containsKey(attributeName)) return;
        attributes.replace(attributeName, value);
    }


    public String toSimpleString() {
        return id + "        " + name + "        " + price;
    }

    public void addViewsBy(int count) {
        this.viewCount += count;
    }

    public void addTimesBoughtBy(int count) {
        this.timesBought += count;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(LocalDateTime addedTime) {
        this.addedTime = addedTime.toString();
    }

    public String getSaleId() {
        return saleId;
    }

    @Override
    public String toString() {
        String string = name + "\nID: " + id + "\nSeller:" + sellerName + "\nStock:" + inStock + "\nPrice:" + price;
        if (MakeRequest.isInSaleItem(id)) {
            Sale sale=MakeRequest.makeGetSale(saleId);
            string += "\nprice after sale: " + price * (100 - sale.getPercent()) / 100;
        }
        string += "\nRating= " + getRating();
        return string;
    }

    public String showIdWithName() {
        return "id: " + id + " name: " + name + " price=" + price;
    }

}
