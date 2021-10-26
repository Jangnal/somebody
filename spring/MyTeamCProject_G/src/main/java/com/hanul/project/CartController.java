package com.hanul.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cart.CartServiceImpl;
import cart.CartVO;

@Controller
public class CartController {
	@Autowired private CartServiceImpl service;
	
	@ResponseBody
	@RequestMapping(value="giftcart.gf")
	public void giftcart(Model model, HttpServletResponse response, HttpServletRequest req, CartVO vo) {
		
		String id =req.getParameter("id");
		String cart_title= req.getParameter("cart_title");
		int point = Integer.parseInt(req.getParameter("point"));
		vo.setCart_title(cart_title);
		vo.setPoint(point);
		vo.setId(id);
		
		int result = service.cart_insert(vo);
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
	
	
	@ResponseBody
	@RequestMapping(value= "/cartselect.gf")
	public void androCtlist(HttpServletResponse response, HttpServletRequest req) {
		System.out.println("select cartlist까지 옴");
		
		String id = req.getParameter("id");
		
		List<CartVO> list = service.cart_list(id);
		
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
		
		
		/*
		 * List<CartVO> list = service.ct_select(); String json = ""; if(list != null) {
		 * Gson gson = new Gson(); json = gson.toJson(list);
		 * 
		 * }//if System.out.println("결과 :" + json);
		 * 
		 * response.setCharacterEncoding("UTF-8");
		 * response.setContentType("text/html; charset=utf-8");
		 * 
		 * try { PrintWriter out = response.getWriter(); out.print(json.trim()); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
	}
	
}
