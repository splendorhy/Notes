package com.splendor.notes.design.ddd.application.service;

import com.splendor.notes.design.ddd.domain.person.entity.Person;
import com.splendor.notes.design.ddd.domain.person.service.PersonDomainService;
import com.splendor.notes.design.ddd.infrastructure.client.AuthFeignClient;
import com.splendor.notes.design.ddd.infrastructure.common.api.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author splendor.s
 * @create 2022/11/26 下午8:59
 * @description
 */
@Service
public class LoginApplicationService{

    @Autowired
    AuthFeignClient authService;
    @Autowired
    PersonDomainService personDomainService;

    public Response login(Person person){
        //调用鉴权微服务
        return authService.login(person);
    }
}
