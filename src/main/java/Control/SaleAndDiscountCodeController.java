package Control;

import Model.DiscountCode;
import Model.Requests.Request;
import Model.Sale;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class SaleAndDiscountCodeController {
    Controller controller = Controller.getInstance();
    private static SaleAndDiscountCodeController saleAndDiscountCodeController;

    private SaleAndDiscountCodeController(){}

    public static SaleAndDiscountCodeController getInstance(){
        if(saleAndDiscountCodeController==null){
            saleAndDiscountCodeController=new SaleAndDiscountCodeController();
        }
        return saleAndDiscountCodeController;
    }


    public DiscountCode getDiscountCodeById(String id) {
        String path="Resource"+File.separator+"Discount Codes";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            return null;
        }
        Gson gson=new Gson();
        try {
            String content=new String(Files.readAllBytes(file.toPath()));
            return gson.fromJson(content, DiscountCode.class);}
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Sale getSaleById(String id) {
        String path="Resource"+File.separator+"Sales";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            return null;
        }
        Gson gson=new Gson();
        try {
            String content=new String(Files.readAllBytes(file.toPath()));
            return gson.fromJson(content, Sale.class);}
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isThereSaleWithId(String id){
        String path="Resource"+File.separator+"Sales";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            return false;
        }
            return true;
    }

    public String addSale(Sale sale) throws IOException {
        String requestId=controller.addId(Request.getIdCount());
        RequestController.getInstance().addSaleRequest(requestId,sale);
        return "your request for adding Sale was sent to our Admins!";
    }

    public void deleteSale(String id){
        String path="Resource"+File.separator+"Sales";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public void deleteDiscountCode(String id){
        String path="Resource"+File.separator+"Discount Codes";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public boolean isThereDiscountCodeWithId(String id){
        String path="Resource"+File.separator+"Discount Codes";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            return false;
        }
            return true;
    }

    public ArrayList<DiscountCode> getAllDiscountCodesFromDataBase(){
        String path="Resource"+File.separator+"Discount Codes";
        File file=new File(path);
        File [] allFiles=file.listFiles();
        String fileContent = null;
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        ArrayList<DiscountCode>allDiscounts=new ArrayList<>();
            for(File file1:allFiles){
                try {
                    fileContent=new String(Files.readAllBytes(file1.toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            allDiscounts.add(gson.fromJson(fileContent,DiscountCode.class));
            }
        return allDiscounts;
    }

    public ArrayList<Sale> getAllSaleFromDataBase(){
        String path="Resource"+File.separator+"Discount Codes";
        File file=new File(path);
        File [] allFiles=file.listFiles();
        String fileContent = null;
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Sale>allSale=new ArrayList<>();
        for(File file1:allFiles){
            try {
                fileContent=new String(Files.readAllBytes(file1.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            allSale.add(gson.fromJson(fileContent,Sale.class));
        }
        return allSale;
    }





}