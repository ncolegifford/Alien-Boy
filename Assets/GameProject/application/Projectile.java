package application;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;

public class Projectile extends GameCharacter {
	private static final String IMAGE_FILENAME = "images/laserRedDot.png";
	private static final double IMAGE_SIZE = 8;
	
	private GameCharacter owner;
	boolean isMovingUp; 
	
	public Projectile(double x, double y, Game game, GameCharacter owner) {
		super(x, y, IMAGE_SIZE, game);
		this.owner = owner;
		imageSize = IMAGE_SIZE;
        imageHeight = IMAGE_SIZE;
        setFitWidth(imageSize);
        setFitHeight(imageHeight);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
		xSpeed = DEFAULT_SPEED * 2;
		forward = owner.forward;
		isMovingRight = forward;
		isMovingLeft = !forward;
		isMovingUp = false; 
	}
	
	@Override
	public void update(Level level, double elapsedTime) {
		try {
		if (isMovingRight) {
            setX(getX() + xSpeed * elapsedTime); //framerate indpp.
        }
        if (isMovingLeft) {
            setX(getX() - xSpeed * elapsedTime);
        }
        
        if (isMovingUp) {
        	setY(getY() - xSpeed * elapsedTime); 
        }
        
        for (Platformer platform : level.getPlatformList()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds) && platform.isBackground == false) {
               isAlive = false;
            }
        }
        
        
        for (WalkingEnemy we : level.walkingList) {
        	Bounds bounds = we.getLayoutBounds();
            if (intersects(bounds) && we.isAlive == true && we.damaged == false && owner == Main.game.mPlayer && isAlive) {
               	isAlive = false;
            	System.out.println("Hit"); 
            	
            	we.health -=1; 
        		we.damaged = true; 
                
        		DamageEnemy DamageAnimation = new DamageEnemy(we, elapsedTime);
                DamageAnimation.start(); 
            
            	if (we.health <= 0) {
            		we.isAlive = false; 
            	}      
            }
        }
        
        for (FlyingEnemy we : level.flyingList) {
        	Bounds bounds = we.getLayoutBounds();
            if (intersects(bounds) && we.isAlive == true && we.damaged == false && owner == Main.game.mPlayer && isAlive) {
               	isAlive = false;
            	System.out.println("Hit"); 
            	
            	we.health -=1; 
        		we.damaged = true; 
                
        		DamageEnemy DamageAnimation = new DamageEnemy(we, elapsedTime);
                DamageAnimation.start(); 
            
            	if (we.health <= 0) {
            		we.isAlive = false; 
            	}      
            }
        }
        
        
        
        
        Bounds playerBound = Main.game.mPlayer.getLayoutBounds();
        if (intersects(playerBound) && owner != Main.game.mPlayer && isAlive == true && Main.game.mPlayer.damaged == false) {
    		Game.lives -=1; 
        	game.mPlayer.damaged = true; 
            DamagePlayer DamageAnimation = new DamagePlayer(" ",  Main.game.mPlayer,elapsedTime);
            DamageAnimation.start(); 
        }
        
  
        
        
        
        if (getX() < 0) {
          isAlive = false; 
        }
       
	} catch (Exception ie) {
		System.out.println(ie.getMessage());
		System.exit(1);
		
	}
	}
}
