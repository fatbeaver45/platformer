package gamelogic.enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gameengine.PhysicsObject;
import gameengine.hitbox.RectHitbox;
import gamelogic.GameResources;
import gamelogic.level.Level;
import gamelogic.tiles.Tile;

public class Star extends PhysicsObject{

    public BufferedImage image;
    public boolean dir; // true means going left
    private boolean hitWall = false;

    public Star(float x, float y, boolean direction, Level level){
        super(x, y,(int)(level.getLevelData().getTileSize()), (int)(level.getLevelData().getTileSize()), level);
        this.hitbox = new RectHitbox(this, 15, 15, 45, 45);
        this.image = GameResources.star;
        dir = direction;
        level.totalstar.add(this);
    }   

    @Override
    public void draw(Graphics g){
        g.drawImage(image, (int)position.x, (int)position.y, width, height, null);
        hitbox.draw(g);
    }

    public void updateStar(float tslf) { //Same code as in update() in Physics object, but with some modifications
		// movementVector.y += (Level.GRAVITY * Level.GRAVITY) * tslf;

		updateCollisionMatrix(tslf); // checking collision based on the new position -> current movement Vector

		//Collision-handling 
		Tile bot = collisionMatrix[BOT];
		if(bot != null) {
			position.y = bot.getHitbox().getY() - (hitbox.getOffsetY() + hitbox.getHeight());
			movementVector.y = 0;
            hitWall = true;

		}
		Tile top = collisionMatrix[TOP];
		if(top != null) {
			position.y = (top.getHitbox().getY() + top.getHitbox().getHeight()) - hitbox.getOffsetY();
			movementVector.y = 0;
            hitWall = true;

		}
		Tile lef = collisionMatrix[LEF];
		if(lef != null) {
			position.x = (lef.getHitbox().getX() + lef.getHitbox().getWidth()) - hitbox.getOffsetX();
			movementVector.x = 0;
            hitWall = true;
		}
		Tile rig = collisionMatrix[RIG];
		if(rig != null) {
			position.x = rig.getHitbox().getX() - (hitbox.getOffsetX() + hitbox.getWidth());
			movementVector.x = 0;
            hitWall = true;
		}

		position.x += movementVector.x * tslf;
		position.y += movementVector.y * tslf;
		
		hitbox.update(); // -> saving old position
	}

    @Override
    public void update(float tslf){
        // super.update(tslf);
        updateStar(tslf);

        if(!dir){
            movementVector.x = -160;
        }
        else{
            movementVector.x = 160;
        }
    }

    public boolean hasHitWall(){
        return hitWall;
    }
    public void starNoHitbox(){ 
        this.hitbox = new RectHitbox(this, 0, 0, 0, 0);
    }
    
}
