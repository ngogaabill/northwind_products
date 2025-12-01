
import java.sql.*;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        String query = "";
        boolean running = true;

        while (running) {
            int choice = homeScreen();
            switch (choice) {
                case 1:
                    query = "SELECT * FROM products";
                    displayResults(query, choice);
                    break;
                case 2:
                    query = "SELECT * FROM customers";
                    displayResults(query, choice);
                    break;
                case 0:
                    exit();
                    running = false;
                    return;
            }
        }

    }

    private static void exit() {
        System.out.println("Good bye");
    }

    private static int homeScreen() {
        System.out.println("""
                What do you want to do?
                1) Display all products
                2) Display all customers
                0) Exit
                Select an option:""");

        int choice = scanner.nextInt();
        return choice;
    }

    public static void displayResults(String query, int choice) throws SQLException {

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", "root", "yearup");
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                if (choice == 1) {
                    int id = resultSet.getInt("ProductID");
                    String name = resultSet.getString("ProductName");
                    double price = resultSet.getDouble("UnitPrice");
                    int stock = resultSet.getInt("UnitsInStock");

                    System.out.println("Product Id: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Price: " + price);
                    System.out.println("Stock: " + stock);
                    System.out.println("------------------");
                } else if (choice == 2) {

                    String id = resultSet.getString("CustomerID");
                    String name = resultSet.getString("ContactName");
                    String country = resultSet.getString("Country");
                    String phone = resultSet.getString("Phone");

                    System.out.println("Product Id: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Price: " + country);
                    System.out.println("Phone: " + phone);
                    System.out.println("------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



