module Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires javafx.media;
    requires kotlin.stdlib;
    requires javax.servlet;
    opens Project to javafx.fxml;
    opens Server.Model.Users to com.google.gson;
    opens Server.Model.Logs to com.google.gson;
    opens Server.Model to com.google.gson;
    opens Server.Model.Requests to com.google.gson;
    opens Project.Client.Menus.MenuController to javafx.fxml;
    opens Project.Client.Menus.MenuController.AdminMenu to javafx.fxml;
    opens Project.Client.Menus.MenuController.BuyerMenu  to javafx.fxml;
    opens Project.Client.Menus.MenuController.LoginRegisterController to javafx.fxml;
    opens Project.Client.Menus.MenuController.SellerMenuController to javafx.fxml;
    exports Project;
    exports Project.Client.Menus.MenuController.AdminMenu;
    exports Project.Client.Menus.MenuController.BuyerMenu;
    exports Project.Client.Menus.MenuController.LoginRegisterController;
    exports Project.Client.Menus.MenuController.SellerMenuController;
    exports Project.Client.Menus.MenuController;
}