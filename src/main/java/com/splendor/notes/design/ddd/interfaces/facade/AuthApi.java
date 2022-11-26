package com.splendor.notes.design.ddd.interfaces.facade;

import com.splendor.notes.design.ddd.application.service.LoginApplicationService;
import com.splendor.notes.design.ddd.infrastructure.common.api.Response;
import com.splendor.notes.design.ddd.interfaces.assembler.PersonAssembler;
import com.splendor.notes.design.ddd.interfaces.dto.PersonDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author splendor.s
 * @create 2022/11/25 下午7:04
 * @description
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthApi {

    @Autowired
    LoginApplicationService loginApplicationService;

    @PostMapping("/login")
    public Response login(PersonDTO personDTO){
        try {
            return loginApplicationService.login(PersonAssembler.toDO(personDTO));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
