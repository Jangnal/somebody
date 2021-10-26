package com.hanul.project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
import common.PageVO;
import community.CommunityPage;
import community.CommunityServiceImpl;
import community.CommunityVO;

@Controller
public class CommunityController {
	
	@Autowired private CommunityServiceImpl service;
	@Autowired private CommunityPage page;
	@Autowired private CommonService common;
	
	//  커뮤니티 내가 쓴 글 보기
	@RequestMapping(value = "/mysearch.co")
	public void mysearch(HttpServletResponse response, String keyword, HttpServletRequest req) {
		System.out.println("CommunityController mysearch");
		String co_keyword = req.getParameter("search");
		page.setSearch(co_keyword);
		System.out.println("id : " + co_keyword);
		CommunityPage co_mysearch = service.co_mysearch(page);

		String json = "";
		if(co_mysearch != null) {
			Gson gson = new Gson();
			 json = gson.toJson(co_mysearch);
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
	
	
	// 커뮤니티 글 삭제처리 요청
	  @RequestMapping(value = "/delete.co") 
	  public void delete(Model model, HttpServletRequest req) {
	  System.out.println("CommunityController delete"); //해당 글을 DB에서 삭제한 후 목록화면으로
	  int c_numb = Integer.parseInt(req.getParameter("c_numb"));
	  service.co_delete(c_numb);
	  
	  model.addAttribute("page", page); 
	  model.addAttribute("uri", "community.co");
	  System.out.println("c_numb" + c_numb);
	  }
	 
	 
	
	// 커뮤니티 목록
	@ResponseBody
	@RequestMapping(value = "/community.co")
	public void list(HttpServletResponse response, String keyword, HttpServletRequest req) {
		System.out.println("CommunityController list");
		String co_keyword = req.getParameter("search");
		page.setSearch(co_keyword);
		CommunityPage co_list = service.co_list(page);
		System.out.println(co_keyword + " , " + co_list);
		String json = "";
		if(co_list != null) {
			Gson gson = new Gson();
			 json = gson.toJson(co_list);
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
	
	//커뮤니티 글 등록
	@ResponseBody
	@RequestMapping(value = "/insert.co", method = {RequestMethod.GET, RequestMethod.POST})
	public void insert(HttpServletResponse response, HttpServletRequest req, HttpSession session) {
		
		System.out.println("CommunityController insert");
		
		CommunityVO vo = new CommunityVO();
		
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String writer = req.getParameter("writer");
		vo.setC_title(title);
		vo.setC_content(content);
		vo.setC_writer(writer);
		vo.setC_category("comm");
		try {
		MultipartRequest multi = (MultipartRequest)req;
		MultipartFile file = multi.getFile("imgpath");
		
		if(file != null) {
			
			if( !file.isEmpty()) {
				vo.setC_filename( file.getOriginalFilename());
				System.out.println("fileName : " + vo.getC_filename());
				vo.setC_filepath(common.fileUpload(session, file, "comm"));
				System.out.println("filePath : " + vo.getC_filepath());
			}//if
		}//if file	
		
		}catch(Exception e) {
			System.out.print("insert Exception!!! " + e.getMessage() +  ", " + e.getStackTrace());					
		}//try & catch 
		
		int succ = service.co_insert(vo);
		String json = "";
		if(succ > 0) {
			json = "글 작성 성공";
		}else {
			json = "글 작성 실패";
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
	
	
	//커뮤니티 상세보기
	@ResponseBody
	@RequestMapping(value = "/co_view.co")
	public void view(HttpServletResponse response, HttpServletRequest req, CommunityVO vo) {
		System.out.println("CommunityController view");
				
		int numb = Integer.parseInt(req.getParameter("c_numb"));
		vo = service.co_view(numb);
		
		service.co_read(numb);
		
		String json = "";
		if(vo  != null) {
			Gson gson = new Gson();
			 json = gson.toJson(vo );
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
	
	//커뮤니티 글 수정
	@RequestMapping(value = "/update.co", method = {RequestMethod.GET, RequestMethod.POST})
	public void update (CommunityVO vo, HttpSession session
						, MultipartFile file, HttpServletResponse response, HttpServletRequest req) {
		System.out.println("CommunityController update");
		
		String c_title = req.getParameter("title");
		String c_content = req.getParameter("content");
		String c_writer = req.getParameter("writer");
		int numb = Integer.parseInt(req.getParameter("c_numb"));
		vo.setC_numb(numb);
		vo.setC_title(c_title);
		vo.setC_content(c_content);
		vo.setC_writer(c_writer);
		System.out.println(numb);
		System.out.println(c_title);
		System.out.println(c_writer);
		System.out.println(c_content);
		vo.setC_category("comm");
		try {
			MultipartRequest multi = (MultipartRequest)req;
			MultipartFile up_file = multi.getFile("imgpath");
			
			String fileName = "";
			if(up_file != null) {
				fileName = up_file.getOriginalFilename();
				System.out.println("fileName : " + fileName);
				
				if(up_file.getSize() > 0) {
					String realImgPath = req.getSession().getServletContext()
							.getRealPath("/resources/") + "member";
					
					System.out.println("realpath" + " : " + realImgPath);
					System.out.println("fileSize : " + up_file.getSize());
					
					
						// 이미지 파일 저장
						up_file.transferTo(new File(realImgPath, fileName));
						vo.setC_filename(fileName);
						vo.setC_filepath(realImgPath);
					
				}//if file size	
			}//if file	
			
			}catch(Exception e) {
				System.out.print("update Exception!!! " + e.getMessage() +  ", " + e.getStackTrace());					
			}//try & catch 
		int succ = service.co_update(vo);
		String json = "";
		if(succ > 0) {
			json = "업데이트 성공";
		}else {
			json = "업데이트 실패";
		}//if
		
		System.out.println("결과 :" + json);	
		
	}
}
