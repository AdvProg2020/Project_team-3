package View.Menus;
import Controller.UserController;
import View.Menus.ShopAndDiscountMenu.ShopMenu;
import View.Menus.ShopAndDiscountMenu.SortAndFilterMenu;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;


public class LoginRegisterMenuTest {


   public void addTestAccount(){
   UserController.getInstance().registerBuyer(200,"loginmenutest","1234","amir","mirza","amir@gmail.com","09123456723");
   }
   public void removeTestAccount(){
   UserController.getInstance().deleteUser("loginmenutest");
   }

   @Test
   public void getInstance() {
      LoginRegisterMenu loginRegisterMenu=LoginRegisterMenu.getInstance();
      assertNotNull(loginRegisterMenu);
   }

   @Test
   public void run() {
   }

   @Test
   public void execute() {

   }

   @Test
   public void help() {
      LoginRegisterMenu.getInstance().help();
   }

   @Test
   public void login() {
   }

   @Test
   public void registerAdmin() {
   }

   @Test
   public void register() {
     LoginRegisterMenu.getInstance().register("ali");
     LoginRegisterMenu.getInstance().register("create account hheehid ali");
   }

   @Test
   public void logout() {
      addTestAccount();
      LoginRegisterMenu.getInstance().logout();
      UserController.getInstance().login("loginmenutest","1234");
      LoginRegisterMenu.getInstance().logout();
      removeTestAccount();
   }

   @Test
   public void setIntendedMenu() {
      LoginRegisterMenu.getInstance().setIntendedMenu("UserMenu");
   }
}
