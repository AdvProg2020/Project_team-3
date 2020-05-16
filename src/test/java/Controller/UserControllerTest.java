package Controller;

import Model.Requests.Request;
import Model.Users.Admin;
import Model.Users.Buyer;
import Model.Users.Seller;
import Model.Users.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UserControllerTest {

    public void registration(){
        registerAdmin();
        registerBuyer();
        registerSeller();
    }

    @Test
    public void getInstance() {
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
    }

    @Test
    public void getCurrentOnlineUser() {
        registration();
        UserController.getInstance().login("Arman","Hitler");
        Assert.assertNotNull(UserController.getInstance().getCurrentOnlineUser());
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
    }

    @Test
    public void isThereUserWithUsername() {
        registration();
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("Alireza"));
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("Arman"));
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("Ho3ein"));
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("Reza Pishro"));
    }

    @Test
    public  void registerBuyer() {
        UserController.getInstance().registerBuyer(500,"Arman","Hitler",
                "Arman","S","arman@gmail.com","33151603");
    }

    @Test
    public  void registerSeller() {
        UserController.getInstance().registerSeller(500,"Alireza","alireza79",
                "reza","pishro","alireza@gmail.com","33824264","benz");
        ArrayList<Request>allRequests=RequestController.getInstance().getAllRequestFromDataBase();
        for(Request request:allRequests){
            RequestController.getInstance().acceptRequest(request.getRequestId());
        }
    }

    @Test
    public  void registerAdmin() {
        UserController.getInstance().registerAdmin("Ho3ein","Yad","Ho3ein","Rahmati"
                ,"h.rah@gmail.com","33142220");
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
    }

    @Test
    public void deleteUser() {
        registration();
        User user1=UserController.getInstance().getUserByUsername("Alireza");
        UserController.getInstance().login("Alireza",user1.getPassword());
        UserController.getInstance().deleteUser("Arman");
        UserController.getInstance().deleteUser("Ho3ein");
        UserController.getInstance().deleteUser("Alireza");
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("Alireza"));
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("Arman"));
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("Ho3ein"));
        UserController.getInstance().logout();
        UserController.getInstance().deleteUser("Alireza");
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("Alireza"));
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

    }

    @Test
    public void viewPersonalInfo() {
        registration();
        System.out.println(UserController.getInstance().viewPersonalInfo("Alireza"));
    }

    @Test
    public void getAllUserFromDataBase() {
        registration();
        ArrayList<User> allUsers=UserController.getInstance().getAllUserFromDataBase();
        for(User user:allUsers) System.out.println(UserController.getInstance().viewPersonalInfo(user.getUsername()));
    }


    @Test
    public void assignBuyLog() {
    }

    @Test
    public void assignSaleLog() {
    }

    @Test
    public void buy() {
    }
    @Test
    public void getAllBuyers(){
        registration();
        ArrayList<Buyer>allBuyers=UserController.getInstance().getAllBuyers();
        System.out.println(allBuyers);
    }
    @Test
    public void getSellerCompany(){
        registration();
        Seller seller=(Seller)UserController.getInstance().getUserByUsername("Alireza");
        UserController.getInstance().login(seller.getUsername(),seller.getPassword());
        System.out.println(UserController.getInstance().getSellerCompany());
        UserController.getInstance().logout();
        System.out.println(UserController.getInstance().getSellerCompany());
    }
    @Test
    public void getUserType(){
        registration();
        User user=UserController.getInstance().getUserByUsername("Alireza");
        UserController.getInstance().login(user.getUsername(),user.getPassword());
        System.out.println(UserController.getInstance().getUserType());
        UserController.getInstance().logout();
        System.out.println(UserController.getInstance().getUserType());
        System.out.println(UserController.getInstance().getUserType("Alireza"));
    }



}