package task.Hotel;

import java.io.Serializable;

public class Service implements Serializable {
    private int cost;
    private final String name;

    public Service(String name, int cost){
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String toString() {
        return name;
    }
}
