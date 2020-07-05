module Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires javafx.media;
    opens Project to javafx.fxml;
    opens Project.Model.Users to com.google.gson;
    opens Project.Model to com.google.gson;
    opens Project.View.Menus.MenuController to javafx.fxml;
    opens Project.View.Menus.MenuController.AdminMenu to javafx.fxml;
    opens Project.View.Menus.MenuController.BuyerMenu  to javafx.fxml;
    opens Project.View.Menus.MenuController.LoginRegisterController to javafx.fxml;
    opens Project.View.Menus.MenuController.SellerMenuController to javafx.fxml;
    exports Project;
    exports Project.View.Menus.MenuController.AdminMenu;
    exports Project.View.Menus.MenuController.BuyerMenu;
    exports Project.View.Menus.MenuController.LoginRegisterController;
    exports Project.View.Menus.MenuController.SellerMenuController;
    exports Project.View.Menus.MenuController;
}