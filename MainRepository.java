import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainRepository {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Arsen2005";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Drive";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        UserRepository userRepository = new UserRepositoryJDBCImpl(connection);
        System.out.println("Введите число пользователей");
        int count = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < count; i++) {
            System.out.println("Введите данные пользователя:");

            System.out.print("Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Surname: ");
            String secondName = scanner.nextLine();

            System.out.print("Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            System.out.print("Sex: ");
            String sex = scanner.nextLine();

            User user = new User(null, firstName, secondName, age, email, password, sex);

            userRepository.save(user);
            System.out.println("Пользователь успешно добавлен!");

        }

        System.out.println("\nВсе пользователи:");
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            System.out.println(user);
        }

        System.out.println("\nВведите email пользователя для удаления:");
        String emailToDelete = scanner.nextLine().trim();

        User userToDelete = null;
        for (User user : allUsers) {
            if (user.getEmail().trim().equalsIgnoreCase(emailToDelete)) {
                userToDelete = user;
                break;
            }
        }

        if (userToDelete != null) {
            userRepository.delete(userToDelete);
            System.out.println("Пользователь с email " + emailToDelete + " успешно удален.");
        } else {
            System.out.println("Пользователь с таким email не найден.");
        }

        System.out.println("\nПользователи с возрастом 25:");
        List<User> usersByAge = userRepository.findByAge(25);
        for (User user : usersByAge) {
            System.out.println(user);
        }

        System.out.println("\nПользователь с email 'aaa':");
        List<User> usersByEmail = userRepository.findByEmail("aaa");
        for (User user : usersByEmail) {
            System.out.println(user);
        }

        System.out.println("\nПользователи женского пола:");
        List<User> usersByGender = userRepository.findBySex("w");
        for (User user : usersByGender) {
            System.out.println(user);
        }

        System.out.println("\nПользователи с паролем 'qwerty':");
        List<User> usersByPassword = userRepository.findByPassword("qwerty");
        for (User user : usersByPassword) {
            System.out.println(user);
        }

        System.out.println("\nОставшиеся пользователи:");
        List<User> remainingUsers = userRepository.findAll();
        for (User user : remainingUsers) {
            System.out.println(user);
        }

    }
}
