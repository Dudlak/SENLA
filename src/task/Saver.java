package task;

import task.Hotel.Hotel;

import java.io.*;


public class Saver {
    /*

    */

    public static void exportState(Object obj) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("saves/state.dat"))) {
            oos.writeObject(obj);
            System.out.println("Объект сериализован: state.dat");
        } catch (IOException e) {
            System.out.println("Ошибка сериализации: " + e.getMessage());
        }
    }

    public static Object importState() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("saves/state.dat"))) {
            Object obj = ois.readObject();
            System.out.println("Объект десериализован: state.dat");
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка десериализации: " + e.getMessage());
            return new Hotel();
        }
    }

    public static void clear() {
        File savesDir = new File("saves");

        if (!savesDir.exists() || !savesDir.isDirectory()) {
            System.out.println("Директория 'saves' не существует");
            return;
        }

        File[] files = savesDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.delete()) {
                System.out.println("Файл " + file.getName() + " удалён.");
            }
        }
    }

}
