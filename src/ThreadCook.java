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
                Order order = controller.getNextOrder();
                System.out.printf("The cook: make order %s for %s;%s", order.getDish(),
                        order.getClient(), System.lineSeparator());
                controller.addMakeOrder(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("The cook end work.");
    }
}
