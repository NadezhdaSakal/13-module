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
                System.out.printf("The waiter: give order %s for %s;%s", order.getDish(), order.getClient(), System.lineSeparator());
            }
        } catch (InterruptedException e) {
            e.getMessage();
        }
        System.out.println("The waiter end work.");
    }
}