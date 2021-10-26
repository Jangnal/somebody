package com.hanul.project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
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
import member.MemberVO;
import mypage.AttendanceVO;
import mypage.MypageDAO;
import mypage.MypageServiceImpl;
import mypage.foodMVO;

@Controller
public class MypageController {
	
	@Autowired private MypageServiceImpl service;
	@Autowired private CommonService common;
	
	@ResponseBody
	@RequestMapping(value="/foodmlist.my", method = {RequestMethod.GET, RequestMethod.POST})
	public void foodmlist(HttpServletRequest req, HttpServletResponse response) {
		
		String id = req.getParameter("id");
		String writedate = req.getParameter("writedate");
		
		foodMVO vo = new foodMVO();
		vo.setId(id);
		vo.setWritedate(writedate);
		
		System.out.println(id + "," + writedate);
		foodMVO rvo = service.FoodM_select(vo);
		
		System.out.println("rvo : " + rvo);
		
		String json = "";
		if(rvo != null) {
			Gson gson = new Gson();
			 json = gson.toJson(rvo);
		}//if
		System.out.println("결과 :" + json);
		
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(json.trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	@RequestMapping(value="/foodminsert.my", method = {RequestMethod.GET, RequestMethod.POST})
	public String foodminsert(HttpServletRequest req, Model model, HttpSession session, HttpServletResponse response) {
		
		System.out.println("foodminsert");
		
		foodMVO vo = new foodMVO();
		
		String id = req.getParameter("id");
		String mornig = req.getParameter("morning");
        String lunch = req.getParameter("lunch");
        String dinner = req.getParameter("dinner");
        String content = req.getParameter("content");
        
        vo.setId(id);
        vo.setMorning(mornig);
        vo.setLunch(lunch);
        vo.setDinner(dinner);
        vo.setContent(content);
        
        MultipartRequest multi = (MultipartRequest)req;
		ArrayList<MultipartFile> multilist = new ArrayList<MultipartFile>();
		
		multilist.add(multi.getFile("m_filepath"));
		multilist.add(multi.getFile("l_filepath"));
		multilist.add(multi.getFile("d_filepath"));
		
		String[] arrfilename = new String[multilist.size()];
		String[] arrfilepath = new String[multilist.size()];                
        try {    		    		    		
    		for(int i = 0; i < multilist.size(); i++) {
    			if(multilist.get(i) != null) {
    				if(!multilist.get(i).isEmpty()) {
    					arrfilename[i] = multilist.get(i).getOriginalFilename();
    					arrfilepath[i] = common.fileUpload(session, multilist.get(i), "foodM");
    					if(i == 0) {
    						vo.setM_filename(arrfilename[0]);
    						vo.setM_filepath(arrfilepath[0]);
    					}else if(i == 1) {
    						vo.setL_filename(arrfilename[1]);
    						vo.setL_filepath(arrfilepath[1]);
    					}else if(i == 2) {
    						vo.setD_filename(arrfilename[2]);
        			        vo.setD_filepath(arrfilepath[2]);
    					}//if
    				}//if    				
    			}//if file
    		}//for		
    		}catch(Exception e) {
    			System.out.print("insert Exception!!! " + e.getMessage() +  ", " + e.getStackTrace());					
    		}//try & catch
        
        
        
		int succ = service.FoodM_insert(vo);
		String json = "";
		if(succ > 0) {
			json = "식단관리 작성 성공";
		}else {
			json = "식단 작성 실패";
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
        
		return null;
	}
	
	
		@ResponseBody
		@RequestMapping(value="/foodmAlllist.my", method = {RequestMethod.GET, RequestMethod.POST})
		public void foodmAllinsert(HttpServletRequest req, Model model, HttpServletResponse response) {
			
			System.out.println("foodmAlllist");
			
			foodMVO vo = new foodMVO();
			String id = req.getParameter("id");
	        List<foodMVO> rvo = service.foodmAllinsert(id);
	        String json = "";
			if(rvo != null) {
				Gson gson = new Gson();
				 json = gson.toJson(rvo);
			}//if
			System.out.println("결과 :" + json);
			
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
			try {
				PrintWriter out = response.getWriter();
				out.print(json.trim());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		@RequestMapping(value="/foodMupdate.my", method = {RequestMethod.GET, RequestMethod.POST})
		public void foodMupdate(HttpServletRequest req, Model model) {
			
			System.out.println("foodMupdate");
			
			foodMVO vo = new foodMVO();
			
			String id = req.getParameter("id");
			String mornig = req.getParameter("morning");
	        String lunch = req.getParameter("lunch");
	        String dinner = req.getParameter("dinner");
	        String content = req.getParameter("content");
	        String writedate = req.getParameter("writedate");
	        
	        vo.setWritedate(writedate);
	        vo.setId(id);
	        vo.setMorning(mornig);
	        vo.setLunch(lunch);
	        vo.setDinner(dinner);
	        vo.setContent(content);
	        
	        service.FoodM_update(vo);
	        System.out.println(mornig + lunch + dinner + content);
	        
	        
	        // 이미지
			/*
			 * MultipartRequest multi = (MultipartRequest)req; MultipartFile file1 =
			 * multi.getFile("m_filepath"); MultipartFile file2 =
			 * multi.getFile("l_filepath"); MultipartFile file3 =
			 * multi.getFile("d_filepath"); //file1 if(file1 != null) { String m_filename =
			 * file1.getOriginalFilename(); System.out.println("fileName : " + m_filename);
			 * 
			 * if(file1.getSize() > 0) { String m_filepath =
			 * req.getSession().getServletContext() .getRealPath("/resources/upload/foodM");
			 * System.out.println("realpath" + " : " + m_filepath);
			 * System.out.println("fileSize : " + file1.getSize());
			 * 
			 * try { // 이미지 파일 저장 file1.transferTo(new File(m_filepath, m_filename)); }
			 * catch (Exception e) { // TODO: handle exception e.getMessage(); } } }else {
			 * //이미지 파일 실패시 String m_filename = "FileFail.jpg"; String m_filepath =
			 * req.getSession().getServletContext() .getRealPath("/resources/upload/foodM" +
			 * m_filename); }
			 * 
			 * //file2 if(file2 != null) { String l_filename = file2.getOriginalFilename();
			 * System.out.println("fileName : " + l_filename);
			 * 
			 * if(file2.getSize() > 0) { String l_filepath =
			 * req.getSession().getServletContext() .getRealPath("/resources/upload/foodM");
			 * System.out.println("realpath" + " : " + l_filepath);
			 * System.out.println("fileSize : " + file2.getSize());
			 * 
			 * try { // 이미지 파일 저장 file2.transferTo(new File(l_filepath, l_filename)); }
			 * catch (Exception e) { // TODO: handle exception e.getMessage(); } } }else {
			 * //이미지 파일 실패시 String l_filename = "FileFail.jpg"; String l_filepath =
			 * req.getSession().getServletContext() .getRealPath("/resources/upload/foodM" +
			 * l_filename); }
			 * 
			 * //file3 if(file3 != null) { String d_filename = file3.getOriginalFilename();
			 * System.out.println("fileName : " + d_filename);
			 * 
			 * if(file3.getSize() > 0) { String d_filepath =
			 * req.getSession().getServletContext() .getRealPath("/resources/upload/foodM");
			 * System.out.println("realpath" + " : " + d_filepath);
			 * System.out.println("fileSize : " + file3.getSize());
			 * 
			 * try { // 이미지 파일 저장 file3.transferTo(new File(d_filepath, d_filename)); }
			 * catch (Exception e) { // TODO: handle exception e.getMessage(); } } }else {
			 * //이미지 파일 실패시 String d_filename = "FileFail.jpg"; String d_filepath =
			 * req.getSession().getServletContext() .getRealPath("/resources/upload/foodM" +
			 * d_filename); }
			 */
			
		}
				
		@RequestMapping("/AttrInsert.me")
		public void AttrInsert(HttpServletRequest request, HttpServletResponse response) {
			
			String id = request.getParameter("id");			
			System.out.println(id);

			int result = service.AttrInsert(id);			
			
			String json = "";
			if(result > 0) {
				json = "출석체크 완료";
			}else {
				json = "출석체크 실패"; 
			}//if
			
			System.out.println("결과 :" + json);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
			try {
				PrintWriter out = response.getWriter();
				out.print(json.trim());
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}//AttrInsert
		
		@RequestMapping("/AttrSelect.me")
		public void AttrSelect(HttpServletRequest request, HttpServletResponse response) {
			
			String id = request.getParameter("id");			

			List<AttendanceVO> list = service.AttrSelect(id);			
			
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
				e.printStackTrace();
			}			
		}//AttrInsert
		
		
	
}