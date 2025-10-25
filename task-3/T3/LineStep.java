package T3;

// Шаг сборки кузова
class BodyLineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Производим кузов автомобиля...");
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
        Engine engine = new Engine();
        System.out.println("Двигатель готов: " + engine);
        return engine;
    }
}
