public class Main {
    private static final int AMOUNT_CLIENTS = 5;
    private static final String[] DISHES = {"Soup", "Salad", "Coffee"};

    public static void main(String[] args) {
        new Controller(AMOUNT_CLIENTS, DISHES).init();
    }
}

class ThreadClient extends Thread {
    private final Client client;

    ThreadClient(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        System.out.printf("%s : come to restaurant;%s", this.client, System.lineSeparator());
        try {
            while (!this.client.dishesIsEmpty()) {
                this.client.makeOrder();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.printf("%s: exit to restaurant;%s", this.client, System.lineSeparator());
    }
}

class ThreadCook extends Thread {
    private final Controller controller;

    ThreadCook(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        System.out.println("The cook begun work.");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = this.controller.getNextOrder();
                System.out.printf("The cook: make order %s for %s;%s", order.getDish(),
                        order.getClient(), System.lineSeparator());
                this.controller.addMakeOrder(order);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());

        }
        System.out.println("The cook end work.");
    }
}

class ThreadWaiter extends Thread {
    private final Controller controller;

    ThreadWaiter(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        System.out.println("The waiter begun work.");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = this.controller.getNextMakeOrder();
                order.getClient().putOrder(order.getDish());
                System.out.printf("The waiter: give order %s for %s;%s", order.getDish(),
                        order.getClient(), System.lineSeparator());
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());

        }
        System.out.println("The waiter end work.");
    }
}
