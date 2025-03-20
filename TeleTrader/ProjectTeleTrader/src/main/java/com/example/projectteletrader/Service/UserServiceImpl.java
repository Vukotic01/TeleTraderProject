package com.example.projectteletrader.Service;
import com.example.projectteletrader.DTO.UserDTO;
import com.example.projectteletrader.Exceptions.UserException;
import com.example.projectteletrader.Model.UserInfo;
import com.example.projectteletrader.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Async
    public CompletableFuture<UserInfo> registerUser(UserDTO user) throws UserException {
        return CompletableFuture.supplyAsync(() -> {
            UserInfo findUser = userRepository.findByEmail(user.getEmail());

            if (findUser != null) {
                try {
                    throw new UserException("User already exists with this email: " + user.getEmail());
                } catch (UserException e) {
                    throw new RuntimeException(e);
                }
            }

            UserInfo newUser = new UserInfo();
            newUser.setEmail(user.getEmail());
            newUser.setFullName(user.getFullName());
            newUser.setPassword(user.getPassword());
            newUser.setRole(user.getRole());

            return userRepository.save(newUser);
        });
    }



    @Override
    @Async
    public CompletableFuture<UserInfo> findUserByEmail(String email) throws UserException {
        return CompletableFuture.supplyAsync(() -> {
            UserInfo user = userRepository.findByEmail(email);
            if (user == null) {
                try {
                    throw new UserException("No user found with this email: " + email);
                } catch (UserException e) {
                    throw new RuntimeException(e);
                }
            }
            return user;
        });
    }
}