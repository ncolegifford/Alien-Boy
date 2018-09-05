package application;

import javafx.scene.image.Image;

public class Shooter extends GameCharacter {
	private static final String IMAGE_FILENAME = "images/laserRedBurst.png";
	private static final double IMAGE_SIZE = 20;
	
	public Shooter(double x, double y, Game game) {
		super(x, y, IMAGE_SIZE, game);
		imageSize = IMAGE_SIZE;
        imageHeight = IMAGE_SIZE;
        setFitWidth(imageSize);
        setFitHeight(imageHeight);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
		setImage(image);
	}
	
	
	/**
	 * The shooter should not update itself at all
	 * It should instead be controlled entirely by the Player's position updates
	 */
	@Override
	void update(Level level, double elapsedTime) {
		
	}
}
