import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Arsen2005";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Drive";

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from driver");

        while (result.next()) {
            System.out.println(result.getInt("id") + " " + result.getString("name"));
        }
        String sqlInsertUser = "insert into driver(name, surname, age)" +
                "values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);

        int total = 0;
        for (int i = 1; i <= 6; i++) {
            String firstName = in.nextLine();
            String secondName = in.nextLine();
            int age = in.nextInt();

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, secondName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
            total++;
        }
        System.out.println("Было добавлено " + total + " строк");

        System.out.println("Водители старше 18 лет и с фамилией:");
        String sqlSelectDrivers = "SELECT * FROM driver WHERE age >= 18";

        ResultSet filteredResult = statement.executeQuery(sqlSelectDrivers);
        while (filteredResult.next()) {
            System.out.println(filteredResult.getInt("id")
                    + " " + filteredResult.getString("name")
                    + " " + filteredResult.getString("surname")
                    + " " + filteredResult.getInt("age"));
        }
    }
}
