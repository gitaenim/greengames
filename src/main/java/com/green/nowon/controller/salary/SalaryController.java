package com.green.nowon.controller.salary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.green.nowon.service.mypage.MyPageService;

@Controller
public class SalaryController {
	
	@Autowired
	private MyPageService service;
	
	
	@GetMapping("/salary/{mno}")//급여관리 페이지에서 보이는 멤버리스트
	public String memberList(@PathVariable long mno,Model model) {
		service.list(mno,model);
		return "member/salary-list";
	}
	
	
	@GetMapping("/salaryinfo/{mno}")
	public String salaryDetail(@PathVariable long mno, Model model,Model model2,Model model3) {
		service.salaryInfo(mno,model, model2, model3);
		return "member/salary-detail";
	}
	@PostMapping("/salary/department/{mno}")
	public String salaryDepartment(@PathVariable long mno,long dno,long pno, Model model,Model model2,Model model3) {
		service.update(mno,pno,dno);
		service.update2(mno);
		service.salaryInfo(mno,model, model2, model3);
		return "member/salary-detail";
	}

}
