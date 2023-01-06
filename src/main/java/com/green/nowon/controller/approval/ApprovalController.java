package com.green.nowon.controller.approval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.green.nowon.domain.dto.approval.ApprovalListDTO;
import com.green.nowon.domain.dto.approval.ApprovalSaveDTO;
import com.green.nowon.service.ApprovalService;

@Controller
public class ApprovalController {

	@Autowired
	ApprovalService approvalService;
	
	
	@GetMapping("/approval")
	public String approval() {
		return "/approval/approval";
	}
	
	@PostMapping("/approval/save")
	public String save(ApprovalSaveDTO dto) {
		approvalService.save(dto);
		return "redirect:/approval";
	}
	
	@PostMapping("/approval/vacation")
	public String vacation(ApprovalSaveDTO dto) {
		approvalService.save(dto);
		return "redirect:/approval";
	}
	
	@GetMapping("/approval/check")
	public String list(Model model) {
		approvalService.list(model);
		return "/approval/check";
	}
	
	@GetMapping("/approval/sure")
	public String sure(Model model) {
		approvalService.list(model);
		return "/approval/sure";
	}
	
	@GetMapping("/approval/detail/{ano}")  //마무리합시다 .
	public String detail(@PathVariable long ano,Model model,ApprovalListDTO dto) {
		approvalService.detail(ano,model,dto);
		return "/approval/detail";
	}
	
	@GetMapping("/approval/vacation")
	public String vacation() {
		
		return "/approval/vacation";
	}
}
