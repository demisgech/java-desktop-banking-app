package com.codewithdemis.models;

public record Transaction(
        int id,
        int accountId,
        String type, // "DEPOSIT","WITHDRAW" , TRANSFER
        double amount,
        String description
) {
}


