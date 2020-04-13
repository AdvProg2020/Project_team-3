public abstract class Menu {
    public abstract void show();
    public abstract void execute(String command);
    public int optionCount;
    public int getOptionCount() {
        return optionCount;
    }
}
