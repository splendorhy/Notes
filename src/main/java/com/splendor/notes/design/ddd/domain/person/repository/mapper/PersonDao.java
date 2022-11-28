package com.splendor.notes.design.ddd.domain.person.repository.mapper;

import com.splendor.notes.design.ddd.domain.person.repository.po.PersonPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author splendor.s
 * @create 2022/11/28 上午9:57
 * @description
 */
@Repository
public interface PersonDao extends JpaRepository<PersonPO, String> {

    @Query(value = "select p from PersonPO  p where p.relationshipPO.personId=?1")
    PersonPO findLeaderByPersonId(String personId);
}
