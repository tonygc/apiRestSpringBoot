package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.models.UserModel;
import com.example.demo.respositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ArrayList<UserModel> getAllUsers(){
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public UserModel saveUser(UserModel entity){
        return (UserModel) userRepository.save(entity);
    }

    public Optional<UserModel> getUserById(int id){
        return (Optional<UserModel>) userRepository.findById(id);
    }

    public boolean deleteUserById(int id){
        try{
            userRepository.deleteById(id);
            return true;
        }catch(Exception ex){
            return false;
        }
        
    }
}
