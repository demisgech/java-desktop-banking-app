package com.codewithdemis.models;

public record Account(int id,
                      int userId,
                      String accountNumber,
                      double balance) {
}
