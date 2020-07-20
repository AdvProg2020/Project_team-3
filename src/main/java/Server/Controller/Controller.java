package Server.Controller;

import Server.Model.Auction;
import Server.Model.Cart;
import Server.Model.Users.User;

import java.io.File;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private static Controller controller;
    User currentOnlineUser;
    Cart ShoppingCart = new Cart();
    int idSize = 5;

    private Controller() {
    }

    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller();
        return controller;
    }

    public Cart getCurrentShoppingCart() {
        return ShoppingCart;
    }

    public void emptyCart() {
        ShoppingCart = new Cart();
    }

    public boolean isLogin() {
        if (currentOnlineUser == null)
            return false;
        return true;
    }

    public int getIdSize() {
        return idSize;
    }

    public String getAlphaNumericString(int n, String folderName) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {

            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        String path = "Resource" + File.separator + folderName;
        String name = sb + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return sb.toString();
        }
        return getAlphaNumericString(n, folderName);
    }

    public static Matcher getMatcher(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(string);
    }

    public User getCurrentOnlineUser() {
        return currentOnlineUser;
    }

    public void setCurrentOnlineUser(User currentOnlineUser) {
        this.currentOnlineUser = currentOnlineUser;
    }

    public void setCurrentOnlineUser(String username) {
        this.currentOnlineUser=UserController.getInstance().getUserByUsername(username);
        if(this.currentOnlineUser==null) System.out.println("ye jayi error darim");
    }

    public void updateDateAndTime(){
        //in miad discount code hayi ke tarikh gozashte hastan va sale haye tarikh gozashte ro pak mikone
        LocalDateTime currentTime = LocalDateTime.now();
        SaleAndDiscountCodeController.getInstance().deleteDeprecatedSales(currentTime);
        SaleAndDiscountCodeController.getInstance().deleteDeprecatedDiscountCodes(currentTime);
        if(currentTime.getDayOfMonth() % 13 == 3){
            SaleAndDiscountCodeController.getInstance().giveRandomDiscountCode();
        }
        AuctionController.getInstance().updateAuctionsTime();
    }



}