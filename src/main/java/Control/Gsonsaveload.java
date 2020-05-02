package Control;

import Model.DiscountCode;
import Model.Item;
import Model.Requests.Request;
import Model.Sale;
import Model.Users.Admin;
import Model.Users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Gsonsaveload<Public> {


    public static void saveUser(User user) throws IOException {
    Gson gson=new GsonBuilder().setPrettyPrinting().create();
    String Username=user.getUsername();
    String path="Resource"+File.separator+"Users";
    String name=Username+".json";
    File file=new File(path+File.separator+name);
        if(!file.exists()){
            file.createNewFile();
        }
    FileWriter writer=new FileWriter(file);
    writer.write(gson.toJson(user));
    writer.close();
    }

    public static void saveRequest(Request request)throws IOException{
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String requestID=request.getRequestId();
        String path="Resource"+File.separator+"Requests";
        String name=requestID+".json";
        File file=new File(path+File.separator+name);
            if(!file.exists()){
                file.createNewFile();
            }
        FileWriter writer=new FileWriter(file);
        writer.write(gson.toJson(request));
        writer.close();
    }


    public static void saveItem(Item item) throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String id=item.getId();
        String path="Resource"+File.separator+"Items";
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
        String path="Resource"+File.separator+"Sales";
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
        String path="Resource"+File.separator+"Discount Codes";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer=new FileWriter(file);
        writer.write(gson.toJson(discount));
        writer.close();
    }

    public static  void saveMainCategory() throws IOException{
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String path="Resource"+File.separator+"Main Category";
        String name="Main Category"+".json";
        File file=new File(path+File.separator+name);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer=new FileWriter(file);
        writer.write(gson.toJson(Controller.getInstance().mainCategory));
        writer.close();
    }


    public static void deleteUser(User user){
        String Username=user.getUsername();
        String path="Resource"+File.separator+"Users";
        String name=Username+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public static void deleteItem(Item item){
        String id=item.getId();
        String path="Resource"+File.separator+"Items";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public static void deleteSale(Sale sale){
        String id=sale.getId();
        String path="Resource"+File.separator+"Sales";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public static void deleteRequest(Request request){
        String id=request.getRequestId();
        String path="Resource"+File.separator+"Requests";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public static void deleteDiscountCode(DiscountCode discount){
        String id=discount.getDiscountId();
        String path="Resource"+File.separator+"Discount Codes";
        String name=id+".json";
        File file=new File(path+File.separator+name);
        file.delete();
    }

    public static void initiate(){
        File file=new File("Resource");
        if(!file.exists()){
            file.mkdir();
        }
         file=new File("Resource"+File.separator+"Users");
        if(!file.exists()){
            file.mkdir();
        }
         file=new File("Resource"+File.separator+"Items");
        if(!file.exists()){
            file.mkdir();
        }
        file=new File("Resource"+File.separator+"Sales");
        if(!file.exists()){
            file.mkdir();
        }
        file=new File("Resource"+File.separator+"Discount Codes");
        if(!file.exists()){
            file.mkdir();
        }
        file=new File("Resource"+File.separator+"Main Category");
        if(!file.exists()){
            file.mkdir();
        }
        file=new File("Resource"+File.separator+"Requests");
        if(!file.exists()){
            file.mkdir();
        }
        Admin.addAdminAccount("admin","12345","admin","admin","admin","admin");
    }

    public static ArrayList<String> printFolderContent(String folderName){
        ArrayList<String> fileNames=new ArrayList();
        String path="Resource"+File.separator+folderName;
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                fileNames.add(file.getName().replace(".json",""));
            }
        }
        return fileNames;
    }
}



