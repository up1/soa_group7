package demo;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.CrossOrigin;
=======
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> 8578cef19c3e780a4649f761738c4ccf70932a9d
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/{userid}")
    public User getUser(@PathVariable("userid") int id) {
        return this.userRepository.findById((long) id);
    }

    @RequestMapping("/users")
    public List<User> getUsers(
            @RequestParam(value="page", defaultValue="1") int page,
            @RequestParam(value="item_per_page", defaultValue="10") int itemPerPage) {
        return this.userRepository.findAll(page, itemPerPage);
    }
}
