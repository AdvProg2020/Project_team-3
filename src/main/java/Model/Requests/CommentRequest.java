package Model.Requests;

import Model.Comment;

public class CommentRequest extends Request {
    Comment newComment;

    public CommentRequest(String requestId, Comment newComment) {
        super(requestId);
        this.newComment = newComment;
        this.setMessage("User "+newComment.getUsername()+" wants to write \""+newComment.getText() +"\" on "+ newComment.getItemId());
        setType("CommentRequest");
    }

    public Comment getNewComment() {
        return newComment;
    }

    @Override
    public String toString() {
        return "id: " + getRequestId() + "\n" + "type: " + getType() + "\n" + "Comment:" + newComment.getText() + "\n";
    }


}
