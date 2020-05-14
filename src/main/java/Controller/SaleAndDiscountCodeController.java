package Controller;

import Model.DiscountCode;
import Model.Sale;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
        String path = "Resource" + File.separator + "Discount Codes";
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

    public String addSale(Sale sale) {
        String requestID = controller.getAlphaNumericString(controller.getIdSize(), "Requests");
        RequestController.getInstance().addSaleRequest(requestID, sale);
        return "your request for adding Sale was sent to our Admins!";
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
        if (id == null) {
            return "Error: discount code doesnt exist";
        }
        Database.getInstance().deleteDiscountCode(code);
        return "Successful";
    }

    public boolean isThereDiscountCodeWithId(String id) {
        String path = "Resource" + File.separator + "Discount Codes";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public ArrayList<DiscountCode> getAllDiscountCodesFromDataBase() {
        String path = "Resource" + File.separator + "Discount Codes";
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

    public String printItemsWithSale() {
        ArrayList<Sale> allSale = getAllSaleFromDataBase();
        String string = "";
        for (Sale sale : allSale) {
            string += sale.itemsInfo() + "\n";
        }
        if (string.isEmpty()) {
            return "there are no sales right now.";
        }
        return string;
    }

    public String editDiscountCodePercentage(String discountID, int percentage) {
        DiscountCode discountCode = getDiscountCodeById(discountID);
        if (discountCode == null) {
            return "Error: Discount code doesnt exist";
        }
        discountCode.setDiscountPercentage(percentage);
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
            return "Error: Discount code doesnt exist";
        }
        if(!endTime.isAfter(discountCode.getStartTime())){
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

    public String editDiscountCodeUsageCount(String discountID,int newUsage){
        if(newUsage<=0) return "Error: usage count has to be positive.";
        DiscountCode discountCode = getDiscountCodeById(discountID);
        if (discountCode == null) {
            return "Error: Discount code doesnt exist";
        }
        discountCode.changeUsageCount(newUsage);
        try {
            Database.getInstance().saveDiscountCode(discountCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Successful";
    }

    public String addDiscountCode(int percentage, LocalDateTime endTime,LocalDateTime startTime,ArrayList<String> validUsers,int usageCount,double maxDiscount) {
        if(!endTime.isAfter(startTime)){
            return "Error: ending time is after the starting time!";
        }
        DiscountCode discountCode = new DiscountCode(percentage,startTime,endTime,validUsers,usageCount,maxDiscount);
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

    public boolean userCanUseDiscountCode(String discountID){
        DiscountCode discountCode = getDiscountCodeById(discountID);
        return discountCode.userCanUse(Controller.getInstance().currentOnlineUser.getUsername());
    }

    public void editSale(String saleID, String changedField, String newFieldValue) {
        String requestID = Controller.getInstance().getAlphaNumericString(5, "Sales");
        RequestController.getInstance().editSaleRequest(requestID, saleID, changedField, newFieldValue);
    }

    protected void deleteDeprecatedDiscountCodes(LocalDateTime currentTime){
        ArrayList<DiscountCode> allDiscountCodes = getAllDiscountCodesFromDataBase();
        for(DiscountCode discountCode:allDiscountCodes){
            if(discountCode.getEndTime().isBefore(currentTime)){
                deleteDiscountCode(discountCode.getDiscountId());
            }
        }

    }

    protected void deleteDeprecatedSales(LocalDateTime currentTime){
        ArrayList<Sale> allSales = getAllSaleFromDataBase();
        for(Sale sale:allSales){
            if(sale.getEndTime().isBefore(currentTime)){
                deleteSale(sale.getId());
            }
        }

    }
}