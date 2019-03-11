package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.error.AccessDeniedException;
import ch.uzh.ifi.seal.soprafs19.error.LoginError;
import ch.uzh.ifi.seal.soprafs19.error.RegisterError;
import ch.uzh.ifi.seal.soprafs19.error.UserNonexistentException;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {

        User dbUser = this.userRepository.findByUsername(newUser.getUsername());
        System.out.println("is newUser already stored? "+(dbUser!=null));
        // case: Login
        if (newUser.getName() == null){
            // case: This username does not exist in the database
            if (dbUser == null){
                throw new LoginError("Please register first in order to login");
            }
            // case: The password of the user with this username is not equal to the entered password
            else if (!dbUser.getPassword().equals(newUser.getPassword())) {
                throw new LoginError("Password or Username are incorrect");
            }
            // case: The username exists in the database and password matches
            else {
                newUser.setName(dbUser.getName());
                newUser.setId(dbUser.getId());
                newUser.setCreationDate(dbUser);
                newUser.setBirthDate(dbUser.getBirthDate());
            }
        }
        // case: Registration
        else if (dbUser != null) {
            throw new RegisterError("username already taken");
        }
        // set the creationDate only once
        if (dbUser == null){
            newUser.setCreationDate(dbUser);
        }
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        System.out.println("saving user: "+newUser.getName()+" "+newUser.getUsername()+" "+
                newUser.getPassword()+ " " +newUser.getCreationDate()+ " " + newUser.getBirthDate());
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }


    public void updateUser(User upUser, Long id){
        System.out.println("upUser id: "+upUser.getId());
        User dbUser = userRepository.findById(id).orElseThrow(UserNonexistentException::new);
        if (!dbUser.getToken().equals(upUser.getToken())){
            throw new AccessDeniedException();
        }
        dbUser.setBirthDate(upUser.getBirthDate());
        dbUser.setUsername(upUser.getUsername());
        //dbUser.setPassword(upUser.getPassword());
        //dbUser.setCreationDate(upUser);
        //dbUser.setPassword(upUser.getPassword());

    }



//    public void updateUser(User upUser, Long id){
//        User dbUser = userRepository.findById(id).orElseThrow(UserNonexistentException::new);
//        if (!dbUser.getToken().equals(upUser.getToken())){
//            throw new AccessDeniedException();
//        }
//        dbUser.setName(upUser.getName());
//        userRepository.save(dbUser);
//    }
}
