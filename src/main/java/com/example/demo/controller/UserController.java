package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;

import com.example.demo.models.UserModel;
import com.example.demo.responses.ErrorResponse;
import com.example.demo.responses.OneUserResponse;
import com.example.demo.responses.SomeUsersResponse;
import com.example.demo.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
public class UserController {
 
    @Autowired
    UserService userService;

    @ApiOperation(position = 0, value = "getUsers", notes = "Get all users")
    @GetMapping(value="/getusers")
    public ResponseEntity<Object> getUser(){
        SomeUsersResponse response = new SomeUsersResponse();
        try{
            response.description="OK";
            response.schema=userService.getAllUsers();
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception ex){
            response.description=ex.getMessage();
            response.schema=new ArrayList<UserModel>();
            return  new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @ApiOperation(position = 1, value = "createUsers", notes = "Create user")
    @PostMapping(value="/createUsers")
    public ResponseEntity<Object> createUser(@Valid @RequestBody(required = false) UserModel user){
        OneUserResponse response = new OneUserResponse();
        response.description = "CREATED";
        response.schema=userService.saveUser(user);
        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ApiOperation(position = 2, value = "getusersById", notes = "Get one user")
    @GetMapping(value="/getusersById/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable(required = true, value="userId") Integer userId){
        OneUserResponse response = new OneUserResponse();
        response.description="OK";
        Optional<UserModel> existUser=userService.getUserById(userId);
        if(existUser.isPresent())
        {
            response.schema=existUser.get();
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.description="User not found";
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(position = 3, value = "updateUsersById", notes = "Update user")
    @PutMapping(value="/updateUsersById/{userId}")
    public ResponseEntity<Object> updateUser(
                                            @PathVariable(required = true, value="userId") Integer userId,
                                            @Valid @RequestBody(required = false) UserModel user){
        Optional<UserModel> existUser=userService.getUserById(userId);
        OneUserResponse response = new OneUserResponse();
        if(!existUser.isPresent())
        {
            response.description="User not found";
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        user.setId(userId);
        response.description = "OK";
        response.schema=userService.saveUser(user);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(position = 4, value = "deleteUsersById", notes = "Delete one user")
    @DeleteMapping(value="/deleteUsersById/{userId}")
    public ResponseEntity<Object> deleteUsersById(@PathVariable(required = true, value="userId") Integer userId){
        OneUserResponse response = new OneUserResponse();
        response.description="OK";
        Optional<UserModel> existUser=userService.getUserById(userId);
        response.schema=null;
        if(existUser.isPresent())
        {
            userService.deleteUserById(userId);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.description="User not found";
            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //Exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleConstraintViolation(MethodArgumentNotValidException ex, WebRequest request) {
        ErrorResponse response=new ErrorResponse();
        ArrayList<String> errors = new ArrayList<String>();
        ((MethodArgumentNotValidException)ex).getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        response.errors = errors;
        response.description = "Invalid input";
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);    
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException ex, WebRequest request) {
        //return new ResponseEntity<>(ex.getMessage()+","+request.getContextPath(), HttpStatus.BAD_REQUEST);
        ErrorResponse response=new ErrorResponse();
        Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException)ex).getConstraintViolations();
        response.errors.addAll(constraintViolations.stream()
                .map(constraintViolation -> String.format("%s value '%s' %s", constraintViolation.getPropertyPath(),
                        constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
                .collect(Collectors.toList()));

        response.description = "Invalid input";
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<Object> handleException(IllegalStateException ex, WebRequest request) {
        ErrorResponse response=new ErrorResponse();
        response.errors = null;
        response.description = "Invalid user id";
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<Object> handleException(NumberFormatException ex, WebRequest request) {
        ErrorResponse response=new ErrorResponse();
        response.errors = null;
        response.description = "Invalid user id";
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleException(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse response=new ErrorResponse();
        response.errors = null;
        response.description = "Invalid input";
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<Object> handleException(MissingServletRequestParameterException ex, WebRequest request) {
        ErrorResponse response=new ErrorResponse();
        response.errors = null;
        response.description = "Invalid user id";
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleException(NullPointerException ex, WebRequest request) {
        ErrorResponse response=new ErrorResponse();
        response.errors = null;
        response.description = "Invalid user id";
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    

    // public void initiateAppShutdown(int returnCode){
    //     SpringApplication.exit(context, () -> returnCode);
    // }
 
    // @RequestMapping("/shutdown")
    // public void shutdown() {
    //     initiateAppShutdown(0);
    // }
}
