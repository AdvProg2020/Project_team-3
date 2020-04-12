public class Comment {
    String username;
    String itemId;
    String text;
    Boolean accepted;
    Boolean hasBought;

    public Comment(String username, String itemId, String text, Boolean hasBought) {
        this.username = username;
        this.itemId = itemId;
        this.text = text;
        this.hasBought = hasBought;
    }

    public void accept(){

    }

    public void decline(){

    }

}
