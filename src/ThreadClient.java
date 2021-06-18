class ThreadClient extends Thread {
    private final Client client;

    ThreadClient(Client client) {

        this.client = client;
    }

    @Override
    public void run() {
        System.out.printf("%s : come to restaurant;%s", client, System.lineSeparator());
        try {
            while (!client.dishesIsEmpty()) {
                client.makeOrder();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.printf("%s: exit to restaurant;%s", client, System.lineSeparator());
    }
}