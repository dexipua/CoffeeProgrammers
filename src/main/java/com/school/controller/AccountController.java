package com.school.controller;

import com.school.dto.mark.MarkResponseForStudent;
import com.school.models.Mark;
import com.school.models.Subject;
import com.school.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/roleId")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> getRoleIdByRoleAndUserId() {
        return ResponseEntity.ok(accountService.findRoleIdByRoleAndUserId());
    }


    @GetMapping("/bookmark") //TODO
    @ResponseStatus(HttpStatus.OK)
    public List<MarkResponseForStudent> getBookmark() {
        HashMap<Subject, List<Mark>> marks = accountService.findBookmark();
        return  marks.entrySet().stream()
                .map(entry -> new MarkResponseForStudent(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());


    }
}