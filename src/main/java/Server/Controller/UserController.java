package Server.Controller;

import Server.Model.Logs.BuyLog;
import Server.Model.Logs.SaleLog;
import Server.Model.Users.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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
        ArrayList<User> valid = new ArrayList<>();
        Gson gson = new Gson();
        Connection connection = null;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM Admins WHERE username='"+username+"'");
            while(rs.next())
            {
                HashMap<String,String> req = gson.fromJson(rs.getString("allRequests"),new TypeToken<HashMap<String,String>>(){}.getType());
                valid.add(new Admin(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),req));
            }
           rs = statement.executeQuery("select * FROM Assistants WHERE username='"+username+"'");
            while(rs.next())
            {
                HashMap<String,String> req = gson.fromJson(rs.getString("requests"),new TypeToken<HashMap<String,String>>(){}.getType());
                valid.add(new Assistant(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),req));
            }
            rs = statement.executeQuery("select * FROM Assistants WHERE username='"+username+"'");
            while(rs.next())
            {
                HashMap<String,String> req = gson.fromJson(rs.getString("allRequests"),new TypeToken<HashMap<String,String>>(){}.getType());
                valid.add(new Assistant(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),req));
            }
            rs = statement.executeQuery("select * FROM Buyers WHERE username='"+username+"'");
            while(rs.next())
            {
                ArrayList<BuyLog> logs = gson.fromJson(rs.getString("logs"),new TypeToken<ArrayList<BuyLog>>(){}.getType());
                HashMap<String,String> req = gson.fromJson(rs.getString("allRequests"),new TypeToken<HashMap<String,String>>(){}.getType());
                double money = Double.parseDouble(rs.getString("money"));
                valid.add(new Buyer(money,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),logs,req));
            }
            rs = statement.executeQuery("select * FROM Sellers WHERE username='"+username+"'");
            while(rs.next())
            {
                ArrayList<SaleLog> logs = gson.fromJson(rs.getString("logs"),new TypeToken<ArrayList<SaleLog>>(){}.getType());
                HashMap<String,String> req = gson.fromJson(rs.getString("allRequests"),new TypeToken<HashMap<String,String>>(){}.getType());
                double balance = Double.parseDouble(rs.getString("balance"));
                boolean v = Boolean.parseBoolean(rs.getString("valid"));
                ArrayList<String> items = gson.fromJson(rs.getString("items"),new TypeToken<ArrayList<String>>(){}.getType());
                String company = rs.getString("company");
                valid.add(new Seller(balance,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),company,logs,items,v,req));
            }
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        if(valid.isEmpty()) return null;
        return valid.get(0);

    }

    public User getCurrentOnlineUser() {
        if(Controller.getInstance().currentOnlineUser == null) return null;
        return Controller.getInstance().currentOnlineUser;
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
        int cnt=0;
        Connection connection = null;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM Admins WHERE username='"+username+"'");
            while(rs.next())
            {
                cnt++;
            }
            rs = statement.executeQuery("select * FROM Buyers WHERE username='"+username+"'");
            while(rs.next())
            {
                cnt++;
            }
            rs = statement.executeQuery("select * FROM Sellers WHERE username='"+username+"'");
            while(rs.next())
            {
                cnt++;
            }
            rs = statement.executeQuery("select * FROM Assistants WHERE username='"+username+"'");
            while(rs.next())
            {
                cnt++;
            }
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        return cnt>0;
    }

    public String userImagePath(String username){
//        if(isThereUserWithUsername(username)==false){
//            return "Erorr: no user exist with this username!";
//        }
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
        if (username.length()>18 || password.length()>18 || name.length() > 18 || lastName.length() > 18) {
            return "Error : Lengthy inputs";
        }
        String allData = username.concat(password).concat(name).concat(lastName).concat(email);
        if(allData.contains("*") || allData.contains("|") || allData.contains("'") || allData.contains("\"") || allData.contains(" ")){
            return "Error: invalid characters!";
        }
        if(isPasswordWeak(password)){
            return "Error: weak password";
        }
        if (isThereUserWithUsername(username)) {
            return "Error : User exist with this username!";
        }
        Buyer user = new Buyer(money, username, password, name, lastName, email, number);
        Database.getInstance().saveUser(user);
        return "Successful: User registered.";
    }

    public String registerSeller(double money, String username, String password, String name, String lastName, String email, String number, String companyName) {
        if (username.length()>18 || password.length()>18 || name.length() > 18 || lastName.length() > 18) {
            return "Error : Lengthy inputs";
        }
        String allData = username.concat(password).concat(name).concat(lastName).concat(email);
        if(allData.contains("*") || allData.contains("|") || allData.contains("'") || allData.contains("\"") || allData.contains(" ")){
            return "Error: invalid characters!";
        }
        if(isPasswordWeak(password)){
            return "Error: weak password";
        }
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
        if (username.length()>18 || password.length()>18 || name.length() > 18 || lastName.length() > 18) {
            return "Error : Lengthy inputs";
        }
        String allData = username.concat(password).concat(name).concat(lastName).concat(email);
        if(allData.contains("*") || allData.contains("|") || allData.contains("'") || allData.contains("\"") || allData.contains(" ")){
            return "Error: invalid characters!";
        }
        if(isPasswordWeak(password)){
            return "Error: weak password";
        }
        if (isThereUserWithUsername(username)) {
            return "Error : User exist with this username!";
        }
        return Admin.addAdminAccount(username, password, name, lastName, email, number);
    }

    public String registerAssistant(String username, String password, String name, String lastName, String email, String number) {
        if (username.length()>18 || password.length()>18 || name.length() > 18 || lastName.length() > 18) {
            return "Error : Lengthy inputs";
        }
        if(isPasswordWeak(password)){
            return "Error: weak password";
        }
        if (isThereUserWithUsername(username)) {
            return "Error : User exist with this username!";
        }
        Assistant user=new Assistant(username,password,name,lastName,email,number);
        Database.getInstance().saveUser(user);
        return "Success: Your assistant account registered.";
    }

    private boolean isPasswordWeak(String password){
        if(password.length() < 5) return true;
        if(password.matches("\\d+")) return true;
        String[] weakPasswords = {"12345","123456","123456789","test1","password","12345678","zinch","g_czechout","asdf"
                ,"qwerty","1234567890","1234567","Aa123456.","iloveyou","1234","abc123","111111","123123","dubsmash","test","princess","qwertyuiop","sunshine","BvtTest123","11111","ashley"
                ,"00000","000000","password1","monkey","livetest","55555","soccer","charlie","asdfghjkl","654321","family"
                ," michael","123321","football","baseball","q1w2e3r4t5y6","nicole","jessica","purple","shadow","hannah","chocolate","michelle","daniel","maggie","qwerty123","hello","112233","jordan","tigger","666666"
                ,"987654321","superman","12345678910","summer","1q2w3e4r5t","fitness","bailey","zxcvbnm","fuckyou","121212","buster","butterfly","dragon","jennifer","amanda","justin","cookie","basketball","shopping","pepper","joshua","hunter","ginger"
                ,"matthew","abcd1234","taylor","samantha","whatever","andrew","1qaz2wsx3edc","thomas","jasmine","animoto","madison","0987654321","54321","flower","Password","maria","babygirl","lovely","sophie","Chegg123"};
        for(String weakPass : weakPasswords){
            if(password.equals(weakPass)){
                return true;
            }
        }
        return false;
    }



    public String login(String username, String password) {
        if(AuthTokenHandler.getInstance().isUserOnline(username)){
            return "Error: you are already login with another device";
        }
        if (!isThereUserWithUsername(username)) {
            return "Error: Incorrect password/username or account pending!";
        }
        User user = getUserByUsername(username);
        if (!user.doesPasswordMatch(password)) {
            return "Error: Incorrect password/username or account pending!";
        }
        if(user instanceof Seller){
            Seller seller=(Seller) user;
            if(!seller.getValid()){
                return "Error: Incorrect password/username or account pending!";
            }
        }
        controller.currentOnlineUser = user;
        Controller.getInstance().setCurrentOnlineUser(user);
        return "Success: Login successful Token: "+AuthTokenHandler.getInstance().generateTokenForUser(username);
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
        if(email.length()>18) return false;
        return Controller.getMatcher(email, "^[A-Za-z0-9+_.-]+@(.+)\\.(.+)$").matches();
    }

    public boolean isValidPhoneNumber(String number) {
        if(number.length()>18) return false;
        return Controller.getMatcher(number, "\\d\\d\\d\\d\\d(\\d+)$").matches();
    }

    public String returnUserType(String username) {
        return getUserByUsername(username).getType();
    }

    public String logout() {
        if (controller.currentOnlineUser == null) {
            return "Error: Not logged in!";
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

    protected ArrayList<Buyer> getAllBuyers(){
        ArrayList<Buyer> ans = new ArrayList<>();
        Connection connection = null;
        Gson gson = new Gson();
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM Buyers");
            while(rs.next())
            {
                ArrayList<BuyLog> logs = gson.fromJson(rs.getString("logs"),new TypeToken<ArrayList<BuyLog>>(){}.getType());
                HashMap<String,String> req = gson.fromJson(rs.getString("allRequests"),new TypeToken<HashMap<String,String>>(){}.getType());
                double money = Double.parseDouble(rs.getString("money"));
                ans.add(new Buyer(money,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),logs,req));
            }

        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
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
        System.err.println("seller nisti!");
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


    public ArrayList<SaleLog> getSaleLogs(String username){
        User user = getUserByUsername(username);
        Seller seller;
        if(user instanceof Seller){
            seller = (Seller)user;
            return seller.getSellLogs();
        }
        return null;
    }



}
