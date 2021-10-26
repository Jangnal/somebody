package community;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityDAO implements CommunityService{
	@Autowired @Qualifier("hanul") private SqlSession sql;
	
	@Override
	public CommunityPage co_list(CommunityPage page) {
		page.setList( (List)sql.selectList("community.mapper.co_list", page) );
		return page;
	}

	@Override
	public int co_insert(CommunityVO vo) {
		return sql.insert("community.mapper.co_insert", vo);
	}

	
	@Override public CommunityVO co_view(int id) { 
		return sql.selectOne("community.mapper.co_view", id); }

	@Override
	public int co_delete(int id) {
		
		return sql.delete("co_delete", id);
	}

	@Override
	public int co_update(CommunityVO vo) {
		return sql.update("co_update", vo);
	}

	@Override
	public int co_read(int numb) {
		return sql.update("co_read", numb);
	}

	@Override
	public CommunityPage co_mysearch(CommunityPage page) {
		// TODO Auto-generated method stub
		page.setList((List)sql.selectList("community.mapper.co_mysearch", page));
		return page;
	}

	
}
