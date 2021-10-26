package android;

import java.sql.SQLException;

import org.springframework.ui.Model;

public interface AndroidCommand {
	public void execute(Model model) throws SQLException;
}
