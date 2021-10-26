package android;

import java.sql.SQLException;

import org.springframework.ui.Model;

public class AnNaverIdChk implements AndroidCommand {

	@Override
	public void execute(Model model) throws SQLException {
		String naver_id = (String) model.asMap().get("naver_id");
		
		AndroidDAO dao = new AndroidDAO();
		String id = dao.anNaverIdChk(naver_id);
		System.out.println("네이버 아이디 :" + naver_id);
		System.out.println("유저 아이디 :" + id);
		
		model.addAttribute("id", id);
	}

}
