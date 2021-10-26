package member;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO implements MemberService {
	@Autowired @Qualifier("hanul") private SqlSession sql;
	
	@Override
	public MemberVO memberAndroLogin(MemberVO vo) {
		
		MemberVO svo = sql.selectOne("memberAndroLogin", vo);
		
			return svo;
				
	}

	public int memberAndroJoin(MemberVO vo) {
		
		int result = sql.insert("memberAndroJoin", vo);
		
		return result;
	}

	@Override
	public boolean social_insert(HashMap<String, Object> map) {
		return (Integer)sql.insert("member.mapper.social_insert", map) > 0 ? true :false;
	}

	@Override
	public boolean social_login(HashMap<String, Object> map) {
		return (Integer) sql.selectOne("member.mapper.social_login", map) > 0 ?true : false;
	}

	@Override
	public MemberVO social(String id) {
		// TODO Auto-generated method stub
		return sql.selectOne("member.mapper.social", id);
	}


	@Override
	public boolean social_update(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return (Integer) sql.update("member.mapper.social_update", map) > 0 ? true : false;
	}
	
	@Override
	public boolean joinIdChk(String id) {
		return (Integer) sql.selectOne("member.mapper.joinIdChk", id) > 0 ? false : true;
		
	}

}
