package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.error.UserNonexistentException;
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
        return userRepository.findById(id).orElseThrow(UserNonexistentException::new);
    }

    @PostMapping("/users")
    ResponseEntity<User> createUser(@RequestBody User newUser){
        System.out.println("\n\n\n service.getUsers(): "+service.getUsers()+"\n\n\n");
        this.service.createUser(newUser);
        return new ResponseEntity<User>(newUser, HttpStatus.OK);


    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/users/{userId}")
    void updateUser (@PathVariable("userId")Long id, @RequestBody User upUser){
        this.service.updateUser(upUser,id);
    }
//    @PostMapping("/users")
//    User createUser(@RequestBody User newUser) {
//        System.out.println("\n\n\n service.getUsers(): ");
//        System.out.println(service.getUsers());
//        System.out.println("\n\n\n");
//        Iterable<User> userIterable = service.getUsers();
////        for(User u: userIterable){
////            if (u.getUsername() == newUser.getUsername()){
////                throw new HttpClientErrorException(HttpStatus.CONFLICT, "username already taken");
////            }
////        }
//
//        System.out.println("********the newUser has this username: "+newUser.getUsername());
//
//        return this.service.createUser(newUser);
//    }


//    @PostMapping("/users")
//    public ResponseEntity createUsr(@RequestBody User newUser){
//        System.out.println("\n\n\n service.getUsers(): ");
//        System.out.println(service.getUsers());
//        System.out.println("\n\n\n");
//        Iterable<User> userIterable = service.getUsers();
//        for(User u: userIterable){
//            if (u.getUsername() == newUser.getUsername()){
//                return new ResponseEntity(HttpStatus.CONFLICT);
//            }
//        }
//        return this.service.createUser(newUser);



}
