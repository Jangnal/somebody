package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommonService {

	//파일 업로드
	public String fileUpload(HttpSession session, MultipartFile file, String category) {
		
		//서버의 물리적 위치
		String resources
		= session.getServletContext().getRealPath("resources");
		
		String upload = resources + "/upload";
		String folder = upload + "/" + category + "/" + 
						new SimpleDateFormat("yyyy/MM/dd").format(new Date());
		System.out.println("업로드 : " + upload);
		System.out.println("폴더 : " + folder);
		
		File f = new File(folder);
		if(!f.exists()) {
			boolean result = f.mkdirs();
			if(result)
				   System.out.println("디렉토리 만들기 성공");
				else
				   System.out.println("디렉토리 만들기 실패");
		}//if
		
		String uuid = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		try {
			file.transferTo(new File(folder,uuid));
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return folder.substring(resources.length()+1 ) + "/" + uuid;
	}//fileUpload

	//파일 다운로드
	public void fileDownload(HttpSession session, String filepath, HttpServletResponse response, String filename) {
		//파일이 업로드되어 있는 서버의 물리적위치를 찾아가서 다운로드할 파일이 있으면 다운로드 처리
		File file = new File(session.getServletContext().getRealPath("resources") + "/" + filepath);
		String mime = session.getServletContext().getMimeType(filepath);
		
		if (mime == null) {
			mime = "application/octet-stream";
		}//if
		
		response.setContentType(mime);
		try {
			filename = URLEncoder.encode(filename, "utf-8").replaceAll("\\+", "%20");
		response.setHeader("content-disposition", "attachment; filename=" + filename);
		
			ServletOutputStream out = response.getOutputStream();
			FileCopyUtils.copy(new FileInputStream(file), out);
			out.flush();
		
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}//try & catch
		
	}
}//CommonService
