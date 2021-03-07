package com.example.demo.services;

import java.util.ArrayList;

import com.example.demo.models.AddressModel;
import com.example.demo.respositories.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public ArrayList<AddressModel> getAllAddress(){
        return (ArrayList<AddressModel>) addressRepository.findAll();
    }
}
