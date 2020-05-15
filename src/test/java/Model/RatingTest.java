package Model;

import junit.framework.TestCase;

public class RatingTest extends TestCase {

    public Rating addRate(){
        Rating rating=new Rating(4,"sdfgh","sdfghy");
        return  rating;
    }

    public void testGetScore() {
        System.out.println(addRate().getScore());
    }

    public void testGetItemId() {
        System.out.println(addRate().getItemId());
    }

    public void testGetUsername() {
        System.out.println(addRate().getUsername());
    }
}