public class Chef {
    private Order order;

    public Chef(Order order) {
        this.order = order;
    }

    public void MakeOrder() {
        for (Dish dish : order.list_of_dishes) {
            System.out.println();
            System.out.println(dish.name + " Preparing...");
            System.out.println(dish.name + " Done");
        }
    }
}
