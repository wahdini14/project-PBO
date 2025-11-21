package wahdini.getajobcopy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wahdini.getajobcopy.model.User;
import wahdini.getajobcopy.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getLoggedUser(String username) {
        return userRepository.findByUsername(username);
    }
}
