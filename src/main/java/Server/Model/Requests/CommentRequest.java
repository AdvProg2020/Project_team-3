package Server.Model.Requests;

import Server.Controller.UserController;
import Server.Model.Comment;

public class CommentRequest extends Request {
    Comment newComment;

    public CommentRequest(String requestId, Comment newComment) {
        super(requestId);
        this.newComment = newComment;
        this.setMessage("User "+newComment.getUsername()+" wants to write \""+newComment.getText() +"\" on "+ newComment.getItemId());
        setType("CommentRequest");
        UserController.getInstance().getUserByUsername(newComment.getUsername()).addRequest(getRequestId(),getPendingMessage());
    }

    public Comment getNewComment() {
        return newComment;
    }

    @Override
    public String toString() {
        return "id: " + getRequestId() + "   " + "type: " + getType();
    }


    @Override
    public String getAcceptedMessage() {
        return "id: "+getRequestId()+" state:accepted "+" info:your comment has been accepted by the admin";
    }

    @Override
    public String getDeclineMessage() {
        return "id: "+getRequestId()+" state:declined "+" info:your comment has been deleted by the admin";
    }

    @Override
    public void accept() {
        UserController.getInstance().getUserByUsername(newComment.getUsername()).addRequest(getRequestId(),getAcceptedMessage());
    }

    @Override
    public void decline() {
        UserController.getInstance().getUserByUsername(newComment.getUsername()).addRequest(getRequestId(),getDeclineMessage());
    }
}
