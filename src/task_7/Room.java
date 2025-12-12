package task_7;

import task_4.T1.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import static task_4.T1.Main.random;
import static task_7.Main.config;

public class Room implements Displayable, Serializable {
    private int cost, number, capacity, stars;
    private String status; // "empty","repair","service","occupied"
    private int updateDay = 0;
    private Guest guest;

    private String[] guestsHistory = new String[config.getInt("db.guestsHistoryLong")];


    public Room(int number, int cost) {
        this.cost = cost;
        this.number = number;
        status = "empty";
        capacity = random.nextInt(1,5);
        stars = random.nextInt(2,5);
    }

    //id;number;cost;capacity;stars;status;updateDay;history;guestExist
    public Room(int number, int cost, int capacity, int stars, String status, int updateDay, String[] history) {
        this.cost = cost;
        this.number = number;
        this.status = status;
        this.capacity = capacity;
        this.stars = stars;
        this.updateDay = updateDay;
        this.guestsHistory = history;
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

    @Override
    public String display() {
        return String.format("Комната №%-2d — Cost = %-5s | Status = %-8s | UpdateDay = %-2s | Вместимость = %-1d | Stars = %d" , getNumber(), getCost() + "$", getStatus(), getUpdateDay(), getCapacity(),getStars());
    }

    @Override
    public String toString() {
        return  "Комната №" + getNumber() + " {\n" +
                "\tCost = " + getCost() + "$\n" +
                "\tStatus = " + getStatus() + "\n" +
                "\tUpdateDay = " + getUpdateDay() + "\n" +
                "\tCapacity = " + getCapacity() + "\n" +
                "\tStars = " + getStars() + "\n}";
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

    public String[] getGuestsHistory() {
        return guestsHistory;
    }

    public String displayGuestsHistory(){
        String data = "[";
        Iterator<String> iterator = Arrays.stream(guestsHistory).iterator();
        if (iterator.hasNext()) {
            data += iterator.next();
            while (iterator.hasNext()) {
                data += "; " + iterator.next();
            }
        }
        data += "]";
        return data;
    }

    public void update() {
        if (status.equals("occupied")) {
            historyUpdate();
            setStatusService();
            updateDay++;
            guest = null;
        } else {
            status = "empty";
        }
    }

    private void historyUpdate() {
        guestsHistory[2] = guestsHistory[1];
        guestsHistory[1] = guestsHistory[0];
        guestsHistory[0] = guest.getName() + "[" + guest.getMoveInDay() + ":" + guest.getMoveOutDay() + "]";
    }

    public int getCapacity() {
        return capacity;
    }

    public int getStars() {
        return stars;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        if (this.guest == null){
            this.guest = guest;
        } else {
            System.out.println("Комната занята");
        }
    }
}
