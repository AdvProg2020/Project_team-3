import java.util.*;

public class Item {
    private static ArrayList<Item>allItems=new ArrayList<>();
    private String id;
    private String state;
    private String description;
    private String name;
    private String brand;
    private int timesBought;
    private int price;
    private Seller seller;
    private Category category;
    private boolean DoesHave;
    private ArrayList<Rating>allRatings;
    private ArrayList<Comment>allComments;
    //constructor
    public Item(String name , String brand , String description , String state
            , String id , int price , int timesBought , Seller seller , Category category , boolean doesHave){
        this.setName(name);
        this.setBrand(brand);
        this.setDescription(description);
        this.setState(state);
        this.setId(id);
        this.setPrice(price);
        this.setTimesBought(timesBought);
        this.setSeller(seller);
        this.setCategory(category);
        this.setDoesHave(doesHave);
        allRatings=new ArrayList<>();
        allComments=new ArrayList<>();
    }

    //getters
    public static ArrayList<Item> getAllItems() {
        return allItems;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isDoesHave() {
        return DoesHave;
    }

    public Seller getSeller() {
        return seller;
    }

    public int getPrice() {
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

    public int getTimesBought() {
        return timesBought;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public ArrayList<Rating> getAllRatings() {
        return allRatings;
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

    public void addComment(Comment newComment){
        this.allComments.add(newComment);
    }

    public void addRating(Rating newRating){
        this.allRatings.add(newRating);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setDoesHave(boolean doesHave) {
        DoesHave = doesHave;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
