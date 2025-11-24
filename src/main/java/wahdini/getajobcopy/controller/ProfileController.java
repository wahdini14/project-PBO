package wahdini.getajobcopy.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wahdini.getajobcopy.model.User;
import wahdini.getajobcopy.repository.UserRepository;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/";

        // Ambil user dari database
        User me = userRepository.findByUsername(username);

        model.addAttribute("user", me);
        model.addAttribute("isOwner", true);

        return "profile";
    }

    // View profile lain berdasarkan id (dipakai untuk melihat pelamar)
    @GetMapping("/profile/{id}")
    public String profileById(@PathVariable Long id, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/";

        java.util.Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) return "redirect:/";

        User user = userOpt.get();
        model.addAttribute("user", user);

        boolean isOwner = username != null && user.getUsername() != null && user.getUsername().equals(username);
        model.addAttribute("isOwner", isOwner);
        return "profile";
    }
}
