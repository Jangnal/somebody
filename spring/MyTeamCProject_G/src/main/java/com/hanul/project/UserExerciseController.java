package com.hanul.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import exercise.ExerciseServiceImpl;
import exercise.ExerciseVO;
import exercise.UserExerciseServiceImpl;
import exercise.UserExerciseVO;

@Controller
public class UserExerciseController {
	
	@Autowired private UserExerciseServiceImpl service;
	
	@ResponseBody	
	@RequestMapping(value = "/androEXPlist.me")
	public void androEXPlist(HttpServletResponse response, HttpServletRequest request) {
		
		String id = request.getParameter("id");
		
		System.out.println(id);
		
		List<UserExerciseVO> list = service.exp_select(id);
		String json = "";
		if(list != null) {
			Gson gson = new Gson();
			 json = gson.toJson(list);
		}//if
		System.out.println("결과 :" + json);
		
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(json.trim());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
