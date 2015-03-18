package fr.upem.andodab.gui;

import java.util.ArrayList;
import java.util.List;

import fr.upem.andodab.dao.DAOCommon;
import fr.upem.andodab.dao.DAODictionary;
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
	
	private final static int PADDING = 5;
	private final static int MARGIN = 25;
	
	private Point position;
	private Point initialPosition;
	private Point offsetPosition;
	
	private DBCommon dbCommon;
	private List<DBDictionary> dbDictionaries;
	
	private ArrayList<Box> boxes;
	
	private float width;
	private float height;
	private float heightMaxLevel;

	public Paint textPaint;

	private Box(Context context, DBCommon dbCommmon, List<DBDictionary> dbDictionaries, List<Box> boxes) {
		super(context);

		this.position = new Point(0, 0);
		this.initialPosition = new Point();
		this.offsetPosition = new Point();

		this.dbCommon = dbCommmon;
		this.dbDictionaries = dbDictionaries;
		
		this.boxes = (ArrayList<Box>) boxes;
		
		width = 0;
		height = 0;
		heightMaxLevel = 0;
		
		textPaint = new Paint();
		textPaint.setColor(Color.rgb(0, 130, 0));
		textPaint.setTextSize(10.0f);
	}
	
	public static Box createBox(Context context, DBCommon dbCommon) {
		DAOCommon daoCommon = new DAOCommon(context.getContentResolver());
		DAODictionary daoDictionary = new DAODictionary(context.getContentResolver());
		
		List<DBCommon> commons = daoCommon.findByAncestor(dbCommon.getId());
		
		ArrayList<Box> boxes = new ArrayList<Box>();
		float heightMaxLevel = 0;
		for (DBCommon common : commons) {
			Box box = Box.createBox(context, common);
			box.computeBoxSize();
			
			if (heightMaxLevel < box.height) {
				heightMaxLevel = box.height;
			}
			boxes.add(box);
		}
		
		for(Box b : boxes) {
			b.heightMaxLevel = heightMaxLevel;
		}
		
		Box box = new Box(context, dbCommon, daoDictionary.findByObject(dbCommon.getId()), boxes);
		box.computeBoxSize();
		if(box.heightMaxLevel == 0) {
			box.heightMaxLevel = box.height;
		}
		
		return box;
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
		if(!boxes.isEmpty()) {
			canvas.translate(0, heightMaxLevel+MARGIN);
		}
		float maxWidth = 0;
		for(Box b : boxes) {
			b.draw(canvas);
			maxWidth += b.getBoxWidth()+10; 

			if(!b.boxes.isEmpty()) {
				canvas.translate(b.width+10, 0);
			}
		}
		
		if(!boxes.isEmpty()) {
			canvas.translate(0, -(heightMaxLevel+MARGIN));
			canvas.translate(-(maxWidth/2)-width/2, 0);
		}
		
		drawBox(canvas);
		
		if(boxes.isEmpty()) {
			canvas.translate(width+10, 0);
		} else {
			canvas.translate((maxWidth/2)+width/2-width,0);
		}

		invalidate();
	}
	
	private void drawBox(Canvas canvas) {
		float height = 0;
		float width = 0;
		Paint paint = new Paint();

		paint.setColor(Color.rgb(0, 130, 0));
		paint.setTextSize(10.0f);

		int yTitle = (int) paint.getTextSize() + PADDING;
		height = yTitle;
		width = paint.measureText(dbCommon.getName()) + PADDING;

		canvas.drawText(dbCommon.getName(), PADDING, yTitle, paint);

		int yAttribute = yTitle + (2 * PADDING);
		height += 2 * PADDING;

		for (DBDictionary dictionary : dbDictionaries) {
			float textWidth = paint.measureText(dictionary.toString());

			if (textWidth > width) {
				width = textWidth + PADDING;
			}

			yAttribute += (int) paint.getTextSize() + PADDING;
			height += (int) paint.getTextSize() + PADDING;

			canvas.drawText(dictionary.toString(), PADDING, yAttribute, paint);
		}

		paint.setColor(Color.rgb(0, 130, 0));
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);

		canvas.drawRect(0, 0, width + PADDING, height + PADDING, paint);

	}
	
	private void computeBoxSize() {
		int yTitle = position.y + (int) textPaint.getTextSize() + PADDING;
		height = yTitle - position.y;
		width = textPaint.measureText(dbCommon.getName()) + PADDING;


		int yAttribute = yTitle + (2 * PADDING);
		height += 2 * PADDING;

		for (DBDictionary dictionary : dbDictionaries) {
			float textWidth = textPaint.measureText(dictionary.toString());

			if (textWidth > width) {
				width = textWidth + PADDING;
			}

			yAttribute += (int) textPaint.getTextSize() + PADDING;
			height += (int) textPaint.getTextSize() + PADDING;
		}
		this.height = height + PADDING;
		this.width = width + PADDING;
	}

	public float getBoxHeight() {
		return height;
	}
	
	public float getBoxWidth() {
		return width;
	}
	
	
	
	
}