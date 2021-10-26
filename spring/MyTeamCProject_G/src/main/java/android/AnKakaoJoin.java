package android;

import java.sql.SQLException;

import org.springframework.ui.Model;

import member.MemberDAO;
import member.MemberVO;

public class AnKakaoJoin implements AndroidCommand {

	@Override
	public void execute(Model model) throws SQLException {
		// TODO Auto-generated method stub
		MemberVO vo =  (MemberVO) model.asMap().get("vo");
		
		AndroidDAO dao = new AndroidDAO();
		String state = dao.anKakaoJoin(vo);
		System.out.println("KAKAOJOIN STATE : " + state);
		
		model.addAttribute("state", state);

	}

}
