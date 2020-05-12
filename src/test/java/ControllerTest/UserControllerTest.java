package ControllerTest;

import org.junit.Assert;
import org.junit.Test;

public class UserControllerTest {

    @Test
    public void getInstance() {
    }

    @Test
    public void getUserByUsername() {
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
    }

    @Test
    public void validateMoney() {
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
    }

    @Test
    public void logout() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void editPersonalInfo() {
    }

    @Test
    public void viewPersonalInfo() {
    }

    @Test
    public void getAllUserFromDataBase() {
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