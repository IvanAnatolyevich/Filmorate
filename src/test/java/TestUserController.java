//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//
//public class TestUserController {
//    UserController userController;
//    User user;
//    @BeforeEach
//    public void init() {
//        userController = new UserController();
//        user = new User();
//    }
//    @Test
//    void validLogin() {
//        user.setName("Иван");
//        user.setBirthday(LocalDate.of(2004,4,7));
//        user.setEmail("sid.i744@yandex.ru");
//        Assertions.assertThrows(ValidationException.class, () -> userController.createUser(user));
//        user.setLogin(" ");
//        Assertions.assertThrows(ValidationException.class, () -> userController.createUser(user));
//    }
//    @Test
//    void validBirthday() {
//        user.setName("Иван");
//        user.setBirthday(LocalDate.of(2030,4,7));
//        user.setEmail("sid.i744@yandex.ru");
//        user.setLogin("ivanLead");
//        Assertions.assertThrows(ValidationException.class, () -> userController.createUser(user));
//    }
//    @Test
//    void validEmail() {
//        user.setName("Иван");
//        user.setBirthday(LocalDate.of(2004,4,7));
//        user.setEmail("sid.i744yandex.ru");
//        user.setLogin("ivanLead");
//        Assertions.assertThrows(ValidationException.class, () -> userController.createUser(user));
//    }
//    @Test
//    void validName() {
//        user.setBirthday(LocalDate.of(2004,4,7));
//        user.setEmail("sid.i744@yandex.ru");
//        user.setLogin("ivanLead");
//        userController.createUser(user);
//        Assertions.assertEquals("ivanLead", user.getName());
//    }
//}
