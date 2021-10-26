package gift;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiftServiceImpl implements GiftService{

	@Autowired private GiftDAO dao;
	
	@Override
	public List<GiftVO> gf_select() {

		return dao.gf_select();
	}

	@Override
	public int giftbuy_insert(String id, String g_title) {
		return dao.giftbuy_insert(id, g_title);
	}

	
}
