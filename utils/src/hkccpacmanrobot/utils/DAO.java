import java.sql.DriverManager;
import java.sql.SQLException;

//import com.mysql.jdbc.Connection;
import java.sql.Connection;

//Database Access Object
public class DAO {
	public Connection connection;
	public MapDAO mapDAO;

	public DAO() throws SQLException {
		connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306",
				"root", "rootpw");
	}

}
