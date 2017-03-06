package com.shenzhentagram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users/{user_id}")
    public User getUser(@PathVariable("user_id") int id) {
        return this.userRepository.findById((long) id);
    }

    @RequestMapping("/users")
    public List<User> getUsers(
            @RequestParam(value="page", defaultValue="1") int page,
            @RequestParam(value="item_per_page", defaultValue="10") int itemPerPage) {
        return this.userRepository.findAll(page, itemPerPage);
    }

    @RequestMapping("/users/create")
    public void saveUser(@RequestParam(value="id") int id,
                         @RequestParam(value="firstname") String firstname,
                         @RequestParam(value="lastname") String lastname){
        User user = new User(id);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        this.userRepository.save(user);
    }
}