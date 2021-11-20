package edu.samuelgarcia.leagueoflogins.models;

public class Account implements Cloneable {
    private String account;
    private String password;

    public Account(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCensoredPassword() {
        return "************";
    }

    @Override
    public String toString() {
        return String.format("%s;%s", account, password);
    }

    @Override
    public Account clone() {
        return new Account(account, password);
    }
}
