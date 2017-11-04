package util;

public enum Difficulty {

	EASY(15),
	MEDIUM(10),
	HARD(5),
	VERY_HARD(0);
	
	private final int extraCells;   // in kilograms
	
	Difficulty(int extraCells) {
		this.extraCells = extraCells;
	}
	
	public int extra() { return extraCells; }
}
