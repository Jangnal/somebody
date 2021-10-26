package android;

import java.sql.SQLException;

import org.springframework.ui.Model;

public class AnKakaoIdChk implements AndroidCommand {

	@Override
	public void execute(Model model) throws SQLException {
		String kakao_id = (String) model.asMap().get("kakao_id");
		
		AndroidDAO dao = new AndroidDAO();
		String id = dao.anKakaoIdChk(kakao_id);
		
		System.out.println("AnKakaoIdChk : " + id);
		
		model.addAttribute("id", id);
	}

}
