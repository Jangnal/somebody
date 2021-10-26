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

@Controller
public class ExerciseController {
	
	@Autowired private ExerciseServiceImpl service;
	
	@ResponseBody	
	@RequestMapping(value = "/androEXTlist.me")
	public void androEXTlist(HttpServletResponse response) {
		
		List<ExerciseVO> list = service.ext_select();
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
	
	@ResponseBody	
	@RequestMapping(value = "/androEXlist.me")
	public void androEXlist(HttpServletResponse response, HttpServletRequest request) {
		
		String e_type = request.getParameter("e_type");
		
		System.out.println(e_type);
		
		List<ExerciseVO> list = service.ex_select(e_type);
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
