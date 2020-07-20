package Server.Controller;

import Project.Client.CLI.View;
import Server.Model.Auction;
import Server.Model.DiscountCode;
import Server.Model.Item;
import Server.Model.Sale;
import Server.Model.Users.Buyer;
import Server.Model.Users.Seller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SaleAndDiscountCodeController {
    Controller controller = Controller.getInstance();
    private static SaleAndDiscountCodeController saleAndDiscountCodeController;

    private SaleAndDiscountCodeController() {
    }

    public static SaleAndDiscountCodeController getInstance() {
        if (saleAndDiscountCodeController == null) {
            saleAndDiscountCodeController = new SaleAndDiscountCodeController();
        }
        return saleAndDiscountCodeController;
    }


    public DiscountCode getDiscountCodeById(String id) {
        /*String path = "Resource" + File.separator + "DiscountCodes";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return null;
        }
        Gson gson = new Gson();
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            return gson.fromJson(content, DiscountCode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;*/
        ArrayList<DiscountCode> viableOptions = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Connection connection = null;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM DiscountCodes WHERE id='"+id+"'");
            while(rs.next())
            {
                String discountID = rs.getString(1);
                int percentage = rs.getInt(2);
                double max = Double.parseDouble(rs.getString(3));
                HashMap<String,Integer> usageCount = gson.fromJson(rs.getString(4),new TypeToken<HashMap<String,Integer>>(){}.getType());
                int usageCountInt = rs.getInt(5);
                String start = rs.getString(6);
                String end = rs.getString(7);
                viableOptions.add(new DiscountCode(discountID,percentage,max,usageCount,usageCountInt,start,end));
            }

        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        if(viableOptions.isEmpty()) return null;
        return viableOptions.get(0);
    }

    public Sale getSaleById(String id) {

        ArrayList<Sale> viableOptions = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Connection connection = null;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM Sales WHERE id='"+id+"'");
            while(rs.next())
            {
                //bayad be viableoptions new ezafe konim
                String saleID = rs.getString(1);
                String seller = rs.getString(2);
                ArrayList<String> items = gson.fromJson(rs.getString(3),new TypeToken<ArrayList<String>>(){}.getType());
                String start = rs.getString(4);
                String end = rs.getString(5);
                int percent = rs.getInt(6);
                String status = rs.getString(7);
                 viableOptions.add(new Sale(saleID,seller,items,start,end,percent,status));
            }

        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        if(viableOptions.isEmpty()) return null;
        return viableOptions.get(0);
    }

    public boolean isThereSaleWithId(String id) {
        /*String path = "Resource" + File.separator + "Sales";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return false;
        }
        return true;*/
        int cnt=0;
        Connection connection = null;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM Sales WHERE id='"+id+"'");
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

    public String getSellerSalesString(String username) {
        String ans = "ID          off percentage       ends in\n";
        for (Sale sale : getSellerSales(username)) {
            ans += sale.toSimpleString() + "\n";
        }
        return ans;
    }

    public String addSale(LocalDateTime startTime, LocalDateTime endTime, int offPercentage, ArrayList<String> saleItems) {
        if (!endTime.isAfter(startTime)) {
            return "Error: Ending time is after the starting time!";
        }
        Seller seller = (Seller) UserController.getInstance().getCurrentOnlineUser();
        if(seller==null){
            return "Error: Internal Error.";
        }
        if(saleItems.isEmpty()){
            return "Error: No items are on sale!";
        }
        for (String item : saleItems) {
            if (!seller.hasItem(item)) {
                return "Error: You do not have " + item + " in stock.";
           }
            if (ItemAndCategoryController.getInstance().getItemById(item).isInSale()) {
                return "Error: " + item + " is already in a sale.";
            }
        }
        Sale sale = new Sale(startTime, endTime, offPercentage, UserController.getInstance().getCurrentOnlineUser().getUsername(), saleItems);
        String requestID = controller.getAlphaNumericString(controller.getIdSize(), "Requests");
        RequestController.getInstance().addSaleRequest(requestID, sale);
        return "Successful: Your request to add the sale has been sent to the admins.";
    }

    public String addAuction(int duration,double startPrice,String itemID){
        LocalDateTime endTime = LocalDateTime.now().plusHours(duration);
        Auction auction = new Auction(endTime,itemID,startPrice);
        String requestID = controller.getAlphaNumericString(controller.getIdSize(), "Requests");
        RequestController.getInstance().addAuctionRequest(requestID,auction);
        return "Successful: Your request to start this auction has been sent to the admins.";
    }

    public ArrayList<Sale> getSellerSales(String username) {
        if (!(UserController.getInstance().getUserByUsername(username) instanceof Seller)) {
            return null;
        }
        ArrayList<Sale> allSales = getAllSaleFromDataBase();
        ArrayList<Sale> ans = new ArrayList<>();
        for (Sale sale : allSales) {
            if (sale.getSellerUsername().equals(username)) ans.add(sale);
        }
        return ans;
    }


    public String deleteSale(String id) {
        Sale sale = getSaleById(id);
        if (id == null) {
            return View.ANSI_RED+"Error: Sale doesn't exist."+View.ANSI_RESET;
        }
        Database.getInstance().deleteSale(sale);
        return "Successful: Deleted sale.";
    }

    public String deleteDiscountCode(String id) {
        DiscountCode code = getDiscountCodeById(id);
        if ((id == null) || (code == null)) {
            return View.ANSI_RED+"Error: Discount code doesn't exist."+View.ANSI_RESET;
        }
        Database.getInstance().deleteDiscountCode(code);
        return "Successful: Deleted discount code.";
    }

    public void giveRandomDiscountCode() {
        ArrayList<Buyer> allBuyers = UserController.getInstance().getAllBuyers();
        if(allBuyers.isEmpty()) return;
        Random random = new Random();
        int randomIndex;
        Buyer buyer;
        for(int i=0;i<3;i++) {
            randomIndex = random.nextInt(allBuyers.size());
            buyer = allBuyers.get(randomIndex);
            giveGiftDiscountCode(buyer.getUsername());
        }
    }

    public void giveGiftDiscountCode(String username) {
        ArrayList<String> allUsers = new ArrayList<>();
        allUsers.add(username);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now();
        endTime = endTime.plusDays(7);
        addDiscountCode(5, endTime, startTime, allUsers, 1, 500000);
    }

    public boolean isThereDiscountCodeWithId(String id) {
        Connection connection = null;
        int cnt = 0;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM DiscountCodes WHERE id='"+id+"'");
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

    public ArrayList<DiscountCode> getAllDiscountCodesFromDataBase() {
        ArrayList<DiscountCode> allDiscounts = new ArrayList<>();
        for(String id:Database.getInstance().getAllDiscountCodeIDs()){
            allDiscounts.add(getDiscountCodeById(id));
        }
        return allDiscounts;
    }

    public ArrayList<Sale> getAllSaleFromDataBase() {

        ArrayList<Sale> viableOptions = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Connection connection = null;
        try {
            connection = Database.getConn();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * FROM Sales");
            while(rs.next())
            {
                //bayad be viableoptions new ezafe konim
                String saleID = rs.getString(1);
                String seller = rs.getString(2);
                ArrayList<String> items = gson.fromJson(rs.getString(3),new TypeToken<ArrayList<String>>(){}.getType());
                String start = rs.getString(4);
                String end = rs.getString(5);
                int percent = rs.getInt(6);
                String status = rs.getString(7);
                viableOptions.add(new Sale(saleID,seller,items,start,end,percent,status));
            }

        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        return viableOptions;
    }

    public ArrayList<String> getAllItemsIDWithSale() {
        ArrayList<Item> allItems = ItemAndCategoryController.getInstance().getAllItemFromDataBase();
        ArrayList<String> itemsID = new ArrayList<>();

        for (Item item : allItems) {
            if(item.isInSale()){
                itemsID.add(item.getId());
            }
        }

        return itemsID;
    }

    public String editDiscountCodePercentage(String discountID, int percentage) {
        DiscountCode discountCode = getDiscountCodeById(discountID);
        if (discountCode == null) {
            return View.ANSI_RED+"Error: Discount code doesn't exist."+View.ANSI_RESET;
        }
        discountCode.setDiscountPercentage(percentage);
        Database.getInstance().saveDiscountCode(discountCode);
        return "Successful: Edited discount code.";
    }

    public String editDiscountCodeMaxDiscount(String discountID, double amount) {
        DiscountCode discountCode = getDiscountCodeById(discountID);
        if (discountCode == null) {
            return View.ANSI_RED+"Error: Discount code doesn't exist."+View.ANSI_RESET;
        }
        discountCode.setMaxDiscount(amount);
        Database.getInstance().saveDiscountCode(discountCode);
        return "Successful: Edited discount code.";
    }

    public String editDiscountCodeEndTime(String discountID, LocalDateTime endTime) {
        DiscountCode discountCode = getDiscountCodeById(discountID);
        if (discountCode == null) {
            return View.ANSI_RED+"Error: Discount code doesn't exist."+View.ANSI_RESET;
        }
        if (!endTime.isAfter(discountCode.getStartTime())) {
            return "Error: Ending time is after the starting time!";
        }
        discountCode.setEndTime(endTime);
        Database.getInstance().saveDiscountCode(discountCode);
        return "Successful: Edited discount code.";
    }

    public String editDiscountCodeUsageCount(String discountID, int newUsage) {
        if (newUsage <= 0) return "Error: Usage count can't be negative.";
        DiscountCode discountCode = getDiscountCodeById(discountID);
        if (discountCode == null) {
            return View.ANSI_RED+"Error: Discount code doesn't exist."+View.ANSI_RESET;
        }
        discountCode.changeUsageCount(newUsage);
        Database.getInstance().saveDiscountCode(discountCode);
        return "Successful: Edited discount code.";
    }

    public String addDiscountCode(int percentage, LocalDateTime endTime, LocalDateTime startTime, ArrayList<String> validUsers, int usageCount, double maxDiscount) {
        if (!endTime.isAfter(startTime)) {
            return "Error: ending time is after the starting time!";
        }
        DiscountCode discountCode = new DiscountCode(percentage, startTime, endTime, validUsers, usageCount, maxDiscount);
        Database.getInstance().saveDiscountCode(discountCode);
        return "Successful: Discount code created";
    }

    public String printDiscount(String id) {
        DiscountCode discountCode = getDiscountCodeById(id);
        if (discountCode == null) {
            return View.ANSI_RED+"Error: Discount code doesn't exist."+View.ANSI_RESET;
        }
        return discountCode.toString();
    }

    public boolean userCanUseDiscountCode(String discountID) {
        DiscountCode discountCode = getDiscountCodeById(discountID);
        return discountCode.userCanUse(Controller.getInstance().currentOnlineUser.getUsername());
    }

    public String editSale(String saleID, String changedField, String newFieldValue) {
        if (!isThereSaleWithId(saleID)) return "Error: invalid ID";
        String requestID = Controller.getInstance().getAlphaNumericString(5, "Sales");
        RequestController.getInstance().editSaleRequest(requestID, saleID, changedField, newFieldValue);
        return "Success: Edited sale.";
    }

    public boolean canAddItemToSale(String itemID) {
        if (!(UserController.getInstance().getCurrentOnlineUser() instanceof Seller)) {
            return false;
        }
        Seller seller = (Seller) UserController.getInstance().getCurrentOnlineUser();
        return (seller.hasItem(itemID) && !ItemAndCategoryController.getInstance().getItemById(itemID).isInSale());
    }

    protected void deleteDeprecatedDiscountCodes(LocalDateTime currentTime) {
        ArrayList<DiscountCode> allDiscountCodes = getAllDiscountCodesFromDataBase();
        for (DiscountCode discountCode : allDiscountCodes) {
            if (discountCode.getEndTime().isBefore(currentTime)) {
                deleteDiscountCode(discountCode.getDiscountId());
            }
        }
    }

    protected void deleteDeprecatedSales(LocalDateTime currentTime) {
        ArrayList<Sale> allSales = getAllSaleFromDataBase();
        for (Sale sale : allSales) {
            if (sale.getEndTime().isBefore(currentTime)) {
                deleteSale(sale.getId());
            }
        }

    }

}