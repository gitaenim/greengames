package com.green.nowon.controller.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.green.nowon.domain.dto.memberDTO.MemberUpdateDTO;
import com.green.nowon.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@ResponseBody
	@PostMapping("/mypage/temp-upload")
	public Map<String, String> memberDetailImg(MultipartFile img) {
		return memberService.fileTempUpload(img);
	}
	
	@GetMapping("/mypage/{mno}")
	public String detail(@PathVariable long mno,Model model,Model model2) {
		System.err.println(">>>>>>>>>>>>>>>>" + mno);
		memberService.detail(mno,model,model2);
		return "/mypage/employee-detail";
	}
	
	@PostMapping("/mypage/{mno}/update")
	public String update(@PathVariable long mno,MemberUpdateDTO dto) {
		System.err.println(">>>>>>>>>>>update : "+mno);
		memberService.update(mno,dto);
		return "redirect:/mypage/info/{mno}";
	}
	
	@GetMapping("/employee")//카테고리 리스트롤 사용할 예정
	public String employeeList() {

		return"member/employee-list";
	}

	
	
	
}
