package com.hanul.project;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import gift.GiftServiceImpl;
import gift.GiftVO;

@Controller
public class GiftController {
	
	@Autowired private GiftServiceImpl service;
	
	@ResponseBody
	@RequestMapping(value= "/giftselect.gf")
	public void androGFlist(HttpServletResponse response) {
		
		List<GiftVO> list = service.gf_select();
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
			out.print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value= "/giftbuy.gf")
	public void giftbuy(HttpServletResponse response, HttpServletRequest req) {
		
		String id = "admin"; //Integer.parseInt(req.getParameter("id"));
		String g_title = req.getParameter("g_title");
		
		int result = service.giftbuy_insert(id, g_title);
		String json = "";
		if(result > 0) {
			json = "성공";
		}else {
			json = "실패";
		}
		

		System.out.println("결과 :" + json);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		try {
			PrintWriter out = response.getWriter();
			out.print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	

	
	
}
