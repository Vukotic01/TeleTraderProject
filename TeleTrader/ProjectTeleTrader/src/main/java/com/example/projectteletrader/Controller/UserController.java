package com.example.projectteletrader.Controller;
import com.example.projectteletrader.DTO.UserDTO;
import com.example.projectteletrader.Exceptions.UserException;
import com.example.projectteletrader.Model.UserInfo;
import com.example.projectteletrader.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/app/sign-up")
    @Async
    public CompletableFuture<ResponseEntity<UserInfo>> signUpUserHandler(@Validated @RequestBody UserDTO user) throws UserException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.registerUser(user)
                .thenApply(registeredUser -> new ResponseEntity<>(registeredUser, HttpStatus.CREATED));
    }


    @GetMapping("/app/sign-in")
    @Async
    public CompletableFuture<ResponseEntity<UserInfo>> signInHandler(Authentication auth) throws BadCredentialsException, UserException, UserException {
        return userService.findUserByEmail(auth.getName())
                .thenApply(user -> {
                    if (user != null) {
                        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
                    } else {
                        throw new BadCredentialsException("Invalid Username or password");
                    }
                });
    }


}