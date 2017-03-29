package com.shenzhentagram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenzhentagram.authentication.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET , path = "/users/{user_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public User getUser(
            @PathVariable("user_id") long id
    ) {
        return this.userRepository.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET , path = "/users/search", produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<User> searchUser(
            @RequestParam("name") String name
    ) {
        return this.userRepository.findByName(name);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/users", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void createUser(
            HttpServletResponse response,
            @RequestBody Map<String, Object> payload
    ){


        // Extract the password
        String password = (String) payload.remove("password");

        // Save new user
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(payload, User.class);
        try {
            this.userRepository.save(user, password);
            // Response 201
            response.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }



    }

    @RequestMapping(method = RequestMethod.GET, path = "/users/self", produces = { MediaType.APPLICATION_JSON_VALUE })
    public User getSelf() {
        AuthenticatedUser auth = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();
        return this.userRepository.findById(auth.getId());
    }

    @RequestMapping(method = { RequestMethod.PUT, RequestMethod.PATCH }, path = "/users/self", produces = { MediaType.APPLICATION_JSON_VALUE })
    public void updateSelf() {
        // TODO Implement update self profile
        throw new NotImplementedException();
    }

}
