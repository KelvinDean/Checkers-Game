import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle{
	
	public Checker checker;
	
	public boolean hasChecker() {
        return checker != null;
    }

    public Checker getChecker() {
        return checker;
    }

    public void setChecker(Checker checker) {
        this.checker = checker;
    }
	
    public Tile(boolean light, int x, int y) {
        setWidth(Board.TILE_SIZE);
        setHeight(Board.TILE_SIZE);

        relocate(x * Board.TILE_SIZE, y * Board.TILE_SIZE);

        setFill(light ? Color.BLACK : Color.WHITE);
    }
}
