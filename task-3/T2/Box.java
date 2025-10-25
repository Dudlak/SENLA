package T2;

import java.util.ArrayList;

public class Box {
    private final int capacity = 3;
    private float weight;
    private final ArrayList<Item> storage = new ArrayList<>();

    public int getCapacity() {
        return capacity;
    }

    public void add(Item el) {
        if (storage.size() < capacity) {
            storage.add(el);
        } else {
            System.out.println("Коробка переполненна.\nСожалеем, но " + el.getName() + " не поместился.\n");
        }
    }

    private void weightUpdate() {
        weight = 0.1f;
        for (Item el : storage) {
            weight += el.getWeight();
        }
    }

    public void delete(Item el) {
        if (storage.contains(el)) {
            storage.remove(el);
        } else {
            System.out.println("Объект не найден");
        }
    }

    public void display(int level) {
        String indent = "  ".repeat(level);
        for (Item el : storage) {
            System.out.println(indent + "- " + el.getName());
        }
    }

    public float getWeight() {
        weightUpdate();
        return weight;
    }
}
