import java.util.*;

public class Bill {
    protected int id;
    private List<Dish> list_of_dishes;

    public Bill(List<Dish> list_of_dishes) {
        this.list_of_dishes = list_of_dishes;
    }

    public int getCost() {
        int sum = 0;
        for (Dish dish : list_of_dishes) {
            try {
                sum += Integer.parseInt(dish.price);
            } catch (NumberFormatException e) {
                System.err.println(e);
            }
        }

        System.out.println("Total Cost " + sum);

        return sum;
    }
}
