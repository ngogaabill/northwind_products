import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword("yearup");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        boolean running = true;
        while (running) {
            int choice = homeScreen();
            switch (choice) {
                case 1:
                    queryProducts(dataSource);
                    break;
                case 2:
                    queryCustomers(dataSource);
                    break;
                case 3:
                    queryCategories(dataSource);
                    searchCategory(dataSource);
                    break;
                case 0:
                    exit();
                    running = false;
                    break;
            }
        }
    }

    private static int homeScreen() {
        System.out.println("""
                What do you want to do?
                1) Display all products
                2) Display all customers
                3) Display all categories
                0) Exit
                Select an option:""");
        return scanner.nextInt();
    }

    private static void exit() {
        System.out.println("Good bye");
    }

    public static void queryProducts(BasicDataSource dataSource) throws SQLException {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ProductID");
                String name = resultSet.getString("ProductName");
                double price = resultSet.getDouble("UnitPrice");
                int stock = resultSet.getInt("UnitsInStock");

                System.out.println("Product Id: " + id);
                System.out.println("Name: " + name);
                System.out.println("Price: " + price);
                System.out.println("Stock: " + stock);
                System.out.println("------------------");
            }
        }
    }
    public static void queryCustomers(BasicDataSource dataSource) throws SQLException {
        String query = "SELECT ContactName, CompanyName, City, Country, Phone " +
                "FROM Customers WHERE Country IS NOT NULL ORDER BY Country";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String contactName = resultSet.getString("ContactName");
                String companyName = resultSet.getString("CompanyName");
                String city = resultSet.getString("City");
                String country = resultSet.getString("Country");
                String phone = resultSet.getString("Phone");

                System.out.println("Company Name: " + companyName);
                System.out.println("Name: " + contactName);
                System.out.println("City: " + city);
                System.out.println("Country: " + country);
                System.out.println("Phone: " + phone);
                System.out.println("------------------");
            }
        }
    }

    public static void queryCategories(BasicDataSource dataSource) throws SQLException {
        String query = "SELECT CategoryID, CategoryName FROM Categories";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int categoryId = resultSet.getInt("CategoryID");
                String categoryName = resultSet.getString("CategoryName");

                System.out.println("Category ID: " + categoryId);
                System.out.println("Category Name: " + categoryName);
                System.out.println("------------------");
            }
        }
    }

    private static void searchCategory(BasicDataSource dataSource) throws SQLException {
        System.out.println("Enter categoryID to search: ");
        int id = scanner.nextInt();
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                "FROM Products WHERE CategoryID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int productId = resultSet.getInt("ProductID");
                    String productName = resultSet.getString("ProductName");
                    double price = resultSet.getDouble("UnitPrice");
                    int unitsInStock = resultSet.getInt("UnitsInStock");

                    System.out.println("Product ID: " + productId);
                    System.out.println("Product Name: " + productName);
                    System.out.println("Unit Price: " + price);
                    System.out.println("Units In Stock: " + unitsInStock);
                    System.out.println("------------------");
                }
            }
        }
    }
}