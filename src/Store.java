import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Store extends Entity {
    // can have single type of product
    //  its maximum stock and selling price is defined when created

    protected ProductType productType;
    protected int maxStock;
    protected int stock;
    protected int price;


    public Store(ProductType type, int maxStock, int price, double x, double y) {
        super(x, y);
        this.productType = type;
        this.maxStock = maxStock;
        this.stock = maxStock;

    }

    @Override
    public void draw(Graphics2D g2d){
        String text = this.getProductType().toString() + "," + this.getStock()+ "," + this.getPrice();
        drawHelper(g2d, text, Color.ORANGE);
    }

    public int getMaxStock() {
        return maxStock;
    }

    public int getStock() {
        return stock;
    }

    public int getPrice() {
        return price;
    }


    // When a customer wants to buy an item and the store is out of stock it should signal it
    //to the customer by raising an error.
    public void sellProduct() throws IllegalStateException {
        // remove the first occurrence of the product
           if (stock > 0) {
                stock--;
            } else {
                throw new IllegalStateException("Out of stock");
            }

    }

    public ProductType getProductType() {
        // return the type of the product that the store sells
        return productType;
    }

    @Override
    public void step() {

    }

    public void replenishStock(){
        stock = maxStock;
    }

}
