package Project.Controller;

import Project.Model.Category;
import Project.Model.DiscountCode;
import Project.Model.Item;
import Project.Model.Requests.Request;
import Project.Model.Sale;
import Project.Model.Users.Admin;
import Project.Model.Users.Buyer;
import Project.Model.Users.Seller;
import Project.Model.Users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Database {
   private static Database database;

   private Database() {
   }

   public static Database getInstance() {
      if (database == null)
         database = new Database();
      return database;
   }

   private static Connection c = null;
   public static Connection getConn() throws SQLException {
      if(c == null){
         c = DriverManager.getConnection("jdbc:sqlite:database.db");
      }
      return c;
   }


   public void saveUser(User user)  {
      /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String Username = user.getUsername();
      String path = "Resource" + File.separator + "Users";
      String name = Username + ".json";
      File file = new File(path + File.separator + name);
      try{
      if (!file.exists()) {
         file.createNewFile();
      }
         FileWriter writer = new FileWriter(file);
         writer.write(gson.toJson(user));
         writer.close();
      }catch(IOException exception){
         exception.printStackTrace();
      }*/
      //inja ba sql
      if(user instanceof Admin){
         insertAdmin((Admin)user);
      }
      else if(user instanceof Buyer){
         insertBuyer((Buyer)user);
      }
      else if(user instanceof Seller){
         insertSeller((Seller)user);
      }
   }

   private void insertAdmin(Admin admin){
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String allRequests=gson.toJson(admin.getReqMap());
      String values = "'"+admin.getUsername()+"', '"+admin.getPassword()+"', '"+admin.getName()+"', '"+admin.getLastName()+"', '"+admin.getEmail()+"', '"+admin.getNumber();
      values+= "', '"+ "Admin" +"', '"+allRequests+"'";
      Connection connection = null;
      try
      {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         try {
            statement.executeUpdate("delete FROM Admins WHERE username='"+admin.getUsername()+"'");
         }catch (Exception e){

         }
         statement.executeUpdate("insert into Admins values("+values+")");
      }
      catch(SQLException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private void insertBuyer(Buyer buyer){
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String allRequests=gson.toJson(buyer.getReqMap());
      String logs = gson.toJson(buyer.getBuyLogs());
      String values = "'"+buyer.getUsername()+"', '"+buyer.getPassword()+"', '"+buyer.getName()+"', '"+buyer.getLastName()+"', '"+buyer.getEmail()+"', '"+buyer.getNumber();
      values+= "', '"+ "Buyer" +"', '"+allRequests+"', '"+logs+"', '"+buyer.getMoney()+"'";
      Connection connection = null;
      try
      {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         try {
            statement.executeUpdate("delete FROM Buyers WHERE username='"+buyer.getUsername()+"'");
         }catch (Exception e){

         }
         statement.executeUpdate("insert into Buyers values("+values+")");
      }
      catch(SQLException e)
      {
         System.err.println(e.getMessage());
      }

   }

   private void insertSeller(Seller seller){
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String allRequests=gson.toJson(seller.getReqMap());
      String logs = gson.toJson(seller.getSellLogs());
      String items = gson.toJson(seller.getAllItemsId());
      String values = "'"+seller.getUsername()+"', '"+seller.getPassword()+"', '"+seller.getName()+"', '"+seller.getLastName()+"', '"+seller.getEmail()+"', '"+seller.getNumber();
      values+= "', '"+ "Seller" +"', '"+allRequests+"', '"+seller.getCompanyName()+"', '"+logs+"', '"+items+"', '"+seller.getValid()+"', '"+seller.getMoney()+"'";
      Connection connection = null;
      try
      {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         try {
            statement.executeUpdate("delete FROM Sellers WHERE username='"+seller.getUsername()+"'");
         }catch (Exception e){

         }
         statement.executeUpdate("insert into Sellers values("+values+")");
      }
      catch(SQLException e)
      {
         System.err.println(e.getMessage());
      }

   }

   public void saveRequest(Request request)  {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String requestID = request.getRequestId();
      String path = "Resource" + File.separator + "Requests";
      String name = requestID + ".json";
      File file = new File(path + File.separator + name);
      try{
      if (!file.exists()) {
         file.createNewFile();
      }
         FileWriter writer = new FileWriter(file);
         writer.write(gson.toJson(request));
         writer.close();
      }catch(IOException exception){
         exception.printStackTrace();
      }
   }


   public void saveItem(Item item)  {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String id = item.getId();
      String path = "Resource" + File.separator + "Items";
      String name = id + ".json";
      File file = new File(path + File.separator + name);
      try{
      if (!file.exists()) {
         file.createNewFile();
      }
      FileWriter writer = new FileWriter(file);
      writer.write(gson.toJson(item));
      writer.close(); }
      catch(IOException exception){
         exception.printStackTrace();
      }
   }

   public void saveSale(Sale sale)  {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String id = sale.getId();
      String path = "Resource" + File.separator + "Sales";
      String name = id + ".json";
      File file = new File(path + File.separator + name);
      try{
      if (!file.exists()) {
         file.createNewFile();
      }
      FileWriter writer = new FileWriter(file);
      writer.write(gson.toJson(sale));
      writer.close();
      }catch(IOException exception){
         exception.printStackTrace();
      }
   }

   public void saveDiscountCode(DiscountCode discount) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String id = discount.getDiscountId();
      String path = "Resource" + File.separator + "DiscountCodes";
      String name = id + ".json";
      File file = new File(path + File.separator + name);
      try {
         if (!file.exists()) {
            file.createNewFile();
         }
         FileWriter writer = new FileWriter(file);
         writer.write(gson.toJson(discount));
         writer.close();
         }catch(IOException exception){
         exception.printStackTrace();
      }
   }

   public void saveCategory(Category category) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String path = "Resource" + File.separator + "Categories";
      String name = category.getName() + ".json";
      File file = new File(path + File.separator + name);
      try {
         if (!file.exists()) {
            file.createNewFile();
         }
         FileWriter writer = new FileWriter(file);
         writer.write(gson.toJson(category));
         writer.close();
      }catch(IOException exception){
         exception.printStackTrace();
      }
   }

   public void deleteUser(User user) {
      String Username = user.getUsername();
      String path = "Resource" + File.separator + "Users";
      String name = Username + ".json";
      File file = new File(path + File.separator + name);
      file.delete();
      Connection connection = null;
      String tableName = user.getType() + "s";
      try
      {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         statement.executeUpdate("delete FROM "+tableName +" WHERE username='"+user.getUsername()+"'");
      }
      catch(SQLException e)
      {
         System.err.println(e.getMessage());
      }

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
      for (String id : sale.getAllItemId()) {
         Item item = ItemAndCategoryController.getInstance().getItemById(id);
         if (item != null) {
            item.setSale("");
         }
      }
      String id = sale.getId();
      String path = "Resource" + File.separator + "Sales";
      String name = id + ".json";
      File file = new File(path + File.separator + name);
      file.delete();
   }


   public void deleteCategory(Category category) {
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
      String path = "Resource" + File.separator + "DiscountCodes";
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
      file = new File("Resource" + File.separator + "Commercials");
      if (!file.exists()) {
         file.mkdir();
      }
      if (!UserController.getInstance().isThereUserWithUsername("admin")) {
         Admin.addAdminAccount("admin", "12345", "admin", "admin", "admin", "admin");
      }
      if (!ItemAndCategoryController.getInstance().isThereCategoryWithName("Project.Main")) {
         Category category = new Category("Project.Main", null);
            saveCategory(category);
      }
   }

   public ArrayList<String> printFolderContent(String folderName) {
      ArrayList<String> fileNames = new ArrayList();
      String path = "Resource" + File.separator + folderName;
      File[] files = new File(path).listFiles();
      for (File file : files) {
         if (file.isFile()) {
            if (file.getName().equals(".DS_Store")) {
               continue;
            }
            fileNames.add(file.getName().replace(".json", ""));
         }
      }
      if (folderName.equals("DiscountCodes")) {
         ArrayList<String> discountCodeInfo = new ArrayList<>();
         for (String fileName : fileNames) {
            DiscountCode discountCode = SaleAndDiscountCodeController.getInstance().getDiscountCodeById(fileName);
            if (discountCode == null) {
               continue;
            }
            discountCodeInfo.add(discountCode.toSimpleString());
         }
         return discountCodeInfo;
      }
      if (folderName.equals("Requests")) {
         ArrayList<String> requestInfo = new ArrayList<>();
         for (String fileName : fileNames) {
            Request request = RequestController.getInstance().getRequestById(fileName);
            if (request == null) {
               continue;
            }
            requestInfo.add(request.toString());
         }
         return requestInfo;
      }
      return fileNames;
   }

   public ArrayList<String> getAllUsername(String type){
      ArrayList<String> allUser = new ArrayList<>();
      Connection connection = null;
      try {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         ResultSet rs = statement.executeQuery("select * FROM Admins");
         while(rs.next())
         {
            allUser.add(rs.getString(1));
         }
         rs = statement.executeQuery("select * FROM Buyers");
         while(rs.next())
         {
            allUser.add(rs.getString(1));
         }
         rs = statement.executeQuery("select * FROM Sellers");
         while(rs.next())
         {
            allUser.add(rs.getString(1));
         }
      }
      catch(SQLException e) {
         System.err.println(e.getMessage());
      }

      ArrayList<String> specificUser=new ArrayList<>();
      for (String user : allUser) {
         if(UserController.getInstance().getUserType(user).equals(type))
            specificUser.add(user);
      }
      return specificUser;
   }
}


