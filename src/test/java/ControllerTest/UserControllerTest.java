package ControllerTest;

import Model.Users.Admin;
import Model.Users.Buyer;
import Model.Users.Seller;
import Model.Users.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UserControllerTest {

    @Test
    public void getInstance() {
    }

    @Test
    public void getUserByUsername() {
        UserController.getInstance().registerBuyer(500,"amir","1234","amirreza","mirzaei","amirreza@gamil.com","09126783212");
        UserController.getInstance().registerAdmin("arman","1234","arman","soleymani","arman@gmail.com","09123107635");
        UserController.getInstance().registerSeller(500,"alireza","1234","ali","eiji","eiji@gmail.com","09126329832","digikala");
        User user=UserController.getInstance().getUserByUsername("amir");
        User user1=UserController.getInstance().getUserByUsername("alireza");
        User user2=UserController.getInstance().getUserByUsername("arman");
        Assert.assertNotNull(user);
        Assert.assertNotNull(user1);
        Assert.assertNotNull(user2);
        Assert.assertTrue(user instanceof Buyer);
        Assert.assertTrue(user2 instanceof Admin);
        Assert.assertTrue(user1 instanceof Seller);
    }

    @Test
    public void getCurrentOnlineUser() {
    }

    @Test
    public void currentOnlineUserBalance() {
    }

    @Test
    public void isThereUserWithUsername() {
        UserController.getInstance().registerBuyer(500,"amir","1234","amirreza","mirzaei","amirreza@gamil.com","09126783212");
        UserController.getInstance().registerAdmin("arman","1234","arman","soleymani","arman@gmail.com","09123107635");
        UserController.getInstance().registerSeller(500,"alireza","1234","ali","eiji","eiji@gmail.com","09126329832","digikala");
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("amir"));
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("arman"));
        Assert.assertTrue(UserController.getInstance().isThereUserWithUsername("alireza"));
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("asgargoli"));
    }

    @Test
    public void registerBuyer() {

    }

    @Test
    public void registerSeller() {
    }

    @Test
    public void registerAdmin() {
    }

    @Test
    public void login() {
        String result="Success: Login successful.";
        String result1="Error: Incorrect password!";
        String result2="Error: No user exists with this username!";
        String ex=UserController.getInstance().login("alireza" , "Yad Biad Migad!");
        String ex1=UserController.getInstance().login("arman","Hitler");
        String ex2=UserController.getInstance().login("Akira Lane","Hogtied!");
        Assert.assertEquals(result,ex);
        Assert.assertEquals(result1,ex1);
        Assert.assertEquals(result2,ex2);
    }

    @Test
    public void validateMoney() {
        String money="4000";
        String money1="asdf345";
        Assert.assertEquals(4000 , UserController.getInstance().validateMoney(money),3);
        //Assert.assertEquals("345" , UserController.getInstance().validateMoney(money1),3);
    }

    @Test
    public void isValidEmail() {
        String email="alirezaeiji151379@gmail.com";
        String email1="alfgh/com";
        Assert.assertTrue(UserController.getInstance().isValidEmail(email));
    }

    @Test
    public void isValidPhoneNumber() {
        String phoneNumber="33824264";
        String phoneNumber1="sdg435sdfv43";
        Assert.assertTrue(UserController.getInstance().isValidPhoneNumber(phoneNumber));
    }

    @Test
    public void returnUserType() {
        UserController.getInstance().registerBuyer(500,"amir","1234","amirreza","mirzaei","amirreza@gamil.com","09126783212");
        UserController.getInstance().registerAdmin("arman","1234","arman","soleymani","arman@gmail.com","09123107635");
        UserController.getInstance().registerSeller(500,"alireza","1234","ali","eiji","eiji@gmail.com","09126329832","digikala");
        User user=UserController.getInstance().getUserByUsername("alireza");
        User user1=UserController.getInstance().getUserByUsername("amir");
        User user2=UserController.getInstance().getUserByUsername("arman");
        Assert.assertEquals(UserController.getInstance().returnUserType("alireza"),"Seller");
    }

    @Test
    public void logout() {
    }

    @Test
    public void deleteUser() {
        UserController.getInstance().registerBuyer(500,"amir","1234","amirreza","mirzaei","amirreza@gamil.com","09126783212");
        UserController.getInstance().registerAdmin("arman","1234","arman","soleymani","arman@gmail.com","09123107635");
        UserController.getInstance().registerSeller(500,"alireza","1234","ali","eiji","eiji@gmail.com","09126329832","digikala");
        UserController.getInstance().deleteUser("alireza");
        UserController.getInstance().deleteUser("amir");
        UserController.getInstance().deleteUser("arman");
        UserController.getInstance().deleteUser("Akira Lane");
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("alireza"));
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("amir"));
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("arman"));
        Assert.assertFalse(UserController.getInstance().isThereUserWithUsername("Akira Lane"));
    }

    @Test
    public void editPersonalInfo() {
        User user=UserController.getInstance().getUserByUsername("alireza");
        UserController.getInstance().editPersonalInfo("alireza","Name" ,"Ho3ein");
        UserController.getInstance().editPersonalInfo("alireza","Surname","Rahmati");
        UserController.getInstance().editPersonalInfo("alireza","Number","33824264");
        UserController.getInstance().editPersonalInfo("alireza","Email","h.rah@gmail.com");
        UserController.getInstance().editPersonalInfo("alireza","CompanyName","Kaqaz Rap" );
        UserController.getInstance().editPersonalInfo("alireza","Password","Yad Biad Migad!");
        Assert.assertEquals(user.getName(),"Ho3ein");
        Assert.assertEquals(user.getLastName(),"Rahmati");
        Assert.assertEquals(user.getEmail(),"h.rah@gmail.com");
        Assert.assertEquals(user.getNumber(),"33824264");
        Seller seller=(Seller)user;
        Assert.assertEquals(seller.getCompanyName(),"Kaqaz Rap");
        System.out.println(UserController.getInstance().viewPersonalInfo("alireza"));
    }

    @Test
    public void viewPersonalInfo() {
        User user=UserController.getInstance().getUserByUsername("alireza");
        System.out.println(UserController.getInstance().viewPersonalInfo("alireza"));
    }

    @Test
    public void getAllUserFromDataBase() {
        ArrayList<User>allUsers=UserController.getInstance().getAllUserFromDataBase();
       // Assert.assertNull(allUsers);
        for(User user:allUsers){
            System.out.println(UserController.getInstance().viewPersonalInfo(user.getUsername()));
        }
    }

    @Test
    public void changeTypeTo() {
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
}