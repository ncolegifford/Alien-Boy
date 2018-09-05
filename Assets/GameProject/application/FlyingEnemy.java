package application;

import java.util.Random;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;

import java.util.Date;
import java.util.Random;

class FlyingEnemy extends GameCharacter {
	private static String IMAGE_IDLE = "";
	
	private static final double IMAGE_SIZE = 50;
	
	private double timeCheck = 0;
	Boolean movementAnimation = false;
	private String imageURL = "";
	private String MovementImageURL = "";
	private double delay;
	private boolean moving = false;
	private boolean isMovingUp = false;
	private boolean isMovingDown = true;
	
	
	FlyingEnemy(double x, double y, Game game, String IMAGE_URL, String MOVEMENT_IMAGE_URL) {
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

		health = 2;
	}

	@Override
	void update(Level level, double elapsedTime) {
		try {
		
        if (getX() < 0) {
            setX(0);
        }
        
        if (getX() + getFitWidth() >= (game.currentLevel.getWidth() )) {
           isMovingRight = !isMovingRight;
           isMovingLeft = isMovingLeft;
        }
        
        if (getY() >= 750 || getY() <= 0) {
			isMovingUp = !isMovingUp;
			isMovingDown = !isMovingDown;
         }
        
		if(delay<=0) {
			moving=true;
		} else {
			delay-=elapsedTime;
		}
		if(!moving) return;
		timeCheck += elapsedTime;
		
		
		Random rand1 = new Random();
		int n1= rand1.nextInt(5) +2;
		if (timeCheck > n1) {
			timeCheck = 0; 
			isMovingUp = !isMovingUp;
			isMovingDown = !isMovingDown;
		}
		
		Random rand = new Random();
		int n= rand.nextInt(5) +2;
		if (timeCheck > n) {
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
            setX(getX() + (xSpeed) * elapsedTime); //framerate indpp.
		}
        if (isMovingLeft) {
            setX(getX() - xSpeed *  elapsedTime);
        }
        if (isMovingUp) {
            setY(getY() - (xSpeed) * elapsedTime); //framerate indpp.
        }
        if (isMovingDown) {
            setY(getY() + xSpeed *  elapsedTime); //framerate indpp.
        }
        

        
        
        for (Platformer platform : level.getPlatformList()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds) && platform.isBackground == false) {
            	isMovingRight = !isMovingRight;
                isMovingLeft = !isMovingLeft;
                isMovingUp = !isMovingUp;
                isMovingDown = !isMovingDown;
                
            }
        }
                
//            	if (isMovingRight) {
//                    setX(bounds.getMinX() - imageSize - 0.01);
//	                isMovingRight = !isMovingRight;
//	                isMovingLeft = !isMovingLeft;
//                }
//            	else if (isMovingLeft) {
//                    setX(bounds.getMaxX() - 0.01);
//	                isMovingRight = !isMovingRight;
//	                isMovingLeft = !isMovingLeft;
//                }
//            	if (isMovingUp) {
//                    setY(bounds.getMinY() - imageSize - 0.01);
//	                isMovingUp = !isMovingUp;
//	                isMovingDown = !isMovingDown;
//                }
//            	else if (isMovingDown) {
//                    setY(bounds.getMaxY() + 0.01);
//	                isMovingUp = !isMovingUp;
//	                isMovingDown = !isMovingDown;
//                }
//                if (intersects(bounds) && platform.isBackground == false && getY() < bounds.getMinY()) {
//                    setY(bounds.getMinY() - imageHeight - 0.01);
//                }
//                else if (intersects(bounds) && platform.isBackground == false && getY() > bounds.getMinY()) {
//                	 setY(bounds.getMinY() + imageHeight + 0.01);
//                }
           
        
        Bounds playerBound = Main.game.mPlayer.getLayoutBounds();
        if (intersects(playerBound) && isAlive == true && Main.game.mPlayer.damaged == false) {
        	Game.lives -=1; 
        	game.mPlayer.damaged = true; 
            DamagePlayer DamageAnimation = new DamagePlayer(" ",  Main.game.mPlayer,elapsedTime);
            DamageAnimation.start(); 
        }
	
	} catch (Exception ie) {
		System.out.println(ie.getMessage());
		System.exit(1);
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
