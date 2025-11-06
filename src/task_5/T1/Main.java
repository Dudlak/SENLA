package task_5.T1;

import java.util.Random;

import task_5.T1.Menu.*;

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
