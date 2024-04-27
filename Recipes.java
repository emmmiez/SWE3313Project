import java.sql.*;
import java.util.Scanner;

public class Recipes
{
    static String url = "jdbc:sqlserver://pantrypickersshop.database.windows.net:1433;database=Shop";
    static String username = "pantrypickers01";
    static String password = "Pantrypickersswe01";
    static Scanner scanner = new Scanner(System.in);
    static boolean looked = false;
    static String userInput = "";

    public static void main(String[] args)
    {
        userInput = "";
        looked = false;

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            while(true)
            {
                ListRecipes();

                System.out.println("\nEnter name of recipe you'd like to look at");
                userInput = scanner.nextLine();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM Recipes");

                while(resultSet.next())
                {
                    if(userInput.equals(resultSet.getString("name")))
                    {
                        GetRecipeDetails();
                        looked = true;
                        break;
                    }
                }

                if(looked == false && resultSet.getString("name").equals(userInput) == false)
                {
                    System.out.println("Recipe you entered is misspelled or does not exist, please enter recipe again");
                }

                if(looked == true)
                {
                    System.out.println("Is there another recipe you'd like to look at? (y/n)");
                    userInput = scanner.nextLine();

                    if (userInput.equalsIgnoreCase("y") || userInput.equals("yes"))
                    {
                        looked = false;
                    }
                    else if (userInput.equalsIgnoreCase("n") || userInput.equals("no"))
                    {
                        System.out.println("Hope you found what you are ready to cook next!");
                        break;
                    }
                }
            }
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

    public static void ListRecipes()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * From Recipes");

            while(resultSet.next())
            {
                System.out.println(resultSet.getString("Name"));
            }
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

    public static void GetRecipeDetails()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Description, PrepTime FROM Recipes WHERE Name = '" + userInput +"'");

            if (resultSet.next()) {
                System.out.println("\nDescription: \n" + resultSet.getString("Description"));
                System.out.println("PrepTime: \n" + resultSet.getString("PrepTime") + "\n");
            } else {
                System.out.println("Recipe not found.");
            }
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
