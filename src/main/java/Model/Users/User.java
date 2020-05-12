package Model.Users;

import ControllerTest.Database;

import java.io.IOException;

public abstract class User {

    private String username;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private String number;
    private String type;

    public boolean doesPasswordMatch(String password) {
        return this.password.equals(password);
    }

    public User(String username, String password, String name, String lastName, String email, String number, String type) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNumber() {
        return number;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public abstract String getPersonalInfo();

    public String changeTypeTo(String type) {
        if (type.equalsIgnoreCase(getType())) {
            return "Error: this account is already an admin";
        }
        if (type.equalsIgnoreCase("Buyer")) {
            double money = 0;
            if (this instanceof Seller) {
                Seller seller = (Seller) this;
                money = seller.getMoney();
            }
            Buyer buyer = new Buyer(0, getUsername(), getPassword(), getName(), getLastName(), getEmail(), getNumber());
            Database.getInstance().deleteUser(this);
            try {
                Database.getInstance().saveUser(buyer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "User " + getUsername() + " type changed from " + getType() + " to " + type;
        }
        if (type.equalsIgnoreCase("Seller")) {
            double money = 0;
            if (this instanceof Buyer) {
                Buyer buyer = (Buyer) this;
                money = buyer.getMoney();
            }
            Seller seller = new Seller(0, getUsername(), getPassword(), getName(), getLastName(), getEmail(), getNumber(), null);
            Database.getInstance().deleteUser(this);
            try {
                Database.getInstance().saveUser(seller);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "User " + getUsername() + " type changed from " + getType() + " to " + type;
        }
        if (type.equalsIgnoreCase("Admin")) {
            Admin admin = new Admin(getUsername(), getPassword(), getName(), getLastName(), getEmail(), getNumber());
            Database.getInstance().deleteUser(this);
            try {
                Database.getInstance().saveUser(admin);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "User " + getUsername() + " type changed from " + getType() + " to " + type;
        }
        return "Error: invalid type";
    }
}