package application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;

import java.util.Date;

class StaticEnemy extends GameCharacter {
	private static String IMAGE_IDLE = "";
	
	private static final double IMAGE_SIZE = 45;
	
	private double timeCheck = 0;
	Boolean movementAnimation = false;
	private String imageURL = "";
	private String MovementImageURL = "";
	
	
	StaticEnemy(double x, double y, Game game, String IMAGE_URL, String MOVEMENT_IMAGE_URL) {
		super(x, y + 30, IMAGE_SIZE, game);
		imageSize = IMAGE_SIZE;
		imageHeight = IMAGE_SIZE;
		imageURL = IMAGE_URL;
		MovementImageURL = MOVEMENT_IMAGE_URL;
		
		setFitWidth(imageSize);
		setFitHeight(imageHeight);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageURL));
		setImage(image);

		health = 10000000;
	}

	@Override
	void update(Level level, double elapsedTime) {
		timeCheck += elapsedTime;
		
		if (timeCheck > 0.09) {
			timeCheck = 0; 
			if (movementAnimation == false) {
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(MovementImageURL));
				setImage(image);
				movementAnimation = true; 
			}
			else {
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageURL));
				setImage(image);
				movementAnimation = false; 
			}
			
		}
		
	}
	
	
	

}
