package community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityServiceImpl implements CommunityService {
	@Autowired private CommunityDAO dao;
	

	@Override
	public int co_insert(CommunityVO vo) {
		return dao.co_insert(vo);
	}


	
	@Override public CommunityVO co_view(int id) { 
		 return dao.co_view(id); 
	}


	@Override
	public int co_delete(int id) {
		return dao.co_delete(id);
	}


	@Override
	public int co_update(CommunityVO vo) {
		return dao.co_update(vo);
	}


	@Override
	public int co_read(int id) {
		return dao.co_read(id);
	}


	@Override
	public CommunityPage co_list(CommunityPage page) {
		return dao.co_list(page);
	}



	@Override
	public CommunityPage co_mysearch(CommunityPage page) {
		// TODO Auto-generated method stub
		return dao.co_mysearch(page);
	}
}
