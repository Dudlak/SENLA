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
        displayAssemblyStart();

        // Последовательные этапы сборки
        assembleBody(product);
        assembleChassis(product);
        assembleEngine(product);

        displayAssemblyComplete();
        return product;
    }

    private void displayAssemblyStart() {
        String separator = "=".repeat(50);
        System.out.println("\n" + separator);
        System.out.println("🏁 НАЧИНАЕМ ПРОЦЕСС СБОРКИ АВТОМОБИЛЯ");
        System.out.println(separator);
    }

    private void displayAssemblyComplete() {
        String separator = "=".repeat(50);
        System.out.println("\n" + separator);
        System.out.println("🎊 СБОРКА АВТОМОБИЛЯ ЗАВЕРШЕНА!");
        System.out.println(separator);
    }

    private void assembleBody(IProduct product) {
        System.out.println("\n📦 ЭТАП 1: Установка кузова");
        IProductPart body = bodyStep.buildProductPart();
        product.installFirstPart(body);
        logPartInstallation("Кузов", body);
    }

    private void assembleChassis(IProduct product) {
        System.out.println("\n📦 ЭТАП 2: Установка шасси");
        IProductPart chassis = chassisStep.buildProductPart();
        product.installSecondPart(chassis);
        logPartInstallation("Шасси", chassis);
    }

    private void assembleEngine(IProduct product) {
        System.out.println("\n📦 ЭТАП 3: Установка двигателя");
        IProductPart engine = engineStep.buildProductPart();
        product.installThirdPart(engine);
        logPartInstallation("Двигатель", engine);
    }

    private void logPartInstallation(String partName, IProductPart part) {
        System.out.println("✅ Установлен " + partName + ": " + part);
    }
}