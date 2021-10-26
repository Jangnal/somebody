package gift;

import java.util.List;

public interface GiftService {

	List<GiftVO> gf_select();
	int giftbuy_insert(String id, String g_title);
}//MemberService
