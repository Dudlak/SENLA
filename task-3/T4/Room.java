package T4;

import java.util.Objects;

public class Room {
    private int cost, number;
    private String status; // "empty","repair","service","occupied"

    public Room(int number, int cost) {
        this.cost = cost;
        this.number = number;
        status = "empty";
    }

    public int getNumber() {
        return number;
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

    @Override
    public String toString() {
        return String.format("Комната №%-2d — Цена = $ %-3d | Status = %s", getNumber(), getCost(), getStatus());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Room) obj).getStatus().equals(this.status) &&
                ((Room) obj).getCost() == this.cost &&
                ((Room) obj).getNumber() == this.number;
    }

    public int hashCode() {
        return Objects.hash(number, cost, status);
    }
}
