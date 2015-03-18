package fr.upem.andodab.gui;

import java.util.ArrayList;
import java.util.List;

import fr.upem.andodab.dao.DAOCommon;
import fr.upem.andodab.dao.DAODictionary;
import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.table.TableCommon;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class Diagram extends Activity {
	private static final int LEVEL_OFFSET = 100;

	private Area area;
	private DAOCommon daoCommon;
	private DAODictionary daoDictionary;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		daoCommon = new DAOCommon(getContentResolver());
		daoDictionary = new DAODictionary(getContentResolver());
		
		List<DBCommon> commons = daoCommon.findByAncestor(this.getIntent().getLongExtra(TableCommon.COL_ID, 1L));
		
		ArrayList<Box> boxes = new ArrayList<Box>();

		/*for (DBCommon common : commons) {
			// Créer les boites
			//boxes.add(new Box(this, new Point(100 - BOX_WIDTH / 2, 100), BOX_WIDTH, BOX_HEIGHT, common, daoDictionary.findByObject(common.getId()))); 
			Box box = new Box(this, common, daoDictionary.findByObject(common.getId()),0);
			boxes.add(box);
		}*/
		
		Box box = Box.createBox(this, daoCommon.read(this.getIntent().getLongExtra(TableCommon.COL_ID, 1L)));
		boxes.add(box);
		//boxes.add(new Box(this, new Point(100 / 2, 100), null, null));
		//boxes.add(new Box(this, new Point(100 / 2, 300), null, null));
		//boxes.add(new Box(this, new Point(100 / 2, 500), null, null));

		Area area = new Area(this, boxes);
		setContentView(area);
	}
}