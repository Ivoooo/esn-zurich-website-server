package esn.controller;

import esn.entity.User;
import esn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserController {
    private final UserService service;
    private String constUsers = "users";

    @Autowired
    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    Resources<Resource<User>> getAllUsers() {

        List<Resource<User>> users = StreamSupport.stream(this.service.getUsers().spliterator(), false)
                .map(user -> new Resource<>(user,
                        linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).getAllUsers()).withRel(constUsers)))
                .collect(Collectors.toList());

        return new Resources<>(users,
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    Resource<User> createUser(@RequestBody User newUser) {
        if (this.service.hasNickname(newUser.getNickname())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"User already exists.");
        }

        User user = this.service.createUser(newUser);

        return new Resource<>(user,
                linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel(constUsers));
    }

    @GetMapping("/users/{id}")
    Resource<User> getUser(@PathVariable Long id) {

        Optional<User> user = this.service.getUser(id);
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find user [getOneUser()]");
        }
        return new Resource<>(user.get(),
                linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel(constUsers));
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    User updateUser(@PathVariable Long id, @RequestBody User changedUser) {
        Optional<User> currentUser = this.service.getUser(id);
        if(!changedUser.getId().equals(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID in url and user don't match [updateUser()]");
        }
        if(currentUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find user [updateUser()] with ID" + id);
        }
        if (! currentUser.get().getToken().equals(changedUser.getToken())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only edit your own profile [updateUser()]");
        }

        if (this.service.hasNickname(changedUser.getNickname())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This nickname is already taken [updateUser()]");
        }

        return this.service.updateUser(currentUser.get(), changedUser);
    }
}
