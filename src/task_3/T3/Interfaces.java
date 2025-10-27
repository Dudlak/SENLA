package T3;

// Интерфейс для шага сборки
interface ILineStep {
    IProductPart buildProductPart();
}

// Интерфейс для продукта
interface IProduct {
    void installFirstPart(IProductPart part);
    void installSecondPart(IProductPart part);
    void installThirdPart(IProductPart part);
}

// Интерфейс для частей продукта
interface IProductPart {
    String getName();
    String getDescription();
}

// Интерфейс для сборочной линии
interface IAssemblyLine {
    IProduct assembleProduct(IProduct product);
}
