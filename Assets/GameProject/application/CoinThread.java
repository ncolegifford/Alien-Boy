package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CoinThread extends Thread{
		
	public CoinThread() {
	}
	
	public void run() {
		try {
			Statement st = Main.conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Userinformation.userinfo where userid=\"" + Main.UserId + "\";");
			rs.next();
			
			st.execute("update userinfo set coins = coins + 1 where userid = '" + Main.UserId + "';");
			st.execute("update userinfo set score = score + 3 where userid = '" + Main.UserId + "';");
			
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
	}

}
