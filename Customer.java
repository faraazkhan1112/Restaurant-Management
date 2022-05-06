import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// public class Customer extends Admin {
public class Customer {
    // private int id = 1;
    public String username, password;
    private List<Dish> list_of_dishes = new ArrayList<Dish>();
    public Order order = new Order(list_of_dishes, 0);
    public List<Dish> list = new ArrayList<Dish>();

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login(Customer customer, Connection c, Statement stmt, Scanner input) {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/restaurant1",
                            "postgres", "hello1234");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt
                    .executeQuery("select * from customer where username='" + customer.username + "' and password='"
                            + customer.password + "';");
            if (rs.next()) {
                rs.close();
                return true;
            } else {
                rs.close();
                System.out.println("Enter 0 to try again and 1 to create new profile : ");
                int temp = Integer.parseInt(input.nextLine());
                if (temp == 0) {
                    return false;
                } else {
                    Create(c, stmt, input);
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return false;
    }

    public void Create(Connection c, Statement stmt, Scanner input) {
        // If customer not in database create new customer
        System.out.println("Create a profile : ");
        String username, password;
        try {
            System.out.println("Enter your username : ");
            username = input.nextLine(); // Read user input
            System.out.println("Enter your password : ");
            password = input.nextLine();
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/restaurant1",
                            "postgres", "hello1234");
            c.setAutoCommit(false);
            // System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO CUSTOMER (USERNAME,PASSWORD) "
                    + "VALUES ('" + username + "','" + password + "');";
            stmt.executeUpdate(sql);
            c.commit();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void TakeOrder(Scanner input, Connection c, Statement stmt) {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/restaurant1",
                            "postgres", "hello1234");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from menu;");
            while (rs.next()) {
                String name = rs.getString("dish");
                int price = rs.getInt("price");
                Dish d = new Dish(name, Integer.toString(price));
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int taking = 1;
        while (taking != 0) {
            try {
                switch (taking) {
                    case 1: {
                        order.AddItem(input, list);
                        break;
                    }
                    case 2: {
                        order.RemoveItem(input, list);
                        break;
                    }
                    default:
                        System.out.println("Enter Valid Number");
                }
                System.out.println("\nYour current order is:");
                for (Dish dish : list_of_dishes) {
                    System.out.println(dish.name + " " + dish.price);
                }
                System.out.println("\n");
                System.out.println("Enter\n0 to Finalize\n1 to Add Dish\n2 to Remove Dish");
                taking = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    public void AskBill() {
        Bill bill = new Bill(order.list_of_dishes);
        System.out.println("Your Bill:");
        for (Dish dish : list_of_dishes) {
            System.out.println(dish.name + " " + dish.price);
        }
        int amount = bill.getCost();
        Payment payment = new Payment(amount);
        payment.PaymentDone();
    }

    private int getTableNumber(ArrayList<Integer> array) {
        int total_seats = 10;
        ArrayList<Integer> temp = new ArrayList<Integer>();

        for (int i = 0; i < total_seats; i++) {
            if (!array.contains(i)) {
                temp.add(i);
            }
        }

        return (int) temp.toArray()[0];
    }

    public int assignTable(Customer customer, Connection c, Statement stmt, Scanner input) {
        int table_number = -1;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/restaurant1",
                            "postgres", "hello1234");
            c.setAutoCommit(false);

            ArrayList<Integer> array = new ArrayList<Integer>();
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM TABLES;");
            while (rs.next()) {
                array.add(rs.getInt("table_number"));
            }

            table_number = getTableNumber(array);

            String sql = "INSERT INTO TABLES (TABLE_NUMBER, USERNAME) "
                    + "VALUES ('" + table_number + "','" + customer.username + "');";
            stmt.executeUpdate(sql);
            c.commit();
        } catch (Exception e) {
            System.err.println("User already logged in!");
            System.exit(0);
        }

        return table_number;
    }
}
