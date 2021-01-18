package ru.grishchenko.mymarket.exception_handling;

import lombok.Data;

import java.util.Date;

@Data
public class MyMarketError {

    private int status;
    private String message;
    private Date timestamp;

    public MyMarketError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
