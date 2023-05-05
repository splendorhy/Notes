package com.splendor.notes.design.ddd.interfaces.assembler;

import com.splendor.notes.design.ddd.domain.person.entity.Person;
import com.splendor.notes.design.ddd.domain.person.entity.valueobject.PersonStatus;
import com.splendor.notes.design.ddd.domain.person.entity.valueobject.PersonType;
import com.splendor.notes.design.ddd.infrastructure.util.DateUtil;
import com.splendor.notes.design.ddd.interfaces.dto.PersonDTO;

import java.text.ParseException;

/**
 * @author splendor.s
 * @create 2022/11/25 下午6:58
 * @description
 */
public class PersonAssembler {

    public static PersonDTO toDTO(Person person){
        PersonDTO dto = new PersonDTO();
        dto.setPersonId(person.getPersonId());
        dto.setPersonType(person.getPersonType().toString());
        dto.setPersonName(person.getPersonName());
        dto.setStatus(person.getStatus().toString());
        dto.setCreateTime(DateUtil.formatDateTime(person.getCreateTime()));
        dto.setLastModifyTime(DateUtil.formatDateTime(person.getLastModifyTime()));
        return dto;
    }

    public static Person toDO(PersonDTO dto) throws ParseException {
        Person person = new Person();
        person.setPersonId(dto.getPersonId());
        person.setPersonType(PersonType.valueOf(dto.getPersonType()));
        person.setPersonName(dto.getPersonName());
        person.setStatus(PersonStatus.valueOf(dto.getStatus()));
        person.setCreateTime(DateUtil.parseDateTime(dto.getCreateTime()));
        person.setLastModifyTime(DateUtil.parseDateTime(dto.getLastModifyTime()));
        return person;
    }
}
