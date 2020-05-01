package Control;

import Model.DiscountCode;
import Model.Requests.Request;
import Model.Sale;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
        String path="Discount Codes";
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
        String path="Sales";
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
        String path="Sales";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            return false;
        }
            return true;
    }

    public String addSale(Sale sale){
        String requestId=controller.addId(Request.getIdCount());
        RequestController.getInstance().addSaleRequest(requestId,sale);
        return "your request for adding Sale was sent to our Admins!";
    }

    public void deleteSale(String id){
        String path="Sales";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public void deleteDiscountCode(String id){
        String path="Discount Codes";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public boolean isThereDiscountCodeWithId(String id){
        String path="Discount Codes";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            return false;
        }
            return true;
    }
}