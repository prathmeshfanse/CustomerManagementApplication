package com.CustomerManagement.CustomerManagementApplication.exception;

public class CustomerWithEmailAlreadyExists extends RuntimeException{

    public CustomerWithEmailAlreadyExists(String message){
        super(message);
    }
}
