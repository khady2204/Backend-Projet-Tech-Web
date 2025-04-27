package ProjetTechWebBackend.spring.TechWeb.Controller;

import ProjetTechWebBackend.spring.TechWeb.Entity.Role;
import ProjetTechWebBackend.spring.TechWeb.Entity.User;
import ProjetTechWebBackend.spring.TechWeb.Repository.UserRepository;
import ProjetTechWebBackend.spring.TechWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/users")
public class UserController {

    final UserRepository userRepository;

    @Autowired
    private UserService userService;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/etudiants")
    public ResponseEntity<List<User>> getAllEtudiants() {
        List<User> etudiants = userService.getEtudiants();
        return ResponseEntity.ok(etudiants);
    }

    // Créer un nouvel étudiant
    @PostMapping("/etudiant")
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    // Créer un admin (optionnel)
    @PostMapping("/admin")
    public User createAdmin() {
        return userService.createAdmin();
    }

    // Récupérer un utilisateur par son ID
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userRepository.findAll();       // ou userRepository.findAll()
        return ResponseEntity.ok(users);
    }

    // Supprimer un utilisateur par ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/etudiants/{id}")
    public ResponseEntity<User> updateStudent(@PathVariable Long id, @RequestBody User updatedUser) {
        User updated = userService.updateStudent(id, updatedUser);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // L'étudiant n'a pas été trouvé
        }
        return ResponseEntity.ok(updated); // L'étudiant a été mis à jour
    }

    @GetMapping("/etudiants/{id}")
    public ResponseEntity<?> getEtudiantById(@PathVariable Long id) {
        Optional<User> etudiant = userRepository.findById(id);
        if (etudiant.isPresent()) {
            return ResponseEntity.ok(etudiant.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Étudiant non trouvé");
        }
    }

    @DeleteMapping("/etudiants/{id}")
    public ResponseEntity<String> deleteEtudiant(@PathVariable Long id) {
        Optional<User> etudiant = userRepository.findById(id);
        if (etudiant.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.ok("Étudiant supprimé avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Étudiant non trouvé.");
        }
    }

}