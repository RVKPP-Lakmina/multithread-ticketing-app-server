package com.example.ticketManager.dao;

import com.example.ticketManager.util.exception.InvalidDataException;
import com.example.ticketing.model.User;
import com.example.ticketing.util.Util;

import java.util.Objects;

public class UserDao {

    public User saveUserLogic(User user) {
//        if (user.getRate() == Integer.parseInt(null)) {
//            throw new InvalidDataException("User Rate is Not Defined.");
//        }

        if (Objects.equals(user.getName(), "")) {
            throw new InvalidDataException("User Name is Not Defined.");
        }

        if(Objects.equals(user.getPrefix(), "")){
            throw new InvalidDataException("User Prefix is Not Defined.");
        }

        user.setId(user.getPrefix() + "-" + Util.generateUniqueKey());


        return user;
    }

}
