package serviceCenter;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import community.CommunityVO;

@Repository
public class ServiceCenterDAO implements ServiceCenterService {
	
	@Autowired @Qualifier("hanul") private SqlSession sql;

	@Override
	public List<CommunityVO> qa_selectList(String category) {
		return sql.selectList("qa_selectList", category);
	}

}
