package cart;

import java.util.List;

public interface CartService {
	
	List<CartVO> cart_list(String id);
	int cart_insert(CartVO vo);

}//MemberService
