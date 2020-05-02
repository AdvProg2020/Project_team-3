package Control;

import Model.Cart;
import Model.Category;
import Model.Users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private static Controller controller;
    User currentOnlineUser;
    Category mainCategory;
    Category currentCategory;
    Cart currentShoppingCart;

    private Controller() {
    }

    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller();
        return controller;
    }

    public Cart getCurrentShoppingCart() {
        return currentShoppingCart;
    }

    public boolean isAValidCommand(String command) {
        if (command.length() > 3) return false;
        int commandNumber;
        try {
            commandNumber = Integer.parseInt(command);
        } catch (Exception e) {
            return false;
        }
        return commandNumber >= 0;
    }

    public String addId(String id) {
        int index = id.length() - 1;
        while (true) {
            char value = id.charAt(index);
            if (id.charAt(index) != '9') {
                value++;
                id = id.substring(0, index) + value + id.substring(index, id.length() - 1);
                return id;
            } else {
                id = id.substring(0, index) + '0' + id.substring(index, id.length() - 1);
                index--;
            }
        }
    }

    public static Matcher getMatcher(String string,String regex){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(string);
    }

    public User getCurrentOnlineUser() {
        return currentOnlineUser;
    }

    public void setCurrentOnlineUser(User currentOnlineUser) {
        this.currentOnlineUser = currentOnlineUser;
    }

    public void loadMainCategory(){
        if(mainCategory==null){
            String path="Resource"+ File.separator+"Main Category";
            String name="Main Category"+".json";
            File file=new File(path+File.separator+name);
            Gson gson=new GsonBuilder().setPrettyPrinting().create();
            try {
                String content=new String(Files.readAllBytes(file.toPath()));
                mainCategory=gson.fromJson(content , Category.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else return;
    }


}