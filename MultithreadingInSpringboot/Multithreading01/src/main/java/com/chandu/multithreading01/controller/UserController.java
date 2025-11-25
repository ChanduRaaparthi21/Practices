package com.chandu.multithreading01.controller;

import com.chandu.multithreading01.entity.User;
import com.chandu.multithreading01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/users" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {"application/json"})
    public ResponseEntity saveuser(@RequestParam(value = "files") MultipartFile[] file) {


        for (MultipartFile multipartFile : file) {
            try {
                userService.saveUser(multipartFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "users", produces = {"application/json"})
    public CompletableFuture<ResponseEntity> getAllUsers() {
        return userService.getAllUsers().thenApply(ResponseEntity::ok);
    }

    @GetMapping(value = "usersByThread", produces = {"application/json"})
    public ResponseEntity getUsers(){
        CompletableFuture<List<User>> users1 = userService.getAllUsers();
        CompletableFuture<List<User>> users2 = userService.getAllUsers();
        CompletableFuture<List<User>> users3 = userService.getAllUsers();
        CompletableFuture.allOf(users1, users2, users3).join();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
