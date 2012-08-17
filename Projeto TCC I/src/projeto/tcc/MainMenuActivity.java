package projeto.tcc;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.animator.SlideMenuAnimator;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.anddev.andengine.entity.scene.menu.item.TextMenuItem;
import org.anddev.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.modifier.ease.EaseQuartInOut;
import android.content.Intent;
import android.graphics.Color;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainMenuActivity extends BaseGameActivity implements IOnMenuItemClickListener {
	
	private static final int CAMERA_WIDTH = 480; //Tamanho da tela
	private static final int CAMERA_HEIGHT = 320; 
	
	protected static final int MENU_ABOUT = 0; // 0
	protected static final int MENU_QUIT = MENU_ABOUT + 1; // 1
	protected static final int MENU_PLAY = 100; // 100
	protected static final int MENU_SCORES = MENU_PLAY + 1; // 101
	protected static final int MENU_OPTIONS = MENU_SCORES + 1; // 102
	protected static final int MENU_HELP = MENU_OPTIONS + 1; // 103
	
	protected Camera mCamera;
	protected Scene mMainScene;
	
	private Texture mMenuBackTexture;
	private TextureRegion mMenuBackTextureRegion;
	
	protected MenuScene mStaticMenuScene, mPopUpMenuScene;
	
	private Texture mPopUpTexture;
	private Texture mFontTexture;
	private Font mFont;
	protected TextureRegion mPopUpAboutTextureRegion, 
		mPopUpQuitTextureRegion, 
		mPopUpPlayTextureRegion, 
		mPopUpScoresTextureRegion, 
		mPopUpOptionsTextureRegion, 
		mPopUpHelpTextureRegion;
	private boolean popupDisplayed;
	
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0,0,CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine (
				new EngineOptions(
						true, ScreenOrientation.LANDSCAPE,
						new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT),
						this.mCamera)
		);
	}

	public void onLoadResources() {
		//Load Font/Textures
		this.mFontTexture = new Texture(256,256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		FontFactory.setAssetBasePath("font/");
		this.mFont = FontFactory.createFromAsset(this.mFontTexture, this, "steak.ttf", 42, true, Color.RED);
		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);
		
		this.mMenuBackTexture = new Texture(512,512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMenuBackTextureRegion = 
				TextureRegionFactory.createFromAsset(this.mMenuBackTexture, this, "gfx/MainMenu/Menubg.png", 0,0);
		this.mEngine.getTextureManager().loadTexture(this.mMenuBackTexture);

		this.mPopUpTexture = new Texture(512,512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mPopUpAboutTextureRegion = TextureRegionFactory.createFromAsset(this.mPopUpTexture, this, "gfx/MainMenu/about.png",0,0);
		this.mPopUpQuitTextureRegion = TextureRegionFactory.createFromAsset(this.mPopUpTexture, this, "gfx/MainMenu/quit.png",0,50);
		this.mEngine.getTextureManager().loadTexture(this.mPopUpTexture);
		
		popupDisplayed = false;
	}

	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.createStaticMenuScene();
		this.createPopUpMenuScene();
		
		//Center
		final int centerX = (CAMERA_WIDTH - this.mMenuBackTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mMenuBackTextureRegion.getHeight()) / 2;
		
		this.mMainScene = new Scene(1);
		final Sprite menuBack = new Sprite(centerX, centerY, this.mMenuBackTextureRegion);
		mMainScene.getLastChild().attachChild(menuBack);
		mMainScene.setChildScene(mStaticMenuScene);
		
		return this.mMainScene;
	}

	private void createPopUpMenuScene() {
		this.mPopUpMenuScene = new MenuScene(this.mCamera);
		
		final SpriteMenuItem aboutMenuItem = new SpriteMenuItem(MENU_ABOUT, this.mPopUpAboutTextureRegion);
		aboutMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mPopUpMenuScene.addMenuItem(aboutMenuItem);
		
		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT, this.mPopUpQuitTextureRegion);
		quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mPopUpMenuScene.addMenuItem(quitMenuItem);
		EaseQuartInOut el = EaseQuartInOut.getInstance();
		this.mPopUpMenuScene.setMenuAnimator(new SlideMenuAnimator(el));
		this.mPopUpMenuScene.buildAnimations();
		this.mPopUpMenuScene.setBackgroundEnabled(false);
		this.mPopUpMenuScene.setOnMenuItemClickListener(this);
	}

	private void createStaticMenuScene() {
		this.mStaticMenuScene = new MenuScene(this.mCamera);
		final IMenuItem playMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_PLAY, mFont, "Play Games"),
				0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f);
		playMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(playMenuItem);
		
		final IMenuItem scoresMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_SCORES, mFont, "Scores"),
				0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.0f);
		scoresMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(scoresMenuItem);
		
		final IMenuItem optionsMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_OPTIONS, mFont, "Options"),
				0.5f, 0.5f, 0.5f, 1.0f, 0.75f, 0.0f);
		optionsMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(optionsMenuItem);
		
		final IMenuItem helpMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(MENU_HELP, mFont, "Help"),
				0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f);
		helpMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(helpMenuItem);
		
		this.mStaticMenuScene.buildAnimations();
		this.mStaticMenuScene.setBackgroundEnabled(false);
		this.mStaticMenuScene.setOnMenuItemClickListener(this);		
	}

	public void onLoadComplete() {		
	}

	public boolean onMenuItemClicked(MenuScene pMenuScene, 
			IMenuItem pMenuItem,
			float pMenuItemLocalX,
			float pMenuItemLocalY) {
		switch(pMenuItem.getID()){
		case MENU_ABOUT:
			Toast.makeText(MainMenuActivity.this, "About Selected", Toast.LENGTH_SHORT).show();
			return true;
		case MENU_QUIT:
			this.finish();
			return true;
		case MENU_PLAY:
			Intent myIntent = new Intent(MainMenuActivity.this, GameplayMenuActivity.class);
			MainMenuActivity.this.startActivity(myIntent);
			//Toast.makeText(MainMenuActivity.this, "Play Selected", Toast.LENGTH_SHORT).show();
			return true;
		case MENU_SCORES:
			Toast.makeText(MainMenuActivity.this, "Scores Selected", Toast.LENGTH_SHORT).show();
			return true;
		case MENU_OPTIONS:
			Toast.makeText(MainMenuActivity.this, "Options Selected", Toast.LENGTH_SHORT).show();
			return true;
		case MENU_HELP:
			Toast.makeText(MainMenuActivity.this, "Help Selected", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if (pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if (popupDisplayed) {
				//remove menu and reset
				this.mPopUpMenuScene.back();
				mMainScene.setChildScene(mStaticMenuScene);
				popupDisplayed = false;
			} else {
				//attach menu
				this.mMainScene.setChildScene(this.mPopUpMenuScene, false, true, true);
				popupDisplayed = true;
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}
	
	

}
