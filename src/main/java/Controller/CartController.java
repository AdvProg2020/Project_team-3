package Controller;

import Model.Cart;
import Model.DiscountCode;
import Model.Item;
import Model.Users.Buyer;

import java.io.IOException;
import java.util.ArrayList;

public class CartController {
    Controller controller = Controller.getInstance();
    private static CartController cartController;

    private CartController() {
    }

    ArrayList<Item> currentViewableItems = new ArrayList<>();

    public static CartController getInstance() {
        if (cartController == null)
            cartController = new CartController();
        return cartController;
    }

    public Cart getCurrentShoppingCart() {
        return controller.ShoppingCart;
    }

    public String showCart() {
        String string = getCurrentShoppingCart().toString();
        if (string.isEmpty()) {
            return "Cart is empty";
        } else {
            return string;
        }
    }

    public String addItemToCart(String itemId) {
        if (!ItemAndCategoryController.getInstance().isThereItemWithId(itemId)) {
            return "Error: invalid id";
        }
        if(getCurrentShoppingCart().includesItem(itemId)==false){
            return "Error: you must first add this item to your cart";
        }
        return getCurrentShoppingCart().add(itemId);
    }

    public String cartIncreaseDecrease(String itemid, int count) { //for decrease count needs to be negative
        if (!ItemAndCategoryController.getInstance().isThereItemWithId(itemid)) {
            return "Error: invalid id";
        }
        count += getCurrentShoppingCart().getItemCount(itemid);
        return getCurrentShoppingCart().changeCountBy(itemid, count);
    }

    public double getCartPriceWithoutDiscountCode() {
        return getCurrentShoppingCart().getCartPriceWithoutDiscountCode();
    }

    public double getCartPriceWithDiscountCode(String discountCodeID) {
        Cart cart = getCurrentShoppingCart();
        DiscountCode discountCode = SaleAndDiscountCodeController.getInstance().getDiscountCodeById(discountCodeID);
        cart.setDiscountCode(discountCode);
        double ans = getCurrentShoppingCart().getCartPriceWithDiscountCode();
        cart.setDiscountCode(null);
        return ans;
    }

    public String buy(String address) {
        if (!(UserController.getInstance().getCurrentOnlineUser() instanceof Buyer)) {
            return "Error: must be a buyer to buy items";
        }
        Buyer buyer = (Buyer) UserController.getInstance().getCurrentOnlineUser();
        Cart cart = Controller.getInstance().getCurrentShoppingCart();
        double price = cart.getCartPriceWithoutDiscountCode();
        if (price > buyer.getMoney()) {
            return "Error: not enough money";
        }
        buyer.setMoney(buyer.getMoney() - cart.getCartPriceWithoutDiscountCode());
            Database.getInstance().saveUser(buyer);
        cart.buy(buyer.getUsername(), address);
        return "Successful:";
    }

    public String buy(String address,String discountID) {
        if (!(UserController.getInstance().getCurrentOnlineUser() instanceof Buyer)) {
            return "Error: must be a buyer to buy items";
        }
        Buyer buyer = (Buyer) UserController.getInstance().getCurrentOnlineUser();
        Cart cart = Controller.getInstance().getCurrentShoppingCart();
        DiscountCode discountCode = SaleAndDiscountCodeController.getInstance().getDiscountCodeById(discountID);
        cart.setDiscountCode(discountCode);
        double price = cart.getCartPriceWithDiscountCode();
        cart.setDiscountCode(discountCode);
        if (price > buyer.getMoney()) {
            return "Error: not enough money";
        }
        if(price > 1000000){
            SaleAndDiscountCodeController.getInstance().giveGiftDiscountCode(buyer.getUsername());
        }
        buyer.setMoney(buyer.getMoney() - cart.getCartPriceWithDiscountCode());
        discountCode.useDiscountCode(buyer.getUsername());
            Database.getInstance().saveUser(buyer);
            Database.getInstance().saveDiscountCode(discountCode);
        cart.buy(buyer.getUsername(), address);
        return "Successful:";
    }
}
