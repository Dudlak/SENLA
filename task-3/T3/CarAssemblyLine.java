package T3;

class CarAssemblyLine implements IAssemblyLine {
    private final ILineStep bodyStep;
    private final ILineStep chassisStep;
    private final ILineStep engineStep;

    public CarAssemblyLine(ILineStep bodyStep, ILineStep chassisStep, ILineStep engineStep) {
        this.bodyStep = bodyStep;
        this.chassisStep = chassisStep;
        this.engineStep = engineStep;
        System.out.println("🏭 Сборочная линия автомобилей инициализирована");
        System.out.println("   Шаг 1: " + bodyStep.getClass().getSimpleName());
        System.out.println("   Шаг 2: " + chassisStep.getClass().getSimpleName());
        System.out.println("   Шаг 3: " + engineStep.getClass().getSimpleName());
    }

    @Override
    public IProduct assembleProduct(IProduct product) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("НАЧИНАЕМ ПРОЦЕСС СБОРКИ АВТОМОБИЛЯ");
        System.out.println("=".repeat(50));

        // Шаг 1: Установка кузова
        System.out.println("\nЭТАП 1: Установка кузова");
        IProductPart body = bodyStep.buildProductPart();
        product.installFirstPart(body);

        // Шаг 2: Установка шасси
        System.out.println("\nЭТАП 2: Установка шасси");
        IProductPart chassis = chassisStep.buildProductPart();
        product.installSecondPart(chassis);

        // Шаг 3: Установка двигателя
        System.out.println("\nЭТАП 3: Установка двигателя");
        IProductPart engine = engineStep.buildProductPart();
        product.installThirdPart(engine);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("СБОРКА АВТОМОБИЛЯ ЗАВЕРШЕНА!");
        System.out.println("=".repeat(50));

        return product;
    }
}