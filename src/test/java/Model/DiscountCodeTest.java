package Model;

import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DiscountCodeTest extends TestCase {
    public DiscountCode addDiscountCode(){
        ArrayList<String> validUsers=new ArrayList<>();
        String startTime="2014-02-25 22:30";
        String endTime="2020-02-27 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime dateTime1 = LocalDateTime.parse(endTime, formatter);
        DiscountCode discountCode=new DiscountCode(50,dateTime
        ,dateTime1,validUsers,10,40);
        return discountCode;
    }


    public void testGetUsageCountInt() {
        System.out.println(addDiscountCode().getUsageCountInt());
    }

    public void testSetUsageCountInt() {
        addDiscountCode().setUsageCountInt(30);
    }

    public void testTestToString() {
        System.out.println(addDiscountCode());
    }

    public void testGetDiscountId() {
        System.out.println(addDiscountCode().getDiscountId());
    }

    public void testGetDiscountPercentage() {
        System.out.println(addDiscountCode().getDiscountPercentage());
    }

    public void testSetDiscountPercentage() {
        addDiscountCode().setUsageCountInt(34);
        System.out.println(addDiscountCode().getDiscountPercentage());
    }

    public void testSetEndTime() {
        String endTime="2020-02-27 22:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(endTime, formatter);
        addDiscountCode().setEndTime(dateTime);
    }

    public void testGetEndTime() {
        System.out.println(addDiscountCode().getEndTime());
    }

    public void testGetStartTime() {
        System.out.println(addDiscountCode().getStartTime());
    }

    public void testHasUser() {
        System.out.println(addDiscountCode().hasUser("sdf"));
    }

    public void testGetUsageCountForUser() {
        System.out.println(addDiscountCode().getUsageCountForUser("sdfsdf"));
    }

    public void testChangeUsageCount() {
        addDiscountCode().changeUsageCount(89);
        System.out.println(addDiscountCode().getUsageCountInt());
    }

    public void testGetMaxDiscount() {
        System.out.println(addDiscountCode().getMaxDiscount());
    }

    public void testUseDiscountCode() {
    }

    public void testUserCanUse() {
        System.out.println(addDiscountCode().userCanUse("alireza"));
    }
}