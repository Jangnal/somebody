package exercise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExerciseServiceImpl implements ExerciseService {
	
	@Autowired private ExerciseDAO dao;
	
	@Override
	public List<ExerciseVO> ex_select(String e_type) {
		return dao.ex_select(e_type);
	}

	@Override
	public List<ExerciseVO> ext_select() {
		return dao.ext_select();
	}

}
