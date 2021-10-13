package web.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import web.model.User;
import java.util.Optional;
public interface UserRepo extends JpaRepository<User, Long> {
    User findByLogin(String login);
    Optional<User> findById(Long id);
}
