package ControllerTest;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

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