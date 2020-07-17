package Project.Client.Model;

import java.util.ArrayList;

public class Comment {
   public String username;
   public String text;

   public enum Status {accepted, inProcess}

   public Boolean hasBought;
   public String fatherCommentId;
   public String commentId;
   public ArrayList<Comment> allReplies=new ArrayList<>();

   public Comment(String username,String text, Boolean hasBought,ArrayList<Comment> allReplies) {
      this.username = username;
      this.text = text;
      this.hasBought = hasBought;
      this.allReplies=allReplies;
   }



   public String getUsername() {
      return username;
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
