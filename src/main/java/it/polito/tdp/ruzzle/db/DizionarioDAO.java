package it.polito.tdp.ruzzle.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DizionarioDAO {
	
	/**
	 * Ritorna tutte le parole presenti nel dizionario
	 * 
	 * @return
	 */
	public List<String> listParola() {
		List<String> result = new ArrayList<>() ;
		
		String query = "SELECT nome FROM parola ORDER BY nome" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(query) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(res.getString("nome")) ;
			}
			
			res.close();
			conn.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result ;

	}
	
	public boolean controlloPrefisso(String inizio) {
		
		boolean result = false;
		
		String query = "SELECT nome FROM parola WHERE nome LIKE ?" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(query) ;
			
			st.setString(1, inizio+'%');
			
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				result=true;
			}
			
			res.close();
			conn.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
	
	public boolean controlloParola(String parola) {
			
			boolean result = false;
			
			String query = "SELECT nome FROM parola WHERE nome = ?" ;
			
			try {
				Connection conn = DBConnect.getConnection() ;
				PreparedStatement st = conn.prepareStatement(query) ;
				
				st.setString(1, parola);
				
				ResultSet res = st.executeQuery() ;
				
				if(res.next()) {
					result=true;
				}
				
				res.close();
				conn.close();
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
	
		}

}
