package com.example.demo.responses;

import java.util.ArrayList;

public class ErrorResponse {
    public String description;
    public ArrayList<String> errors;

    public ErrorResponse(){
        errors=new ArrayList<String>();
    }
}
