import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    String name;
    Long id;
    String description;
    LocalDate releaseDate;
    Duration duration;

}
