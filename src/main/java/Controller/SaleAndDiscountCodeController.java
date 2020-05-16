package Controller;

import Model.DiscountCode;
import Model.Item;
import Model.Sale;
import Model.Users.Buyer;
import Model.Users.Seller;
import Model.Users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        String path = "Resource" + File.separator + "DiscountCodes";
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
        return null;
    }

    public Sale getSaleById(String id) {
        String path = "Resource" + File.separator + "Sales";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return null;
        }
        Gson gson = new Gson();
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            return gson.fromJson(content, Sale.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isThereSaleWithId(String id) {
        String path = "Resource" + File.separator + "Sales";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return false;
        }
        return true;
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
            return "Error: ending time is after the starting time!";
        }
        Seller seller = (Seller) UserController.getInstance().getCurrentOnlineUser();
        for (String item : saleItems) {
            if (!seller.hasItem(item)) {
                return "Error: you do not have " + item + " in stock.";
            }
            if (ItemAndCategoryController.getInstance().getItemById(item).isInSale()) {
                return "Error: " + item + " is already in a sale.";
            }
        }
        Sale sale = new Sale(startTime, endTime, offPercentage, UserController.getInstance().getCurrentOnlineUser().getUsername(), saleItems);
        String requestID = controller.getAlphaNumericString(controller.getIdSize(), "Requests");
        RequestController.getInstance().addSaleRequest(requestID, sale);
        return "Successful: your request to add the sale was sent to the admins.";
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
            return "Error: sale doesnt exist";
        }
        Database.getInstance().deleteSale(sale);
        return "Successful";
    }

    public String deleteDiscountCode(String id) {
        DiscountCode code = getDiscountCodeById(id);
        if ((id == null) || (code == null)) {
            return "Error: discount code doesnt exist";
        }
        Database.getInstance().deleteDiscountCode(code);
        return "Successful";
    }

    public void giveRandomDiscountCode() {
        ArrayList<Buyer> allBuyers = UserController.getInstance().getAllBuyers();
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
        endTime.plusDays(7);
        addDiscountCode(5, endTime, startTime, allUsers, 1, 500000);
    }

    public boolean isThereDiscountCodeWithId(String id) {
        String path = "Resource" + File.separator + "DiscountCodes";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public ArrayList<DiscountCode> getAllDiscountCodesFromDataBase() {
        String path = "Resource" + File.separator + "DiscountCodes";
        File file = new File(path);
        File[] allFiles = file.listFiles();
        String fileContent = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<DiscountCode> allDiscounts = new ArrayList<>();
        for (File file1 : allFiles) {
            try {
                fileContent = new String(Files.readAllBytes(file1.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            allDiscounts.add(gson.fromJson(fileContent, DiscountCode.class));
        }
        return allDiscounts;
    }

    public ArrayList<Sale> getAllSaleFromDataBase() {
        String path = "Resource" + File.separator + "Sales";
        File file = new File(path);
        File[] allFiles = file.listFiles();
        String fileContent = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Sale> allSale = new ArrayList<>();
        for (File file1 : allFiles) {
            try {
                fileContent = new String(Files.readAllBytes(file1.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            allSale.add(gson.fromJson(fileContent, Sale.class));
        }
        return allSale;
    }

    public ArrayList<String> getAllSaleFromDataBaseToString() {
        ArrayList<Sale> allSale = getAllSaleFromDataBase();
        ArrayList<String> allSaleToString = new ArrayList<>();
        for (Sale sale : allSale) {
            allSaleToString.add(sale.toString());
        }
        return allSaleToString;
    }

    public ArrayList<String> getAllItemsIDWithSale() {
        ArrayList<Sale> allSale = getAllSaleFromDataBase();
        ArrayList<String> itemsID = new ArrayList<>();

        for (Sale sale : allSale) {
            itemsID.addAll(sale.getAllItemId());
        }

        return itemsID;
    }

    public String editDiscountCodePercentage(String discountID, int percentage) {
        DiscountCode discountCode = getDiscountCodeById(discountID);
        if (discountCode == null) {
            return "Error: invalid ID for discount code.";
        }
        discountCode.setDiscountPercentage(percentage);
        try {
            Database.getInstance().saveDiscountCode(discountCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Successful:";
    }

    public String editDiscountCodeMaxDiscount(String discountID, double amount) {
        DiscountCode discountCode = getDiscountCodeById(discountID);
        if (discountCode == null) {
            return "Error: invalid ID for discount code.";
        }
        discountCode.setMaxDiscount(amount);
        try {
            Database.getInstance().saveDiscountCode(discountCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Successful:";
    }

    public String editDiscountCodeEndTime(String discountID, LocalDateTime endTime) {
        DiscountCode discountCode = getDiscountCodeById(discountID);
        if (discountCode == null) {
            return "Error: invalid ID for discount code.";
        }
        if (!endTime.isAfter(discountCode.getStartTime())) {
            return "Error: ending time is after the starting time!";
        }
        discountCode.setEndTime(endTime);
        try {
            Database.getInstance().saveDiscountCode(discountCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Successful";
    }

    public String editDiscountCodeUsageCount(String discountID, int newUsage) {
        if (newUsage <= 0) return "Error: usage count has to be positive.";
        DiscountCode discountCode = getDiscountCodeById(discountID);
        if (discountCode == null) {
            return "Error: invalid ID for discount code.";
        }
        discountCode.changeUsageCount(newUsage);
        try {
            Database.getInstance().saveDiscountCode(discountCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Successful";
    }

    public String addDiscountCode(int percentage, LocalDateTime endTime, LocalDateTime startTime, ArrayList<String> validUsers, int usageCount, double maxDiscount) {
        if (!endTime.isAfter(startTime)) {
            return "Error: ending time is after the starting time!";
        }
        DiscountCode discountCode = new DiscountCode(percentage, startTime, endTime, validUsers, usageCount, maxDiscount);
        try {
            Database.getInstance().saveDiscountCode(discountCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Successful: discount code created";
    }

    public String printDiscount(String id) {
        DiscountCode discountCode = getDiscountCodeById(id);
        if (discountCode == null) {
            return "Error: discount code doesnt exist";
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
        return "Success:";
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

    public String addItemToSale(String itemID,String saleID){
        if(ItemAndCategoryController.getInstance().isThereItemWithId(itemID)==false){
            return "invalid item id!";
        }
        if(isThereSaleWithId(saleID)==false){
            return "invalid sale id!";
        }
        if(canAddItemToSale(itemID)==false){
            return "the item can not be added to sale!";
        }
        Sale sale=getSaleById(saleID);
        Item item=ItemAndCategoryController.getInstance().getItemById(itemID);
        item.setSale(saleID);
        double inStock= item.getPrice()*(1-((double)sale.getOffPercentage()/100));
        item.setInStock((int) inStock);
        sale.addItemToSale(itemID);
        Seller seller=(Seller) UserController.getInstance().getUserByUsername(item.getSellerName());
        seller.addAllSaleId(sale.getId());
        try {
            Database.getInstance().saveUser(seller);
            Database.getInstance().saveSale(sale);
            Database.getInstance().saveItem(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "the item added to sale successfully!";
    }
}