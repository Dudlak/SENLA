package task_4.T1;

import java.util.*;

public class Guest {
    Random random = new Random();

    private final String name;
    private Room room;
    private final int moveInDay;
    private final int moveOutDay;
    private int pay = 0;

    private final List<ServiceCase> history = new ArrayList<>();

    private class ServiceCase {
        Service service;
        int date;
        public ServiceCase(Service service, int date) {
            this.service = service;
            this.date = date;
        }

        public int getServiceCost() {
            return service.getCost();
        }

        public int getServiceDate() {
            return date;
        }

        @Override
        public String toString() {
            return "'" + name + "'";
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
        return "Guest{" +
                "\tname='" + name + "'\n" +
                "\troom='Комната №" + room.getNumber() + "'\n" +
                "\tmoveInDay=" + moveInDay + "\n" +
                "\tmoveOutDay=" + moveOutDay + "\n" +
                "\tpay=" + getPay() + "$\n" +
                "\thistory=" + history + "\n" +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getRoomNumber() {
        return room.getNumber();
    }

    public void useService(Service service){
        history.add(new ServiceCase(service, Main.DAY));
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getMoveOutDay() {
        return moveOutDay;
    }

    public int getMoveInDay() {
        return moveInDay;
    }

    public int getPay() {
        updatePay();
        return pay;
    }

    private void updatePay() {
        pay = (moveOutDay - moveInDay) * room.getCost();
        for (ServiceCase el : history) {
            pay += el.getServiceCost();
        }
    }

    public String getServices() {
        return getServices(history);
    }
    public String getServices(List<ServiceCase> history) {
        String data = "";
        for (ServiceCase serviceCase : history) {
            data += " " + serviceCase.service + " - Cost: " + serviceCase.service.getCost() + " | " + "Date: " + serviceCase.date + "\n";
        }
        return data;
    }

    public String getServices(String value) {
        List<ServiceCase> sorted = new ArrayList<>(history);
        switch (value){
            case "cost":
                sorted.sort(Comparator.comparing(ServiceCase::getServiceCost));
                break;

            case "date":
                sorted.sort(Comparator.comparing(ServiceCase::getServiceDate));
                break;
        }
        return getServices(sorted);
    }
}
