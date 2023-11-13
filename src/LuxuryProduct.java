public class LuxuryProduct extends Product{
    public LuxuryProduct() {
        super(ProductType.LUXURY);
    }

    @Override
    public String getProductType() {
        return "L";
    }
}
