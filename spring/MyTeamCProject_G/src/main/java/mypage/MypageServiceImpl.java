package mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import member.MemberVO;


@Service
public class MypageServiceImpl implements MypageService {

	@Autowired private MypageDAO dao;
	
	@Override
	public foodMVO FoodM_select(foodMVO vo) {
		// TODO Auto-generated method stub
		return dao.FoodM_select(vo);
	}
	
	@Override
	public int FoodM_insert(foodMVO vo) {
		return dao.FoodM_insert(vo);
				
	}

	@Override
	public void FoodM_update(foodMVO vo) {
		// TODO Auto-generated method stub
		dao.FoodM_update(vo);
	}

	@Override
	public List<foodMVO> foodmAllinsert(String id) {
		// TODO Auto-generated method stub
		return dao.foodmAllinsert(id);
	}

	@Override
	public int AttrInsert(String id) {
		// TODO Auto-generated method stub
		return dao.AttrInsert(id);
	}

	@Override
	public List<AttendanceVO> AttrSelect(String id) {
		// TODO Auto-generated method stub
		return dao.AttrSelect(id);
	}

}
