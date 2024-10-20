import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    String name;
    Long id;
    String email;
    String login;
    LocalDate birthday;

}