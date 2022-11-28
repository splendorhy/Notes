package com.splendor.notes.design.ddd.infrastructure.client;

import com.splendor.notes.design.ddd.domain.person.entity.Person;
import com.splendor.notes.design.ddd.infrastructure.common.api.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author splendor.s
 * @create 2022/11/25 下午5:56
 * @description
 */
@FeignClient(name = "auth-service", path = "/demo/auth")
public interface AuthFeignClient {

    @PostMapping(value = "/login")
    Response login(Person person);
}
