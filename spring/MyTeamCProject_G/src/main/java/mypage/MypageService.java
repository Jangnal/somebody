package mypage;

import java.util.List;

import member.MemberVO;

public interface MypageService  {

	foodMVO FoodM_select(foodMVO vo);
	int FoodM_insert(foodMVO vo);
	void FoodM_update(foodMVO vo);
	List<foodMVO> foodmAllinsert(String id);
	int AttrInsert(String id);
	List<AttendanceVO> AttrSelect(String id);	
}
