package cart;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CartDAO implements CartService {
	@Autowired @Qualifier("hanul") private SqlSession sql;
	

	@Override
	public int cart_insert(CartVO vo) {
		
		//CartVO vo = sql.selectOne("cart_select", cart_title); 값을 따로 불러오지 않으니 여기선 필요없다
	
		
		return sql.insert("cart_insert", vo);
	}

	@Override
	public List<CartVO> cart_list(String id) {
		// TODO Auto-generated method stub
		return sql.selectList("cart_list",id);
	}	


}
