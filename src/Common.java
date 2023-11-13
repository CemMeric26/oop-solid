import javax.print.attribute.standard.NumberOfInterveningJobs;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.ArrayList;

public class Common {
    private static final String title = "Economics 101";
    private static final int windowWidth = 1650;
    private static final int windowHeight = 1000;

    private static final int entityDiameter = 20; //diameter of drawn entity (customer or store)

    private static final int storeNo = 10; //number of stores in the simulation
    private static final int customerNo = 10; //number of customers

    private static final int stockReplenishmentFrequency = 3000; // number of stepAllEntities calls before replenishing all stores

    private static final int foodBottomPrice = 20;
    private static final int foodCeilingPrice = 50;
    private static final int electronicsBottomPrice = 200;
    private static final int electronicsCeilingPrice = 2000;
    private static final int LuxuryBottomPrice = 5000;
    private static final int LuxuryCeilingPrice = 10000;

    private static final int minimumShoppingListLength = 5;
    private static final int maximumShoppingListLength = 10;

    private static final int stockStorageMin = 15; //minimum size of storage available for a store
    private static final int stockStorageMax = 25; //maximum number of storage available for a store

    private static final int customerMovementSpeed = 2;
    private static final Font font =new Font("Verdana", Font.BOLD, 20);

    public static String getTitle() {
        return title;
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static int getEntityDiameter(){ return entityDiameter;}

    public static Font getFont() {return font;}

    public static int getCustomerMovementSpeed() {
        return customerMovementSpeed;
    }

    private static final List<Store> simulationStores = new ArrayList<>();

    private static final List<Customer> simulationCustomers = new ArrayList<>();

    private static int lastReplenishmentStep = 0;


    public static List<Store> getSimulationStores(){
        return simulationStores;
    }

    public static List<Customer> getSimulationCustomers(){
        return simulationCustomers;
    }


    //TODO
    // put your necessary fields and methods here
    ////

    // TODO
    // When adding new stores or customers, their position, shopping list,
    // max stock etc. should be random between the ranges defined above.
    // You can use the function below or define your own function to generate
    // a random integer
    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    // E.g.
    /*
    //Product Generation
    switch(randInt(0,2)){
        case 0:
            //Food
            break;
        case 1:
            //Electronics
            break;
        case 2:
            //Luxury
            break;
     }
    }
    //x position of the center of entity generation
    double centerX = randInt(0,windowWidth-2*entityDiameter)
    */

    // TODO
    // Create customers and stores
    // Hint: you can also use a static block for this
    // Also note that you should make sure there are about equal number of stores for all products
    // like 4 stores for food, 3 stores for electronics and 4 stores for luxury etc.
    // Otherwise your simulation may deadlock

    public static List<Product> createShoppingList(){
        int listSize = randInt(minimumShoppingListLength,maximumShoppingListLength);
        List<Product> shoppingList= null;

        for(int i=0;i<listSize;i++)
        {
            switch (randInt(0,2)){
                // add food
                case 0:
                    shoppingList.add(new FoodProduct());
                case 1:
                    shoppingList.add(new ElectronicsProduct());
                case 2:
                    shoppingList.add(new LuxuryProduct());
            }

        }

        return shoppingList;

    }

    public static Customer createCustomer(){
        double x = randInt(0,windowWidth-2*entityDiameter);
        double y = randInt(0,windowHeight-2*entityDiameter);
        List<Product> shoppingList = createShoppingList();
        ShoppingStrategy strategy = null;
        switch (randInt(0,2)){
            case 0:
                strategy = new CheapestStrategy();
                break;
            case 1:
                strategy = new ClosestStrategy();
                break;
            case 2:
                strategy = new TravellingStrategy();
                break;
        }

        return new Customer(shoppingList,strategy,x,y);
    }

    private static int currentStoreType = 0;
    public static Store createStore() {
        double x = randInt(0,windowWidth-2*entityDiameter);
        double y = randInt(0,windowHeight-2*entityDiameter);
        int storageCapacity = randInt(stockStorageMin,stockStorageMax);
        int price;
        ProductType productType;
        switch (currentStoreType){
            case 0:
                price = randInt(foodBottomPrice,foodCeilingPrice);
                productType = ProductType.FOOD;
            case 1:
                price = randInt(electronicsBottomPrice,electronicsCeilingPrice);
                productType = ProductType.ELECTRONICS;
            case 2:
            price = randInt(LuxuryBottomPrice,LuxuryCeilingPrice);
            productType = ProductType.LUXURY;

        }
        currentStoreType = (currentStoreType + 1) % 3;
        return new Store(productType,storageCapacity,price,x,y);
    }
    static {
        for(int i=0;i<storeNo;i++){
            simulationStores.add(createStore());
        }
        for(int i=0;i<customerNo;i++){
            simulationCustomers.add(createCustomer());
        }
    }
    ////
    public static void stepAllEntities() {
        // TODO
        // In the function, you should:
        //    Remove left customers and add new ones as needed
        //    Move entities
        //    Replenish stocks of all stores on interval (stockReplenishmentFrequency)

        // Note that you should NOT handle transaction logic between customers and stores in here.

        for(int i=0;i<customerNo;i++){
            Customer c = simulationCustomers.get(i);
            if(c.getPosition().getIntX()<-2*entityDiameter || c.getPosition().getIntX()>windowWidth || c.getPosition().getIntY()<-2*entityDiameter || c.getPosition().getIntY()>windowHeight){
                simulationCustomers.set(i,createCustomer());

            }
        }
        for(Store s:simulationStores){
            s.step();
        }
        for(Customer c:simulationCustomers){
            c.step();
        }
        lastReplenishmentStep++;
        if(lastReplenishmentStep==stockReplenishmentFrequency){
            for(Store s:simulationStores){
                s.replenishStock();
            }
            lastReplenishmentStep=0;
        }


        ////
    }
}
