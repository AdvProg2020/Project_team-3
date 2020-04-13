import java.util.*;

public class Item {
    private static ArrayList<String>allItemsId=new ArrayList<>();
    private String id;
    private String state;
    private String description;
    private String name;
    private String brand;
    private int timesBought;
    private int price;
    private int inStock;
    private HashMap<String,String > attributes;
    private String sellerName;
    private String categoryName;
    private static String idCount="00000000";
    private ArrayList<Rating>allRatings;
    private ArrayList<Comment>allComments;
    //constructor
    public Item(String name , String brand , String description , String state, int price , String sellerName , String categoryName , boolean doesHave,HashMap<String,String> attributes,int inStock){
        this.name=name;
        this.brand=brand;
        this.description=description;
        this.state=state;
        this.price=price;
        this.timesBought=0;
        this.sellerName=sellerName;
        this.categoryName=categoryName;
        this.attributes=attributes;
        this.inStock=inStock;
        this.id=idCount;
        idCount=Controller.getInstance().addId(idCount);
        timesBought=0;
        allRatings=new ArrayList<>();
        allComments=new ArrayList<>();
    }
    //getters

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


}
