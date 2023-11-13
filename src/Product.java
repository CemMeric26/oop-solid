public abstract class Product {

    private ProductType type;


    public Product(ProductType type) {
        this.type = type;
    }

    public ProductType getType() { return type; }

    public abstract String getProductType();

}
