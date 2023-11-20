package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import dao.ConnectionJDBC;
import dao.EquipeJDBC;

public class TestInsertion {
	public static void main (String[]args) throws Exception {	
													
		// Création d'une connexion
		Connection dbConnection = ConnectionJDBC.createConnection();
		
		EquipeJDBC equipe = new EquipeJDBC(dbConnection);
		
		// Statement st = dbConnection.createStatement();
		// String req = "DELETE FROM EQUIPE";
		// st.executeUpdate(req);
		
		//String req2 = "INSERT INTO PAYS VALUES('FR')";
		//st.executeUpdate(req2);

		
		equipe.add(new Equipe(3,"DanFQFQFQois",11,Pays.FR));
		equipe.add(new Equipe(5,"DanFQFDQDois",11,Pays.FR));
		equipe.add(new Equipe(6,"DanoFQFis",11,Pays.FR));
		equipe.add(new Equipe(7,"DanQDQois",11,Pays.FR));
		equipe.add(new Equipe(8,"DanQQois",11,Pays.FR));
		equipe.add(new Equipe(9,"DanoiSFSs",11,Pays.FR));
		equipe.add(new Equipe(10,"DaYInois",11,Pays.FR));
		equipe.add(new Equipe(11,"Do",11,Pays.FR));
		equipe.add(new Equipe(12,"Danois",11,Pays.FR));
		equipe.add(new Equipe(13,"Danois",11,Pays.FR));
		equipe.add(new Equipe(14,"Dnis",11,Pays.FR));
		equipe.add(new Equipe(15,"Dois",11,Pays.FR));
		equipe.add(new Equipe(16,"Da",11,Pays.FR));
	}
}
