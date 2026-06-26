package com.lehuuquynhnhi.models;

import java.util.ArrayList;
import java.util.List;

public class ListUserAccount {
    public static ArrayList<UserAccount> listUserAccount()
    {
        ArrayList<UserAccount> list = new ArrayList<>();
        list.add(new UserAccount("admin", "123", "admin","Tí", true));
        list.add(new UserAccount("employee1", "123", "employee","Tèo", true));
        list.add(new UserAccount("employee2", "123", "employee","Tý", true));
        return list;
    }
    public static UserAccount getUserAccount(String username, String password)
        {
            UserAccount uc=null;
             ArrayList<UserAccount> List=listUserAccount();
            for (UserAccount u : List)
            {
            if (u.getUsername().equals(username) &&
                    u.getPassword().equals(password))
            {
               uc=u;
               break;
            }
        }
        return uc;
    }
}
