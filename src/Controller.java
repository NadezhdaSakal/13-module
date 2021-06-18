import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

class Controller {
    private final int amountClients;
    private final String[] dishes;

    private final List<ThreadClient> poolClients;
    private final ThreadCook cook;
    private final ThreadWaiter waiter;

    private final ArrayDeque<Order> orders;
    private final ArrayDeque<Order> makeOrders;

    Controller (int amountClients, String[] dishes) {
        this.amountClients = amountClients;
        this.dishes = dishes;

        orders = new ArrayDeque<>();
        makeOrders = new ArrayDeque<>();

        poolClients = new ArrayList<>();
        cook = new ThreadCook(this);
        waiter = new ThreadWaiter(this);
    }

    private ArrayDeque<Dish> getListOrders(String[] dishes) {
        ArrayDeque<Dish> resultList = new ArrayDeque<>();
        if (dishes != null) {
            for (int index = 0; index < dishes.length; index++) {
                resultList.add(new Dish(index + 1, dishes[index]));
            }
        }
        return resultList;
    }

    void init() {
        System.out.println("Start program");
        this.cook.start();
        this.waiter.start();
        this.startClients();
        while (true) {
            if (!this.clientsIs()) {
                this.stop();
                break;
            }
        }
        System.out.println("End program");
    }

    private void startClients() {
        for (int count = 0; count < this.amountClients; count++) {
            ThreadClient client = new ThreadClient(new Client(count + 1, "Client",
                    this, this.getListOrders(this.dishes)));
            this.poolClients.add(client);
            client.start();
        }
    }

    private void stop() {
        this.cook.interrupt();
        this.waiter.interrupt();
        while (cook.isAlive() || waiter.isAlive()) {

        }
    }

    private boolean clientsIs() {
        boolean result = false;
        for (ThreadClient client : this.poolClients) {
            if (result = client.isAlive()) {
                break;
            }
        }
        return result;
    }

    public Order getNextMakeOrder() throws InterruptedException {
        Order order;
        synchronized (makeOrders) {
            while (makeOrders.isEmpty()) {
                makeOrders.wait();
            }
            order = makeOrders.removeFirst();
        }
        return order;
    }

    public Order getNextOrder() throws InterruptedException {
        Order order;
        synchronized (orders) {
            while (orders.isEmpty()) {
                orders.wait();
            }
            order = this.orders.removeFirst();
        }
        return order;
    }

    public void addOrder(Order order) {
        if (order != null) {
            synchronized (this.orders) {
                orders.add(order);
                System.out.println(order);
                orders.notify();
            }
        }
    }

    public void addMakeOrder(Order order) {
        if (order != null) {
            synchronized (this.makeOrders) {
                this.makeOrders.add(order);
                this.makeOrders.notify();
            }
        }
    }
}
