public class FoodProduct extends Product{
    public FoodProduct() {
        super(ProductType.FOOD);
    }

    @Override
    public String getProductType() {
        return "F";
    }
}
