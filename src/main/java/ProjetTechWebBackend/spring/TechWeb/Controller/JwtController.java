package ProjetTechWebBackend.spring.TechWeb.Controller;

import ProjetTechWebBackend.spring.TechWeb.Entity.JwtRequest;
import ProjetTechWebBackend.spring.TechWeb.Entity.JwtResponse;
import ProjetTechWebBackend.spring.TechWeb.Entity.User;
import ProjetTechWebBackend.spring.TechWeb.Service.JwtService;
import ProjetTechWebBackend.spring.TechWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // ✅ pour que Spring gère ce contrôleur comme un REST controller
@RequestMapping("/api/auth")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.registerNewUser(user);
        return ResponseEntity.ok(savedUser);
    }

}
