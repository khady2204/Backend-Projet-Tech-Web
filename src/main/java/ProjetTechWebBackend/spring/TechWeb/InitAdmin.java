package ProjetTechWebBackend.spring.TechWeb;

import ProjetTechWebBackend.spring.TechWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitAdmin implements CommandLineRunner {
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userService.createAdmin();
    }
}
