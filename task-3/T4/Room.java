package T4;

public class Room {
    private int cost;
    private String status; // "empty","repair","service","occupied"

    public Room(int cost) {
        this.cost = cost;
        status = "empty";
    }

    public void setStatusEmpty(){
        status = "empty";
    }

    public void setStatusRepair(){
        status = "repair";
    }

    public void setStatusService(){
        status = "service";
    }

    public void setStatusOccupied() {
        this.status = "occupied";
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }
}
