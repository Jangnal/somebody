package android;

import java.sql.SQLException;

import org.springframework.ui.Model;

import member.MemberVO;

public class AndroidIdChk implements AndroidCommand {

	@Override
	public void execute(Model model) throws SQLException {
		/*컨트롤러에서 넘겨받은 Model 에서 값을 추출*/
		
		String id = (String) model.asMap().get("id");
		
		AndroidDAO dao = new AndroidDAO();
		int result = dao.anIdChk(id);
		System.out.println("모델" + id);
		//System.out.println("id : " + vo.getUser_id());
		
		String state = "";
		if(result > 0) {
			state = "false";
		}else {
			state = "true";
		}

		
		System.out.println(state);
		
		
		model.addAttribute("state", state);
	}

}
