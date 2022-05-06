public class Delivery {
    protected int id;
    public int table_number;
    private Order order;

    public Delivery(int table_number, Order order) {
        this.table_number = table_number;
        this.order = order;
    }

    public void Deliver() {
        for (Dish dish : order.list_of_dishes) {
            System.out.println(dish);
        }
        Waiter waiter = new Waiter(id, table_number);
        waiter.Deliver();
    }
}
