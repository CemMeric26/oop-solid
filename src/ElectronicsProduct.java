public class ElectronicsProduct extends Product {
    public ElectronicsProduct() {
        super(ProductType.ELECTRONICS);
    }

    @Override
    public String getProductType() {
        return "E";
    }
}
