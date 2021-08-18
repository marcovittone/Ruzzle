package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import it.polito.tdp.ruzzle.db.DizionarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {
	private final int SIZE = 4;
	private Board board;
	private List<String> dizionario;
	private StringProperty statusText;
	private DizionarioDAO dao;

	// mie variabili
	private List<String> paroleValide;
	private Map<String,List<Pos>> paroleValideMap;
	private int paroleInsensate;
	// private Set<StringExpression> dizionarioSet ;

	public Model() {
		this.statusText = new SimpleStringProperty();

		this.board = new Board(SIZE);
		this.board.reset(); // messa da me per iniziare subito con lettere
		this.dao = new DizionarioDAO();
		this.dizionario = dao.listParola();
		statusText.set(String.format("%d parole lette", this.dizionario.size()));

	}

	public void reset() {
		this.board.reset();
		this.statusText.set("Board Reset");
	}

	public Board getBoard() {
		return this.board;
	}

	public final StringProperty statusTextProperty() {
		return this.statusText;
	}

	public final String getStatusText() {
		return this.statusTextProperty().get();
	}

	public final void setStatusText(final String statusText) {
		this.statusTextProperty().set(statusText);
	}

	public List<Pos> trovaParola(String parola) {
		
		for(Pos p: this.board.getPositions())
		{
			if(this.board.getCellValueProperty(p).get().charAt(0) == parola.charAt(0))
			{
				List<Pos> posizioni = new ArrayList<Pos>();
				posizioni.add(p);
				if(this.cerca(1, parola, posizioni))
					return posizioni;
					
			}
		}
		
		return null;
	}

	public List<String> trovaTutte() {
		return null;
	}

	private boolean cerca(int livello, String parola, List<Pos> posizioni) {
		
		if(livello == parola.length())
			return true;
		else
		{
			List<Pos> ad = this.board.getAdjacencies(posizioni.get(posizioni.size()-1));
			
			for(Pos p: ad)
			{
				if(!posizioni.contains(p) && parola.charAt(livello)== this.board.getCellValueProperty(p).get().charAt(0))
				{
					posizioni.add(p);
					if(this.cerca(livello+1, parola, posizioni))
						return true;
					posizioni.remove(posizioni.size()-1);
				}
			}
		}
		
		return false;

	}
	
	public boolean parolaValida (String parola) {
		
		if(this.dizionario.contains(parola))
			return true;
		return false;
	}
}
