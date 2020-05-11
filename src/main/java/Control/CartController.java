package Control;

import Model.Cart;
import Model.Item;

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
        return getCurrentShoppingCart().add(itemId);
    }

    public String cartIncreaseDecrease(String itemid, int count) { //for decrease count needs to be negative
        if (!ItemAndCategoryController.getInstance().isThereItemWithId(itemid)) {
            return "Error: invalid id";
        }
        if (count < 0) {
            count = getCurrentShoppingCart().getItemCount(itemid) + count;
        } else {
            count += getCurrentShoppingCart().getItemCount(itemid);
        }
        return getCurrentShoppingCart().changeCountBy(itemid, count);
    }

    public double getCartPriceWithoutDiscountCode() {
        return getCurrentShoppingCart().getCartPriceWithoutDiscountCode();
    }

    public double getCartPriceWithDiscountCode() {
        return getCurrentShoppingCart().getCartPriceWithDiscountCode();
    }

}
