package exercise;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserExerciseDAO implements UserExerciseService {
	@Autowired @Qualifier("hanul") private SqlSession sql;

	@Override
	public List<UserExerciseVO> exp_select(String id) {
		return sql.selectList("exp_select", id);
	}

}
