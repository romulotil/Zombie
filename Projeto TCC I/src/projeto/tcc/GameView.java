package projeto.tcc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Sprite_1 sprite;
    AccelActivity_1 main;
   
    public GameView(Context context, AccelActivity_1 main) {
          super(context);
          gameLoopThread = new GameLoopThread(this);
          holder = getHolder();
          this.main = main;
          holder.addCallback(new SurfaceHolder.Callback() {

                 public void surfaceDestroyed(SurfaceHolder holder) {
                        boolean retry = true;
                        gameLoopThread.setRunning(false);
                        while (retry) {
                               try {
                                     gameLoopThread.join();
                                     retry = false;
                               } catch (InterruptedException e) {
                               }
                        }
                 }

                 public void surfaceCreated(SurfaceHolder holder) {
                        gameLoopThread.setRunning(true);
                        gameLoopThread.start();
                 }

                 public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {
                 }
          });
          bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);
          sprite = new Sprite_1(this,bmp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
          canvas.drawColor(Color.BLACK);
          sprite.onDraw(canvas, main.getSide());
    }	
}
