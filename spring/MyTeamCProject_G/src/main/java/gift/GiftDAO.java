package gift;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class GiftDAO implements GiftService {
	@Autowired @Qualifier("hanul") private SqlSession sql;
	
	@Override
	public List<GiftVO> gf_select() {
		return sql.selectList("giftlist_select");
	}

	@Override
	public int giftbuy_insert(String id, String g_title) {
		
		GiftVO vo = sql.selectOne("gift_select", g_title);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("point", vo.getPoint());
		map.put("gs_name", vo.getGs_name());
		
		sql.update("giftbuy_update", map);
		
		return sql.insert("giftbuy_insert", map);
	}


}
