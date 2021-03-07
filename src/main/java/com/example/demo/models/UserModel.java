package com.example.demo.models;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.persistence.*;

@Entity
@Table(name="user")
public class UserModel implements Serializable {
    // /**
    //  *
    //  */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name should have a value")
    @Column(nullable = false)
    private String name;
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email should have a value")
    @Email
    private String email;
    @Pattern(message="Date format must be YYYY-MM-DD", regexp = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")
    @NotNull(message = "BirthDate is required")
    private String birthDate;
    @NotNull(message = "Address is required")
    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "address", nullable = false)
    private AddressModel address;

    public void setId(int _id){
        this.id=_id;
    }
    public int getId(){
        return this.id;
    }

    public void setName(String _name){
        this.name=_name;
    }
    public String getName(){
        return this.name;
    }

    public void setEmail(String _email){
        this.email=_email;
    }
    public String getEmail(){
        return this.email;
    }

    public void setBirthDate(String _birthDate){
        this.birthDate=_birthDate;
    }
    public String getBirthDate(){
        return this.birthDate;
    }

    public void setAddress(AddressModel _address){
        this.address=_address;
    }
    public AddressModel getAddress(){
        return this.address;
    }

    public UserModel(){

    }

    public UserModel(String name, String email, String birthDate){
        this.name=name;
        this.email=email;
        this.birthDate=birthDate;
        
    }
    
}
