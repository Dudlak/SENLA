package task_7;

import task_7.Menu.*;
import task_7.Properties.Config;

import java.util.Random;

/*

*/

public class Main {
    public static Random random = new Random();
    public static Config config;

    public static void main(String[] args) {
        config = new Config();
        Hotel hotel = new Hotel();

        Builder builder = new Builder(hotel);
        Navigator navigator = new Navigator(null);
        MenuController controller = new MenuController(builder, navigator);

        controller.run();
    }
}
