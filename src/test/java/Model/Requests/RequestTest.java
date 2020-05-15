package Model.Requests;

import Model.Comment;
import junit.framework.TestCase;

public class RequestTest extends TestCase {

    public CommentRequest addRequest(){
        Comment comment=new Comment("sdf","sdf","asdfasdf",false);
        CommentRequest commentRequest=new CommentRequest("sdf",comment);
        return  commentRequest;
    }

    public void testGetMessage() {
        System.out.println(addRequest().getMessage());
    }

}