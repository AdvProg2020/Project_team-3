import java.util.ArrayList;

public class Seller extends User {
    private String companyName;
    private ArrayList<SaleLog> sellLogs;
    private ArrayList<String> soldItemsId;
    private ArrayList<String> allItemsId;
    private ArrayList<String> allSaleId;
    private double money;
    public Seller(String username,String password, String name, String lastName, String email, String number,String companyName) {
        super(username,password,name,lastName,email,"Seller",number);
        this.companyName=companyName;
        sellLogs=new ArrayList<>();
        soldItemsId=new ArrayList<>();
        allItemsId=new ArrayList<>();
        allSaleId=new ArrayList<>();

    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ArrayList<SaleLog> getSellLogs(){
        return sellLogs;
    }
    
    public void addSale(double amount) {

    }

    public void addItemToSale(String itemid){

    }


    public String showAllSale() {
       return "hello";
    }

    public String printItems(){
       return "hello";
    }


    public void editSale(Sale sale,Sale updatedSale){

      }

     public void removeItem(String itemId){

      }

      public boolean isSaleInPast(){
        return false;
      }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney(){
        return this.money;
    }
}