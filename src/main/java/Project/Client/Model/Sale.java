package Project.Client.Model;


import java.util.ArrayList;

public class Sale {

    private String startTime;
    private String endTime;
    private int offPercentage;



    public int getPercent() {
        return offPercentage;
    }

    public String getEnd() {
        return startTime;
    }

    public String getStart() {
        return endTime;
    }
}