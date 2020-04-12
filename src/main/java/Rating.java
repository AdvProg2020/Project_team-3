public class Rating {
    private int score;
    private String username;
    private String itemId;


    //getters
    public int getScore() {
        return score;
    }

    public String getItemId() {
        return itemId;
    }

    public String getUsername() {
        return username;
    }
    //constructor
    public Rating(int score, String username, String itemId) {
        this.score = score;
        this.username = username;
        this.itemId = itemId;
    }


}
