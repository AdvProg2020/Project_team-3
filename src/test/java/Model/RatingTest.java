package Model;

import Model.Requests.Request;
import org.junit.Test;

import static org.junit.Assert.*;

public class RatingTest {

    public Rating createRating(){
        return new Rating(4,"testRating","TTTTT");
    }
    @Test
    public void getScore() {
    assertEquals(createRating().getScore(),4);
    }

    @Test
    public void getItemId() {
     assertEquals(createRating().getItemId(),"TTTTT");
    }

    @Test
    public void getUsername() {
     assertEquals(createRating().getUsername(),"testRating");
    }
}