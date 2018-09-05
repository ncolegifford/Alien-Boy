package application; 

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

/**
 * Super-class for all characters existing in game. Can move right and left,
 * and can jump. Cannot move through platforms. Does not die to anything by
 * default, but this method will be overwritten by subclasses.
 */
class GameCharacter extends ImageView {
    protected static final double DEFAULT_SPEED = 300;
    protected static final double DEFAULT_JUMP = 850;
    protected static final double FALL_ACCEL = 2000;

    protected double imageSize;
    protected double imageHeight;
    protected boolean isMovingRight, isMovingLeft;
    protected boolean canJump;
    protected double jumpHeight;
    protected double xSpeed;
    protected double yVelocity;
    protected double yAccel;
    protected boolean isAlive;
    protected int health; 
    protected boolean forward; // true if facing right
	public boolean damaged; 
    
    // Animation variables
    protected double animTimer = 0;
    protected double animSpeed = 8;
    
    protected Game game;
   
    public enum animationStates {
    	Idle,
    	WalkingRight,
    	WalkingLeft,
    	Jumping,
    	Falling,
    	Damaged, 
    }
    
    protected animationStates animationState; 
    
    /**
     * Creates character of specified size and initializes default speed and
     * jump height.
     * @param x is x-position.
     * @param y is y-position.
     * @param size is size of character.
     */
    GameCharacter(double x, double y, double size, Game game) {
    	this.game = game;
    	
        setX(x);
        setY(y);

        /*imageSize = size;
        imageHeight = size + 20;
        setFitWidth(imageSize);
        setFitHeight(imageHeight);*/

        isAlive = true;
        xSpeed = DEFAULT_SPEED;
        jumpHeight = DEFAULT_JUMP;
        forward = true;
        
        //game.getSceneRoot().getChildren().add(this);
        game.getGameCharacters().add(this);
    }

    boolean isAlive() {
        return isAlive;
    }


    /**
     * Sets character to move right.
     */
    void moveRight() {
    	animationState = animationStates.WalkingRight;
    	//Update sprite frame function here
        isMovingRight = true;
        isMovingLeft = false;
        forward = true;
    }

    /**
     * Sets character to move left.
     */
    void moveLeft() {
    	animationState = animationStates.WalkingLeft;
    	//Update sprite frame function here
        isMovingLeft = true;
        isMovingRight = false;
        forward = false;
    }

    /**
     * Sets character to stop moving.
     */
    void stall() {
    	animationState = animationStates.Idle;
    	//Update sprite frame function here
        isMovingLeft = false;
        isMovingRight = false;
    }

    /**
     * Sets character to jump, if possible.
     */
    void jump(double elapsedTime) {    	

        animationState = animationStates.Jumping; 
    	//Update sprite frame function here
        if (canJump) {
            yVelocity -= jumpHeight;
        }
        canJump = false;
    }
  
    /**
     * Update character movement and check if it died.
     * @param level is Level to update information on.
     * @param elapsedTime is time spent updating.
     */
    void update(Level level, double elapsedTime) {
        updateMovementX(level, elapsedTime);
        updateMovementY(level, elapsedTime);
        updateAliveStatus(level);
        animTimer += animSpeed * elapsedTime;
        if (animTimer >= 2) {
        	animTimer = animTimer % 2;
        }
        if (yVelocity > 0) {
        	animationState = animationStates.Falling;
        }
    }
    

    /**
     * Updates character's X direction movement based on current movement
     * status. Prevents character from moving through platforms or offscreen.
     * @param level is Level to update information in.
     * @param elapsedTime is time spent updating.
     */
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
        if (getX() < 0) {
            setX(0);
        }

    }

    /**
     * Updates character's Y direction movement. If mid-air, y velocity is
     * increased by the given fall acceleration variable. Prevents character
     * from falling through platform. Once a player touches a platform, its
     * y velocity is reset and its ability to jump is restored.
     * @param level is Level to update information in.
     * @param elapsedTime is time spent updating.
     */
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
                return;
            }
            else if (intersects(bounds) && platform.isBackground == false && getY() > bounds.getMinY()) {
            	System.out.println("Top"); 
            	 setY(bounds.getMinY() + imageHeight + 10);
            	 yVelocity = 0;
                 yAccel = 0;
                 return;
            }
        }
    }

    /**
     * Updates whether player is still alive. This is used by Game to determine
     * whether the character should be removed from screen. By default,
     * GameCharacter cannot die. This may have been better to implement as an
     * abstract method.
     * @param level is Level to update information in.
     */
    void updateAliveStatus(Level level) {
        return;
    }
}
