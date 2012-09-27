package projeto.tcc.game3;

import java.util.Arrays;
import java.util.Random;

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
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import android.os.Handler;

public class Gameplay3_test1 extends BaseGameActivity{

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	private Handler mHandler;
	protected Camera mCamera;
	protected Scene mScene;
	
	private Texture mScrumTexture;
	private TiledTextureRegion mScrumTextureRegion;
	
	private AnimatedSprite[] zombie1 = new AnimatedSprite[10];
	private int nZombie;
	Random gen;
	
	@Override
	public Engine onLoadEngine() {
		mHandler = new Handler();
		gen = new Random();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
		TextureRegionFactory.setAssetBasePath("gfx/Sprites/");
		
		mScrumTexture = new Texture(512, 512, TextureOptions.DEFAULT);
		mScrumTextureRegion = TextureRegionFactory.createTiledFromAsset(mScrumTexture, this, "zombie2.png", 0, 0, 2, 2);
		mEngine.getTextureManager().loadTexture(this.mScrumTexture);		
	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(1);
		
		nZombie = 0;
		mHandler.postDelayed(mStartZombie, 5000);
		return scene;
	}

	@Override
	public void onLoadComplete() {
		
	}
	
	private Runnable mStartZombie = new Runnable(){ 
		public void run(){
			int i = nZombie++;
			Scene scene = Gameplay3_test1.this.mEngine.getScene();
			float startY = gen.nextFloat() * (CAMERA_HEIGHT - 200);
			zombie1[i] = new AnimatedSprite(CAMERA_WIDTH, startY, mScrumTextureRegion.clone());
			final long[] frameDuration = new long[4];
			Arrays.fill(frameDuration, 150);
			zombie1[i].animate(frameDuration, 0, 3, true);
			zombie1[i].registerEntityModifier(new SequenceEntityModifier(
							new AlphaModifier(5.0f, 0.0f, 1.0f), 
							new MoveModifier(20.0f, zombie1[i].getX(), 0.0f, zombie1[i].getY(), startY)));
			scene.getLastChild().attachChild(zombie1[i]);
			if (nZombie < 3){
				mHandler.postDelayed(mStartZombie, 10000);
			}
		}
	};

}
