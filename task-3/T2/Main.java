package T2;

public class Main {

    /* Программа содержащая иерархию товаров для склада.
    Заполнить склад до предела и высчитать вес хранимого товара */

    public static void main(String[] args) {
        Storage storage = new Storage();
        Box box1 = new Box();
        Box box2 = new Box();
        Box box3 = new Box();

        Item item1 = new Item("телефон", 0.2f);
        Item item2 = new Item("микрофон", 0.5f);
        Item item3 = new Item("камень", 1f);

        Item item4 = new Item("ручка", 0.1f);
        Item item5 = new Item("лампочка", 0.2f);
        Item item6 = new Item("ноутбук", 2f);

        Item item7 = new Item("кирпич", 1f);

        Item item8 = new Item("бутылка", 0.5f);
        Item item9 = new Item("фонарь", 0.3f);
        Item item10 = new Item("павербанк", 0.8f);


        box1.add(item1);
        box1.add(item2);
        box1.add(item3);

        box2.add(item4);
        box2.add(item5);
        box2.add(item6);
        box2.add(item7);

        box3.add(item8);
        box3.add(item9);
        box3.add(item10);


        storage.add(box1);
        storage.add(box2);
        storage.add(box3);
        storage.display();

        System.out.printf("\nПолный вес хранилища = %.2fКг", storage.getWeight());


    }
}
