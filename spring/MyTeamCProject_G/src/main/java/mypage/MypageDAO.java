package mypage;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import member.MemberVO;

@Repository
public class MypageDAO implements MypageService {
	@Autowired @Qualifier("hanul") private SqlSession sql;

	@Override
	public foodMVO FoodM_select(foodMVO vo) {
		// TODO Auto-generated method stub
		return sql.selectOne("FoodM_select", vo);
	}

	@Override
	public int FoodM_insert(foodMVO vo) {
		// TODO Auto-generated method stub
		return sql.insert("FoodM_insert", vo);
	}

	@Override
	public void FoodM_update(foodMVO vo) {
		// TODO Auto-generated method stub
		sql.update("FoodM_update", vo);
	}

	@Override
	public List<foodMVO> foodmAllinsert(String id) {
		// TODO Auto-generated method stub
		return sql.selectList("foodmAllList", id);
	}

	@Override
	public int AttrInsert(String id) {
		// TODO Auto-generated method stub
		return sql.insert("AttrInsert", id);
	}

	@Override
	public List<AttendanceVO> AttrSelect(String id) {
		// TODO Auto-generated method stub
		return sql.selectList("AttrSelect", id);
	}

}
