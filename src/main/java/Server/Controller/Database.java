package Server.Controller;


import Server.Model.*;
import Server.Model.Chat.Channel;
import Server.Model.Requests.Request;
import Server.Model.Users.*;
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
      if(user instanceof Admin){
         insertAdmin((Admin)user);
      }
      else if(user instanceof Buyer){
         insertBuyer((Buyer)user);
      }
      else if(user instanceof Seller){
         insertSeller((Seller)user);
      }
      else if(user instanceof Assistant){
         insertAssistant((Assistant)user);
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

   private void insertAssistant(Assistant assistant){
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String allRequests=gson.toJson(assistant.getReqMap());
      String values = "'"+assistant.getUsername()+"', '"+assistant.getPassword()+"', '"+assistant.getName()+"', '"+assistant.getLastName()+"', '"+assistant.getEmail()+"', '"+assistant.getNumber();
      values+= "', '"+ "Assistant" +"', '"+allRequests+"'";
      Connection connection = null;
      try
      {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         try {
            statement.executeUpdate("delete FROM Assistants WHERE username='"+assistant.getUsername()+"'");
         }catch (Exception e){

         }
         statement.executeUpdate("insert into Assistants values("+values+")");
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
      System.out.println("djwodjwpjdpwjdpw");
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
      /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
      }*/
      //
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String attributes=gson.toJson(item.getAttributes());
      String buyers = gson.toJson(item.getBuyerUserName());
      String ratings = gson.toJson(item.getAllRatings());
      String comments = gson.toJson(item.getAllComments());
      String values = "'"+item.getId()+"', '"+item.getState()+"', '"+item.getDescription()+"', '"+item.getName()+"', '"+item.getBrand()+"', '"+item.getPrice();
      values+= "', "+ item.getInStock() +", "+item.getViewCount()+", '"+attributes+"', '"+item.getSellerName()+"', '"+item.getCategoryName()+"', '"+buyers+"', '"+ratings;
      values+= "', '" + comments + "', '" + item.getSaleId() + "', '" + item.getImageName() + "', '" + item.getVideoName() + "', '" + item.getAddedTime() + "'";
      Connection connection = null;
      try
      {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         try {
            statement.executeUpdate("delete FROM Items WHERE id='"+item.getId()+"'");
         }catch (Exception e){

         }
         System.err.println(values);
         statement.executeUpdate("insert into Items values("+values+")");
         System.err.println(values);
      }
      catch(SQLException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public void saveSale(Sale sale)  {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      Connection connection = null;
      String items = gson.toJson(sale.getAllItemId());
      String values = "'" + sale.getId() +"', '"+sale.getSellerUsername()+"', '"+items+"', '"+sale.getStartTime().toString()+"', '"+sale.getEndTime().toString();
      values += "', " + sale.getOffPercentage() + ", '" + sale.getStatus() + "'";

      try{
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         try {
            statement.executeUpdate("delete FROM Sales WHERE id='"+sale.getId()+"'");
         }catch (Exception e){

         }
         statement.executeUpdate("insert into Sales values("+values+")");
      }catch (SQLException e){
         System.err.println(e.getMessage());
      }

   }

   public void saveDiscountCode(DiscountCode discount) {

      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      Connection connection = null;
      String values = "'"+discount.getDiscountId()+"', "+discount.getDiscountPercentage()+", '"+discount.getMaxDiscount()+"', '";
      String usageCount = gson.toJson(discount.getUsageCount());
      values+=usageCount+"', "+discount.getUsageCountInt()+", '"+discount.getStartTime()+"', '"+discount.getEndTime()+"'";

      try{
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         try {
            statement.executeUpdate("delete FROM DiscountCodes WHERE id='"+discount.getDiscountId()+"'");
         }catch (Exception e){

         }
         statement.executeUpdate("insert into DiscountCodes values("+values+")");
      }catch (SQLException e){
         System.err.println(e.getMessage());
      }
   }

   public void saveCategory(Category category) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();

      Connection connection = null;
      String children = gson.toJson(category.getSubCategories());
      String attributes = gson.toJson(category.getAttributes());
      String items = gson.toJson(category.getAllItemsID());
      String values = "'" + category.getName() +"', '"+category.getParent()+"', '"+items+"', '"+attributes+"', '"+children+"'";

      try{
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         try {
            statement.executeUpdate("delete FROM Categories WHERE name='"+category.getName()+"'");
         }catch (Exception e){

         }
         statement.executeUpdate("insert into Categories values("+values+")");
      }catch (SQLException e){
         System.err.println(e.getMessage());
      }
   }

   public void saveAuction(Auction auction){
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      Connection connection = null;
      String values = "'" + auction.getId() +"', '"+auction.getItemID()+"', '"+auction.getEndTime()+"', '"+auction.getHighestBidderUsername()+"', '"+auction.getHighestBid()+"', '";
      values += gson.toJson(auction.getChat()) + "'";
      try{
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         try {
            statement.executeUpdate("delete FROM Auctions WHERE id='"+auction.getId()+"'");
         }catch (Exception e){

         }
         statement.executeUpdate("insert into Auctions values("+values+")");
      }catch (SQLException e){
         System.err.println(e.getMessage());
      }
   }

   public void deleteAuction(Auction auction){
      Connection connection = null;
      try{
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         statement.executeUpdate("delete FROM Auctions WHERE id='"+auction.getId()+"'");
      }catch (SQLException e){
         System.err.println(e.getMessage());
      }
   }

   public void saveChannel(Channel channel) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String path = "Resource" + File.separator + "Channels";
      String name = channel.getName() + ".json";
      File file = new File(path + File.separator + name);
      try {
         if (!file.exists()) {
            file.createNewFile();
         }
         FileWriter writer = new FileWriter(file);
         writer.write(gson.toJson(channel));
         writer.close();
      } catch (IOException exception) {
         exception.printStackTrace();
      }
   }

   public void deleteUser(User user) {
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
      /*item.delete();
      String id = item.getId();
      String path = "Resource" + File.separator + "Items";
      String name = id + ".json";
      File file = new File(path + File.separator + name);
      file.delete();*/

      Connection connection = null;
      try
      {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         statement.executeUpdate("delete FROM Items WHERE id='"+item.getId()+"'");
      }
      catch(SQLException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public void deleteSale(Sale sale) {

      Connection connection = null;
      try{
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         try {
            statement.executeUpdate("delete FROM Sales WHERE id='"+sale.getId()+"'");
         }catch (Exception e){

         }
      }catch (SQLException e){
         System.err.println(e.getMessage());
      }
   }

   public void deleteCategory(Category category) {

      Connection connection = null;
      try
      {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         statement.executeUpdate("delete FROM Categories WHERE name='"+category.getName()+"'");
      }
      catch(SQLException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public void deleteRequest(Request request) {
      String id = request.getRequestId();
      String path = "Resource" + File.separator + "Requests";
      String name = id + ".json";
      File file = new File(path + File.separator + name);
      file.delete();
   }

   public void deleteDiscountCode(DiscountCode discount) {

      Connection connection = null;
      try{
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         statement.executeUpdate("delete FROM DiscountCodes WHERE id='"+discount.getDiscountId()+"'");
      }catch (SQLException e){
         System.err.println(e.getMessage());
      }
   }

   public void initiate() {
      File file = new File("Resource");
      if (!file.exists()) {
         file.mkdir();
      }

      file = new File("Resource" + File.separator + "Items");
      if (!file.exists()) {
         file.mkdir();
      }

      file = new File("Resource" + File.separator + "DiscountCodes");
      if (!file.exists()) {
         file.mkdir();
      }

      file = new File("Resource" + File.separator + "Requests");
      if (!file.exists()) {
         file.mkdir();
      }

      file = new File("Resource" + File.separator + "Channels");
      if (!file.exists()) {
         file.mkdir();
      }
      
      if (!UserController.getInstance().isThereUserWithUsername("admin")) {
         Admin.addAdminAccount("admin", "12345", "admin", "admin", "admin", "admin");
      }
      if (!ItemAndCategoryController.getInstance().isThereCategoryWithName("Main")) {
         Category category = new Category("Main", new ArrayList<String>());
            saveCategory(category);
      }
      if (!ItemAndCategoryController.getInstance().isThereCategoryWithName("File")) {
         Category category = new Category("File", new ArrayList<String>());
         saveCategory(category);
      }
   }

   public ArrayList<String> printFolderContent(String folderName) {
      if(folderName.equals("Users")){
         ArrayList<String> allUsers = new ArrayList<>();
         allUsers.addAll(getAllUsername("Admin"));
         allUsers.addAll(getAllUsername("Buyer"));
         allUsers.addAll(getAllUsername("Seller"));
         allUsers.addAll(getAllUsername("Assistant"));
         return allUsers;
      }
      if(folderName.equals("Categories")){
         ArrayList<String> result=getAllCategories();
         result.remove("File");
         return result;
      }
      if(folderName.equals("Sales")){
         return getAllSales();
      }
      if(folderName.equals("Items")){
         return getAllItemIDs();
      }
      ArrayList<String> fileNames = new ArrayList<>();
      if (folderName.equals("DiscountCodes")) {
         ArrayList<String> discountCodeInfo = new ArrayList<>();
         for (String fileName : getAllDiscountCodeIDs()) {
            DiscountCode discountCode = SaleAndDiscountCodeController.getInstance().getDiscountCodeById(fileName);
            if (discountCode == null) {
               continue;
            }
            discountCodeInfo.add(discountCode.toSimpleString());
         }
         return discountCodeInfo;
      }
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
      String tableName = type + "s";
      ArrayList<String> allUser = new ArrayList<>();
      Connection connection = null;
      try {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         ResultSet rs = statement.executeQuery("select * FROM "+tableName);
         while(rs.next())
         {
            allUser.add(rs.getString(1));
         }

      }
      catch(SQLException e) {
         System.err.println(e.getMessage());
      }
      return allUser;
   }

   public ArrayList<String> getAllCategories(){
      ArrayList<String> allCats = new ArrayList<>();
      Connection connection = null;
      try {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         ResultSet rs = statement.executeQuery("select * FROM Categories");
         while(rs.next())
         {
            allCats.add(rs.getString(1));
         }

      }
      catch(SQLException e) {
         System.err.println(e.getMessage());
      }
      return allCats;
   }

   public ArrayList<String> getAllSales(){
      ArrayList<String> allSales = new ArrayList<>();
      Connection connection = null;
      try {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         ResultSet rs = statement.executeQuery("select * FROM Sales");
         while(rs.next())
         {
            allSales.add(rs.getString(1));
         }

      }
      catch(SQLException e) {
         System.err.println(e.getMessage());
      }
      return allSales;
   }

   public ArrayList<String> getAllItemIDs(){
      ArrayList<String> allItems = new ArrayList<>();
      Connection connection = null;
      try {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         ResultSet rs = statement.executeQuery("select * FROM Items");
         while(rs.next())
         {
            allItems.add(rs.getString(1));
         }

      }
      catch(SQLException e) {
         System.err.println(e.getMessage());
      }
      return allItems;
   }

   public ArrayList<String> getAllDiscountCodeIDs(){
      ArrayList<String> allDiscountCodes = new ArrayList<>();
      Connection connection = null;
      try {
         connection = getConn();
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);
         ResultSet rs = statement.executeQuery("select * FROM DiscountCodes");
         while(rs.next())
         {
            allDiscountCodes.add(rs.getString(1));
         }

      }
      catch(SQLException e) {
         System.err.println(e.getMessage());
      }
      return allDiscountCodes;
   }
}


