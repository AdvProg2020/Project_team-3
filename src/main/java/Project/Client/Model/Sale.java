package Project.Client.Model;


public class Sale {
    int percent;
    String end;
    String start;

    public Sale(int percent,String end,String start){
       this.start=start;
       this.end=end;
       this.percent=percent;
    }

    public int getPercent() {
        return percent;
    }

    public String getEnd() {
        return end;
    }

    public String getStart() {
        return start;
    }
}