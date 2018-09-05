package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;

class Player extends GameCharacter {
	private static String IMAGE_IDLE = "images/alienBlue_stand";
	private static String IMAGE_WALKING = "images/alienBlue_walk";
	private static String IMAGE_JUMPING = "images/alienBlue_jump";
	private static String IMAGE_FALLING = "images/alienBlue_falling";
	private static final double IMAGE_SIZE = 50;

	private static int SHOOTER_X_OFFSET = 37;
	private static int SHOOTER_Y_OFFSET = 42;
	private boolean isInvincible;


	Boolean damaged = false; 

	private Shooter shooter;

	Player(double x, double y, Game game) {
		super(x, y, IMAGE_SIZE, game);
		imageSize = IMAGE_SIZE;
		imageHeight = IMAGE_SIZE + 20;
		
		if(isEquipped(0)) {
			IMAGE_IDLE = "images/alienGreen_stand";
			IMAGE_WALKING = "images/alienGreen_walk";
			IMAGE_JUMPING = "images/alienGreen_jump";
			IMAGE_FALLING = "images/alienGreen_falling";
		} else if (isEquipped(1)) {
			IMAGE_IDLE = "images/alienBlue_stand";
			IMAGE_WALKING = "images/alienBlue_walk";
			IMAGE_JUMPING = "images/alienBlue_jump";
			IMAGE_FALLING = "images/alienBlue_falling";
		} else if (isEquipped(2)) {
			IMAGE_IDLE = "images/alienYellow_stand";
			IMAGE_WALKING = "images/alienYellow_walk";
			IMAGE_JUMPING = "images/alienYellow_jump";
			IMAGE_FALLING = "images/alienYellow_falling";
		} else {
			IMAGE_IDLE = "images/alienPink_stand";
			IMAGE_WALKING = "images/alienPink_walk";
			IMAGE_JUMPING = "images/alienPink_jump";
			IMAGE_FALLING = "images/alienPink_falling";
		}
		
		
		setFitWidth(imageSize);
		setFitHeight(imageHeight);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_IDLE + ".png"));
		setImage(image);

	}

	void setShooter(Shooter shooter) {
		this.shooter = shooter;
	}

	Shooter getShooter() {
		return shooter;
	}

	@Override
	void update(Level level, double elapsedTime) {
		try {
		super.update(level, elapsedTime);

 		if (Game.lives <= 0) {
 			System.out.println("Dead"); 
 			 Platform.runLater(new Runnable() {
                 @Override public void run() {
 		 			Main.restartGame(); 
                 }
             });
 			
 		}

        for (StaticEnemy staticEnemy : level.staticList) {
            Bounds bounds = staticEnemy.getLayoutBounds();
            if (intersects(bounds) && damaged == false && staticEnemy.isAlive == true) {
            	System.out.println("SIZE" + level.staticList.size());
            	System.out.println(staticEnemy.getX() - getX());
            	System.out.println(staticEnemy.getY() - getY());

            	System.out.println("Static Enemy");
        		Game.lives -=1; 
            		damaged = true; 
                    DamagePlayer DamageAnimation = new DamagePlayer(" ", this, elapsedTime);
                    DamageAnimation.start(); 
            }
        }
        
        for (WalkingEnemy walkingEnemy : level.walkingList) {
            Bounds bounds = walkingEnemy.getLayoutBounds();
            if (intersects(bounds) && damaged == false && walkingEnemy.isAlive == true) {
            	System.out.println("Walking Enemy");
        		Game.lives -=1; 
            		damaged = true; 
                    DamagePlayer DamageAnimation = new DamagePlayer(" ", this, elapsedTime);
                    DamageAnimation.start(); 
            }
        }
        
        for (ShootingEnemy se : level.shootingList) {
            Bounds bounds = se.getLayoutBounds();
            if (intersects(bounds) && damaged == false && se.isAlive == true) {
            	System.out.println("Shooting Enemy");
        		Game.lives -=1; 
            		damaged = true; 
                    DamagePlayer DamageAnimation = new DamagePlayer(" ", this, elapsedTime);
                    DamageAnimation.start(); 
            }
        }
        

        
		shooter.setX(getX() + SHOOTER_X_OFFSET);
		shooter.setY(getY() + SHOOTER_Y_OFFSET);
		
		// Change animations
		switch (animationState) {
		case Idle: {
			if (forward) {
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_IDLE + ".png"));
				setImage(image);
				SHOOTER_X_OFFSET = 37;
				SHOOTER_Y_OFFSET = 42;

			} else {
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_IDLE + "_reverse.png"));
				setImage(image);
				SHOOTER_X_OFFSET = -7;
				SHOOTER_Y_OFFSET = 42;
			}
			break;
		}
		case WalkingRight: {
			Image image = new Image(
					getClass().getClassLoader().getResourceAsStream(IMAGE_WALKING + (int) animTimer + ".png"));
			setImage(image);

			if ((int) animTimer == 1) {
				SHOOTER_X_OFFSET = 38;
				SHOOTER_Y_OFFSET = 37;
			} else {
				SHOOTER_X_OFFSET = 37;
				SHOOTER_Y_OFFSET = 42;
			}
			break;
		}
		case WalkingLeft: {
			Image image = new Image(
					getClass().getClassLoader().getResourceAsStream(IMAGE_WALKING + (int) animTimer + "_reverse.png"));
			setImage(image);
			if ((int) animTimer == 1) {
				SHOOTER_X_OFFSET = -9;
				SHOOTER_Y_OFFSET = 37;
			} else {
				SHOOTER_X_OFFSET = -9;
				SHOOTER_Y_OFFSET = 42;
			}
			break;
		}
		case Jumping: {
			if (forward) {
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_JUMPING + ".png"));
				setImage(image);
				SHOOTER_X_OFFSET = 37;
				SHOOTER_Y_OFFSET = 34;
			} else {
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_JUMPING + "_reverse.png"));
				setImage(image);
				SHOOTER_X_OFFSET = -10;
				SHOOTER_Y_OFFSET = 34;
			}
			break;
		}
		case Falling: {	        
			if (forward) {
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FALLING + ".png"));
				setImage(image);
				SHOOTER_X_OFFSET = 40;
				SHOOTER_Y_OFFSET = 35;			
				
			}
			else {
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FALLING + "_reverse.png"));
				setImage(image);
				SHOOTER_X_OFFSET = -10;
				SHOOTER_Y_OFFSET = 35;
			}
		}
		case Damaged:
		}	
		
		
        if (getY() > 1000) {	
        	System.out.println(getY());
            setX(Level.levelSpawnx);
            setY(Level.levelSpawny);
            Main.game.sceneRoot.setLayoutX(-1 * getX());
        }
		} catch (Exception ie) {
    		System.out.println(ie.getMessage());
    		System.exit(1);
    		
    	}
		
	}

	void setInvincible() {
		isInvincible = true;
	}

	void superSpeed() {
		xSpeed = DEFAULT_SPEED * 3;
	}

	void superJump() {
		jumpHeight = DEFAULT_JUMP * 2;
	}

	@Override
	void updateAliveStatus(Level level) {
		if (isInvincible) {
			return;
		}
	}
	
	@Override
	   void updateMovementX(Level level, double elapsedTime) {
		try {
		
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
        
        for (Coin coin : level.coinList) {
            Bounds bounds = coin.getLayoutBounds();
            if (intersects(bounds)) {
            	coin.isAlive = false;
            	if(Main.UserId != 0) {
	            	CoinThread ct = new CoinThread();
	            	ct.start();
            	}
            }
        }
        ;
       
        if (getX() < 0) {
            setX(0);
        }
		} catch (Exception ie) {
    		System.out.println(ie.getMessage());
    		System.exit(1);
    		
    	}
        
    }
	@Override
    void updateMovementY(Level level, double elapsedTime) {
		try {
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
	        
		} catch (Exception ie) {
			System.out.println(ie.getMessage());
			System.exit(1);
			
		}
	}
	
	public boolean isEquipped(int index) {
		if(Main.UserId==0) return false;
		try {
			Statement st = Main.conn.createStatement();
			ResultSet rs = st.executeQuery("select * from Userinformation.userinfo where userid=\"" + Main.UserId + "\";");
			String equipStatus = "";
			while(rs.next()) {
				equipStatus = rs.getString("equipstatus");
				System.out.println("Equip status: " + equipStatus);
			}
			if(equipStatus.charAt(index)=='y') {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
