package com.example.storedemo.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class PurchaseDTO {
    @NotNull
    private Map<Integer, Integer> productCount;
    @NotEmpty
    private String userName;
    private String userSurname;
    @NotEmpty
    private String userEmail;
    @NotEmpty
    private String userPhone;
    @NotEmpty
    private String userAddress;
    private String userComment;
}
