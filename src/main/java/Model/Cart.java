package Model;

import Controller.ItemAndCategoryController;
import Controller.UserController;
import Model.Logs.BuyLog;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
    HashMap<String, Integer> allItemCount;
    ArrayList<String> allItemId;
    DiscountCode discountCode;

    public Cart() {
        allItemCount = new HashMap<>();
        allItemId = new ArrayList<>();
    }

    public void setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public String add(String itemId) {
        if (0 == ItemAndCategoryController.getInstance().getItemById(itemId).getInStock()) {
            return "Error: there isn't enough in stock";
        }
        if (allItemCount.get(itemId) == null) {
            allItemCount.put(itemId, 1);
            allItemId.add(itemId);
            return "Successful";
        } else {
            return "Error: item is already in the cart";
        }
    }

    public void remove(String itemName) {
        allItemCount.remove(itemName);
        allItemId.remove(itemName);
    }

    public boolean isEmpty() {
        if (allItemId.size() == 0) return true;
        return false;
    }

    public String changeCountBy(String itemId, int count) {
        if (count > ItemAndCategoryController.getInstance().getItemById(itemId).getInStock()) {
            return "Error: there isn't enough in stock";
        }
        allItemCount.replace(itemId, count);
        if (count <= 0) {
            allItemCount.remove(itemId);
            allItemId.remove(itemId);
        }
        return "Successful";
    }


    public boolean includesItem(String itemId) {
        if (allItemCount.get(itemId) == null) {
            return false;
        }
        return true;
    }

    public int getItemCount(String itemID) {
        if (allItemCount.containsKey(itemID)) {
            return allItemCount.get(itemID);
        }
        return 0;
    }

    public double getCartPriceWithoutDiscountCode() {
        double price = 0;
        for (String id : allItemId) {
            price += ItemAndCategoryController.getInstance().getItemById(id).getPrice() * allItemCount.get(id);
        }
        return price;
    }

    public double getCartPriceWithDiscountCode() {
        int code = 100;
        if (discountCode != null) {
            code = discountCode.getDiscountPercentage();
        }
        double totalDiscount = getCartPriceWithoutDiscountCode() - getCartPriceWithoutDiscountCode() * code / 100;
        if(totalDiscount > discountCode.getMaxDiscount()){
            return getCartPriceWithoutDiscountCode() - discountCode.getMaxDiscount();
        }
        return getCartPriceWithoutDiscountCode() * code / 100;
    }

    @Override
    public String toString() {
        String cart = "";
        int count = 0;
        for (String id : allItemId) {
            count = allItemCount.get(id);
            cart += ("item name:" + ItemAndCategoryController.getInstance().getItemById(id).getName() + " item id:" + id + " count:" + count + "\n");
        }
        return cart;
    }

    public void buy(String buyerName, String address) {
        BuyLog buyLog = new BuyLog(buyerName, address,getCartPriceWithoutDiscountCode()-getCartPriceWithDiscountCode());
        int count = 0;
        for (String itemID : allItemId) {
            double price = ItemAndCategoryController.getInstance().getItemById(itemID).getPrice();
            String sellerName = ItemAndCategoryController.getInstance().getItemById(itemID).getSellerName();
            buyLog.addItem(price, allItemCount.get(itemID), itemID, sellerName);
            ItemAndCategoryController.getInstance().getItemById(itemID).addTimesBoughtBy(allItemCount.get(itemID));
        }
        empty();
        UserController.getInstance().assignBuyLog(buyerName, buyLog);
    }

    private void empty() {
        allItemId.clear();
        allItemCount.clear();
        discountCode = null;
    }
}
