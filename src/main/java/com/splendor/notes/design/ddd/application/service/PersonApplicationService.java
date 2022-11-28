package com.splendor.notes.design.ddd.application.service;

import com.splendor.notes.design.ddd.domain.person.entity.Person;
import com.splendor.notes.design.ddd.domain.person.service.PersonDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author splendor.s
 * @create 2022/11/26 下午8:57
 * @description
 */
@Service
public class PersonApplicationService {

    @Autowired
    PersonDomainService personDomainService;

    public void create(Person person) {
        personDomainService.create(person);
    }

    public void update(Person person) {
        personDomainService.update(person);
    }

    public void deleteById(String personId) {
        personDomainService.deleteById(personId);
    }

    public Person findById(String personId) {
        return null;
    }

    public Person findFirstApprover(String applicantId, int leaderMaxLevel) {
        return personDomainService.findFirstApprover(applicantId, leaderMaxLevel);
    }


}
