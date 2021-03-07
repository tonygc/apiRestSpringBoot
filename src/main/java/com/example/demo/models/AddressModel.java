package com.example.demo.models;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="address")
public class AddressModel implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;
    @NotNull
    @Column(nullable = false)
    private String street;
    @NotNull
    @Column(nullable = false)
    private String state;
    @NotNull
    @Column(nullable = false)
    private String city;
    @NotNull
    @Column(nullable = false)
    private String country;
    @NotNull
    @Column(nullable = false)
    private String zip;

    public void setId(int _id){
        this.id=_id;
    }
    public int getId(){
        return this.id;
    }

    public void setStreet(String _street){
        this.street=_street;
    }
    public String getStreet(){
        return this.street;
    }

    public void setState(String _state){
        this.state=_state;
    }
    public String getState(){
        return this.state;
    }

    public void setCity(String _city){
        this.city=_city;
    }
    public String getCity(){
        return this.city;
    }

    public void setCountry(String _country){
        this.country=_country;
    }
    public String getCountry(){
        return this.country;
    }

    public void setZip(String _zip){
        this.zip=_zip;
    }
    public String getZip(){
        return this.zip;
    }

    public AddressModel(){

    }

    public AddressModel(String street, String state, String city, String country, String zip){
        this.street=street;
        this.state=state;
        this.city=city;
        this.country=country;
        this.zip=zip;
    }
}