import java.util.List;

public class CheapestStrategy extends ShoppingStrategy {
    // Find and go to the cheapest store that have the first product on the shopping list
    //. Upon arrival, if the store is out of stock wait until the product is available again

    @Override
    public String getStrategyType(){
        return "Ch";
    }

    @Override
    public void setNextTargetStore(Customer c, List<Store> storeList){
        Product firstProduct = c.getShoppingList().get(0);

        Store cheapestStore = null;
        int lowestPrice = Integer.MAX_VALUE;

        for (Store store : storeList) {
            if (store.getProductType()== firstProduct.getType()) {
                if (store.getPrice() < lowestPrice) {
                    lowestPrice = store.getPrice();
                    cheapestStore = store;
                }
            }
        }
        targetStore = cheapestStore;
    }

    @Override
    public void buyProduct(Customer c){
        try {
            targetStore.sellProduct(); // sell the first product in the store
            c.updateShoppingList(c.getShoppingList().get(0)); // update the shopping list of the customer
            targetStore = null;
        } catch (IllegalStateException e ) {
            // if the store is out of stock wait until the product is available again

        }
    }


}
