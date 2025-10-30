package task_4.T1;

import java.util.*;

public class Guest {
    Random random = new Random();

    private final String name;
    private int roomNumber;
    private final int moveInDay;
    private final int moveOutDay;
    private int pay = 0;

    private final List<serviceCase> history = new ArrayList<>();

    private class serviceCase {
        Service service;
        int date;
        public serviceCase(Service service, int date) {
            this.service = service;
            this.date = date;
        }

        public int getServiceCost() {
            return service.getCost();
        }

        public int getServiceDate() {
            return date;
        }
    }

    private final String[] names = {
            "ALEXANDER", "MARIA", "DMITRY", "ANNA", "SERGEY",
            "ELENA", "ANDREY", "OLGA", "ALEXEY", "NATALIA",
            "IVAN", "IRINA", "MIKHAIL", "TATYANA", "EVGENY",
            "SVETLANA", "VLADIMIR", "YULIA", "PAVEL", "EKATERINA",
            "ARTEM", "ANASTASIA", "NIKOLAY", "LYUDMILA", "VIKTOR",
            "MARINA", "KONSTANTIN", "VALENTINA", "ROMAN", "GALINA",
            "VYACHESLAV", "LARISA", "GRIGORY", "NADEZHDA", "FEDOR",
            "VERA", "BORIS", "KSENIYA", "DANIIL", "ZOYA",
            "VASILY", "SOFIA", "STANISLAV", "DIANA", "TIMOFEY",
            "ELINA", "GEORGY", "YANA", "SEMEN", "ULYANA"
    };

    public Guest(int moveOutDay) {
        name = names[random.nextInt(names.length)];
        this.moveInDay = Main.DAY;
        this.moveOutDay = moveOutDay;
    }

    @Override
    public String toString() {
        String data = "";
        data += "Постоялец " + name + " {\n";
        data += "\tRoom = " + roomNumber + "\n";
        data += "\tMoveOutDay = " + moveOutDay + "\n}";

        return data;
    }

    public String getName() {
        return name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void useService(Service service){
        history.add(new serviceCase(service, Main.DAY));
        pay += service.getCost();
    }

    public void setRoom(Room room) {
        this.roomNumber = room.getNumber();
        pay += (moveOutDay - moveInDay) * room.getCost();
    }

    public int getMoveOutDay() {
        return moveOutDay;
    }

    public int getMoveInDay() {
        return moveInDay;
    }

    public int getPay() {
        return pay;
    }

    public String getServices() {
        return getServices(history);
    }
    public String getServices(List<serviceCase> history) {
        String data = "";
        for (serviceCase serviceCase : history) {
            data += " " + serviceCase.service + " - Cost: " + serviceCase.service.getCost() + " | " + "Date: " + serviceCase.date + "\n";
        }
        return data;
    }

    public String getServices(String value) {
        List<serviceCase> sorted = new ArrayList<>(history);
        switch (value){
            case "cost":
                sorted.sort(Comparator.comparing(serviceCase::getServiceCost));
                break;

            case "date":
                sorted.sort(Comparator.comparing(serviceCase::getServiceDate));
                break;
        }
        return getServices(sorted);
    }
}
