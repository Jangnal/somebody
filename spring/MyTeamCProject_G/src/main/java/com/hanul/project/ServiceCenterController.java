package com.hanul.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import community.CommunityVO;
import exercise.ExerciseVO;
import serviceCenter.ServiceCenterServiceImpl;

@Controller
public class ServiceCenterController {
	
	@Autowired private ServiceCenterServiceImpl service;
	
	@ResponseBody	
	@RequestMapping(value = "/qalist.sc")
	public void qalist(HttpServletResponse response, HttpServletRequest request) {
		
		String category = request.getParameter("category");
		
		List<CommunityVO> list = service.qa_selectList(category);
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
		}//try & catch
		
	}//qalist

}//ServiceCenterController
