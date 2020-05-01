package Model.Requests;

import Model.Comment;

public class CommentRequest extends Request {
    Comment newComment;
    public CommentRequest(String requestId, Comment newComment) {
        super(requestId);
        this.newComment = newComment;
        setType("CommentRequest");
    }

    public Comment getNewComment(){
        return newComment;
    }

}
