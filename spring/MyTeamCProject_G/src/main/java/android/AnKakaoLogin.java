package android;

import java.sql.SQLException;

import org.springframework.ui.Model;

import member.MemberVO;

public class AnKakaoLogin implements AndroidCommand {

	@Override
	public void execute(Model model) throws SQLException {
		// TODO Auto-generated method stub
		MemberVO vo = (MemberVO) model.asMap().get("vo");
		AndroidDAO dao = new AndroidDAO();
		vo = dao.anKakaoLogin(vo);
		
		System.out.println("AnKakaoLogin의 값 : " +vo.getKakao());
		
		
		model.addAttribute("vo", vo);

	}

}
