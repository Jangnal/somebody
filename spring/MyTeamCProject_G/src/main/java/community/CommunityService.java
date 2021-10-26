package community;


public interface CommunityService {

	int co_insert(CommunityVO vo);
	CommunityVO co_view(int id);
	int co_delete(int id);
	int co_update(CommunityVO vo);
	int co_read(int id);
	CommunityPage co_list(CommunityPage page);
	CommunityPage co_mysearch(CommunityPage page);
	
}
