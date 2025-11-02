package task_5.T1.Menu;

public class MenuController {
    private Builder builder;
    private Navigator navigator;

    public MenuController(Builder builder, Navigator navigator) {
        this.builder = builder;
        this.navigator = navigator;
    }

    public void run() {
        builder.buildMenu(); // Строим меню
        navigator.setCurrentMenu(builder.getRootMenu()); // Устанавливаем корневое меню

        // Пример использования: выводим меню и навигируем
        navigator.printMenu();
        navigator.navigate(1); // Выбираем первый пункт
    }
}