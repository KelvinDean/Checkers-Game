
import java.awt.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

public class Checker extends StackPane implements java.io.Serializable {

	public CheckerType type;
	
	private double mouseX, mouseY;
	private double oldX, oldY;
	
	CheckerType getType()
	{
		return type;
	}
	
	public double getOldX()
	{
		return oldX;
	}
	
	public double getOldY() 
	{
        return oldY;
    }
	
	
	public Checker(CheckerType type, int x, int y)
	{
		this.type = type;
		move(x,y);
		 Ellipse bg = new Ellipse(100 * 0.3125, 100 * 0.26);
	        bg.setFill(Color.GREEN);

	        bg.setStroke(Color.BLACK);
	        bg.setStrokeWidth(100 * 0.03);

	        bg.setTranslateX((100 - 100 * 0.3125 * 2) / 2);
	        bg.setTranslateY((100 - 100 * 0.26 * 2) / 2 + 100 * 0.07);

	        Ellipse ellipse = new Ellipse(100 * 0.3125, 100 * 0.26);
	        ellipse.setFill(type == CheckerType.YELLOW
	                ? Color.GREEN : Color.YELLOW);

	        ellipse.setStroke(Color.BLACK);
	        ellipse.setStrokeWidth(100 * 0.03);

	        ellipse.setTranslateX((100 - 100 * 0.3125 * 2) / 2);
	        ellipse.setTranslateY((100 - 100 * 0.26 * 2) / 2);

	        getChildren().addAll(bg, ellipse);

	        
	        setOnMousePressed(e -> {
	            mouseX = e.getSceneX();
	            mouseY = e.getSceneY();
	        });

	        setOnMouseDragged(e -> {
	            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
	        });
		
		
	}
	
	void move(int x, int y) {
        oldX = x * 100;
        oldY = y * 100;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}