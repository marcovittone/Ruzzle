package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Memorizza le lettere presenti nella scacchiera Ruzzle.
 * @author Fulvio
 *
 */
public class Board {
	private List<Pos> positions;
	private Map<Pos, StringProperty> cells;

	private int size;

	/**
	 * Crea una nuova scacchiera della dimensione specificata
	 * @param size
	 */
	public Board(int size) {
		this.size = size;

		//Definisco le "caselle" del gioco (e la forma del piano di gioco)
		this.positions = new ArrayList<>();
		for (int row = 0; row < this.size; row++) {
			for (int col = 0; col < this.size; col++) {
				this.positions.add(new Pos(row, col));
			}
		}

		//Definisco il contenuto delle caselle
		this.cells = new HashMap<>();

		//Ogni casella conterrà una String Property, inizialmente vuota, per contenere il proprio carattere  
		for (Pos p : this.positions) {
			this.cells.put(p, new SimpleStringProperty());
		}
	}
	
	/**
	 * Fornisce la {@link StringProperty} corrispondente alla {@link Pos} specificata. <p>
	 * 
	 * Può essere usata per sapere che lettera è presente
	 * (es. {@code getCellValueProperty(p).get()}) oppure per fare un binding della proprietà stessa sulla mappa visuale.
	 * @param p
	 * @return
	 */
	public StringProperty getCellValueProperty(Pos p) {
		return this.cells.get(p) ;
	}

	/**
	 * Restituisce la lista di oggetti {@link  Pos} che corrispondono alle posizioni lecite sulla scacchiera. Gli elementi sono ordinati per righe.
	 * @return
	 */
	public List<Pos> getPositions() {
		return positions;
	}

	/**
	 * Crea una nuova scacchiera generando tutte lettere casuali
	 */
	public void reset() {
		for(Pos p: this.positions) {
			
//			//TODO: migliorare l'assegnazione secondo la probabiltà di ogni lettetera di essere utilizzata nella lingua italiana
//			int random = (int)(Math.random()*26) ;
//			String letter = Character.toString((char)('A'+random)) ; //generatore di caratteri random non so come funzioni (nello specifico, a leggere ce la faccio ancora...)
//			
//			//grazie al "binding" fatto in FXMLController, la "set" modifica direttamente il testo del bottone collegato alla posizione corrente //
//			this.cells.get(p).set(letter);
			
			this.cells.get(new Pos(0,0)).set("N");
			this.cells.get(new Pos(0,1)).set("A");
			this.cells.get(new Pos(0,2)).set("S");
			this.cells.get(new Pos(0,3)).set("P");
			this.cells.get(new Pos(1,0)).set("M");
			this.cells.get(new Pos(1,1)).set("S");
			this.cells.get(new Pos(1,2)).set("Z");
			this.cells.get(new Pos(1,3)).set("B");
			this.cells.get(new Pos(2,0)).set("V");
			this.cells.get(new Pos(2,1)).set("S");
			this.cells.get(new Pos(2,2)).set("Q");
			this.cells.get(new Pos(2,3)).set("C");
			this.cells.get(new Pos(3,0)).set("I");
			this.cells.get(new Pos(3,1)).set("R");
			this.cells.get(new Pos(3,2)).set("Z");
			this.cells.get(new Pos(3,3)).set("D");
		
			
		}
	}
	
	/**
	 * Data una posizione, restituisce tutte le posizioni adiacenti
	 * @param p
	 * @return
	 */
	
	// da studiare
	public List<Pos> getAdjacencies(Pos p) {
		
		
		List<Pos> result = new ArrayList<>() ;
		
		for(int r = -1; r<=1; r++) {
			for(int c = -1; c<=1; c++) {
				// tutte le 9 posizioni nell'intorno della cella				
				if(r!=0 || c!=0) { // escludo la cella stessa (offset 0,0)
					Pos adj = new Pos(p.getRow()+r, p.getCol()+c) ;
					//controllo che gli indici non siano fuori dalla griglia
					if(positions.contains(adj)) {
						result.add(adj) ;
					}
				}
			}
		}
		
		return result ;
	}


	
}
