package main;

import java.util.Arrays;
import util.*;

public class Game {
	
	private GameEditor editor = new GameEditor();
	private int[] cell = new int[81];
	private int[] cellSol = new int[81];
	private boolean[] isDefaultCell = new boolean[81];
	private Difficulty difficulty;

	public Game(Difficulty diff) {
		cellSol = editor.solve(cellSol);
		for (int i = 0; i < 81; i++) cell[i] = cellSol[i];
		difficulty = diff;
		hideFields(difficulty);
		
	}
	
	public int[] getGrid () {
		return cell;
	}
	
	public void hideFields(Difficulty diff) {

		cell = editor.hide(cellSol, diff);
		for (int i = 0; i < 81; i++) isDefaultCell[i] = (cell[i] == 0)?false:true;
			
	}
	
	public void setField(int index, int value) {
		cell[index] = value;
	}
	
	
	public boolean isOver() {
		return Arrays.equals(cell, cellSol);
	}
	
	
}