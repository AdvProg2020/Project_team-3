package Model;

import junit.framework.Assert;
import junit.framework.TestCase;

public class RatingTest extends TestCase {

    public void testGetScore() {
        Rating rating=new Rating(5,"Alireza","56et");
        Assert.assertEquals(rating.getScore(),5);
    }

    public void testGetItemId() {
        Rating rating=new Rating(5,"Alireza","56et");
        Assert.assertEquals(rating.getItemId(),"56et");
    }

    public void testGetUsername() {
        Rating rating=new Rating(5,"Alireza","56et");
        Assert.assertEquals(rating.getUsername(),"Alireza");
    }
}