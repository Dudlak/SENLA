package T3;

class Car implements T3.IProduct {
    private T3.CarBody body;
    private T3.Chassis chassis;
    private T3.Engine engine;
    private final String model;

    public Car(String model) {
        this.model = model;
        System.out.println("Начинаем сборку автомобиля: " + model);
    }

    @Override
    public void installFirstPart(T3.IProductPart part) {
        if (part instanceof T3.CarBody) {
            this.body = (T3.CarBody) part;
            System.out.println("Устанавливаем кузов: " + part);
        }
    }

    @Override
    public void installSecondPart(T3.IProductPart part) {
        if (part instanceof T3.Chassis) {
            this.chassis = (T3.Chassis) part;
            System.out.println("Устанавливаем шасси: " + part);
        }
    }

    @Override
    public void installThirdPart(T3.IProductPart part) {
        if (part instanceof T3.Engine) {
            this.engine = (T3.Engine) part;
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
