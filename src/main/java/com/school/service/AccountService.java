package com.school.service;


import com.school.models.Mark;
import com.school.models.Subject;

import java.util.HashMap;
import java.util.List;

public interface AccountService {
    Object findAllInformationByRoleAndUserId();

    long findRoleIdByRoleAndUserId();

    HashMap<Subject, List<Mark>> findBookmark();
}
