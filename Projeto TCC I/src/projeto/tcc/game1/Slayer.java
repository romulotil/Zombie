package projeto.tcc.game1;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Slayer extends GameObject{

	public Slayer(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
    }

	@Override
    public void move() {
 
        this.mPhysicsHandler.setVelocityX(100);
 
        OutOfScreenX();
    }
	
	private void OutOfScreenX() {
        if (mX > Gameplay1_test2.CAMERA_WIDTH) { // OutOfScreenX (right)
            mX = 0;
        } else if (mX < 0) { // OutOfScreenX (left)
            mX = Gameplay1_test2.CAMERA_WIDTH;
        }
    }

}
