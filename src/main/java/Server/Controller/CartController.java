package Server.Controller;

import Server.Model.Cart;
import Server.Model.DiscountCode;
import Server.Model.Users.Buyer;

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
            return "Error: invalid id";
        }
        if(ItemAndCategoryController.getInstance().getItemById(itemId).getInStock()==0){
            return "Error: item is sold out";
        }
        return getCurrentShoppingCart().add(itemId);
    }

    public String cartIncreaseDecrease(String itemid, int count) { //for decrease count needs to be negative
        if (!ItemAndCategoryController.getInstance().isThereItemWithId(itemid)) {
            return "Error: invalid id";
        }
        if(!getCurrentShoppingCart().includesItem(itemid)){
            return "Error: you must first add this item to your cart";
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

    public String buy(String address, String bankAccountId,String bankToken) {
        if(showCart().equals("Cart is empty")) return "Error: cart is empty";
        if (!(UserController.getInstance().getCurrentOnlineUser() instanceof Buyer)) {
            return "Error: must be a buyer to buy items";
        }
        Buyer buyer = (Buyer) UserController.getInstance().getCurrentOnlineUser();
        Cart cart = Controller.getInstance().getCurrentShoppingCart();
        double price = cart.getCartPriceWithoutDiscountCode();
        if (price > buyer.getMoney()) {
            return  "Error: insufficient money.";
        }
        if(price > 1000000){
            SaleAndDiscountCodeController.getInstance().giveGiftDiscountCode(buyer.getUsername());
        }
        if(!bankAccountId.equals("")){
            int money= (int) price;
            String receipt=TransactionController.getInstance().getReceiptID(bankToken,
                    "move",String.valueOf(money),bankAccountId,"10001","");
            System.out.println(receipt);
            String transactionResult=TransactionController.getInstance().payReceipt(receipt);
            if(!transactionResult.equals("done successfully")){
                return transactionResult;
            }
        }
        if(bankAccountId.equals("") && buyer.getMoney() - cart.getCartPriceWithoutDiscountCode()< TransactionController.getInstance().getMinimumMoney()){
            return "your wallet can not contains less than +"+TransactionController.getInstance().getMinimumMoney();
        }
        if(bankAccountId.equals("")) {
            buyer.setMoney(buyer.getMoney() - cart.getCartPriceWithoutDiscountCode());
            int deposit = (int) price;
            String depositReceipt = TransactionController.getInstance().getReceiptID(bankToken, "deposit", String.valueOf(deposit), "-1", "10001", "");
            TransactionController.getInstance().payReceipt(depositReceipt);
        }
            Database.getInstance().saveUser(buyer);
        cart.buy(buyer.getUsername(), address);
        return "Successful: Shopping complete.";
    }

    public String buy(String address,String discountID,String bankAccountId,String bankToken) {
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
            return  "Error: insufficient money.";
        }
        if(price > 1000000){
            SaleAndDiscountCodeController.getInstance().giveGiftDiscountCode(buyer.getUsername());
        }
        if(!bankAccountId.equals("")){
            int money= (int) price;
            String receipt=TransactionController.getInstance().getReceiptID(bankToken,
                    "move",String.valueOf(money),bankAccountId,"10001","");
            String transactionResult=TransactionController.getInstance().payReceipt(receipt);
            if(!transactionResult.equals("done successfully")){
                return transactionResult;
            }
        }
        if(bankAccountId.equals("") && buyer.getMoney() - cart.getCartPriceWithDiscountCode()< TransactionController.getInstance().getMinimumMoney()){
            return "your wallet can not contains less than +"+TransactionController.getInstance().getMinimumMoney();
        }
        if(bankAccountId.equals("")) {
        buyer.setMoney(buyer.getMoney() - cart.getCartPriceWithDiscountCode());
        int deposit = (int) cart.getCartPriceWithDiscountCode();
        String depositReceipt = TransactionController.getInstance().getReceiptID(bankToken, "deposit", String.valueOf(deposit), "-1", "10001", "");
        TransactionController.getInstance().payReceipt(depositReceipt);
        }
        discountCode.useDiscountCode(buyer.getUsername());
            Database.getInstance().saveUser(buyer);
            Database.getInstance().saveDiscountCode(discountCode);
        cart.buy(buyer.getUsername(), address);
        return "Successful: Shopping complete.";
    }


}
