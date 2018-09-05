package application;

import javafx.application.Platform;

public class ShootingEnemyThread extends Thread{
	
	GameCharacter mgc; 
	
	ShootingEnemyThread(GameCharacter gc) {
		mgc = gc;

	 Platform.runLater(new Runnable() {
		 	public void run() {
        	if (Main.game.doesGCExist(mgc) && mgc.isAlive== true) {
		 	Projectile projectile = new Projectile(mgc.getX() + 35 ,mgc.getY(), Main.game, mgc);
 			projectile.isMovingUp = true;
 			projectile.isMovingRight = false;
 			projectile.isMovingLeft = false; 
 			Main.game.getSceneRoot().getChildren().add(projectile);
 			Game.projectileList.add(projectile);
        	}
        	else {
     			Thread.yield(); 
        	}
         }
     });
	
}


}