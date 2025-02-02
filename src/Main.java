

public class Main {
    public static void main(String[] args) {
        DatabaseHelper dbHelper = new DatabaseHelper();
        MainFrame view = new MainFrame();

        MainController controller = new MainController(dbHelper, view);


        view.setController(controller);

        view.setVisible(true);
    }
}