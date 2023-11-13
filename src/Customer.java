import java.awt.*;
import java.util.List;
import java.util.ArrayList;

enum Strategy {
    Cheapest, Closest, Travelling
}
public class Customer extends Entity {
    // Customers keep a shopping list that the products they will buy

    protected List<Product> shoppingList;
    protected ShoppingStrategy shoppingStrategy;


    public Customer (List<Product> shoppingList, ShoppingStrategy shoppingStrategy, double x, double y) {
        super(x, y);
        this.shoppingList = shoppingList;
        this.shoppingStrategy = shoppingStrategy;
    }

    public void draw(Graphics2D g2d){
        // first three of the products should be printed
        String text = shoppingStrategy.getStrategyType();
        for(int i = 0; i < 3; i++){
            try {
                text += "," + shoppingList.get(i).getProductType();
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        drawHelper(g2d, text, Color.GRAY);
    }


    public String getStrategyType() {
        return shoppingStrategy.getStrategyType();
    }

    public List<Product> getShoppingList() {
        return shoppingList;
    }


    public void updatePosition(Position position) {
        this.position = position;
    }

    public void updateShoppingList(Product boughtProduct) {
        this.shoppingList.remove(boughtProduct);
    }


    public void step() {

        if(shoppingList.isEmpty()){
            // target store is set
            if(position.getIntX()<Common.getWindowWidth() - position.getIntX()){
                position.setX(position.getX()-Common.getCustomerMovementSpeed());
            }else {
                position.setX(position.getX()+Common.getCustomerMovementSpeed());
            }

        } else if (!shoppingStrategy.isTargetSet()) { // customer finished shopping list
            // remove the customer from the simulation
            this.shoppingStrategy.setNextTargetStore(this,Common.getSimulationStores());
        }
        else {

            double distance = this.getPosition().distanceTo(shoppingStrategy.getTargetStorePosition());
            // if the customer clashes with the store then buy it
            if (distance<= 2* Common.getEntityDiameter()) {
                shoppingStrategy.buyProduct(this);
            } else {
                // move towards the target store
                Position targetStorePosition = shoppingStrategy.getTargetStorePosition();
                double x = targetStorePosition.getX() - this.getPosition().getX();
                double y = targetStorePosition.getY() - this.getPosition().getY();

                if(distance > 2*Common.getEntityDiameter()){
                    double vx = x/distance*Common.getCustomerMovementSpeed();
                    double vy = y/distance*Common.getCustomerMovementSpeed();
                    this.getPosition().setX(this.getPosition().getX()+vx);
                    this.getPosition().setY(this.getPosition().getY()+vy);

                }else {
                    this.getPosition().setX(targetStorePosition.getX());
                    this.getPosition().setY(targetStorePosition.getY());
                }

            }


        }


    }

    
}
