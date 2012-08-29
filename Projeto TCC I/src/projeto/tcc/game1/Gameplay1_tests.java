package projeto.tcc.game1;

import java.util.Arrays;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.extension.physics.box2d.util.Vector2Pool;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.sensor.accelerometer.AccelerometerData;
import org.anddev.andengine.sensor.accelerometer.IAccelerometerListener;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.badlogic.gdx.math.Vector2;

import android.os.Handler;

public class Gameplay1_tests extends BaseGameActivity{

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	
	private Camera mCamera;
	private Texture mSlayerTexture;
	private TiledTextureRegion mTileSlayerTexture;
	private AnimatedSprite[] mSlayer = new AnimatedSprite[1];
	private Handler mHandler;
	
	public Engine onLoadEngine() {
		mHandler = new Handler();
		this.mCamera = new Camera(0,0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, 
						CAMERA_HEIGHT),this.mCamera));
	}

	public void onLoadResources() {		
		mSlayerTexture = new Texture(256, 512, TextureOptions.DEFAULT);
		mTileSlayerTexture = TextureRegionFactory.createTiledFromAsset(this.mSlayerTexture, this, "gfx/slayer1.png", 0,0,2,2);
		mEngine.getTextureManager().loadTexture(this.mSlayerTexture);		
	}

	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		final Scene scene = new Scene(1);
		
		this.mHandler.postDelayed(mStartSlayer, 2000);
		
		return scene;
	}

	public void onLoadComplete() {
	}
	
	private Runnable mStartSlayer = new Runnable() {
		public void run() {
			Scene scene = Gameplay1_tests.this.mEngine.getScene();
			mSlayer[0] = new AnimatedSprite(0, 100, mTileSlayerTexture.clone());
			final long[] frameDuration = new long[2];
			Arrays.fill(frameDuration, 150);
			mSlayer[0].animate(frameDuration, 0, 1, true);
			mSlayer[0].registerEntityModifier(new SequenceEntityModifier(
					new AlphaModifier(0.5f, 0.0f, 1.0f),
					new MoveModifier(60.0f,  
							mSlayer[0].getX(), 3000.0f,
							100.0f, 100.0f)));
						 
			scene.getLastChild().attachChild(mSlayer[0]);
		}
	};

}
