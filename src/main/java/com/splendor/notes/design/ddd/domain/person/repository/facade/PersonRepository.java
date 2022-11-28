package com.splendor.notes.design.ddd.domain.person.repository.facade;

import com.splendor.notes.design.ddd.domain.person.repository.po.PersonPO;

/**
 * @author splendor.s
 * @create 2022/11/28 上午9:54
 * @description
 */
public interface PersonRepository {

    void insert(PersonPO personPO);

    void update(PersonPO personPO);

    PersonPO findById(String id);

    PersonPO findLeaderByPersonId(String personId);

}