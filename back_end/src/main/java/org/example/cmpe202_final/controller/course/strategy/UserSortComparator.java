package org.example.cmpe202_final.controller.course.strategy;

import org.example.cmpe202_final.model.user.User;

import java.util.Comparator;

public class UserSortComparator implements Comparator<User> {
    @Override
    public int compare(User user1, User user2) {
        String fullName1 = user1.getFirstName() + user1.getLastName();
        String fullName2 = user2.getFirstName() + user2.getLastName();
        return fullName1.compareTo(fullName2);
    }
}
