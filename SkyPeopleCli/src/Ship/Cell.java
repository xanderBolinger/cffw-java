package Ship;

public class Cell {

	public enum CellType {
		NUMBER,WRENCH,BLANK,CIRCLE,VEHICLE,PASSENGER,CREW
	}
	
	public CellType cellType;
	public double number;
	public char character = ' ';
	
	
	public Cell(CellType cellType) {
		this.cellType = cellType;
	}
	
	public Cell(double number) {
		cellType = CellType.NUMBER;
		this.number = number;
	}
	
	public Cell(char character) {
		this.character = character;
	}
	
}
