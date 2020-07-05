package Project.Controller;

import Project.Model.Comment;
import Project.Model.DiscountCode;
import Project.Model.Item;
import Project.Model.Requests.Request;
import Project.Model.Users.Admin;
import Project.Model.Users.Buyer;
import Project.Model.Users.Seller;
import Project.Model.Users.User;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserControllerTest {

    public void registration(){
        registerAdmin();
        registerBuyer();
        registerSeller();
    }

    public void deleteJunk(){
        UserController.getInstance().logout();
        UserController.getInstance().login("admin","12345");
        UserController.getInstance().deleteUser("Arman");
        UserController.getInstance().deleteUser("Alireza");
        UserController.getInstance().deleteUser("Ho3ein");
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("Alireza"));
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("Arman"));
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("Ho3ein"));
        UserController.getInstance().logout();
    }

    @Test
    public void getInstance() {
        Database.getInstance().initiate();
        UserController userController=UserController.getInstance();
        assertNotNull(userController);
    }

    @Test
    public void getUserByUsername() {
        registration();
        User user=UserController.getInstance().getUserByUsername("Arman");
        User user1=UserController.getInstance().getUserByUsername("Alireza");
        User user2=UserController.getInstance().getUserByUsername("Ho3ein");
        Assert.assertNotNull(user);
        Assert.assertNotNull(user1);
        Assert.assertNotNull(user2);
        Assert.assertTrue(user instanceof Buyer);
        Assert.assertTrue(user1 instanceof Seller);
        Assert.assertTrue(user2 instanceof Admin);
        deleteJunk();
    }

    @Test
    public void getCurrentOnlineUser() {
        registration();
        UserController.getInstance().login("Arman","Hitler");
        Assert.assertNotNull(UserController.getInstance().getCurrentOnlineUser());
        deleteJunk();
    }

    @Test
    public void currentOnlineUserBalance() {
        registration();
        User user=UserController.getInstance().getUserByUsername("Alireza");
        User user1= UserController.getInstance().getUserByUsername("Arman");
        UserController.getInstance().login("Alireza",user.getPassword());
        Assert.assertEquals(UserController.getInstance().currentOnlineUserBalance(), ((Seller) user).getMoney(),3);
        UserController.getInstance().logout();
        UserController.getInstance().login("Arman","Hitler");
        Assert.assertEquals(UserController.getInstance().currentOnlineUserBalance(), ((Buyer) user1).getMoney(),3);
        UserController.getInstance().logout();
        deleteJunk();
    }

    @Test
    public void isThereUserWithUsername() {
        registration();
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("Alireza"));
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("Arman"));
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("Ho3ein"));
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("Reza Pishro"));
        deleteJunk();
    }

    @Test
    public  void registerBuyer() {
        UserController.getInstance().registerBuyer(50000,"Arman","Hitler",
                "Arman","S","arman@gmail.com","33151603");
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("Arman"));
    }

    @Test
    public  void registerSeller() {
        UserController.getInstance().registerSeller(500,"Alireza","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("Alireza"));
    }

    @Test
    public  void registerAdmin() {
        UserController.getInstance().registerAdmin("Ho3ein","Yad","Ho3ein","Rahmati"
                ,"h.rah@gmail.com","33142220");
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("Ho3ein"));
    }

    @Test
    public void login() {
        registration();
        User user1=UserController.getInstance().getUserByUsername("Alireza");
        UserController.getInstance().login("Alireza",user1.getPassword());
        User user=UserController.getInstance().getCurrentOnlineUser();
        Assert.assertNotNull(user);
        System.out.println(UserController.getInstance().logout());
        System.out.println(UserController.getInstance().login("mamad","Yad"));
        Assert.assertNull(UserController.getInstance().getCurrentOnlineUser());
        deleteJunk();
    }

    @Test
    public void validateMoney() {
        String money="4000";
        String money1="werwer45";
        Assert.assertEquals(UserController.getInstance().validateMoney(money),4000,3);
        Assert.assertEquals(UserController.getInstance().validateMoney(money1),-1,3);
    }

    @Test
    public void isValidEmail() {
        String email="alireza@gmail.com";
        String email1="Asdf.dfg";
        Assert.assertTrue(UserController.getInstance().isValidEmail(email));
        Assert.assertFalse(UserController.getInstance().isValidEmail(email1));

    }

    @Test
    public void isValidPhoneNumber() {
        String phoneNumber="33824264";
        String phoneNumber1="331dfasfasdf";
        Assert.assertTrue(UserController.getInstance().isValidPhoneNumber(phoneNumber));
        Assert.assertFalse(UserController.getInstance().isValidPhoneNumber(phoneNumber1));
    }

    @Test
    public void returnUserType() {
        registration();
        Assert.assertEquals(UserController.getInstance().returnUserType("Alireza"),"Seller");
        Assert.assertEquals(UserController.getInstance().returnUserType("Arman"),"Buyer");
        Assert.assertEquals(UserController.getInstance().returnUserType("Ho3ein"),"Admin");
        deleteJunk();
    }

    @Test
    public void logout() {
        registration();
        UserController.getInstance().login("Ho3ein","Yad");
        Assert.assertNotNull(UserController.getInstance().getCurrentOnlineUser());
        UserController.getInstance().logout();
        UserController.getInstance().login("Akira","Bondage");
        Assert.assertNull(UserController.getInstance().getCurrentOnlineUser());
        UserController.getInstance().logout();
        Assert.assertNull(Controller.getInstance().currentOnlineUser);
        deleteJunk();
    }

    @Test
    public void deleteUser() {
        registration();
        User user1=UserController.getInstance().getUserByUsername("Alireza");
        UserController.getInstance().login("Admin","12345");
        UserController.getInstance().deleteUser("Arman");
        UserController.getInstance().deleteUser("Ho3ein");
        UserController.getInstance().deleteUser("Alireza");
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("Alireza"));
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("Arman"));
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("Ho3ein"));
        UserController.getInstance().logout();
        deleteJunk();
    }

    @Test
    public void editPersonalInfo() {
        registration();
        UserController.getInstance().editPersonalInfo("Alireza","Name","RezaPishro");
        UserController.getInstance().editPersonalInfo("Alireza","Surname","Eiji");
        UserController.getInstance().editPersonalInfo("Alireza","Number","09140307011");
        UserController.getInstance().editPersonalInfo("Alireza","Email","alirezaeiji@gmail.com");
        UserController.getInstance().editPersonalInfo("Alireza","CompanyName","Kaqaz");
        UserController.getInstance().editPersonalInfo("Alireza","Password","Behaeen");
        Seller seller=(Seller) UserController.getInstance().getUserByUsername("Alireza");
        Assert.assertEquals(seller.getName(),"RezaPishro");
        Assert.assertEquals(seller.getLastName(),"Eiji");
        Assert.assertEquals(seller.getNumber(),"09140307011");
        Assert.assertEquals(seller.getCompanyName(),"Kaqaz");
        Assert.assertEquals(seller.getEmail(),"alirezaeiji@gmail.com");
        Assert.assertEquals(seller.getPassword(),"Behaeen");
        deleteJunk();

    }

    @Test
    public void viewPersonalInfo() {
        registration();
        System.out.println(UserController.getInstance().viewPersonalInfo("Alireza"));
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("Alireza"));
        User user=UserController.getInstance().getUserByUsername("Alireza");
        Assert.assertEquals(user.getName(),"reza");
        Assert.assertEquals(user.getLastName(),"pishro");
        Assert.assertEquals(user.getType(),"Seller");
        Assert.assertEquals(user.getEmail(),"alireza@gmail.com");
        Assert.assertEquals(user.getNumber(),"33824264");
        Seller seller=(Seller)user;
        Assert.assertEquals(seller.getCompanyName(),"benz");
        deleteJunk();
    }

    @Test
    public void getAllUserFromDataBase() {
        registration();
        ArrayList<User> allUsers=UserController.getInstance().getAllUserFromDataBase();
        for(User user:allUsers) System.out.println(UserController.getInstance().viewPersonalInfo(user.getUsername()));
        Assert.assertNotNull(allUsers);
        Assert.assertTrue(allUsers.size()>0);
        deleteJunk();
    }

    @Test
    public void getAllBuyers(){
        registration();
        ArrayList<Buyer>allBuyers=UserController.getInstance().getAllBuyers();
        for(User user:allBuyers) Assert.assertEquals(user.getType(),"Buyer");
        deleteJunk();
    }
    @Test
    public void getSellerCompany(){
        registration();
        Seller seller=(Seller)UserController.getInstance().getUserByUsername("Alireza");
        UserController.getInstance().login(seller.getUsername(),seller.getPassword());
        Assert.assertEquals(seller.getCompanyName(),"benz");
        System.out.println(UserController.getInstance().getSellerCompany());
        UserController.getInstance().logout();
        Assert.assertNull(Controller.getInstance().currentOnlineUser);
        System.out.println(UserController.getInstance().getSellerCompany());
        Assert.assertEquals(UserController.getInstance().getSellerCompany(),"Error: Seller doesn't exist.");
        deleteJunk();
    }
    @Test
    public void getUserType(){
        registration();
        User user=UserController.getInstance().getUserByUsername("Alireza");
        UserController.getInstance().login(user.getUsername(),user.getPassword());
        System.out.println(UserController.getInstance().getUserType());
        Assert.assertEquals(user.getType(),"Seller");
        UserController.getInstance().logout();
        System.out.println(UserController.getInstance().getUserType());
        System.out.println(UserController.getInstance().getUserType("Alireza"));
        Assert.assertEquals(UserController.getInstance().getUserType("Alireza"),"Seller");
        deleteJunk();
    }

    @Test
    public void isLogin(){
        registration();
        User user=UserController.getInstance().getUserByUsername("Alireza");
        UserController.getInstance().login(user.getUsername(),user.getPassword());
        Assert.assertTrue(Controller.getInstance().isLogin());
        System.out.println(Controller.getInstance().isLogin());
        UserController.getInstance().logout();
        Assert.assertFalse(Controller.getInstance().isLogin());
        System.out.println(Controller.getInstance().isLogin());
        deleteJunk();
    }

    @Test
    public void Purchase(){
        registerBuyer();  //username Arman
        registerSeller(); //username Alireza
        HashMap<String,String> attributes=new HashMap<>();
        attributes.put("price","cheap");
        HashMap<String , String>attributes1=new HashMap();
        attributes1.put("price","cheap");
        HashMap<String,String> attributes2=new HashMap<>();
        attributes2.put("price","cheap");
        UserController.getInstance().login("Alireza","alireza79");
        for (Item item : ItemAndCategoryController.getInstance().getAllItemFromDataBase()) {
            ItemAndCategoryController.getInstance().deleteItem(item.getId());
        }
        ArrayList<Request>allRequest=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequest){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
        System.out.println(ItemAndCategoryController.getInstance().addItem("Vacuum345","Benz","this is vaccum",500,10,"Project.Main", attributes));
        ArrayList<Request>allRequess=RequestController.getInstance().getAllRequestFromDataBase();
         System.out.println(allRequess.size());
        for(Request request:allRequess){
            System.out.println(request.toString());
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
        UserController.getInstance().logout();
        assertEquals("Error: cart is empty",CartController.getInstance().buy("sssss"));
        UserController.getInstance().login("Arman","Hitler");
        System.out.println(CartController.getInstance().showCart());
        assertEquals(CartController.getInstance().showCart(),"Cart is empty");
        System.out.println(ItemAndCategoryController.getInstance().getItemBuyer(ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId()));
        assertEquals("Error: cart is empty",CartController.getInstance().buy("sssss"));
        CartController.getInstance().addItemToCart(ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId());
        assertEquals("Successful: Shopping complete.",CartController.getInstance().buy("sssss"));
        Buyer buyer=(Buyer)UserController.getInstance().getUserByUsername("Arman");
        System.out.println(buyer.getBuyLogs().toString());

        ItemAndCategoryController.getInstance().rate(2,ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId());
        assertEquals((int)ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getRating(),2);
        for (Item item : ItemAndCategoryController.getInstance().getAllItemFromDataBase()) {
            ItemAndCategoryController.getInstance().deleteItem(item.getId());
        }
        allRequest=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequest){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
        deleteUser();

    }

    @Test
    public void PurchaseWithDiscountCode(){
        addDiscountCode();
        registerBuyer();  //username Arman
        registerSeller(); //username Alireza
        HashMap<String,String> attributes=new HashMap<>();
        attributes.put("price","cheap");
        HashMap<String , String>attributes1=new HashMap();
        attributes1.put("price","cheap");
        HashMap<String,String> attributes2=new HashMap<>();
        attributes2.put("price","cheap");
        UserController.getInstance().login("Alireza","alireza79");
        UserController.getInstance().assignItemToSeller("randomItemID","Alireza");
        UserController.getInstance().deleteItemFromSeller("randomItemID","Alireza");
        for (Item item : ItemAndCategoryController.getInstance().getAllItemFromDataBase()) {
            ItemAndCategoryController.getInstance().deleteItem(item.getId());
        }
        ArrayList<Request>allRequest=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequest){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
        System.out.println(ItemAndCategoryController.getInstance().addItem("Vacuum345","Benz","this is vaccum",500,10,"Project.Main", attributes));
        ArrayList<Request>allRequess=RequestController.getInstance().getAllRequestFromDataBase();
        System.out.println(allRequess.size());
        for(Request request:allRequess){
            System.out.println(request.toString());
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
        System.out.println(UserController.getInstance().getSaleHistory());
        UserController.getInstance().logout();
        assertEquals("Error: cart is empty",CartController.getInstance().buy("sssss"));
        UserController.getInstance().login("Arman","Hitler");
        System.out.println(CartController.getInstance().showCart());
        assertEquals(CartController.getInstance().showCart(),"Cart is empty");
        assertEquals("Error: cart is empty",CartController.getInstance().buy("sssss"));
        System.out.println(UserController.getInstance().getBuyerDiscountCode());
        CartController.getInstance().addItemToCart(ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId());
        Buyer buyer=(Buyer)UserController.getInstance().getUserByUsername("Arman");

        assertEquals("Successful: Shopping complete.",CartController.getInstance().buy("sssss",SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase().get(0).getDiscountId()));
        assertNotNull(buyer.getBuyLogs());
        assertNotNull(buyer.getBuyLogsString());
        assertNotNull(buyer.getDiscountCodes());
        System.out.println(ItemAndCategoryController.getInstance().comment("kheili alie",ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId(),null));
        allRequess=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequess){
            System.out.println(request.toString());
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
        for (Comment comment : ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getAllComments()) {
            System.out.println(comment.getText());
            assertEquals(comment.getItemId(),ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId());
            assertNotNull(comment.getCommentId());
            assertNotNull(comment.getAllReplies());
            ItemAndCategoryController.getInstance().comment("reply",ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId(),comment.getFatherCommentId());
        }
        allRequess=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequess){
            System.out.println(request.toString());
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
        System.out.println(UserController.getInstance().getBuyLog(0));
        Seller seller=(Seller) UserController.getInstance().getUserByUsername("Alireza");
        System.out.println(seller.getSaleLogsString());
        assertEquals(seller.getSellLogs().size(),1);
        ItemAndCategoryController.getInstance().rate(2,ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId());
        assertEquals((int)ItemAndCategoryController.getInstance().getScore(ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getId()),2);
        assertEquals((int)ItemAndCategoryController.getInstance().getAllItemFromDataBase().get(0).getRating(),2);
        System.out.println(UserController.getInstance().getAllBuyLogs());
        for (Item item : ItemAndCategoryController.getInstance().getAllItemFromDataBase()) {
            ItemAndCategoryController.getInstance().deleteItem(item.getId());
        }
        allRequest=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequest){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
        for (DiscountCode discountCode : SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase()) {
            SaleAndDiscountCodeController.getInstance().deleteDiscountCode(discountCode.getDiscountId());
        }
        deleteUser();
    }

    @Test
    public void addDiscountCode() {
        SaleAndDiscountCodeController.getInstance().giveRandomDiscountCode();
        SaleAndDiscountCodeController.getInstance().giveGiftDiscountCode("Arman");
        ArrayList<String>validUsers=new ArrayList<>();
        validUsers.add("Arman");
        String startTime="2014-02-25T22:30";
        String endTime="2020-02-27T22:30";
        LocalDateTime dateTime = LocalDateTime.parse(startTime);
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime);
        SaleAndDiscountCodeController.getInstance().addDiscountCode(20,dateTime1,dateTime,validUsers
                ,6,50);
        SaleAndDiscountCodeController.getInstance().editDiscountCodeMaxDiscount(SaleAndDiscountCodeController.getInstance().getAllDiscountCodesFromDataBase().get(0).getDiscountId(),30);
    }




}