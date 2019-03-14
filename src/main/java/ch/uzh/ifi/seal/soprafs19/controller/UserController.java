package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.error.UserNonexistentException;
import ch.uzh.ifi.seal.soprafs19.error.UsernameTakenException;

import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService service;
    private final UserRepository userRepository;

    @Autowired
    UserController(UserService service,UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    Iterable<User> all() {
        return service.getUsers();
    }

    @GetMapping("/users/{userId}")
    User getUser(@PathVariable("userId")Long id) {
        return this.userRepository.findById(id).orElseThrow(UserNonexistentException::new);
    }

    @PostMapping("/users")
    ResponseEntity<User> createUser(@RequestBody User newUser){
        System.out.println("\n\n\n service.getUsers(): "+service.getUsers()+"\n\n\n");
        this.service.createUser(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(HttpStatus.NO_CONTENT) // is only used when completed successfully
    @PutMapping("/users/{userId}")
    void updateUser (@PathVariable("userId")Long id, @RequestBody User upUser){
        System.out.println("\nupUser token: "+upUser.getToken());
        this.service.updateUser(upUser,id);
    }
}
