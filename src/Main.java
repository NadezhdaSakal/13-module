public class Main {
    private static final int AMOUNT_CLIENTS = 5;
    private static final String[] DISHES = {"Soup", "Salad", "Coffee"};

    public static void main(String[] args) {
        new Controller(AMOUNT_CLIENTS, DISHES).init();
    }
}



