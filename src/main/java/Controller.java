import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private static Controller controller;

    private Admin admin;
    private ArrayList<User> allUsers = new ArrayList<>();
    private ArrayList<Sale> allSales = new ArrayList<>();
    private ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
    private ArrayList<Item> allItems = new ArrayList<>();
    public ArrayList<Request> allRequests = new ArrayList<>();
    public ArrayList<Cart> allCarts = new ArrayList<>();
    private User currentOnlineUser;
    private Category mainCategory;
    private Category currentCategory;
    private Cart currentShoppingCart;
    private Menu currentMenu;
    private ArrayList<Item> currentViewableItems = new ArrayList<>();

    private Controller() {
    }

    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller();
        return controller;
    }

    public void loadGson() {   //should be called to initiate saved Gsons

    }

    public void saveGson() {

    }

    public void processCommand(String command) {
        currentMenu.execute(command);
    }

    public User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean isThereUserWithUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean addUser(User user){
        return false;
    }

    public boolean isThereSaleWithId(String id){
        return false;
    }

    public Sale getSaleById(String id) {
        for (Sale sale : allSales) {
            if (sale.getId().equals(id)) {
                return sale;
            }
        }
        return null;
    }
    public void addSale(Sale sale){}

    public void addDiscountCode(DiscountCode discountCode){}

    public void deleteSale(String id){}

    public void deleteDiscountCode(String id){}

    public void deleteItem(String id){}

    public void deleteRequest(String id){}


    public boolean isThereItemWithId(String id){
        return false;
    }

    public boolean isThereRequestWithId(String id){
        return false;
    }

    public boolean isThereDiscountCodeWithId(String id){
        return false;
    }




    public Item getItemById(String id) {
        for (Item item : allItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public DiscountCode getDiscountCodeById(String id) {
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.getDiscountId().equals(id)) {
                return discountCode;
            }
        }
        return null;
    }

    public Request getRequestById(String id){
        return null;
    }

    public void Logout() {

    }

    public boolean addItem(Item item) {
        return false;
    }

    public void removeItemByID(String itemID){
        for(Item item :allItems){
            if(item.getId().equals(itemID)){
                allItems.remove(item);
            }
        }
    }

    public void registerBuyer(double money, String username, String password, String name, String lastName, String email, String number) {

    }

    public void registerSeller(String username, String password, String name, String lastName, String email, String number) {

    }

    public void deleteUser(String Username) {

    }


    public boolean searchItemInCategory(String categoryName, String itemId) {
        return false;
    }

    public String compare(String itemId1, String itemId2) {
        return "hello";
    }

    public void addToBucket(String itemId) {

    }

    public void removeItemFromBucket(String itemId) {

    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void comment(String text, String itemId) {

    }

    public void rate(int score, String itemId) {

    }

    public Boolean addCategory(String Name) {
        return false;
    }

    public Boolean Buy() {
        return false;
    }

    public boolean isAValidCommand(String command) {
        if (command.length() > 3) return false;
        int commandNumber;
        try {
            commandNumber = Integer.parseInt(command);
        } catch (Exception e) {
            return false;
        }
        return commandNumber >= 0;
    }

    public String addId(String id) {
        int index = id.length() - 1;
        while (true) {
            char value = id.charAt(index);
            if (id.charAt(index) != '9') {
                value++;
                id = id.substring(0, index) + value + id.substring(index, id.length() - 1);
                return id;
            } else {
                id = id.substring(0, index) + '0' + id.substring(index, id.length() - 1);
                index--;
            }
        }
    }


    public void addUserRequest(String requestID, User newUser) {
        AccountRequest newRequest = new AccountRequest(requestID, newUser);
        allRequests.add(newRequest);
    }

    public void addSaleRequest(String requestId, Sale newSale) {
        SaleRequest newRequest = new SaleRequest(requestId, newSale);
        allRequests.add(newRequest);
    }

    public void addItemRequest(String requestId, Item newItem) {
        ItemRequest newRequest = new ItemRequest(requestId, newItem);
        allRequests.add(newRequest);
    }

    public void editSaleRequest(String requestId, String saleID, String changedFiled, String newFieldValue) {
        SaleEdit newRequest = new SaleEdit(requestId, saleID, changedFiled, newFieldValue);
        allRequests.add(newRequest);
    }

    public void editItemRequest(String requestId, String saleID, String changedFiled, String newFieldValue) {
        ItemEdit newRequest = new ItemEdit(requestId, saleID, changedFiled, newFieldValue);
        allRequests.add(newRequest);
    }

    public void acceptRequest(){
        ///inja ba chand rikhti va moshakhas kardan request haye accept shode amaliat marbote ra anjam midim!
    }

    public void declineRequest(){

        ArrayList<Request> toBeRemoved = new ArrayList<>();
        for(Request request:allRequests){
            if(!request.isIsAccepted()){
                toBeRemoved.add(request);
            }
        }
        allRequests.removeAll(toBeRemoved);
    }

    public static Matcher getMatcher(String string,String regex){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(string);
    }

    public ArrayList<Item> getCurrentViewableItems() {
        return currentViewableItems;
    }

    public void sortBy(String sortByWhat){

    }

    public void filterBy(String filterByWhat){

    }

    public ArrayList<Cart> getUsersCarts(String username){
        return null;
    }

    public Category getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public Cart getCurrentShoppingCart() {
        return currentShoppingCart;
    }

    public void setCurrentShoppingCart(Cart currentShoppingCart) {
        this.currentShoppingCart = currentShoppingCart;
    }

    public User getCurrentOnlineUser() {
        return currentOnlineUser;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

}