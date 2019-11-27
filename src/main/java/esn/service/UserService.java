package esn.service;

import esn.entity.User;
import esn.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {return this.userRepository.findById(id);}

    public Optional<User> getUserByNickname(String user) {return this.userRepository.findByNickname(user);}

    public boolean hasNickname(String nickname) {return this.userRepository.findByNickname(nickname).isPresent();}

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setCreationDate(LocalDateTime.now());
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public void authenticate(Long id, String token){
        Optional<User> user = this.userRepository.findById(id);
        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with given id not found!");
        }

        if(!user.get().getToken().equals(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized.FindById doesn't match token. [authenticate()]");
        }
    }

    public User updateUser(User user, User changedUser) {
        if (changedUser.getNickname() != null && !changedUser.getNickname().isEmpty()) {
            user.setNickname(changedUser.getNickname());
        }

        if (changedUser.getBirthdayDate() != null && !changedUser.getBirthdayDate().toString().isEmpty()) {
            user.setBirthdayDate(changedUser.getBirthdayDate());
        }

        if (changedUser.getFirstName() != null && !changedUser.getFirstName().isEmpty()) {
            user.setFirstName(changedUser.getFirstName());
        }

        if (changedUser.getPassword() != null && !changedUser.getPassword().isEmpty()) {
            user.setPassword(changedUser.getPassword());
        }

        userRepository.save(user);
        return user;
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }

}
