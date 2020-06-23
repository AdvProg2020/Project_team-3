package Model;

public class Comment {
    private String username;
    private String itemId;
    private String text;

    private enum Status {accepted, inProcess}

    private Boolean hasBought;
    private Status status;

    public Comment(String username, String itemId, String text, Boolean hasBought) {
        this.username = username;
        this.itemId = itemId;
        this.text = text;
        this.hasBought = hasBought;
    }

    public void accept() {
        status = Status.accepted;
    }

    public void inProcess() {
        status = Status.inProcess;
    }

    public String getUsername() {
        return username;
    }

    public String getItemId() {
        return itemId;
    }

    public String getText() {
       return text;
    }


    public Boolean hasBought() {
        return hasBought;
    }
}
