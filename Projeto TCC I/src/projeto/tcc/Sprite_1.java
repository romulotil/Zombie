package projeto.tcc;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite_1 {
	
	private static final int BMP_ROWS = 1;
	private static final int BMP_COLUMNS = 2;
	private int x = 0;
	private int y = 0;
	private int xSpeed = 5;
	private Bitmap bmp;
	private int currentFrame = 0;
	private int width;
	private int height;

	public Sprite_1(GameView gameView, Bitmap bmp) {
		this.bmp = bmp;
	    this.width = bmp.getWidth() / BMP_COLUMNS;
	    this.height = bmp.getHeight() / BMP_ROWS;
	}

	private void update(int side) {
		if (side == 1) {
	    	xSpeed = -5;
	    	side = 1;
		} else {
			xSpeed = 5;
	    	side = 2;
		}
	    x = x + xSpeed;
	    currentFrame = ++currentFrame % BMP_COLUMNS;	    
//		if (x > gameView.getWidth() - width - xSpeed) {
//	    	xSpeed = -5;
//	       	side = 1;
//		}
//	    if (x + xSpeed < 0) {
//	    	xSpeed = 5;
//	        side = 2;
//	    }     	
	}

	public void onDraw(Canvas canvas, int side) {
		update(side);
		int srcX = currentFrame * width;
	    int srcY = side * height;
	    Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
	    Rect dst = new Rect(x, y, x + width, y + height);
	    canvas.drawBitmap(bmp, src, dst, null);
	}	
}
