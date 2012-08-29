package projeto.tcc.game1;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;

public class Slayer extends GameObject{

	public Slayer(float pX, float pY, TiledTextureRegion pTiledTextureRegion,
			RectangleVertexBuffer pRectangleVertexBuffer) {
		super(pX, pY, pTiledTextureRegion, pRectangleVertexBuffer);
		// TODO Auto-generated constructor stub
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
