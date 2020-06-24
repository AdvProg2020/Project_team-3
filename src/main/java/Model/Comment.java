package Model;

import java.util.ArrayList;

public class Comment {
    private String username;
    private String itemId;
    private String text;

    private enum Status {accepted, inProcess}

    private Boolean hasBought;
    private Status status;
    private String fatherCommentId;
    private String commentId;
    private ArrayList<Comment> allReplies=new ArrayList<>();

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

    public String getFatherCommentId() {
        return fatherCommentId;
    }

    public void setFatherCommentId(String fatherCommentId) {
        this.fatherCommentId = fatherCommentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public Boolean hasBought() {
        return hasBought;
    }

    public void addReply(Comment comment){
        allReplies.add(comment);
    }

    public String getCommentId(){return this.commentId;}

    public ArrayList<Comment> getAllReplies(){return allReplies;}

}
