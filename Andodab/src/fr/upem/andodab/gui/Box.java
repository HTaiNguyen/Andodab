package fr.upem.andodab.gui;

import java.util.List;

import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.db.DBDictionary;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Box extends View {
	private Paint paint;

	private final static int PADDING = 4;
	
	private DBCommon dbCommon;
	private List<DBDictionary> dbDictionaries;
	
	private int x;
	private int y;

	public Box(Context context, int color, int x, int y, DBCommon dbCommmon, List<DBDictionary> dbDictionaries) {
		super(context);
		this.dbCommon = dbCommmon;
		this.dbDictionaries = dbDictionaries;
		paint = new Paint();
		paint.setColor(color);
		this.x = x;
		this.y = y;
	}

	@Override
	public void onDraw(Canvas canvas) {
		paint.setTextSize(16);
		
		canvas.drawText(dbCommon.getName(), y, x, paint);
		int y = 24;
		float height=8;
		float width = paint.measureText(dbCommon.getName());

		for(DBDictionary dbDictionary : dbDictionaries) {
			float textWidth = paint.measureText(dbDictionary.toString());
			if(textWidth > width) {
				width = textWidth;
			}
			canvas.drawText(dbDictionary.toString(), this.x, this.y+y, paint);
			y += 20;
			height += 16;
			
		}
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(this.x-PADDING, this.y-PADDING-8, this.x+width+PADDING, this.y+8, paint);
		canvas.drawRect(this.x-PADDING, this.y-PADDING-8, this.x+width+PADDING, this.y+height+PADDING, paint);
		
	}
}
