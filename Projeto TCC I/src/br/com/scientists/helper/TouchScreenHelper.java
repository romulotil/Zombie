package br.com.scientists.helper;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchScreenHelper implements OnTouchListener {

	public boolean JUMP = false;
	
	public TouchScreenHelper(View view) {
		view.setOnTouchListener(this);
	}

	public boolean onTouch(View view, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE)
			this.JUMP = true;
		else
			this.JUMP = false;
		return true;
	}

}
