public class CategoryMenu {
    private static CategoryMenu categoryMenu;
    private CategoryMenu(){ }

    public static CategoryMenu getInstance(){
        if(categoryMenu==null)
            categoryMenu = new CategoryMenu();
        return categoryMenu;
    }
    public void show(){
        System.out.println("1-show items");
        System.out.println("2-add filter");
        System.out.println("3-remove filter");
        System.out.println("4-search");
    }

    public void execute(){

    }
}
