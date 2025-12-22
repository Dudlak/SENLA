package task;

import task.Hotel.Hotel;
import task.Menu.*;
import task.Properties.Config;

import java.util.Random;

/*

*/

public class Main {
    public static Random random = new Random();
    public static Config config;
    public static Hotel hotel;

    public static void main(String[] args) {
        config = new Config();
        hotel = new Hotel();

        Builder builder = new Builder();
        Navigator navigator = new Navigator(null);
        MenuController controller = new MenuController(builder, navigator);

        controller.run();
    }
}
