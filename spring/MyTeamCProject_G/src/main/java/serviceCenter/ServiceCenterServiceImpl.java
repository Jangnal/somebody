package serviceCenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import community.CommunityVO;

@Service
public class ServiceCenterServiceImpl implements ServiceCenterService {
	
	@Autowired private ServiceCenterDAO dao;

	@Override
	public List<CommunityVO> qa_selectList(String category) {
		return dao.qa_selectList(category);
	}

}
