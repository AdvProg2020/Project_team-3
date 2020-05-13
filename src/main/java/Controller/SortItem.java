package Controller;

import Model.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortItem {
    private static SortItem sortItem;
    private String currentSort=new String();


    private SortItem(){}

    public static SortItem getInstance(){
        if(sortItem==null)
            sortItem=new SortItem();
        return sortItem;
    }

    public ArrayList<Item> sortBy(String sortBy){
        if(SearchAndFilter.getInstance().getCurrentViewableItems().isEmpty()) return null;
        ArrayList<Item> newViewable=new ArrayList<>();
            for(Item item:SearchAndFilter.getInstance().getCurrentViewableItems()) {
                newViewable.add(item);
            }
        if(sortBy.equals("View Count")) {
            currentSort="ViewCount";
            Collections.sort(newViewable, new ViewCountComparator());
        }else if(sortBy.equals("Grade")){
            currentSort="Grade";
            Collections.sort(newViewable , new GradeComparator());
        }else if(sortBy.equals("Price")){
            currentSort="Price";
            Collections.sort(newViewable , new PriceComparator());
        }else if(sortBy.equals("InStock")){
            currentSort="InStock";
            Collections.sort(newViewable ,new InStockComparator());
        }else if(sortBy.equals("Alphabetic")){
            currentSort="Alphabetic";
            Collections.sort(newViewable , new AlphabeticComparator());
        }
        return  newViewable;
    }

    public ArrayList<Item> disabeSort(){return SearchAndFilter.getInstance().getCurrentViewableItems();}

    public String getCurrentSort(){return  currentSort;}

    public String availableSorts(){
        return "you can sort items by Grade,Price,InStock,Alphabetic!";
    }

}

class ViewCountComparator implements Comparator<Item>{
    @Override
    public int compare(Item item1 , Item item2){
        int viewCountDiffrence=item1.getViewCount()-item2.getViewCount();
        return viewCountDiffrence;
    }
}

class GradeComparator implements  Comparator<Item>{
    @Override
    public int compare(Item item1 , Item item2){
        double gradeDiffrence=item1.getRating()-item2.getRating();
        if(gradeDiffrence>0) return 1;
        else if(gradeDiffrence<0) return -1;
        return 0;
    }
}

class PriceComparator implements  Comparator<Item>{
    @Override
    public int compare(Item item1 , Item item2){
        double priceDiffrence=item1.getPrice()-item2.getPrice();
        if(priceDiffrence>0) return 1;
        else if(priceDiffrence<0) return -1;
        else return 0;
    }
}

class InStockComparator implements Comparator<Item>{
    @Override
    public int compare(Item item1 , Item item2){
        double inStockDiffrence=item1.getInStock()-item2.getInStock();
        if(inStockDiffrence>0) return 1;
        else if(inStockDiffrence<0) return -1;
        else return 0;
    }
}

class AlphabeticComparator implements Comparator<Item>{
    @Override
    public int compare(Item item1 , Item item2){
        int literalDiffrence=item1.getName().compareTo(item2.getName());
        return literalDiffrence;
    }
}




