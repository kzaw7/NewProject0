package com.khinezaw;

public class User {

    private String username;
    private String password;
    private int id;
    private String name;
    private String email;
    private int balance;

    public User(int id, String name, String email, String s, int balance) {

    }

    public User(int id, String username, String password, String name, String email, int balance) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }


    public User() {

    }

    public User(int anInt, String string, String string1) {
    }

    public User(int anInt, String string, String string1, String string2) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Banking{" +
                "username=" + username +
                ", password=" + password + '\'' +
                ", id=" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }

    public void forEach(Object o) {
    }
}

