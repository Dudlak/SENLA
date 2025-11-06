package task_6;

public class Service {
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
