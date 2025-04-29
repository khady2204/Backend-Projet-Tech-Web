package ProjetTechWebBackend.spring.TechWeb.Service;

import ProjetTechWebBackend.spring.TechWeb.Entity.Role;
import ProjetTechWebBackend.spring.TechWeb.Entity.User;
import ProjetTechWebBackend.spring.TechWeb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        user.setRole(Role.ETUDIANT); // ou Role.ADMIN selon le besoin

        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return userRepository.save(user);
    }

    public User createAdmin() {
        List<User> adminAccount = userRepository.findByRole(Role.ADMIN);
        if (!adminAccount.isEmpty()) {
            return adminAccount.get(0); // Admin déjà existant
        }
        User admin = new User();
        admin.setUserPrenom("Admin");
        admin.setUserNom("Admin");
        admin.setUserEmail("admin@admin.com");
        admin.setUserPassword(getEncodedPassword("admin123"));
        admin.setRole(Role.ADMIN);
        return userRepository.save(admin);

    }

    // Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Récupérer un utilisateur par son ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null); // Renvoie null si l'utilisateur n'est pas trouvé
    }

    // Supprimer un utilisateur par ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Récupère tous les utilisateurs ayant un rôle spécifique
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }



    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }


    public List<User> getEtudiants() {
        return userRepository.findByRole(Role.ETUDIANT);
    }

    public User updateStudent(Long id, User updatedUser) {
        // Cherche l'étudiant par son ID
        User existingUser = userRepository.findByIdAndRole(id, Role.ETUDIANT).orElse(null);

        if (existingUser == null) {
            return null; // L'étudiant avec ce rôle n'existe pas
        }

        // Mise à jour des informations de l'étudiant
        existingUser.setUserPrenom(updatedUser.getUserPrenom());
        existingUser.setUserNom(updatedUser.getUserNom());
        existingUser.setUserEmail(updatedUser.getUserEmail());
        existingUser.setUserPassword(updatedUser.getUserPassword() != null ? getEncodedPassword(updatedUser.getUserPassword()) : existingUser.getUserPassword());

        return userRepository.save(existingUser);
    }

    public User findByEmail(String email) {
        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));
    }

}
