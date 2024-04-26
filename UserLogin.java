import java.sql.*;
import java.util.Scanner;

public class UserLogin
{
    static String url = "jdbc:sqlserver://pantrypickersshop.database.windows.net:1433;database=Shop";
    static String username = "pantrypickers01";
    static String password = "Pantrypickersswe01";
    static boolean login = false;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        login = false;

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            //this will continue to loop until login becomes true when the user inputs the correct password and username that's in the database
            do
            {
                //just in case something else is inputted
                while (true)
                {
                    System.out.println("Are you an already existing user? (y/n)");
                    userInput = scanner.nextLine();

                    if (userInput.equalsIgnoreCase("y") || userInput.equals("yes") || userInput.equalsIgnoreCase("n") || userInput.equals("no"))
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("Please enter a valid option");
                    }
                }

                if (userInput.equalsIgnoreCase("y") || userInput.equals("yes"))
                {
                    Login();
                }
                else if (userInput.equalsIgnoreCase("n") || userInput.equals("no"))
                {
                    CreateUser();
                }
            }
            while(login != true);

            statement.close();
            connection.close();
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

    public static void CreateUser()
    {
        String emailInput = "";
        String nameInput = "";
        String passwordInput = "";
        Scanner scanner = new Scanner(System.in);

        System.out.println("To create an account please enter your email and then create a username and password"
            + "\n\nEmail: ");
        emailInput = scanner.nextLine();
        System.out.println("\nUsername: ");
        nameInput = scanner.nextLine();
        System.out.println("\nPassword: ");
        passwordInput = scanner.nextLine();

        //this will go into the database and update it to add a new email, username, and password
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            statement.executeUpdate("INSERT into Users (username, email, password)"
                    + " values('" + nameInput + "', '" + emailInput + "', '" + passwordInput + "')");
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

    public static void Login()
    {
        String userInput = "";
        String nameInput = "";
        String passwordInput = "";
        Scanner scanner = new Scanner(System.in);

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();

            while(true)
            {
                System.out.println("To login please enter Username and Password"
                        + "\n\nUsername: ");
                nameInput = scanner.nextLine();
                System.out.println("\nPassword: ");
                passwordInput = scanner.nextLine();

                //this will go through everything that's in the Users table
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");

                //this will go through the entire table and if what the user put in as their login matches with
                //what's in the database then it will set login as true and go back to the main method ending the class
                while (resultSet.next())
                {
                    if (resultSet.getString("Username").equals(nameInput) && resultSet.getString("Password").equals(passwordInput)) {
                        System.out.println("You are logged in!");
                        login = true;
                        break;
                    }
                }

                //this is so that it doesn't continue to the next few lines
                if (login == true)
                {
                    break;
                }

                //in case the user didn't make an account or can't remember their login then they can create one
                //could possibly have it where they can change their password instead, and it alters the table
                System.out.println("username or password is incorrect! Do you need to create a new account? (y/n)");
                userInput = scanner.nextLine();

                if (userInput.equalsIgnoreCase("y") || userInput.equals("yes"))
                {
                    CreateUser();
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
}