import java.util.ArrayDeque;

class Client {
    private final int id;
    private final String name;
    private final Controller controller;
    private final ArrayDeque<Dish> dishes;

    Client(int id, String name, Controller controller, ArrayDeque<Dish> dishes) {
        this.id = id;
        this.name = name;
        this.controller = controller;
        this.dishes = dishes;
    }

    boolean dishesIsEmpty() {
        synchronized (this.dishes) {
            return this.dishes.isEmpty();
        }
    }

    void makeOrder() throws InterruptedException {
        if (!this.dishesIsEmpty()) {
            synchronized (this.dishes) {
                Order order = new Order(this, this.dishes.getFirst());
                this.controller.addOrder(order);
                this.dishes.wait();
            }
        }
    }

    void putOrder(Dish dish) {
        if (dish != null) {
            synchronized (this.dishes) {
                this.dishes.removeFirst();
                this.dishes.notify();
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%s N%d", this.name, this.id);
    }
}
