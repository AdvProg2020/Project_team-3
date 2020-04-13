public class CategoryMenu extends Menu{
    private static CategoryMenu categoryMenu;
    private int optionCount = 4;
    private CategoryMenu(){ }

    public static CategoryMenu getInstance(){
        if(categoryMenu==null)
            categoryMenu = new CategoryMenu();
        return categoryMenu;
    }
    @Override
    public void show(){
        System.out.println("1-show items");
        System.out.println("2-add filter");
        System.out.println("3-remove filter");
        System.out.println("4-search");
    }

    @Override
    public String toString(){
        return "1-show items\n2-add filter\n3-remove filter\n4-search";
    }
    @Override
    public void execute(String command){

    }
}
