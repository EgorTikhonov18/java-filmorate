package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@NonNull
@Valid
@javax.validation.constraints.NotEmpty
@NotBlank
public class User {
    private int id;
    @Email
    @NotEmpty
    private String email;
    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^\\S*$")
    private String login;
    private String name;
    @PastOrPresent
    @NonNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;
    @JsonIgnore
    Set<Integer> friends;

}
