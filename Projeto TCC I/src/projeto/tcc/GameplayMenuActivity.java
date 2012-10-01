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
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
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

import projeto.tcc.game3.*;

import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

public class GameplayMenuActivity extends BaseGameActivity implements IOnMenuItemClickListener{

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320; 
	
	protected static final int GAME_1 = 100;
	protected static final int GAME_2 = 101;
	protected static final int GAME_3 = 102;
	protected static final int BACK = 103;
	
	
	protected Camera mCamera;
	protected Scene mMainScene;
	
	private Texture mMenuBackTexture;
	private TextureRegion mMenuBackTextureRegion;
	
	protected MenuScene mStaticMenuScene;
	private Texture mFontTexture;
	private Font mFont;
	
	protected TextureRegion mPopUpGame1TextureRegion, 
		mPopUpGame2TextureRegion, 
		mPopUpGame3TextureRegion;
	
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
		this.mFontTexture = new Texture(256,256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		FontFactory.setAssetBasePath("font/");
		this.mFont = FontFactory.createFromAsset(this.mFontTexture, this, "steak.ttf", 42, true, Color.RED);
		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);	
		
		this.mMenuBackTexture = new Texture(512,512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMenuBackTextureRegion = 
				TextureRegionFactory.createFromAsset(this.mMenuBackTexture, this, "gfx/MainMenu/Menubg.png", 0,0);
		this.mEngine.getTextureManager().loadTexture(this.mMenuBackTexture);

	}

	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.createStaticMenuScene();
		
		//Center
		final int centerX = (CAMERA_WIDTH - this.mMenuBackTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mMenuBackTextureRegion.getHeight()) / 2;
		
		this.mMainScene = new Scene(1);
		final Sprite menuBack = new Sprite(centerX, centerY, this.mMenuBackTextureRegion);
		mMainScene.getLastChild().attachChild(menuBack);
		mMainScene.setChildScene(mStaticMenuScene);
		
		return this.mMainScene;
	}

	private void createStaticMenuScene() {
		this.mStaticMenuScene = new MenuScene(this.mCamera);
		final IMenuItem game1MenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(GAME_1, mFont, "Normal"),
				0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f);
		game1MenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(game1MenuItem);
		
		final IMenuItem game2MenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(GAME_2, mFont, "Accelerometer"),
				0.5f, 0.5f, 0.5f, 1.0f, 0.5f, 0.0f);
		game2MenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(game2MenuItem);
		
		final IMenuItem game3MenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(GAME_3, mFont, "Aug. Reality"),
				0.5f, 0.5f, 0.5f, 1.0f, 0.75f, 0.0f);
		game3MenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(game3MenuItem);	
		
		final IMenuItem backMenuItem = new ColorMenuItemDecorator(
				new TextMenuItem(BACK, mFont, "BACK"),
				0.5f, 0.5f, 0.5f, 1.0f, 0.75f, 0.0f);
		backMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mStaticMenuScene.addMenuItem(backMenuItem);	
		
		this.mStaticMenuScene.buildAnimations();
		this.mStaticMenuScene.setBackgroundEnabled(false);
		this.mStaticMenuScene.setOnMenuItemClickListener(this);			
	}

	public void onLoadComplete() {
		// TODO Auto-generated method stub		
	}

	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch(pMenuItem.getID()){
		case GAME_1:
			Toast.makeText(GameplayMenuActivity.this, "No Idea Yet", Toast.LENGTH_SHORT).show();
			return true;
		case GAME_2:
			Toast.makeText(GameplayMenuActivity.this, "No Idea Yet", Toast.LENGTH_SHORT).show();
			return true;
		case GAME_3:
			Intent myIntent = new Intent(GameplayMenuActivity.this, VampiresInBackyard.class);
			GameplayMenuActivity.this.startActivity(myIntent);
			return true;
		case BACK:
			this.finish();
			return true;
		default:
			return false;
		}
	}

}
