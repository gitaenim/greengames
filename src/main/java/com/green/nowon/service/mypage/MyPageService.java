package com.green.nowon.service.mypage;

import org.springframework.ui.Model;

public interface MyPageService {


	void info(long mno, Model model, Model model2);

	void salaryInfo(long mno, Model model);

}
