import java.util.ArrayList;

public class Seller extends User {

    private ArrayList<SaleLog> sellLogs;
    private ArrayList<String> soldItemsId;
    private ArrayList<String> allItemsId;
    private ArrayList<String> allSaleId;

    public Seller(String username, String password, String name, String lastName, String email, String number) {
        super(username,password,name,lastName,email,"Seller",number);
        sellLogs=new ArrayList<>();
        soldItemsId=new ArrayList<>();
        allItemsId=new ArrayList<>();
        allSaleId=new ArrayList<>();
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

    //addItem(contrusctor item iji benevise){
    //}

    public void editSale(Sale sale,Sale updatedSale){

      }

     public void removeItem(String itemId){

      }

}