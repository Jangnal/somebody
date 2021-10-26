package exercise;

import java.util.List;

public interface ExerciseService {

	List<ExerciseVO> ex_select(String e_type);
	List<ExerciseVO> ext_select();
}//MemberService
