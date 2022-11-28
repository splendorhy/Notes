package com.splendor.notes.design.ddd.domain.person.repository.persistence;

import com.splendor.notes.design.ddd.domain.person.repository.facade.PersonRepository;
import com.splendor.notes.design.ddd.domain.person.repository.mapper.PersonDao;
import com.splendor.notes.design.ddd.domain.person.repository.po.PersonPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author splendor.s
 * @create 2022/11/28 上午9:58
 * @description
 */
@Repository
public class PersonRepositoryImpl implements PersonRepository {

    @Autowired
    PersonDao personDao;

    @Override
    public void insert(PersonPO personPO) {
        personDao.save(personPO);
    }

    @Override
    public void update(PersonPO personPO) {
        personDao.save(personPO);
    }

    @Override
    public PersonPO findById(String id) {
        return personDao.findById(id).orElseThrow(() -> new RuntimeException("未找到用户"));
    }

    @Override
    public PersonPO findLeaderByPersonId(String personId) {
        return personDao.findLeaderByPersonId(personId);
    }

}