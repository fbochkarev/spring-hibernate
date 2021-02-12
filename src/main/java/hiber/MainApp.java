package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);


        UserService userService = context.getBean(UserService.class);
//      User user1 = new User("User1", "Lastname1", "user1@mail.ru");
//      Car car1 = new Car("bmw", 525);
//      user1.setCar(car1);
//
//      userService.add(user1);
//      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
//      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
//      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Model car = " + user.getCar().getModel());
            System.out.println("Series car = " + user.getCar().getSeries());
            System.out.println();
        }

        System.out.println(userService.getUserByModelAndSeriesCar("bmw", 525));

        context.close();
    }

}
