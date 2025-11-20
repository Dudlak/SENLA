package task_7;

import java.io.*;


public class Saver {
    Hotel hotel;

    public Saver(Hotel hotel){
        this.hotel = hotel;
    }

    /*
        тесты
    */

    // Экспорт комнат
    private void exportRooms(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("saves/"+filename))) {
            //по roomNumber загружать соответствующий объект room в importGuest
            pw.println("id;number;cost;capacity;stars;status;updateDay;history;guestExist");
            for (Room room : hotel.getRooms()) {
                pw.printf("%d;%d;%d;%d;%d;%s;%d;%s", hotel.getId(room), room.getNumber(), room.getCost(), room.getCapacity(), room.getStars(), room.getStatus(), room.getUpdateDay(), room.displayGuestsHistory());
                Guest guest = hotel.getGuest(room);
                if (guest != null) {
                    pw.println(";1");
                } else {
                    pw.println();
                }
            }
            System.out.println("Guests exported to '" + filename + "'");
        } catch (IOException e) {
            System.err.println("Error exporting cities: " + e.getMessage());
        }
    }

    // Экспорт гостей
    private void exportGuests(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("saves/"+filename))) {
            //по roomNumber загружать соответствующий объект room в importGuest
            pw.println("id;name;moveInDay;moveOutDay;pay");
            for (Guest el : hotel.getGuests()) {
                if (el != null) {
                    pw.printf("%d;%s;%d;%d;%d%n", hotel.getId(el), el.getName(), el.getMoveInDay(), el.getMoveOutDay(), el.getPay());
                }
            }
            System.out.println("Guests exported to '" + filename + "'");
        } catch (IOException e) {
            System.err.println("Error exporting cities: " + e.getMessage());
        }
    }

    // Экспорт истории услуг гостя
    private void exportGuestHistory(Guest guest) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("saves/guest"+ hotel.getId(guest) +"History.csv"))) {
            pw.println("id;name;date;cost");
            for (Guest.ServiceCase el : guest.getHistory()) {
                pw.printf("%d;%s;%d;%d%n", el.getID(), el.getServiceName(), el.getServiceDate(), el.getServiceCost());
            }
            System.out.println("Guests exported to 'guest"+ hotel.getId(guest) +"History'");
        } catch (IOException e) {
            System.err.println("Error exporting cities: " + e.getMessage());
        }
    }

    // Импорт комнат
    private void importRooms(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader("saves/"+filename))) {
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false; continue;
                }

                String[] parts = line.split(";");

                if (parts.length < 2) continue;

                //id;number;cost;capacity;stars;status;updateDay;history;guestExist
                int id = Integer.parseInt(parts[0].trim());
                Room room = hotel.getRoom(id);
                if (room == null) {
                    String[] history = parts[7].replace("[", "").replace("]", "").split(", ");
                    room = new Room(Integer.parseInt(parts[1].trim()), Integer.parseInt(parts[2].trim()), Integer.parseInt(parts[3].trim()), Integer.parseInt(parts[4].trim()), parts[5].trim(), Integer.parseInt(parts[6].trim()), history);

                    hotel.addRoom(id, room);
                } else {
                    System.out.println("Комната " + parts[1].trim() + " уже существует");
                }
            }
            System.out.println("Комнаты импортированы из файла '" + filename + "'");
        } catch (IOException e) {
            System.err.println("Ошибка импорта комнат: " + e.getMessage());
        }
    }

    // Импорт гостей
    private void importGuests(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader("saves/"+filename))) {
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false; continue;
                }

                String[] parts = line.split(";");

                if (parts.length < 2) continue;

                // обработка неактуальных дат (уже уехал, ещё не приехал)
                if (Integer.parseInt(parts[2].trim()) > Hotel.getDAY() || Integer.parseInt(parts[2].trim()) < Hotel.getDAY()) continue;

                //id;name;moveInDay;moveOutDay;pay
                int id = Integer.parseInt(parts[0].trim());
                Guest guest = hotel.getGuest(id);
                if (guest == null) {
                    guest = new Guest(parts[1].trim(), Integer.parseInt(parts[2].trim()), Integer.parseInt(parts[3].trim()), Integer.parseInt(parts[4].trim()));
                    hotel.getRoom(id).setGuest(guest);
                    importHistoryOfGuest(guest);
                } else {
                    System.out.println("Гость " + parts[1].trim() + " уже существует");
                }
            }
            System.out.println("Гости загружены из файла '" + filename + "'");
        } catch (IOException e) {
            System.err.println("Ошибка загрузки гостей: " + e.getMessage());
        }
    }

    // импорт истории для каждого гостя
    private void importHistoryOfGuest(Guest guest) {
        try (BufferedReader br = new BufferedReader(new FileReader("saves/guest"+ hotel.getId(guest) +"History.csv"))) {
            guest.getHistory().clear();
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false; continue;
                }

                String[] parts = line.split(";");

                if (parts.length < 2) continue;

                // id;name;date;cost
                long id = Long.parseLong(parts[0].trim());
                guest.getHistory().add(new Guest.ServiceCase(parts[1].trim(), Integer.parseInt(parts[2].trim()), Integer.parseInt(parts[3].trim())));
            }
            System.out.println("Guests imported from '" + "guest"+ hotel.getId(guest) +"History" + "'");
        } catch (IOException e) {
            System.err.println("Error importing cities: " + e.getMessage());
        }
    }

    public void clear() {
        File savesDir = new File("saves");

        if (!savesDir.exists() || !savesDir.isDirectory()) {
            System.out.println("Директория 'saves' не существует");
            return;
        }

        File[] files = savesDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.delete()) {
                System.out.println("Файл " + file.getName() + " удалён.");
            }
        }
    }

    public void exportSave() {
        exportGuests("guests.csv");
        exportRooms("rooms.csv");

        for (Guest guest : hotel.getGuests()) {
            exportGuestHistory(guest);
        }
    }

    public void importSave() {
        importRooms("rooms.csv");
        importGuests("guests.csv");
    }
}
