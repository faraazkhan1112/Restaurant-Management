import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Menu {
    public void display(Connection c, Statement stmt) throws Exception {
        int counter = 0;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/restaurant1",
                            "postgres", "hello1234");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("select * from menu;");
            while (rs.next()) {
                System.out.println(counter++);
                String name = rs.getString("dish");
                int price = rs.getInt("price");
                System.out.println("NAME = " + name);
                System.out.println("PRICE = " + price);
                // System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editMenu(Dish dish, Connection c, Statement stmt) throws Exception {
        Class.forName("org.postgresql.Driver");
        c = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/restaurant1",
                        "postgres", "hello1234");
        c.setAutoCommit(false);

        stmt = c.createStatement();
        String sql = "insert into menu values (dish, price) '" + dish.name + "', " + Integer.parseInt(dish.price) + ");";
        stmt.executeUpdate(sql);
        c.commit();
    }
}
