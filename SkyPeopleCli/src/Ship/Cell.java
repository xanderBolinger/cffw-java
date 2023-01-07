package Ship;

public class Cell {

	public enum CellType {
		NUMBER,WRENCH,BLANK,CIRCLE,VEHICLE,PASSENGER,CREW
	}
	
	public CellType cellType;
	public int number;
	
	public Cell(CellType cellType) {
		this.cellType = cellType;
	}
	
	public Cell(int number) {
		cellType = CellType.NUMBER;
		this.number = number;
	}
	
}
