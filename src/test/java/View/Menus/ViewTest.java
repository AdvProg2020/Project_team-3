package View.Menus;


import View.Menus.ShopAndDiscountMenu.ShopMenu;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Scanner;
import java.util.regex.Matcher;

import static org.junit.Assert.*;


public class ViewTest {


   @Test
   public void run(){
      View.setProgramRunning(false);
     View.run();
   }

   @Test
   public void setProgramRunning() {
      View.setProgramRunning(false);
      View.run();
   }

   @Test
   public void setCurrentMenu() {
      View.setCurrentMenu(ShopMenu.getInstance());
      Menu menu=View.getCurrentMenu();
      assertFalse(menu instanceof LoginRegisterMenu);
   }

   @Test
   public void getCurrentMenu() {
      View.setCurrentMenu(LoginRegisterMenu.getInstance());
      Menu menu=View.getCurrentMenu();
      assertTrue(menu instanceof LoginRegisterMenu);
   }

   @Test
   public void getMatcher() {
      Matcher matcher=View.getMatcher("create account (\\S+)","create account seller");
      assertTrue(matcher.matches());
      matcher=View.getMatcher("create account (\\S+)","create accont seller");
      assertFalse(matcher.matches());
   }

   @Test
   public void getRead() {
      Scanner scan=View.getRead();
   }
}
