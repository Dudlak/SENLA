package task;

import task.Hotel.Hotel;
import task.Menu.*;
import task.Properties.Config;

import java.util.Random;

/*

*/

public class Main {
    public static Random random = new Random();
    public static Config config = new Config();
    public static Hotel hotel = new Hotel();

    public static void main(String[] args) throws Exception {

        MenuController controller = new MenuController();
        DI.injectDependencies(controller);

        controller.run();
    }
}
