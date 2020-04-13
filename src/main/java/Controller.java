import java.util.ArrayList;

public class Controller {
    private static Controller controller;

    private Admin admin;
    private ArrayList <User> allUsers = new ArrayList<>();
    private ArrayList <Sale> allSales = new ArrayList<>();
    private ArrayList <DiscountCode> allDiscountCodes = new ArrayList<>();
    private ArrayList <Item> allItems = new ArrayList<>();
    private User currentOnlineUser;
    private Category mainCategory;
    private Category currentCategory;
    private Cart currentShoppingCart;
    private ArrayList <Item> currentViewableItems = new ArrayList<>();

    private Controller(){ }

    public static Controller getInstance(){
        if(controller==null)
            controller = new Controller();
        return controller;
    }

    public void initiate(){   //should be called to initiate saved Gsons

    }


    public User getUserByUsername (String username){
        for(User user:allUsers){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public boolean isThereUserByName(String name){
        for (User user : allUsers) {
            if(user.getUsername().equals(name)){
                return true;
            }
        }
              return false;
    }

    public Sale getSaleById(String id){
        for (Sale sale : allSales) {
            if(sale.getId().equals(id)){
                return sale;
            }
        }
               return null;
    }

    public Item getItemById(String id){
        for (Item item : allItems) {
            if(item.getId().equals(id)) {
                return item;
            }
        }
                return null;
    }

    public DiscountCode getDiscountCodeById(String id){
        for (DiscountCode discountCode : allDiscountCodes) {
            if(discountCode.getDiscountId().equals(id)){
                return discountCode;
            }
        }
               return null;
    }

    public void Logout(){

    }

    public void registerBuyer(double money,String username, String password, String name, String lastName, String email, String number){

    }

    public void registerSeller(String username, String password, String name, String lastName, String email, String number){

    }

    public void deleteUser(String Username){

    }

    public boolean searchItemInCategory(String categoryName,String itemId){
       return false;
    }

    public String compare(String itemId1,String itemId2){
        return "hello";
    }

    public void addToBucket(String itemId){

    }

    public void removeItemFromBucket(String itemId){

    }


    public boolean isAValidCommand(String command){
        if(command.length()>3) return false;
        int commandNumber;
        try {
            commandNumber = Integer.parseInt(command);
        }catch (Exception e){
            return false;
        }
        return commandNumber>=0;
    }
    public  String addId(String id) {
        int index=id.length()-1;
        while(true){
            char value=id.charAt(index);
            if(id.charAt(index)!='9'){
                value++;
                id=id.substring(0,index)+value+id.substring(index,id.length()-1);
                return id;
            }
            else{
                id=id.substring(0,index)+'0'+id.substring(index,id.length()-1);
                index--;
            }
        }
    }
}