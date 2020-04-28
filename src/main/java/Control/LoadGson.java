package Control;

import Model.Category;
import Model.DiscountCode;
import Model.Item;
import Model.Requests.Request;
import Model.Sale;
import Model.Users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LoadGson {
    private static LoadGson loadGson;
    private Controller controller;
    private LoadGson(){}

    public LoadGson getInstance(){
        if(loadGson==null)
            loadGson=new LoadGson();
        return loadGson;
    }

    private void loadUser() throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String content=new String(Files.readAllBytes(Paths.get("User.json")));
        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
        controller.allUsers=new ArrayList<User>(gson.fromJson(content,userListType));
    }

    private void loadSale() throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String content=new String(Files.readAllBytes(Paths.get("Sale.json")));
        Type saleListType = new TypeToken<ArrayList<Sale>>(){}.getType();
        controller.allSales=new ArrayList<Sale>(gson.fromJson(content,saleListType));
    }

    private void loadItem() throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String content=new String(Files.readAllBytes(Paths.get("Item.json")));
        Type itemListType = new TypeToken<ArrayList<Item>>(){}.getType();
        controller.allItems=new ArrayList<Item>(gson.fromJson(content,itemListType));
    }

    private void loadDiscountCode() throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String content=new String(Files.readAllBytes(Paths.get("DiscountCode.json")));
        Type discountCodeListType = new TypeToken<ArrayList<DiscountCode>>(){}.getType();
        controller.allDiscountCodes=new ArrayList<DiscountCode>(gson.fromJson(content,discountCodeListType));
    }

    private void loadRequest() throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String content=new String(Files.readAllBytes(Paths.get("Request.json")));
        Type requestListType = new TypeToken<ArrayList<Request>>(){}.getType();
        controller.allRequests=new ArrayList<Request>(gson.fromJson(content,requestListType));
    }

    private void loadMainCategory() throws IOException {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        String content=new String(Files.readAllBytes(Paths.get("MainCategory.json")));
        controller.mainCategory=gson.fromJson(content, Category.class);
    }

    public void loadFromDataBase() throws IOException {
        System.out.println("Loading...");
        loadUser();
        loadItem();
        loadDiscountCode();
        loadMainCategory();
        loadRequest();
        loadSale();
        System.out.println("loaded and Ready!");
    }
}