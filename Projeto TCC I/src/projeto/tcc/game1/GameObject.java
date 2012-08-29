package projeto.tcc.game1;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;

public abstract class GameObject extends AnimatedSprite {

	public GameObject(float pX, float pY, TiledTextureRegion pTiledTextureRegion, RectangleVertexBuffer pRectangleVertexBuffer) {
		super(pX, pY, pTiledTextureRegion, pRectangleVertexBuffer);
		this.mPhysicsHandler = new PhysicsHandler(this);
        this.registerUpdateHandler(this.mPhysicsHandler);
	}

	public PhysicsHandler mPhysicsHandler;
	
	@Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        move();
 
        super.onManagedUpdate(pSecondsElapsed);
    }
	
	public abstract void move();
	
}
