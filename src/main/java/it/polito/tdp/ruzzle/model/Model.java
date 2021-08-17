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
		
		this.paroleValide = new ArrayList<String>();
		this.paroleValideMap = new HashMap<String,List<Pos>>();
		this.cerca(0, "", new ArrayList<Pos>(this.board.getPositions()),new ArrayList<Pos>());
		
		if(this.paroleValideMap.containsKey(parola))
			return this.paroleValideMap.get(parola);
		else
			return null;
	}

	public List<String> trovaTutte() {

		this.paroleValide = new ArrayList<String>();
		this.paroleValideMap = new HashMap<String,List<Pos>>();
		this.cerca(0, "", new ArrayList<Pos>(this.board.getPositions()),new ArrayList<Pos>());
		return this.paroleValide;
	}

	private void cerca(int livello, String parziale, List<Pos> posizioni,List <Pos>posParziale) {

		for (Pos p : posizioni) {
			
			
			if(!posParziale.contains(p))
				{	this.paroleInsensate = 0;
					List<Pos> adiacenti = this.board.getAdjacencies(p);
		
					for (Pos pos : adiacenti) {
						
						if(!posParziale.contains(pos))
						{
							String nuovaParziale = parziale.concat(this.board.getCellValueProperty(p).get().concat(this.board.getCellValueProperty(pos).get()));
						
							
							if (this.dao.controlloPrefisso(nuovaParziale)) {
								
								posParziale.add(p);
								posParziale.add(pos);
								if (this.dao.controlloParola(nuovaParziale)) {
									this.paroleValide.add(nuovaParziale);
									this.paroleValideMap.put(nuovaParziale, new ArrayList<Pos>(posParziale));
								}
								List<Pos> tempPosizioni = new ArrayList<Pos>(this.board.getAdjacencies(pos));
								//int i=tempPosizioni.indexOf(p);
								//tempPosizioni.remove(p);
								this.cerca(livello + 1, nuovaParziale, tempPosizioni,posParziale);
								//tempPosizioni.add(i, p);
								posParziale.remove(pos);
								posParziale.remove(p);
								
							}
						} else
							this.paroleInsensate++;
		
						if (this.paroleInsensate == adiacenti.size())
							return;
		
					}
				}
		}

	}
}
