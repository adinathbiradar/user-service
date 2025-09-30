package in.main.service;

import in.main.model.User;
import in.main.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(String id) {
        return repository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    public User login(String email, String password) {
        User user = repository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user; // basic login check
        }
        return null;
    }
}
