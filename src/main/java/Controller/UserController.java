package Controller;

import Model.Logs.BuyLog;
import Model.Logs.SaleLog;
import Model.Users.Admin;
import Model.Users.Buyer;
import Model.Users.Seller;
import Model.Users.User;
import View.Menus.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class UserController {
    Controller controller = Controller.getInstance();

    private static UserController userController;

    private UserController() {
    }

    public static UserController getInstance() {
        if (userController == null)
            userController = new UserController();
        return userController;
    }

    public User getUserByUsername(String username) {
        String path = "Resource" + File.separator + "Users";
        String name = username + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return null;
        }
        Gson gson = new Gson();
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            if (content.contains("\"type\": \"Admin\"")) {
                return gson.fromJson(content, Admin.class);
            }
            if (content.contains("\"type\": \"Buyer\"")) {
                return gson.fromJson(content, Buyer.class);
            }
            if (content.contains("\"type\": \"Seller\"")) {
                return gson.fromJson(content, Seller.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getCurrentOnlineUser() {
        if(Controller.getInstance().currentOnlineUser == null) return null;
        return getUserByUsername(Controller.getInstance().currentOnlineUser.getUsername());
    }

    public String getCurrentOnlineUserUsername() {
        if(Controller.getInstance().currentOnlineUser == null) return null;
        return Controller.getInstance().currentOnlineUser.getUsername();
    }

    public double currentOnlineUserBalance() {
        if (getCurrentOnlineUser() instanceof Buyer) {
            return ((Buyer) getCurrentOnlineUser()).getMoney();
        }
        if (getCurrentOnlineUser() instanceof Seller) {
            return ((Seller) getCurrentOnlineUser()).getMoney();
        }
        return 0;
    }

    public boolean isThereUserWithUsername(String username) {
        String path = "Resource" + File.separator + "Users";
        String name = username + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public String userImagePath(String username){
        if(isThereUserWithUsername(username)==false){
            return "Erorr: no user exist with this username!";
        }
        String jpgFullPath="src/main/resources/Images/"+username+".jpg";
        String pngFullPath="src/main/resources/Images/"+username+".png";
        File jpgFile=new File(jpgFullPath);
        File pngFile=new File(pngFullPath);
        if(jpgFile.exists()){
            return jpgFullPath;
        }else if(pngFile.exists()){
            return pngFullPath;
        }
        return "src/main/resources/Images/default.jpg";
    }

    public String registerBuyer(double money, String username, String password, String name, String lastName, String email, String number) {
        if (isThereUserWithUsername(username)) {
            return "Error : User exist with this username!";
        }
        Buyer user = new Buyer(money, username, password, name, lastName, email, number);
        Database.getInstance().saveUser(user);
        return "Successful: User registered.";
    }

    public String registerSeller(double money, String username, String password, String name, String lastName, String email, String number, String companyName) {
        if (isThereUserWithUsername(username)) {
            return "Error : User exist with this username!";
        }
        Seller user = new Seller(money, username, password, name, lastName, email, number, companyName);
        Database.getInstance().saveUser(user);
        String requestID = controller.getAlphaNumericString(controller.getIdSize(), "Requests");

        RequestController.getInstance().addUserRequest(requestID, user);
        return "Success: Your request has been sent to the admin.";
    }

    public String registerAdmin(String username, String password, String name, String lastName, String email, String number) {
        return Admin.addAdminAccount(username, password, name, lastName, email, number);
    }

    public String registerFirstAdmin(String username, String password, String name, String lastName, String email, String number){
        ArrayList<User> allUsers = getAllUserFromDataBase();
        for(User user:allUsers){
            if(user instanceof Admin) return "Error : only admins can register admins.";
        }
        if (isThereUserWithUsername(username)) {
            return "Error : User exist with this username!";
        }
        Admin admin = new Admin(username,password,name,lastName,email,number);
        Database.getInstance().saveUser(admin);
        return "Success: You own this shop.";

    }

    public String login(String username, String password) {
        if (!isThereUserWithUsername(username)) {
            return "Error: No user exists with this username!";
        }
        User user = getUserByUsername(username);
        if (!user.doesPasswordMatch(password)) {
            return "Error: Incorrect password!";
        }
        if(user instanceof Seller){
            Seller seller=(Seller) user;
            if(!seller.getValid()){
                return "Error: your account is not accepted yet";
            }
        }
        controller.currentOnlineUser = user;
        Controller.getInstance().setCurrentOnlineUser(user);
        return "Success: Login successful.";
    }

    public double validateMoney(String money) {
        double moneyDouble = -1;
        try {
            moneyDouble = Double.parseDouble(money);
        } catch (Exception e) {
            return -1;
        }
        return moneyDouble;
    }

    public boolean isValidEmail(String email) {
        return Controller.getMatcher(email, "^[A-Za-z0-9+_.-]+@(.+)\\.(.+)$").matches();
    }

    public boolean isValidPhoneNumber(String number) {
        return Controller.getMatcher(number, "\\d\\d\\d\\d\\d(\\d+)$").matches();
    }

    public String returnUserType(String username) {
        return getUserByUsername(username).getType();
    }

    public String logout() {
        if (controller.currentOnlineUser == null) {
            return View.ANSI_RED + "Error: Not logged in!" + View.ANSI_RESET;
        }
        controller.currentOnlineUser = null;
        controller.emptyCart();
        SortAndFilterController.getInstance().reset();
        return "Success: Logged out.";
    }

    public String deleteUser(String username) {
        if (getCurrentOnlineUser() == null) {
            return "Error:";
        }
        if (username.equals(getCurrentOnlineUser().getUsername())) {
            return "Error: you cant remove your own account";
        }
        User user = getUserByUsername(username);
        if (user == null) {
            return "Error: user doesnt exist";
        }
        if(user instanceof Seller){
            Seller seller=(Seller) user;
            seller.delete();
        }
        Database.getInstance().deleteUser(user);
        return "Successful:";
    }

    public void editPersonalInfo(String username, String field, String newValue) {
        User user = getUserByUsername(username);
        if (field.equals("Name")) {
            user.setName(newValue);
        } else if (field.equals("Surname")) {
            user.setLastName(newValue);
        } else if (field.equals("Number")) {
            user.setNumber(newValue);
        } else if (field.equals("Email")) {
            user.setEmail(newValue);
        } else if (field.equals("Password")) {
            user.setPassword(newValue);
        } else if ((field.equals("CompanyName"))&&(user instanceof Seller)) {
            ((Seller) user).setCompanyName(newValue);
        }
        Database.getInstance().saveUser(user);
    }

    public String viewPersonalInfo(String username) {
        User user = getUserByUsername(username);
        if (user == null) {
            return "Error: user doesnt exist";
        }
        return user.getPersonalInfo();
    }

    public ArrayList<User> getAllUserFromDataBase() {
        String path = "Resource" + File.separator + "Users";
        File file = new File(path);
        File[] allFiles = file.listFiles();
        String fileContent = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<User> allUser = new ArrayList<>();
        for (File file1 : allFiles) {
            try {
                fileContent = new String(Files.readAllBytes(file1.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fileContent.contains("\"type\": \"Admin\"")) {
                allUser.add(gson.fromJson(fileContent, Admin.class));
            }
            if (fileContent.contains("\"type\": \"Seller\"")) {
                allUser.add(gson.fromJson(fileContent, Seller.class));
            }
            if (fileContent.contains("\"type\": \"Buyer\"")) {
                allUser.add(gson.fromJson(fileContent, Buyer.class));
            }
        }
        return allUser;
    }

    protected ArrayList<Buyer> getAllBuyers(){
        ArrayList<User> allUsers = getAllUserFromDataBase();
        ArrayList<Buyer> ans = new ArrayList<>();

        for(User user:allUsers){
            if(user instanceof Buyer) ans.add((Buyer)user);
        }

        return ans;
    }

    public void assignBuyLog(String buyerName, BuyLog buyLog) {
        Buyer buyer = (Buyer) getUserByUsername(buyerName);
        buyer.addBuyLog(buyLog);
        Database.getInstance().saveUser(buyer);
    }

    public void assignSaleLog(String sellerName, SaleLog saleLog) {
        Seller seller = (Seller) getUserByUsername(sellerName);
        seller.addSaleLog(saleLog);
        Database.getInstance().saveUser(seller);
    }

    public void assignItemToSeller(String id, String username) {
        User user = getUserByUsername(username);
        if (user instanceof Seller) {
            Seller seller = (Seller) user;
            seller.addItemID(id);
            Database.getInstance().saveUser(seller);
        }
    }

    public void deleteItemFromSeller(String id, String username) {
        User user = getUserByUsername(username);
        if (user instanceof Seller) {
            Seller seller = (Seller) user;
            seller.deleteItem(id);
            Database.getInstance().saveUser(seller);
        }
    }

    public String getSaleHistory(){
        if(getCurrentOnlineUser() instanceof Seller==false){
            return "Error:";  //this wont happen because we call this function in the seller menu
        }
        Seller seller=(Seller) getCurrentOnlineUser();
        if(seller==null){
            return "Error:";
        }
        return seller.getSaleLogsString();
    }

    public String getBuyLog(int index){
        if((getCurrentOnlineUser()==null)||(getCurrentOnlineUser() instanceof Buyer==false)){
            return "Error:";  //this wont happen because we call this function in the buyer menu
        }
        Buyer buyer=(Buyer) getCurrentOnlineUser();
        try{
            if(index>buyer.getBuyLogSize()-1){
                return "Error: Invalid buyLog";
            }
                return buyer.getBuyLogByID(index).toString();
        } catch (Exception e){
            return "Error: Invalid ID";
        }
    }

    public String getAllBuyLogs(){
        if((getCurrentOnlineUser()==null)||(getCurrentOnlineUser() instanceof Buyer==false)){
            return "Error: Internal Error";  //this wont happen because we call this function in the buyer menu
        }
        Buyer buyer=(Buyer) getCurrentOnlineUser();
        return buyer.getBuyLogsString();
    }

    public String getUserType(String username){
        User user = getUserByUsername(username);
        if(user==null){
            return "Error: User doesn't exist.";
        }
        return user.getType();
    }
    public String getUserType(){
        if(getCurrentOnlineUser()==null)
            return "Error: User isn't online.";
        return getUserType(getCurrentOnlineUser().getUsername());
    }

    public ArrayList<String> getSellerItems()  {
        if((getCurrentOnlineUser()!=null)&&(getCurrentOnlineUser() instanceof Seller)){
            Seller seller=(Seller) getCurrentOnlineUser();
            return seller.getAllItemsId();
        }
        return null;
    }

    public String getSellerCompany() {
        if((getCurrentOnlineUser()!=null)&&(getCurrentOnlineUser() instanceof Seller)){
            Seller seller=(Seller) getCurrentOnlineUser();
            return seller.getCompanyName();
        }
        return "Error: Seller doesn't exist.";
    }

    public Boolean doesSellerHaveItem(String itemId){
        if((getCurrentOnlineUser() instanceof Seller==false)||(getCurrentOnlineUser()==null))
            return false;
        Seller seller = (Seller) Controller.getInstance().getCurrentOnlineUser();
        return seller.hasItem(itemId);
    }

    public String getBuyerDiscountCode(){
        if((getCurrentOnlineUser() instanceof Buyer==false)||(getCurrentOnlineUser()==null))
            return "";
        Buyer buyer=(Buyer) getCurrentOnlineUser();
        return buyer.getDiscountCodes();
    }
}
