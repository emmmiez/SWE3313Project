import java.sql.*;
import java.util.Scanner;

public class DatabaseTest
{
    public static void main(String[] args)
    {
        String url = "jdbc:sqlserver://pantrypickersshop.database.windows.net:1433;database=Shop";
        String username = "pantrypickers01";
        String password = "Pantrypickersswe01";

        Scanner scanner = new Scanner(System.in);
        String emailInput = "";
        String nameInput = "";
        String passwordInput = "";

        System.out.println("Connecting database...");

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            //use this to test and see if you connect to the database
            //System.out.println("Database connected!");

            //user input to test if you can add to the database
            System.out.println("Enter email, username, and password!");
            emailInput = scanner.nextLine();
            nameInput = scanner.nextLine();
            passwordInput = scanner.nextLine();

            //to add to the database specifically Users table
            statement.executeUpdate("INSERT into Users (username, email, password)"
                + " values('" + nameInput + "', '" + emailInput + "', '" + passwordInput + "')");

            //to print everything in a column in the Users table
            //ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");

            statement.close();
            connection.close();

            //this is to print out the columns in the database this one specifically for Users
            /*while(resultSet.next())
            {
                System.out.println(resultSet.getString("UserName"));
            }*/

        }
        catch(ClassNotFoundException e)
        {
            throw new IllegalStateException("Could not find JDBC driver", e);
        }
        catch(SQLException e)
        {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}