package T3;

// Базовый класс для всех частей автомобиля
abstract class CarPart implements IProductPart {
    protected String name;
    protected String description;

    public CarPart(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " (" + description + ")";
    }
}

// Конкретные реализации частей автомобиля
class CarBody extends CarPart {
    public CarBody() {
        super("Кузов", "Стальной кузов седан с антикоррозийным покрытием");
    }
}

class Chassis extends CarPart {
    public Chassis() {
        super("Шасси", "Несущая рама с подвеской и колесами");
    }
}

class Engine extends CarPart {
    public Engine() {
        super("Двигатель", "Бензиновый двигатель 2.0L с турбонаддувом");
    }
}
