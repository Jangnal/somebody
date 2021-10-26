package serviceCenter;

import java.util.List;

import community.CommunityVO;


public interface ServiceCenterService {
	
	List<CommunityVO> qa_selectList(String category);

}//ServiceCenterService
