package dis403.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@Builder
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String password;
    private String role;
}