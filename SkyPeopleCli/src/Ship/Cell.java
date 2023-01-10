package Ship;

public class Cell {

	public enum CellType {
		NUMBER,WRENCH,BLANK,CHARACTER,CREW
	}
	
	public CellType cellType;
	public double number;
	public char character = ' ';
	public boolean destroyed = false;
	
	public Cell(CellType cellType) {
		this.cellType = cellType;
	}
	
	public Cell(double number) {
		cellType = CellType.NUMBER;
		this.number = number;
	}
	
	public Cell(char character) {
		cellType = CellType.CHARACTER;
		this.character = character;
	}
	
	@Override
	public String toString() {
		String rslts = "";
		
		if(destroyed)
			return "X";
		
		switch(cellType) {
		case NUMBER:
			rslts = Double.toString(Math.round(number * 100.0) / 100.0); 
			break;
		case CHARACTER:
			rslts += character;
			break;
		case CREW:
			rslts = "C";
			break;
		case WRENCH:
			rslts = "W";
			break;
		case BLANK:
			rslts = "O";
			break;
		}
		
		return rslts; 
	}
	
}
