package com.green.nowon.domain.entity.cate;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.nowon.domain.dto.memberDTO.DepartmentDTO;
import com.green.nowon.domain.entity.member.MemberEntity;

@Repository
public interface DepartmentMemberEntityRepository extends JpaRepository<DepartmentMemberEntity, Long>{

	List<DepartmentMemberEntity> findByMemberMno(long arr);

	List<DepartmentMemberEntity> findAllByMember_mno(long mno);

	Optional<DepartmentMemberEntity> findByMember_mnoAndDepartment_depth(long mno, int i);

}
