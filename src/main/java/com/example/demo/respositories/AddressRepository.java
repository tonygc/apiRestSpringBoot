package com.example.demo.respositories;

import com.example.demo.models.AddressModel;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<AddressModel, Integer> {
    
}
