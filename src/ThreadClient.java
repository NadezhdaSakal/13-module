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