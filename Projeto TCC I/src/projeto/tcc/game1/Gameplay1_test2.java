package projeto.tcc.game1;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class Gameplay1_test2 extends SimpleBaseGameActivity {

	public static final int CAMERA_WIDTH = 480;
    public static final int CAMERA_HEIGHT = 320;
    
    private Camera mCamera;
    private Scene mMainScene;
 
    private BitmapTextureAtlas mBitmapTextureAtlas;
    private TiledTextureRegion mPlayerTiledTextureRegion;
    
	public EngineOptions onCreateEngineOptions() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);		 
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
	}

	@Override
	protected void onCreateResources() {
        // Load all the textures this game needs.
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 228, 300);
        this.mPlayerTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "gfx/slayer1.png", 0, 0, 2, 2);
        this.mBitmapTextureAtlas.load();
    }

	@Override
	protected Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger()); // logs the frame rate
 
        // Create Scene and set background colour to (1, 1, 1) = white
        this.mMainScene = new Scene();
        this.mMainScene.setBackground(new Background(1, 1, 1));
 
        // Centre the player on the camera.
        final float centerX = (CAMERA_WIDTH - this.mPlayerTiledTextureRegion.getWidth()) / 2;
        final float centerY = (CAMERA_HEIGHT - this.mPlayerTiledTextureRegion.getHeight()) / 2;
 
        // Create the sprite and add it to the scene.
        final Slayer oPlayer = new Slayer(centerX, centerY, this.mPlayerTiledTextureRegion, this.getVertexBufferObjectManager());
        this.mMainScene.attachChild(oPlayer);
 
        return this.mMainScene;
    }
	
	
	
}
