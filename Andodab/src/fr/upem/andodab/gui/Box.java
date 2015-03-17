package fr.upem.andodab.gui;

import java.util.List;

import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.db.DBDictionary;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class Box extends View {
	private Point position;
	private int width;
	private int height;
	private Point initialPosition;
	private Point offsetPosition;
	private DBCommon dbCommon;
	private List<DBDictionary> dbDictionaries;

	public Box(Context context, Point position, DBCommon dbCommmon, List<DBDictionary> dbDictionaries) {
		super(context);

		this.position = new Point(position.x, position.y);

		initialPosition = new Point();
		offsetPosition = new Point();

		this.dbCommon = dbCommmon;
		this.dbDictionaries = dbDictionaries;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			initialPosition = position;

			offsetPosition = new Point((int) event.getX(), (int) event.getY());

			if (offsetPosition.x >= position.x && offsetPosition.x <= (position.x + width) && offsetPosition.y >= position.y && offsetPosition.y <= (position.y + height)) {
				// selection de la box
			}

			break;
		case MotionEvent.ACTION_MOVE:
			position = new Point(initialPosition.x + (int) event.getX() - offsetPosition.x, initialPosition.y + (int) event.getY() - offsetPosition.y);

			break;
		}

		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

		int padding = 5;

		float height = 0;
		float width = 0;

		Paint paint = new Paint();

		paint.setColor(Color.rgb(0, 130, 0));
		paint.setTextSize(10.0f);

		String name = "OBJET";
		int yTitle = position.y + (int) paint.getTextSize() + padding;
		height = yTitle - position.y;
		width = paint.measureText(name);

		canvas.drawText(name, position.x + padding, yTitle, paint);

		String[] attributes = { "ATTR1 : VALUE1", "ATTR2 : VALUE2", "ATTR3 : VALUE3", "ATTR4 : VALUE4", "ATTR5 : VALUE5" };

		int yAttribute = yTitle + (2 * padding);
		height += 2 * padding;

		for (int i = 0; i < attributes.length; i++) {
			float textWidth = paint.measureText(attributes[i]);

			if (textWidth > width) {
				width = textWidth;
			}

			yAttribute += (int) paint.getTextSize() + padding;
			height += (int) paint.getTextSize() + padding;

			canvas.drawText(attributes[i], position.x + padding, yAttribute, paint);
		}

		paint.setColor(Color.rgb(0, 130, 0));
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);

		canvas.drawRect(position.x, position.y, position.x + width + (2 * padding), position.y + height + padding, paint);

		invalidate();
	}
}