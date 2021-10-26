package exercise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserExerciseServiceImpl implements UserExerciseService {
	
	@Autowired private UserExerciseDAO dao;
	
	@Override
	public List<UserExerciseVO> exp_select(String id) {
		return dao.exp_select(id);
	}

}
