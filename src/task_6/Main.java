package task_6;

import task_6.Menu.Builder;
import task_6.Menu.MenuController;
import task_6.Menu.Navigator;

import java.util.Random;

/*
    переписать с использованием Menu
*/

public class Main {
    public static Random random = new Random();
    public static int DAY = 0;

    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        Builder builder = new Builder(hotel);
        Navigator navigator = new Navigator(null);
        MenuController controller = new MenuController(builder, navigator);

        controller.run();
    }
}
