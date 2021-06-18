class Order {
    private final Client client;
    private final Dish dish;

    Client getClient() {
        return client;
    }

    Dish getDish() {
        return dish;
    }

    Order(Client client, Dish dish) {
        this.client = client;
        this.dish = dish;
    }

    @Override
    public String toString() {
        return String.format("%s order %s", client, dish);
    }
}