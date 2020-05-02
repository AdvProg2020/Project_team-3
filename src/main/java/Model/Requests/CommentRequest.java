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

    @Override
    public String toString(){
        return "id: "+getRequestId()+"\n"+"type: "+getType()+"\n"+"Comment:"+newComment.getText()+"\n"+
                "is Accepted"+(isIsAccepted()? "accepted" : "not accepted!");
    }


}
