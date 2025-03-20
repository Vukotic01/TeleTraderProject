package com.example.projectteletrader.Service;


import com.example.projectteletrader.DTO.UserDTO;
import com.example.projectteletrader.Exceptions.UserException;
import com.example.projectteletrader.Model.UserInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface UserService {

    @Async
    CompletableFuture<UserInfo> registerUser(UserDTO user) throws UserException;


    @Async
    CompletableFuture<UserInfo> findUserByEmail(String email) throws UserException;
}