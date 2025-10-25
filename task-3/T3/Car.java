package T3;

class Car implements IProduct {
    private CarBody body;
    private Chassis chassis;
    private Engine engine;
    private final String model;

    public Car(String model) {
        this.model = model;
        System.out.println("Начинаем сборку автомобиля: " + model);
    }

    @Override
    public void installFirstPart(IProductPart part) {
        if (part instanceof CarBody) {
            this.body = (CarBody) part;
            System.out.println("Устанавливаем кузов: " + part);
        }
    }

    @Override
    public void installSecondPart(IProductPart part) {
        if (part instanceof Chassis) {
            this.chassis = (Chassis) part;
            System.out.println("Устанавливаем шасси: " + part);
        }
    }

    @Override
    public void installThirdPart(IProductPart part) {
        if (part instanceof Engine) {
            this.engine = (Engine) part;
            System.out.println("Устанавливаем двигатель: " + part);
        }
    }

    public void displayInfo() {
        System.out.println("\nИНФОРМАЦИЯ ОБ АВТОМОБИЛЕ:");
        System.out.println("   Модель: " + model);
        System.out.println("   Кузов: " + (body != null ? body.getDescription() : "Не установлен"));
        System.out.println("   Шасси: " + (chassis != null ? chassis.getDescription() : "Не установлено"));
        System.out.println("   Двигатель: " + (engine != null ? engine.getDescription() : "Не установлен"));

        if (isComplete()) {
            System.out.println("АВТОМОБИЛЬ ГОТОВ К ИСПОЛЬЗОВАНИЮ!");
        } else {
            System.out.println("Автомобиль требует завершения сборки");
        }
    }

    public boolean isComplete() {
        return body != null && chassis != null && engine != null;
    }

    public String getModel() {
        return model;
    }
}
