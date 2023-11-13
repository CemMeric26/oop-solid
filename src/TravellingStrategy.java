import java.util.ArrayList;
import java.util.List;

public class TravellingStrategy extends ShoppingStrategy {
    //Similar to Closest strategy, find the closest store,
    // buy anything available and on the list and go to the closest store from there that is not visited yet.
    // After visiting all stores, if there are still things to buy on the shopping list, start to visit
    // all stores again with same strategy

    private ArrayList<Store> visitedStores;

    public TravellingStrategy(){
        super();
        visitedStores = new ArrayList<>();
    }


    @Override
    public String getStrategyType(){
        return "Tr";
    }

    @Override
    public void setNextTargetStore(Customer c, List<Store> storeList){

        double minDistance = Double.MAX_VALUE;
        for (Store store : storeList) {
            // if the store has the first product
            if (!visitedStores.contains(store)) {
                double distance = c.getPosition().distanceTo(store.getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    targetStore = store;
                }
            }
        }

        if(minDistance == Double.MAX_VALUE){
            visitedStores = new ArrayList<Store>();
        }

    }

    @Override
    public void buyProduct(Customer c){
        List<Product> shoppingList = c.getShoppingList();

        for (Product product : shoppingList) {
            if (targetStore.getProductType() == product.getType()) {
                try {
                    targetStore.sellProduct(); // sell the first product in the store
                    c.updateShoppingList(product); // update the shopping list of the customer

                    break;
                } catch (IllegalStateException e) {
                    break;
                }

            }
        }
        visitedStores.add(targetStore);
        targetStore = null;
    }
}
