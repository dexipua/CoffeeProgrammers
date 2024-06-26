package com.school.controller;

import com.school.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

}