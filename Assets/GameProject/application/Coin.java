package application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;

public class Coin extends GameCharacter {
	private static final String IMAGE_FILENAME = "images/coinGold.png";
	private static final double IMAGE_SIZE = 40;

	public Coin(double x, double y, Game game) {
		super(x + 20, y + 20, IMAGE_SIZE, game);
		imageSize = IMAGE_SIZE;
        imageHeight = IMAGE_SIZE;
        setFitWidth(imageSize);
        setFitHeight(imageHeight);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		
	}
}
