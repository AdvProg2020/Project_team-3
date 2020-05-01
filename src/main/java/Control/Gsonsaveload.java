package Control;

import Model.DiscountCode;
import Model.Item;
import Model.Sale;
import Model.Users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Gsonsaveload {


    public static void saveUser(User user) throws IOException {
    Gson gson=new GsonBuilder().setPrettyPrinting().create();
    String Username=user.getUsername();
    String path="Users";
    String name=Username+".json";
    File file=new File(path+File.separator+name);
        if(!file.exists()){
            file.createNewFile();
        }
    FileWriter writer=new FileWriter(file);
    writer.write(gson.toJson(user));
    writer.close();
    }

    public static void saveItem(Item item) throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String id=item.getId();
        String path="Items";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer=new FileWriter(file);
        writer.write(gson.toJson(item));
        writer.close();
    }

    public static void saveSale(Sale sale) throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String id=sale.getId();
        String path="Sales";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer=new FileWriter(file);
        writer.write(gson.toJson(sale));
        writer.close();
    }

    public static void saveDiscountCode(DiscountCode discount) throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String id=discount.getDiscountId();
        String path="Discount Codes";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer=new FileWriter(file);
        writer.write(gson.toJson(discount));
        writer.close();
    }

    public static void deleteUser(User user){
        String Username=user.getUsername();
        String path="Users";
        String name=Username+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public static void deleteItem(Item item){
        String id=item.getId();
        String path="Items";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public static void deleteSale(Item item){
        String id=item.getId();
        String path="Sales";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public static void deleteDiscountCode(DiscountCode discount){
        String id=discount.getDiscountId();
        String path="Discount Codes";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public static void iniate(){
        File file=new File("Users");
        if(!file.exists()){
            file.mkdir();
        }
         file=new File("Items");
        if(!file.exists()){
            file.mkdir();
        }
        file=new File("Sales");
        if(!file.exists()){
            file.mkdir();
        }
        file=new File("Discount Codes");
        if(!file.exists()){
            file.mkdir();
        }
    }

}



