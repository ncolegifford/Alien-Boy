package application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;

import java.util.Date;
import java.util.Random;

class WalkingEnemy extends GameCharacter {
	private static String IMAGE_IDLE = "";
	
	private static final double IMAGE_SIZE = 45;
	
	private double timeCheck = 0;
	Boolean movementAnimation = false;
	private String imageURL = "";
	private String MovementImageURL = "";
	private double delay;
	private boolean moving = false;
	
	
	
	WalkingEnemy(double x, double y, Game game, String IMAGE_URL, String MOVEMENT_IMAGE_URL) {
		super(x, y + 30, IMAGE_SIZE, game);
		xSpeed = 150;
		delay = x%2;
		imageSize = IMAGE_SIZE;
		imageHeight = IMAGE_SIZE;
		imageURL = IMAGE_URL;
		MovementImageURL = MOVEMENT_IMAGE_URL;
		isMovingLeft = true;
		damaged = false; 
		
		setFitWidth(imageSize);
		setFitHeight(imageHeight);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageURL));
		setImage(image);

		health = 3;
	}

	@Override
	void update(Level level, double elapsedTime) {
		if(delay<=0) {
			moving=true;
		} else {
			delay-=elapsedTime;
		}
		if(!moving) return;
		timeCheck += elapsedTime;
		Random rand = new Random();
		int max = 10;
		int min = 5; 
		
		
		
		if (timeCheck > 1.5) {
			timeCheck = 0; 
			isMovingLeft = !isMovingLeft;
			isMovingRight = !isMovingRight;
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
	
		if (isMovingRight) {
            setX(getX() + xSpeed * elapsedTime); //framerate indpp.
		}
        if (isMovingLeft) {
            setX(getX() - xSpeed * elapsedTime);
        }
        
        for (Platformer platform : level.getPlatformList()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds) && platform.isBackground == false) {
            	if (isMovingRight) {
                    setX(bounds.getMinX() - imageSize - 0.01);
                }
                if (isMovingLeft) {
                    setX(bounds.getMaxX() + 0.01);
                }
            }
        }
        
        Bounds playerBound = Main.game.mPlayer.getLayoutBounds();
        if (intersects(playerBound) && isAlive == true && Main.game.mPlayer.damaged == false) {
        	Game.lives -=1; 
        	game.mPlayer.damaged = true; 
            DamagePlayer DamageAnimation = new DamagePlayer(" ",  Main.game.mPlayer,elapsedTime);
            DamageAnimation.start(); 
        }
	}
	
	/*
	@Override
	void updateMovementX(Level level, double elapsedTime) {
		
	     if (isMovingRight) {
	         setX(getX() + xSpeed * elapsedTime); //framerate indpp.
	     }
	     if (isMovingLeft) {
	         setX(getX() - xSpeed * elapsedTime);
	     }
	     
	          
	     for (Platformer platform : level.getPlatformList()) {
	         Bounds bounds = platform.getLayoutBounds();
	         if (intersects(bounds) && platform.isBackground == false) {
	             if (isMovingRight) {
	                 setX(bounds.getMinX() - imageSize - 0.01);
	             }
	             if (isMovingLeft) {
	                 setX(bounds.getMaxX() + 0.01);
	             }
	         }
	     }
	}
	
	@Override
    void updateMovementY(Level level, double elapsedTime) {
        yVelocity += FALL_ACCEL * elapsedTime;
        setY(getY() + yVelocity * elapsedTime);
        
        for (Platformer platform : level.getPlatformList()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds) && platform.isBackground == false && getY() < bounds.getMinY()) {
                setY(bounds.getMinY() - imageHeight - 0.01);
                canJump = true;
                yVelocity = 0;
                yAccel = 0;
            }
            else if (intersects(bounds) && platform.isBackground == false && getY() > bounds.getMinY()) {
            	System.out.println("Top"); 
            	 setY(bounds.getMinY() + imageHeight + 10);
            	 yVelocity = 0;
                 yAccel = 0;
            }
        }
        
    }
	*/
	

}
