package task_6;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static task_4.T1.Main.random;

public class Room {

    private int cost, number, capacity, stars;
    private String status; // "empty","repair","service","occupied"
    private int updateDay = 0;
    private Guest guest;

    private final List<Guest> guestsHistory = new ArrayList<>(){
        @Override
        public boolean add(Guest guest) {
            super.add(guest);
            if (super.size() > 3) {
                super.removeFirst();
            }
            return true;
        }
    };


    public Room(int number, int cost) {
        this.cost = cost;
        this.number = number;
        status = "empty";
        capacity = random.nextInt(1,4);
        stars = random.nextInt(2,5);
    }

    public int getNumber() {
        return number;
    }

    public void setStatusEmpty(){
        status = "empty";
        updateDay = 0;
    }

    public void setStatusRepair(){
        status = "repair";
    }

    public void setStatusService(){
        status = "service";
    }

    public void setStatusOccupied(int day) {
        this.status = "occupied";
        updateDay = day;
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

    public String display() {
        return String.format("Комната №%-2d — Cost = %-5s | Status = %-8s | UpdateDay = %s" , getNumber(), getCost() + "$", getStatus(), getUpdateDay());
    }

    @Override
    public String toString() {
        String data = "";
        data += "Комната №" + getNumber() + " {\n";
        data += "\tCost = " + getCost() + "$\n";
        data += "\tStatus = " + getStatus() + "\n";
        if (getStatus().equals("occupied")) {
            data += "\t  Guest = " + getGuest() + "\n";
        }
        data += "\tUpdateDay = " + getUpdateDay() + "\n";
        data += "\tCapacity = " + getCapacity() + "\n";
        data += "\tStars = " + getStars() + "\n}";

        return data;
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

    public int getUpdateDay() {
        return updateDay;
    }

    public String getGuestsHistory() {
        String data = "{ ";
        for (Guest el : guestsHistory){
            data += "\"" + el.getName() + "\"[" + el.getMoveInDay() + "-" + el.getMoveOutDay() + "] ";
        }
        data += "}";
        return data;
    }

    public void update() {
        if (status.equals("occupied")) {
            guestsHistory.add(guest);
            guest = null;
            setStatusService();
            updateDay++;
        } else {
            status = "empty";
        }
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
        guest.setRoom(this);
    }

    public Guest getGuest() {
        return guest;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getStars() {
        return stars;
    }
}
