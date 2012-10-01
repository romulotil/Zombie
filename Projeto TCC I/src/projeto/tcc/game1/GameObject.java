package projeto.tcc.game1;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;

public abstract class GameObject extends AnimatedSprite{

	public PhysicsHandler mPhysicsHandler;
	
	public GameObject(float pX, float pY,
			TiledTextureRegion pTiledTextureRegion,
			RectangleVertexBuffer pRectangleVertexBuffer) {
		super(pX, pY, pTiledTextureRegion, pRectangleVertexBuffer);
		// TODO Auto-generated constructor stub
		this.mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.mPhysicsHandler);
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		super.onManagedUpdate(pSecondsElapsed);
	}	
	
	public abstract void move();

}
