package vitaliiev.bookstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue
    private UUID uuid;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors;
}
