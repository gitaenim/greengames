package com.green.nowon.controller.salary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.green.nowon.service.mypage.MyPageService;

@Controller
public class SalaryController {
	
	@Autowired
	private MyPageService service;
	
	@GetMapping("/salaryinfo/{mno}")
	public String salaryDetail(@PathVariable long mno, Model model) {
		service.salaryInfo(mno,model);
		return "member/salary-detail";
	}
	
}
