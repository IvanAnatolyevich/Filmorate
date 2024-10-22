import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private String name;
    private Long id;
    private String email;
    private String login;
    private LocalDate birthday;

}