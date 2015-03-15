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

	
	private DBCommon dbCommon;
	private List<DBDictionary> dbDictionaries;

	public Box(Context context, int color, DBCommon dbCommmon, List<DBDictionary> dbDictionaries) {
		super(context);
		this.dbCommon = dbCommmon;
		this.dbDictionaries = dbDictionaries;
		paint = new Paint();
		paint.setColor(color);
	}

	@Override
	public void onDraw(Canvas canvas) {
		//canvas.drawRect(50, 50, 10, 10, paint);
		paint.setTextSize(16);
		
		canvas.drawText(dbCommon.getName(), 10, 0, paint);
		int x = 10;
		int y = 20;
		for(DBDictionary dbDictionary : dbDictionaries) {
			
			canvas.drawText(dbDictionary.toString(), x, y, paint);
			y += 20;
		}
		
	}
}