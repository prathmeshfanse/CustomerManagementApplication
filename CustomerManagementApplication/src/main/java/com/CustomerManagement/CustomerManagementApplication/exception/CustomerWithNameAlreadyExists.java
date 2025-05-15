package com.CustomerManagement.CustomerManagementApplication.exception;

public class CustomerWithNameAlreadyExists extends RuntimeException{

    public CustomerWithNameAlreadyExists(String message){
        super(message);
    }
}
