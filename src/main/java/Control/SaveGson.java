package Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class SaveGson {
    private static Controller controller = Controller.getInstance();


    private static void saveUser() throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer=new FileWriter("User.json");
        writer.write(gson.toJson(controller.allUsers));
        writer.close();
    }

    private static void saveItem() throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer=new FileWriter("Item.json");
        writer.write(gson.toJson(controller.allItems));
        writer.close();
    }

    private static void saveRequest() throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer=new FileWriter("Request.json");
        writer.write(gson.toJson(controller.allRequests));
        writer.close();
    }

    private static void saveSale() throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer=new FileWriter("Sale.json");
        writer.write(gson.toJson(controller.allSales));
        writer.close();
    }

    private static void saveDiscountCode() throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer=new FileWriter("DiscountCode.json");
        writer.write(gson.toJson(controller.allDiscountCodes));
        writer.close();
    }

    private static void saveMainCategory() throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer=new FileWriter("MainCategory.json");
        writer.write(gson.toJson(controller.mainCategory));
        writer.close();
    }

    public static void saveDataInDataBase(){
        try{
            saveUser();
            saveSale();
            saveItem();
            saveDiscountCode();
            saveRequest();
            saveMainCategory();
        }
        catch (IOException e){
            //System.out.println("input output error was occurred!");// naaaaaaaaa oskol
        }
    }
}