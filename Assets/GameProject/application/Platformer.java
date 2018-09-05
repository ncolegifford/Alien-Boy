package application; 
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

class Platformer extends Rectangle {
    public boolean isBackground;
    static Color PLATFORM_COLOR = Color.LIGHTGRAY;

    Platformer(double x, double y, double width, double height, boolean isBackgroundObject, String ImagePath) {
        super(x, y, width, height);
        isBackground = isBackgroundObject; 
    	Image img = new Image(ImagePath);
        setFill(new ImagePattern(img));
        
        if (isBackground == true) {
        	toFront(); 
        }
        
    }
}
