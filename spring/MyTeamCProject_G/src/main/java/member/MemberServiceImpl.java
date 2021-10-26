package member;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired private MemberDAO dao;
	
	@Override
	public MemberVO memberAndroLogin(MemberVO vo) {
		return dao.memberAndroLogin(vo);
	}

	public int memberAndroJoin(MemberVO vo) {
		return dao.memberAndroJoin(vo);
	}

	@Override
	public boolean social_insert(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.social_insert(map);
	}

	@Override
	public boolean social_login(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.social_login(map);
	}

	@Override
	public MemberVO social(String id) {
		// TODO Auto-generated method stub
		return dao.social(id);
	}

	@Override
	public boolean social_update(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.social_update(map);
	}

	@Override
	public boolean joinIdChk(String id) {
		// TODO Auto-generated method stub
		return dao.joinIdChk(id);
	}


}
