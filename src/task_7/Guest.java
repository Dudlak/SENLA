package task_7;

import java.io.Serializable;
import java.util.*;

import static task_7.Main.hotel;

/*

*/

public class Guest implements Displayable, Serializable {
    Random random = new Random();

    private final String name;
    private final int moveInDay;
    private final int moveOutDay;
    private int pay, roomPay;

    private final List<ServiceCase> history = new ArrayList<>();

    static class ServiceCase {
        static long lastID;
        long id;
        Service service;
        int date;
        public ServiceCase(Service service, int date) {
            lastID++;
            id = lastID;
            this.service = service;
            this.date = date;
        }

        public ServiceCase(String name, int date, int cost) {
            this.service = new Service(name, cost);
            this.date = date;
        }

        public int getServiceCost() {
            return service.getCost();
        }

        public int getServiceDate() {
            return date;
        }
        public String getServiceName() {
            return service.getName();
        }

        public long getID() {
            return id;
        }



        @Override
        public String toString() {
            return "'" + service.getName() + "'";
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
        this.moveInDay = hotel.getDAY();
        this.moveOutDay = moveOutDay;
    }

    public Guest(String name, int moveInDay, int moveOutDay, int pay) {
        this.name = name;
        this.moveInDay = moveInDay;
        this.moveOutDay = moveOutDay;
        this.pay = pay;
    }

    @Override
    public String display() {
        return "Постоялец: '" + getName() + "'";
    }

    @Override
    public String toString() {
        return "Guest{\n" +
                "\tname='" + name + "'\n" +
                "\tmoveInDay=" + moveInDay + "\n" +
                "\tmoveOutDay=" + moveOutDay + "\n" +
                "\tpay=" + getPay() + "$\n" +
                "\thistory=" + history + "\n" +
                '}';
    }

    public String getName() {
        return name;
    }

    public void useService(Service service){
        history.add(new ServiceCase(service, hotel.getDAY()));
    }

    public int getMoveOutDay() {
        return moveOutDay;
    }

    public int getMoveInDay() {
        return moveInDay;
    }

    public void setRoomPay(int roomPay) {
        this.roomPay = roomPay;
    }

    public int getPay() {
        updatePay();
        return pay;
    }

    private void updatePay() {
        pay = roomPay;
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
            data += String.format("\t%-10s - Цена: %-2d$ | Дата: %d\n", serviceCase.service, serviceCase.service.getCost(), serviceCase.date);
        }
        return data;
    }

    public String getServices(String value) {
        List<ServiceCase> sorted = new ArrayList<>(history);
        switch (value){
            case "цена":
                sorted.sort(Comparator.comparing(ServiceCase::getServiceCost));
                break;

            case "дата":
                sorted.sort(Comparator.comparing(ServiceCase::getServiceDate));
                break;

                default:
                System.out.println("Некорректное значение сортировки услуг");;
        }
        return getServices(sorted);
    }

    public List<ServiceCase> getHistory() {
        return history;
    }
}
