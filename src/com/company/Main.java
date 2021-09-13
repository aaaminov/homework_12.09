//задание с авторизацией в бесконечном меню
package com.company;

import java.util.Scanner;

public class Main {
    public static Users users_list[] = new Users[100];
    public static void main(String[] args) {
        Scanner line = new Scanner(System.in);
        while (true) {
            System.out.println("add/a - добавить пользователя," + "\nlist/l - список пользователей," +
                                "\nchange/c - изменить данные пользователя," + "\nexit/e - выход.");
            String move = line.nextLine();
            if (move.equals("exit") || move.equals("e")){
                System.out.println("Выход из программы.");
                break;
            }
            if (move.equals("add") || move.equals("a")){
                System.out.println("(если создавать пользователся не надо, оставь любое поле пустыми.)");
                int id = add();
                if (id != -1){
                    System.out.printf("Пользователь %s создан.\n\n", users_list[id].getUserData()[2]); // [2] это login
                }
                else{
                    System.out.print("Пользователь не создан.\n\n");
                }
            }
            if (move.equals("list") || move.equals("l")){
                if (users_list[0] != null){
                    System.out.println("Пользователи:");
                    for (Users user : users_list){
                        if (user == null){
                            break;
                        }
                        System.out.printf("(id = %s): email - '%s', login - '%s', password - '%s'.\n\n",
                                user.getUserData()[0], user.getUserData()[1], user.getUserData()[2], user.getUserData()[3]);
                    }
                }
                else{
                    System.out.println("Пользователей нет.\n");
                }
            }
            if (move.equals("change") || move.equals("c")){
                int what_changed = change();
                if (what_changed != -1){
                    System.out.printf("Пользователь изменён.\n\n");
                }
                else{
                    System.out.print("Пользователь не изменён.\n\n");
                }
            }
        }
        line.close();
    }

    static int add(){
        int right_email = -1;
        String email = "";
        String login = "";
        String password = "";
        Scanner add_line = new Scanner(System.in);
        while (right_email == -1){
            System.out.print("Введи email (aaa@bbb.ccc): ");
            email = add_line.nextLine();
            right_email = checkEmail(email);
            if (right_email == 1){
                break;
            }
            else if (right_email == 0){
                return -1;
            }
        }
        System.out.print("Введи login: ");
        login = add_line.nextLine();
        if (login.equals("")){
            return -1;
        }
        System.out.print("Введи password: ");
        password = add_line.nextLine();
        if (password.equals("")){
            return -1;
        }
        if (!email.equals("") && !login.equals("") && !password.equals("")){
            Users user = new Users(email, login, password);
            users_list[Integer.parseInt(user.getUserData()[0])] = user; //получаем id через массив из getUserData
            return Integer.parseInt(user.getUserData()[0]); // id
        }
        return -1;
    }

    static int checkEmail(String email){
        if (email.equals("")) {
            return 0;
        }
        String chars[] = email.split("");
        String at = "@";
        String point = ".";
        int count_at = 0;
        int count_point = 0;
        int ind_at = -1;
        int ind_point = -1;
        for (String ch: chars) {
            if (ch.equals(at)){
                count_at++;
            }
            if (ch.equals(point)){
                count_point++;
            }
        }
        if (count_at == 1 && count_point == 1){
            ind_at = email.indexOf('@');
            ind_point = email.indexOf('.');
            if (ind_at > 0 && ind_at < (ind_point - 1) && ind_point < (email.length() - 1)){
                return 1;
            }
            //a@f.
            //012345
        }
        return -1;
    }

    static int change(){
        int id = -1;
        String old_login = "";
        String old_password = "";
        System.out.println("(если изменять пользователся не надо, оставь поля пустыми.)");
        Scanner change_line = new Scanner(System.in);
        System.out.println("Введи login пользователя: ");
        old_login = change_line.nextLine();
        System.out.println("Введи password пользователя: ");
        old_password = change_line.nextLine();
        if (old_login != "" && old_password != ""){
            for (Users user : users_list){
                if (user == null)
                    break;
                if (user.getUserData()[2].equals(old_login) && user.getUserData()[3].equals(old_password)){
                    id = Integer.parseInt(user.getUserData()[0]); //для работы по id;
                    System.out.printf("Пользователь %s найден.\n", users_list[id].getUserData()[2]);
                }
            }
            if (id != -1){
                int what_changed = -1;
                System.out.println("Введи что изменять (login/l или password/p): ");
                String what_change = change_line.nextLine();
                if (what_change.equals("login") || what_change.equals("l")){
                    System.out.print("(оставь пустое поле если не нужно изменять)\nВведи новый логин: ");
                    String new_login = change_line.nextLine();
                    if (new_login != "")
                        what_changed = users_list[id].setLogin(new_login);
                }
                else if (what_change.equals("password") || what_change.equals("p")){
                    System.out.print("(оставь пустые поля если не нужно изменять)\nВведи новый пароль: ");
                    String new_password = change_line.nextLine();
                    System.out.print("Введи старый пароль: ");
                    old_password = change_line.nextLine();
                    if (new_password != "" && old_password != "")
                        what_changed = users_list[id].setPassword(new_password, old_password);
                }
                return what_changed;
            }
        }
        return -1;
    }
}
