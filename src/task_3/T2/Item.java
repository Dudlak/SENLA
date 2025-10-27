package T2;

public class Item {
    private final String name;
    public float weight;

    public Item(String name, float weight){
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public float getWeight() {
        return weight;
    }
}
