import java.util.ArrayList;
import java.util.List;

public abstract class ShoppingStrategy {
    protected Store targetStore;

    public Boolean isTargetSet() {
        return targetStore != null;
    }

    public Position getTargetStorePosition() {
        return targetStore.getPosition();
    }

    public abstract String getStrategyType();

    public abstract void setNextTargetStore(Customer c, List<Store> storeList);


    public abstract void buyProduct(Customer c);


    public abstract void setNextTargetStore(Customer c, ArrayList<Store> storeList);
}
