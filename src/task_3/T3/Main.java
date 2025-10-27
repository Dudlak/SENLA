package T3;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("ЗАПУСК ТЕСТА СБОРОЧНОЙ ЛИНИИ АВТОМОБИЛЕЙ");
        System.out.println("=".repeat(50));

        // Создаем шаги сборки
        ILineStep bodyStep = new BodyLineStep();
        ILineStep chassisStep = new ChassisLineStep();
        ILineStep engineStep = new EngineLineStep();

        // Создаем сборочную линию
        IAssemblyLine assemblyLine = new CarAssemblyLine(bodyStep, chassisStep, engineStep);

        // Создаем заготовку автомобиля
        Car car = new Car("Toyota Camry 2024");

        // Запускаем сборку
        System.out.println("\nНачинаем процесс сборки...");
        Thread.sleep(1000);

        Car assembledCar = (Car) assemblyLine.assembleProduct(car);

        assembledCar.displayInfo();

        System.out.println("\nТЕСТИРОВАНИЕ ЗАВЕРШЕНО");
    }
}
