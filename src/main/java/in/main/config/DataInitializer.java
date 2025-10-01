package in.main.config;

import in.main.model.User;
import in.main.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        // Check if admin user already exists
        if (userRepository.findByEmail("admin@gmail.com") == null) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword("123456");  // You should ideally hash passwords
            admin.setRole("admin");
            userRepository.save(admin);
            System.out.println("Admin user created!");
        }
    }
}
