package Controller;

import Model.Cart;
import Model.DiscountCode;
import Model.Item;
import Model.Users.Buyer;
import View.Menus.View;

import java.io.IOException;
import java.util.ArrayList;

public class CartController {
    Controller controller = Controller.getInstance();
    private static CartController cartController;
    private CartController() {
    }

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
            return View.ANSI_RED+"Error: invalid id"+View.ANSI_RESET;
        }
        return getCurrentShoppingCart().add(itemId);
    }

    public String cartIncreaseDecrease(String itemid, int count) { //for decrease count needs to be negative
        if (!ItemAndCategoryController.getInstance().isThereItemWithId(itemid)) {
            return View.ANSI_RED+"Error: invalid id"+View.ANSI_RESET;
        }
        if(!getCurrentShoppingCart().includesItem(itemid)){
            return View.ANSI_RED+"Error: you must first add this item to your cart"+View.ANSI_RESET;
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
            return View.ANSI_RED+"Error: must be a buyer to buy items"+View.ANSI_RESET;
        }
        Buyer buyer = (Buyer) UserController.getInstance().getCurrentOnlineUser();
        Cart cart = Controller.getInstance().getCurrentShoppingCart();
        double price = cart.getCartPriceWithoutDiscountCode();
        if (price > buyer.getMoney()) {
            return View.ANSI_RED+ "Error: insufficient money."+View.ANSI_RESET;
        }
        if(price > 1000000){
            SaleAndDiscountCodeController.getInstance().giveGiftDiscountCode(buyer.getUsername());
        }
        buyer.setMoney(buyer.getMoney() - cart.getCartPriceWithoutDiscountCode());
            Database.getInstance().saveUser(buyer);
        cart.buy(buyer.getUsername(), address);
        return View.ANSI_GREEN+"Successful: Shopping complete."+View.ANSI_RESET;
    }

    public String buy(String address,String discountID) {
        if (!(UserController.getInstance().getCurrentOnlineUser() instanceof Buyer)) {
            return View.ANSI_RED+"Error: must be a buyer to buy items"+View.ANSI_RESET;
        }
        Buyer buyer = (Buyer) UserController.getInstance().getCurrentOnlineUser();
        Cart cart = Controller.getInstance().getCurrentShoppingCart();
        DiscountCode discountCode = SaleAndDiscountCodeController.getInstance().getDiscountCodeById(discountID);
        cart.setDiscountCode(discountCode);
        double price = cart.getCartPriceWithDiscountCode();
        cart.setDiscountCode(discountCode);
        if (price > buyer.getMoney()) {
            return View.ANSI_RED+ "Error: insufficient money."+View.ANSI_RESET;
        }
        if(price > 1000000){
            SaleAndDiscountCodeController.getInstance().giveGiftDiscountCode(buyer.getUsername());
        }
        buyer.setMoney(buyer.getMoney() - cart.getCartPriceWithDiscountCode());
        discountCode.useDiscountCode(buyer.getUsername());
            Database.getInstance().saveUser(buyer);
            Database.getInstance().saveDiscountCode(discountCode);
        cart.buy(buyer.getUsername(), address);
        return View.ANSI_GREEN+"Successful: Shopping complete."+View.ANSI_RESET;
    }


}
