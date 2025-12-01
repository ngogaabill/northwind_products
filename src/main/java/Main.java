
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
                    query = "SELECT * FROM customers WHERE country is not NUll ORDER BY country  ";
                    displayResults(query, choice);
                    break;
                case 3:
                    query = "SELECT * FROM categories";
                    displayResults(query, choice);
                    searchCategory();
                    break;
                case 0:
                    exit();
                    running = false;
                    return;
            }
        }

    }

    private static void searchCategory() throws SQLException {
        System.out.println("Enter categoryID to search: ");
        int id = scanner.nextInt();
        String query = "SELECT ProductID,ProductName,UnitPrice,UnitsInStock FROM Products WHERE CategoryID = " + id;
        displayResults(query, 4);
    }

    private static void exit() {
        System.out.println("Good bye");
    }

    private static int homeScreen() {
        System.out.println("""
                What do you want to do?
                1) Display all products
                2) Display all customers
                3) Display all categories
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

                    //String id = resultSet.getString("CustomerID");
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
                } else if (choice == 3) {
                    int categoryId = resultSet.getInt("CategoryID");
                    String categoryName = resultSet.getString("CategoryName");

                    System.out.println("Category ID: " + categoryId);
                    System.out.println("Category Name: " + categoryName);
                    System.out.println("------------------");
                } else if (choice == 4) {
                    int productId = resultSet.getInt("ProductID");
                    String productName = resultSet.getString("ProductName");
                    Double price = resultSet.getDouble("UnitPrice");
                    int unitsInStock = resultSet.getInt("UnitsInStock");

                    System.out.println("Product ID: " + productId);
                    System.out.println("Product Name: " + productName);
                    System.out.println("Unit Price: " + price);
                    System.out.println("Units In Stock: " + unitsInStock);
                    System.out.println("------------------");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



