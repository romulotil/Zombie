package projeto.tcc.game1;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;
import org.anddev.andengine.opengl.vertex.VertexBuffer;

import br.com.scientists.helper.AccelerometerHelper;

public class Slayer extends GameObject {

//	public Slayer(float pX, float pY, TiledTextureRegion pTiledTextureRegion,
//			VertexBuffer pVertexBuffer) {
//		super(pX, pY, pTiledTextureRegion, pVertexBuffer);
//		// TODO Auto-generated constructor stub
//	}
	
	public Slayer(float pX, float pY, TiledTextureRegion pTiledTextureRegion,
			RectangleVertexBuffer pRectangleVertexBuffer) {
		super(pX, pY, pTiledTextureRegion, pRectangleVertexBuffer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		this.mPhysicsHandler.setVelocityX(AccelerometerHelper.TILT * 100);
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
