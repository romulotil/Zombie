package projeto.tcc.game3;

import java.util.Arrays;
import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.extension.augmentedreality.BaseAugmentedRealityGameActivity;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.os.Handler;
import android.widget.Toast;

public class Gameplay3_test2 extends BaseAugmentedRealityGameActivity {
	
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	private static final int ZOMB_RATE = 1500;
	private static final int ZOMB_MAX = 5;
	
	private Camera mCamera;
	private Handler mHandler;
	private Texture mScrumTexture;
	private TiledTextureRegion mScrumTextureRegion;
	
	private AnimatedSprite[] zombie = new AnimatedSprite[5];
	private int nZombie;
	
	private Random gen;

	@Override
	public Engine onLoadEngine() {
		Toast.makeText(this, "If you don't see a vampire moving over the screen, try starting this while already being in Landscape orientation!!", Toast.LENGTH_LONG).show();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		mHandler = new Handler();
		gen = new Random();
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
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
		final Scene scene = new Scene(1);
		
		scene.setBackground(
				new ColorBackground(0.0f, 0.0f, 0.0f, 0.0f));
		
		nZombie = 0;
		mHandler.postDelayed(mStartZombie, 1000);
		
		scene.registerUpdateHandler(new IUpdateHandler(){

			@Override
			public void onUpdate(float pSecondsElapsed) {
				for (int i=0; i < nZombie; i++){
					if (zombie[i].getX() < 50.0f){
						float startY = gen.nextFloat() * (CAMERA_HEIGHT - 100.0f);
						zombie[i].clearEntityModifiers();
						zombie[i].registerEntityModifier(new MoveModifier(20.0f, CAMERA_WIDTH - 30.0f, 0.0f, startY, 340.0f));
					}
				}
			}

			@Override
			public void reset() {
				
			}
			
		});
		
		return scene;
	}

	@Override
	public void onLoadComplete() {
				
	}
	
	private Runnable mStartZombie = new Runnable(){ 
		public void run(){
			int i = nZombie;
			Scene scene = Gameplay3_test2.this.mEngine.getScene();
			float startY = gen.nextFloat() * (CAMERA_HEIGHT);
			zombie[i] = new AnimatedSprite(CAMERA_WIDTH, startY, mScrumTextureRegion.clone());
			scene.registerTouchArea(zombie[i]);
			nZombie++;
			final long[] frameDuration = new long[4];
			Arrays.fill(frameDuration, 150);
			zombie[i].animate(frameDuration, 0, 3, true);
			zombie[i].registerEntityModifier(new SequenceEntityModifier(
							new AlphaModifier(5.0f, 0.0f, 1.0f), 
							new MoveModifier(20.0f, zombie[i].getX(), 0.0f, zombie[i].getY(), startY)));
			scene.getLastChild().attachChild(zombie[i]);
			if (nZombie < ZOMB_MAX){
				mHandler.postDelayed(mStartZombie, ZOMB_RATE);
			}
		}
	};

}
