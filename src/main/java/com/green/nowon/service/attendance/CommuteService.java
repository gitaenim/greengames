package com.green.nowon.service.attendance;

import java.security.Principal;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.ui.Model;

import com.green.nowon.domain.dto.attendance.CommuteInsertDTO;
import com.green.nowon.domain.dto.attendance.CommuteUpdateDTO;
import com.green.nowon.domain.dto.commuteMember.CommuteMemberListDTO;
import com.green.nowon.domain.entity.attendance.CommuteEntity;

public interface CommuteService {

	Optional<CommuteEntity> findGoTime(long mno);

	void save(long mno, CommuteInsertDTO idto, CommuteUpdateDTO udto);

	long MemberMno(Principal principal);

	void showListTime(long memberMno, Model model2);

	/**
	 * 가장 최근에 근무한 날짜 조회
	 */
	void showGTime(Long mno, Model model, CommuteInsertDTO idto);

}
