package application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;

import java.util.Date;
import java.util.Random;

class ShootingEnemy extends GameCharacter {
	private static String IMAGE_IDLE = "";
	
	private static final double IMAGE_SIZE = 65;
	
	private double timeCheck = 0;
	Boolean movementAnimation = false;
	private String imageURL = "";
	private String MovementImageURL = "";
	ShootingEnemyThread st = new ShootingEnemyThread(this); 
	
	
	ShootingEnemy(double x, double y, Game game, String IMAGE_URL, String MOVEMENT_IMAGE_URL) {
		super(x + 5, y + 15, IMAGE_SIZE, game);
		imageSize = IMAGE_SIZE;
		imageHeight = IMAGE_SIZE;
		imageURL = IMAGE_URL;
		MovementImageURL = MOVEMENT_IMAGE_URL;
		timeCheck = x*100%1.5;
		
		setFitWidth(imageSize);
		setFitHeight(imageHeight);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageURL));
		setImage(image);

		health = 5;
	}

	@Override
	void update(Level level, double elapsedTime) {
		try {
		timeCheck += elapsedTime;
		
		Random rand = new Random();
		int max = 10;
		int min = 5; 
		
		
		if (timeCheck > 2.5) {
			timeCheck = 0; 
			if (movementAnimation == false && isAlive()) {
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(MovementImageURL));
				setImage(image);
				movementAnimation = true; 
				ShootingEnemyThread st = new ShootingEnemyThread(this); 
				st.start(); 	
			}
			else {

				Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageURL));
				setImage(image);
				movementAnimation = false; 
			}
			
		}
		} catch (Exception ie) {
			System.out.println(ie.getMessage());
			System.exit(1);
		}
		
	}
		
	
	
	

}
