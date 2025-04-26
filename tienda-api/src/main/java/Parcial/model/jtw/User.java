package Parcial.model.jtw;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    Long id;

    @Basic
    @Column(unique = true)
    String username;
    String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token> tokens;

}