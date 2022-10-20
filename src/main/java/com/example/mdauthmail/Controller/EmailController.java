package com.example.mdauthmail.Controller;

import com.example.mdauthmail.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class EmailController {

    @Autowired
    EmailService mailService;

    @GetMapping(value = "email-oauth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMail() throws Exception {
        mailService.sendEmailTest();
        return ResponseEntity.ok().build();
    }
}
