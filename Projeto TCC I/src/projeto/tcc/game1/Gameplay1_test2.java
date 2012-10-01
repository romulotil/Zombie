package projeto.tcc.game1;

import org.anddev.andengine.engine.Engine;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.BaseBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.opengl.vertex.RectangleVertexBuffer;
import org.anddev.andengine.opengl.vertex.VertexBuffer;

import android.graphics.Color;
import android.os.Handler;

public class Gameplay1_test2 extends BaseGameActivity{

	public static final int CAMERA_WIDTH = 480;
	public static final int CAMERA_HEIGHT = 320;
	
	private Camera mCamera;
	private Scene mainScene;
	
	private Texture mSlayerTexture;
	private TiledTextureRegion mTileSlayerTexture;
	
	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		this.mCamera = new Camera(0,0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, 
						CAMERA_HEIGHT),this.mCamera));
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		mSlayerTexture = new Texture(256, 512, TextureOptions.DEFAULT);
		mTileSlayerTexture = TextureRegionFactory.createTiledFromAsset(this.mSlayerTexture, this, "gfx/Sprites/slayer1.png", 0,0,2,2);
		mEngine.getTextureManager().loadTexture(this.mSlayerTexture);	
	}

	@Override
	public Scene onLoadScene() {
		this.mainScene = new Scene();
		
		final int StartX = 0;
		final int StartY = (int)(CAMERA_HEIGHT - mTileSlayerTexture.getHeight());
		
		final Slayer slayer = new Slayer(StartX, StartY, mTileSlayerTexture, null);
		
		mainScene.attachChild(slayer);
		slayer.animate(150);
		
		return this.mainScene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
	
	public void changeSprite() {
		if (mTileSlayerTexture.getWidth() == 143){
			mTileSlayerTexture = TextureRegionFactory.createTiledFromAsset(this.mSlayerTexture, this, "gfx/Sprites/Slayer1.png", 0, 0, 2, 2);
		}
		else{
			mTileSlayerTexture = TextureRegionFactory.createTiledFromAsset(this.mSlayerTexture, this, "gfx/Sprites/Slayer1.png", 0, 0, 2, 2);
			
		}
	}

}
