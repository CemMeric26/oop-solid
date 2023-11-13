import java.util.List;

public class ClosestStrategy extends ShoppingStrategy{
    // Find and go to the closest store that have the first product on the list.
    //Upon arrival, if the store is out of stock, try to go to the nearest closest store that
    //sells the product from there

    private Store previousStore;

    @Override
    public String getStrategyType(){
        return "Cl";
    }

    @Override
    public void setNextTargetStore(Customer c, List<Store> storeList){
        Product firstProduct = c.getShoppingList().get(0);
        Store closestStore = null;
        double minDistance = Double.MAX_VALUE;
        for (Store store : storeList) {
            // if the store has the first product
            if (store.getProductType() == firstProduct.getType() && store != previousStore) {
                double distance = c.getPosition().distanceTo(store.getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    closestStore = store;
                }
            }
        }
        targetStore = closestStore;
    }

    @Override
    public void buyProduct(Customer c){
        try {
            targetStore.sellProduct(); // sell the first product in the store
            c.updateShoppingList(c.getShoppingList().get(0)); // update the shopping list of the customer
        } catch (IllegalStateException e ) {
            // if the store is out of stock try to go to the nearest closest store that sells the product from there
            previousStore = targetStore;
        }
        targetStore = null;
    }



}
