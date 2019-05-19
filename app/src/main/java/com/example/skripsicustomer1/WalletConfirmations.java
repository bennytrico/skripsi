package com.example.skripsicustomer1;

public class WalletConfirmations {
    private Integer amount;
    private String bank_account_name, bank_account_number, email, name, status, userId;

    public WalletConfirmations (String bank_account_name, String bank_account_number, String email,
                                String name, String status, String userId, Integer amount) {
        this.bank_account_name = bank_account_name;
        this.bank_account_number = bank_account_number;
        this.email = email;
        this.name = name;
        this.status = status;
        this.userId = userId;
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getBank_account_name() {
        return bank_account_name;
    }

    public void setBank_account_name(String bank_account_name) {
        this.bank_account_name = bank_account_name;
    }

    public String getBank_account_number() {
        return bank_account_number;
    }

    public void setBank_account_number(String bank_account_number) {
        this.bank_account_number = bank_account_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
