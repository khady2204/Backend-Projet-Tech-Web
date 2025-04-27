package ProjetTechWebBackend.spring.TechWeb.Repository;

import ProjetTechWebBackend.spring.TechWeb.Entity.Role;
import ProjetTechWebBackend.spring.TechWeb.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Méthode personnalisée pour récupérer les utilisateurs par rôle
    List<User> findByRole(Role role);
    // Cette méthode permet de chercher un utilisateur par son email
    Optional<User> findByUserEmail(String email);

    Optional<User> findByIdAndRole(Long id, Role role);
}
