package fr.upem.andodab.gui;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Area extends View {
	private List<Box> boxes;
	private Paint paint;

	public Area(Context context, List<Box> boxes) {
		super(context);

		paint = new Paint();
		paint.setColor(Color.LTGRAY);

		this.boxes = boxes;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		for (Box b : boxes) {
			b.onTouchEvent(event);
		}

		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

		for (Box b : boxes) {
			b.draw(canvas);
		}

		invalidate();
	}
}