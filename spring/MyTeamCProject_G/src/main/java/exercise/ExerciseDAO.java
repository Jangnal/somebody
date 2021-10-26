package exercise;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ExerciseDAO implements ExerciseService {
	@Autowired @Qualifier("hanul") private SqlSession sql;

	@Override
	public List<ExerciseVO> ex_select(String e_type) {
		return sql.selectList("ex_select", e_type);
	}

	@Override
	public List<ExerciseVO> ext_select() {
		return sql.selectList("ext_select");
	}

}
