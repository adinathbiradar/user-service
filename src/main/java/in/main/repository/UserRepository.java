package in.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import in.main.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email); // for login
}
