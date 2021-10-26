package member;

import java.util.HashMap;

public interface MemberService {
	/*회원 로그인*/
	MemberVO memberAndroLogin(MemberVO vo);
	/*회원 가입*/
	int memberAndroJoin(MemberVO vo);
	/*소셜 가입*/
	boolean social_insert(HashMap<String, Object> map);
	/*소셜 로그인*/
	boolean social_login(HashMap<String, Object> map);
	boolean social_update(HashMap<String, Object> map);
	MemberVO social(String id);
	/*아이디 중복 체크*/
	boolean joinIdChk(String id);
}//MemberService
