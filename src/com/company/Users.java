package com.company;

public class Users {
    static int countUsers = 0;
    private int id;
    private String email;
    private String login;
    private String password;
    public Users(String email, String login, String password){
        id = countUsers;
        this.email = email;
        this.login = login;
        this.password = password;
        countUsers++;
    }

    public String[] getUserData() {
        String data[] = {Integer.toString(id), email, login, password};
        return data;
    }

    public int setLogin(String login) {
        this.login = login;
        return 2;
    }

    public int setPassword(String new_password, String old_password) {
        if (old_password.equals(this.password)){
            this.password = new_password;
            return 3;
        }
        return -1;
    }
}