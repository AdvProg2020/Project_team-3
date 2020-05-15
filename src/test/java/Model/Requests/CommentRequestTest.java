package Model.Requests;

import Model.Comment;
import junit.framework.TestCase;

public class CommentRequestTest extends TestCase {

    public CommentRequest addRequest(){
        Comment comment=new Comment("alireza","sdf","sdfsfd",false);
        CommentRequest commentRequest=new CommentRequest("sdfsdf",comment);
        return commentRequest;
    }

    public void testGetNewComment() {
        System.out.println(addRequest().getNewComment().getText());
    }

    public void testTestToString() {
        System.out.println(addRequest().toString());
    }
}