package projeto.tcc.game1;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class Gameplay1_test2 extends BaseGameActivity {
	
	public static final int CAMERA_WIDTH = 480;
    public static final int CAMERA_HEIGHT = 800;
 
    // ===========================================================
    // Fields
    // ===========================================================
 
    private Camera mCamera;
    private Scene mMainScene;
 
    private BitmapTextureAtlas mBitmapTextureAtlas;
    private TiledTextureRegion mPlayerTiledTextureRegion;

 
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);		 
		return new Engine(new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_WIDTH, 
						CAMERA_HEIGHT),this.mCamera));
    }

	public void onLoadResources() {
		// Load all the textures this game needs.
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32);
        this.mPlayerTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "face_box.png", 0, 0, 1, 1);
        this.mBitmapTextureAtlas.load();
	}

	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger()); // logs the frame rate
		 
        // Create Scene and set background colour to (1, 1, 1) = white
        this.mMainScene = new Scene();
 
        // Center the player on the camera.
        final float centerX = (CAMERA_WIDTH - this.mPlayerTiledTextureRegion.getWidth()) / 2;
        final float centerY = (CAMERA_HEIGHT - this.mPlayerTiledTextureRegion.getHeight()) / 2;
 
        // Create the sprite and add it to the scene.
        final Slayer oSlayer = new Slayer(centerX, centerY, this.mPlayerTiledTextureRegion, this.getVertexBufferObjectManager());
        this.mMainScene.attachChild(oSlayer);
 
        return this.mMainScene;
	}

	public void onLoadComplete() {
		// TODO Auto-generated method stub		
	}

}
