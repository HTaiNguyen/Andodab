package fr.upem.andolab.gui;

import java.util.List;

import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.db.DBDictionary;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Box extends View {
	private Paint paint;
	private int x;
	private int y;
	private int height;
	private int width;
	
	private DBCommon dbCommon;
	private List<DBDictionary> dbDictionaries;

	public Box(Context context, int x, int y, int height, int width, int color) {
		super(context);

		paint = new Paint();
		paint.setColor(color);
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawRect(50, 50, 10, 10, paint);
		canvas.drawText(dbCommon.getName(), x, y, paint);
		for(DBDictionary dbDictionary : dbDictionaries) {
			canvas.drawText(dbDictionary.toString(), x, y, paint);
		}
		
	}
}