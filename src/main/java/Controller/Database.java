package Controller;

import Model.Category;
import Model.DiscountCode;
import Model.Item;
import Model.Requests.Request;
import Model.Sale;
import Model.Users.User;
import View.Menus.LoginRegisterMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Database<Public> {
    private static Database database;

    private Database() {
    }

    public static Database getInstance() {
        if (database == null)
            database = new Database();
        return database;
    }

    public void saveUser(User user) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String Username = user.getUsername();
        String path = "Resource" + File.separator + "Users";
        String name = Username + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(gson.toJson(user));
        writer.close();
    }

    public void saveRequest(Request request) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String requestID = request.getRequestId();
        String path = "Resource" + File.separator + "Requests";
        String name = requestID + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(gson.toJson(request));
        writer.close();
    }


    public void saveItem(Item item) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String id = item.getId();
        String path = "Resource" + File.separator + "Items";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(gson.toJson(item));
        writer.close();
    }

    public void saveSale(Sale sale) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String id = sale.getId();
        String path = "Resource" + File.separator + "Sales";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(gson.toJson(sale));
        writer.close();
    }

    public void saveDiscountCode(DiscountCode discount) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String id = discount.getDiscountId();
        String path = "Resource" + File.separator + "DiscountCodes";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(gson.toJson(discount));
        writer.close();
    }

    public void saveCategory(Category category) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String path = "Resource" + File.separator + "Categories";
        String name = category.getName() + ".json";
        File file = new File(path + File.separator + name);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(gson.toJson(category));
        writer.close();
    }


    public void deleteUser(User user) {
        String Username = user.getUsername();
        String path = "Resource" + File.separator + "Users";
        String name = Username + ".json";
        File file = new File(path + File.separator + name);
        file.delete();
    }

    public void deleteItem(Item item) {
        item.delete();
        String id = item.getId();
        String path = "Resource" + File.separator + "Items";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        file.delete();
    }

    public void deleteSale(Sale sale) {
        String id = sale.getId();
        String path = "Resource" + File.separator + "Sales";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        file.delete();
    }


    public void deleteCategory(Category category) {
        Item item;
        for (String id : category.getSubCategories()) {
            item = ItemAndCategoryController.getInstance().getItemById(id);
            deleteItem(item);
        }
        String categoryName = category.getName();
        String path = "Resource" + File.separator + "Categories";
        String name = categoryName + ".json";
        File file = new File(path + File.separator + name);
        file.delete();
    }

    public void deleteRequest(Request request) {
        String id = request.getRequestId();
        String path = "Resource" + File.separator + "Requests";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        file.delete();
    }

    public void deleteDiscountCode(DiscountCode discount) {
        String id = discount.getDiscountId();
        String path = "Resource" + File.separator + "Discount9999999Codes";
        String name = id + ".json";
        File file = new File(path + File.separator + name);
        file.delete();
    }

    public void initiate() {
        File file = new File("Resource");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File("Resource" + File.separator + "Users");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File("Resource" + File.separator + "Items");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File("Resource" + File.separator + "Sales");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File("Resource" + File.separator + "DiscountCodes");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File("Resource" + File.separator + "Categories");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File("Resource" + File.separator + "Requests");
        if (!file.exists()) {
            file.mkdir();
        }
        if(printFolderContent("Users").isEmpty()){
            System.out.println("there are no admin account.please renter the information to make an admin.");
            LoginRegisterMenu.getInstance().registerAdmin();
        }
        //Admin.addAdminAccount("admin", "12345", "admin", "admin", "admin", "admin");
        Category category = new Category("Main", null);
        try {
            saveCategory(category);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> printFolderContent(String folderName) {
        ArrayList<String> fileNames = new ArrayList();
        String path = "Resource" + File.separator + folderName;
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if(file.getName().equals(".DS_Store")){
                    continue;
                }
                fileNames.add(file.getName().replace(".json", ""));
            }
        }
        return fileNames;
    }


}



