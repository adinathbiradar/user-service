package in.main.controller;

import in.main.model.User;
import in.main.security.JwtUtil;
import in.main.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService service;
    private final JwtUtil jwtUtil;  // ✅ inject JwtUtil

    // ✅ constructor injection for both service and jwtUtil
    public UserController(UserService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    // GET all users
    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = service.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }


 // POST -> Register User
    @PostMapping("/signup")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        // enforce default role = "user"
        user.setRole("user");
        User savedUser = service.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // PUT -> Update User
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        User existing = service.getUserById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setRole(user.getRole());
        return ResponseEntity.ok(service.saveUser(existing));
    }

    // DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

 // LOGIN -> generate JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = service.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            String token = jwtUtil.generateToken(user.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", user.getRole());
            response.put("name", user.getName());
            response.put("id", user.getId());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

}
