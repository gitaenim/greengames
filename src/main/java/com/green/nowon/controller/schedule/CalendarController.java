package com.green.nowon.controller.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.green.nowon.domain.dto.schedule.calendarDTO;
import com.green.nowon.service.CalendarService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CalendarController {
	
	@Autowired
	CalendarService cService;

	// 캘린더 페이지로 가기
	@GetMapping("/schedule/calendar")
	public String calendar() {
		return "/schedule/calendar";
	}
	// 캘린더 select
	@GetMapping("/schedule/calendar/{cno}")
	public String calendarSelect(@PathVariable long cno, Model model){
		System.out.println("CalendarController cno>>>>>"+cno);
		List<calendarDTO> dto = cService.findById(cno);
		System.out.println("CalendarController dto>>>>>"+dto.toString());
		model.addAttribute("data", dto);
		return "/schedule/calendar";
	}
	// 캘린더 insert
	@PostMapping("/schedule/calendar")
	public String calendarInsert(@RequestBody calendarDTO dto){

		//임시 로그인 방편
		dto.setMno(5);
		cService.save(dto);
		return "/schedule/calendar";
	}
	
}
