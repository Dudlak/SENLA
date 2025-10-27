package T2;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private int capacity = 3;
    private final List<Box> storage = new ArrayList<>();
    private float storageWeight;

    public Storage() {
    }

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void add(Box el) {
        if (storage.size() < capacity) {
            storage.add(el);
        } else {
            System.out.println("Хранилище переполненно");
        }
    }



    public void delete(Box el) {
        if (storage.contains(el)) {
            storage.remove(el);
        } else {
            System.out.println("Объект не найден");
        }
    }

    private void weightUpdate() {
        storageWeight = 0;
        for (Box el : storage) {
            storageWeight += el.getWeight();
        }
    }

    public void display() {
        System.out.println("Хранилище:");
        int level = 0;
        String indent = "  ".repeat(level);

        for (Box el : storage) {
            System.out.printf("%s+ Коробка %d:\n", indent, storage.indexOf(el) + 1);
            el.display(level + 1);
        }
    }

    public float getWeight() {
        weightUpdate();
        return storageWeight;
    }
}
