package T3;

// Шаг сборки кузова
class BodyLineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производим кузов автомобиля...");
        try {
            Thread.sleep(1000); // Имитация времени производства
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        CarBody body = new CarBody();
        System.out.println("Кузов готов: " + body);
        return body;
    }
}

// Шаг сборки шасси
class ChassisLineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Собираем шасси...");
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Chassis chassis = new Chassis();
        System.out.println("Шасси готово: " + chassis);
        return chassis;
    }
}

// Шаг сборки двигателя
class EngineLineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Изготавливаем двигатель...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Engine engine = new Engine();
        System.out.println("Двигатель готов: " + engine);
        return engine;
    }
}
