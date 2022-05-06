import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static Scanner input = new Scanner(System.in);
    public static Connection c = null;
    public static Statement stmt = null;

    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to the Manojini's Dosa Corner!");
        Customer customer = new Customer("", "");
        boolean login_value = false;

        while (!login_value) {
            String username, password;
            try {
                System.out.println("Login : ");
                System.out.println("Enter your username : ");
                username = input.nextLine();
                System.out.println("Enter your password : ");
                password = input.nextLine();
                customer.username = username;
                customer.password = password;
            } catch (Exception e) {
                System.err.println(e);
            }
            login_value = customer.login(customer, c, stmt, input);
        }
        System.out.println("Login Successful...");
        int table_number = customer.assignTable(customer, c, stmt, input);
        Menu menu = new Menu();
        try {
            menu.display(c, stmt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        customer.TakeOrder(input, c, stmt);
        if (customer.order.list_of_dishes.isEmpty()) {
            System.out.println("Thank you for Visiting, Order next time!");
            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/restaurant1",
                                "postgres", "hello1234");
                c.setAutoCommit(false);

                stmt = c.createStatement();
                String sql = "DELETE FROM tables WHERE username='" + customer.username + "';";
                stmt.executeUpdate(sql);
                c.commit();
            } catch (Exception e) {
                System.err.println(e);
            }
            c.close();
            stmt.close();
            input.close();
            System.exit(0);
        }

        int delivery_id = customer.hashCode();

        Chef chef = new Chef(customer.order);
        chef.MakeOrder();

        Waiter waiter = new Waiter(delivery_id, table_number);
        waiter.Deliver();

        customer.AskBill();

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/restaurant1",
                            "postgres", "hello1234");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "DELETE FROM tables WHERE username='" + customer.username + "';";
            stmt.executeUpdate(sql);
            c.commit();
        } catch (Exception e) {
            System.err.println(e);
        }

        c.close();
        stmt.close();
        input.close();
    }
}
