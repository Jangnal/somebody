package cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

	@Autowired private CartDAO dao;
	
	

	@Override
	public int cart_insert(CartVO vo) {
		return dao.cart_insert(vo);
	}



	@Override
	public List<CartVO> cart_list(String id) {
		// TODO Auto-generated method stub
		return dao.cart_list(id);
	}

	
	
}
