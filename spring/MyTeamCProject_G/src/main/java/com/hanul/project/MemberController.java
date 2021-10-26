package com.hanul.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.gson.Gson;

import common.CommonService;
import member.MemberServiceImpl;
import member.MemberVO;


@Controller
public class MemberController {
	
	@Autowired private MemberServiceImpl service;
	@Autowired private CommonService common;
	
	// 네이버 아이디로 로그인
		@ResponseBody
		@RequestMapping(value="/anNaverLogin", method = {RequestMethod.GET, RequestMethod.POST})
		public void anNaverLogin(HttpServletRequest request, HttpServletResponse response) throws SQLException{
			String id = (String) request.getParameter("naver_id");
			String email = (String) request.getParameter("naver_email");
			String name = (String) request.getParameter("naver_name");
			String phone = (String) request.getParameter("naver_phone");
			String naver = (String) request.getParameter("naver");
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("naver", naver);
			
			if(service.social_login(map)) {
				//값이 있음
				map.put("name", name);
				map.put("email", email);
				map.put("phone", phone);
				if(service.social_update(map)) {
					MemberVO vo = service.social(id);
					Gson gson = new Gson(); String json = gson.toJson(vo);
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=utf-8");
					try {
						PrintWriter out = response.getWriter(); out.print(json.trim());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}
	
	//네이버 아이디 등록
		@ResponseBody
		@RequestMapping(value="/anNaverJoin", method = {RequestMethod.GET, RequestMethod.POST})
		public void anNaverJoin(HttpServletRequest request, HttpServletResponse response) throws SQLException{
			
			String id = (String) request.getParameter("naver_id");
			String email = (String) request.getParameter("naver_email");
			String name = (String) request.getParameter("naver_name");
			String phone = (String) request.getParameter("naver_phone");
			String naver = (String) request.getParameter("naver");
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("email", email);
			map.put("name", name);
			map.put("phone", phone);
			map.put("naver", naver);
			
			try {
				PrintWriter out = response.getWriter();
				out.print(service.social_insert(map));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	//네이버 아이디 중복검사
		@ResponseBody
		@RequestMapping(value="/anNaverIdChk", method = {RequestMethod.GET, RequestMethod.POST})
		public void anNaverIdChk(HttpServletRequest requst, HttpServletResponse response) throws SQLException{
			
			String id = (String) requst.getParameter("naver_id");
			System.out.println("네이버 아이디 : " + id);
			
			try {
				PrintWriter out = response.getWriter();
				out.print(service.joinIdChk(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	// 카카오 아이디 중복 검사
		@ResponseBody
		@RequestMapping(value="/anKakaoIdChk", method = {RequestMethod.GET, RequestMethod.POST})
		public void anKakaoIdChk(HttpServletRequest request, HttpServletResponse response) throws SQLException {
			String id = (String) request.getParameter("kakao_id");
			
			try {
				PrintWriter out = response.getWriter();
				out.print(service.joinIdChk(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	//소셜 로그인 카카오 : 이전에 로그인한 적이 있는 경우 -ing
		@ResponseBody
		@RequestMapping(value="/anKakaoLogin", method = {RequestMethod.GET, RequestMethod.POST})
		public void anKakaoLogin(HttpServletRequest request, HttpServletResponse response) throws SQLException {
			String id =(String) request.getParameter("kakao_id");
			String email = (String) request.getParameter("kakao_email");
			String name = (String) request.getParameter("kakao_name");
			String kakao = (String) request.getParameter("kakao");
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("kakao", kakao);
			
			if(service.social_login(map)) {
				map.put("name", name);
				map.put("email", email);
				//db 업데이트 -> 업데이트가 성공적이면 id를 가지고 조회해서 넘겨준다
				if(service.social_update(map)) {
					MemberVO vo = service.social(id);
					
					Gson gson = new Gson();
					String json = gson.toJson(vo);
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=utf-8");
					try {
						PrintWriter out = response.getWriter();
						out.print(json.trim());
					} catch (IOException e) {
						e.printStackTrace();
					}//try & catch
				}
			}
		}
	
	//소셜로그인 - 카카오 (가입)
		@ResponseBody
		@RequestMapping(value="/anKakaoJoin", method = {RequestMethod.GET, RequestMethod.POST})
		public void anKakaoJoin(HttpServletRequest request, HttpServletResponse response) throws SQLException {
			String id =(String) request.getParameter("kakao_id");
			String name = (String) request.getParameter("kakao_name");
			String email = (String) request.getParameter("kakao_email");
			String kakao = (String) request.getParameter("kakao");
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("name", name);
			map.put("email", email);
			map.put("kakao", kakao);
			
			try {
				PrintWriter out = response.getWriter();
				out.print(service.social_insert(map));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	
	@RequestMapping("/join.me")
	public String androidjoin(Model model, HttpServletRequest request, HttpSession session) {
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		float bmi = Float.parseFloat(request.getParameter("height"));
		int height = Integer.parseInt(request.getParameter("height"));
		int weight = Integer.parseInt(request.getParameter("weight"));
		
		System.out.println(id + ", " + password + ", " + email 
								+ ", " + name + ", " + phone
								+ ", " + height + ", " + weight);
		
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setPassword(password);
		vo.setEmail(email);
		vo.setName(name);
		vo.setPhone(phone);
		vo.setHeight(height);
		vo.setWeight(weight);
		vo.setBmi(bmi);
		
		try {
			MultipartRequest multi = (MultipartRequest)request;
			MultipartFile file = multi.getFile("imgpath");
			
			if(file != null) {
				
				if( !file.isEmpty()) {
					vo.setMember_c_file_name( file.getOriginalFilename());
					System.out.println("fileName : " + vo.getMember_c_file_name());
					vo.setMember_c_file_path(common.fileUpload(session, file, "comm"));
					System.out.println("filePath : " + vo.getMember_c_file_path());
				}//if
			}//if file	
			
			}catch(Exception e) {
				System.out.print("insert Exception!!! " + e.getMessage() +  ", " + e.getStackTrace());					
			}//try & catch 
		
		int result = service.memberAndroJoin(vo);
		
		String json = "";
		if(result > 0) {
			json = "회원가입 성공";
		}else{
			json = "회원가입 실패";
		}//if
		System.out.println("결과 :" + json);
		
		return json;
		
	}//androidjoin
	
	@ResponseBody
	@RequestMapping("/login.me")
	public String androidlogin(Model model, HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		System.out.println(id + ", " + password);
		/*
		 * // 3. 안드로이드에서 보낸 파일 받기 MultipartRequest multi = (MultipartRequest)request;
		 * MultipartFile file = multi.getFile("imgrealpath");
		 * 
		 * String fileName = ""; if(file != null) { fileName =
		 * file.getOriginalFilename(); System.out.println("fileName : " + fileName);
		 * 
		 * if(file.getSize() > 0) { String realImgPath =
		 * req.getSession().getServletContext() .getRealPath("/resources/");
		 * 
		 * System.out.println("realpath" + " : " + realImgPath);
		 * System.out.println("fileSize : " + file.getSize());
		 * 
		 * try { // 이미지 파일 저장 file.transferTo(new File(realImgPath, fileName));
		 * }catch(Exception e) { e.getMessage(); }
		 * 
		 * }
		 * 
		 * }
		 */
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setPassword(password);
		
		MemberVO svo = service.memberAndroLogin(vo);
		
		String json = "";
		if(svo != null) {
			Gson gson = new Gson();
			 json = gson.toJson(svo);
		}//if
		System.out.println("결과 :" + json);
		
		return json;
	}//androidlogin

}//MemberController
