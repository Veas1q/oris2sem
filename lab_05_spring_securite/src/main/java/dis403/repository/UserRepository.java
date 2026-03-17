package dis403.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dis403.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);
}