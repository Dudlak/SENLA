package task_6_1;

import task_6_1.Menu.*;

import java.util.Random;

/*

*/

public class Main {
    public static Random random = new Random();

    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        Builder builder = new Builder(hotel);
        Navigator navigator = new Navigator(null);
        MenuController controller = new MenuController(builder, navigator);

        controller.run();
    }
}
