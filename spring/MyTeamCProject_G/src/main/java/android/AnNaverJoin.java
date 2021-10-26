package android;

import java.sql.SQLException;

import org.springframework.ui.Model;

import member.MemberVO;

public class AnNaverJoin implements AndroidCommand {

	@Override
	public void execute(Model model) throws SQLException {
		MemberVO vo = (MemberVO) model.asMap().get("vo");
		
		AndroidDAO dao = new AndroidDAO();
		String state = dao.anNaverJoin(vo);
		
		model.addAttribute("state", state);
		
	}

}
