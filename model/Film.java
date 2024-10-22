import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private String name;
    private Long id;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;

}
