package com.splendor.notes.design.ddd.domain.leave.repository.mapper;

import com.splendor.notes.design.ddd.domain.leave.repository.po.LeaveEventPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author splendor.s
 * @create 2022/11/25 下午6:15
 * @description
 */

@Repository
public interface LeaveEventDao extends JpaRepository<LeaveEventPO, String> {

}
