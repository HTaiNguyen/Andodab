package fr.upem.andodab.gui;

import java.util.ArrayList;
import java.util.List;

import fr.upem.andodab.dao.DAOCommon;
import fr.upem.andodab.dao.DAODictionary;
import fr.upem.andodab.db.DBCommon;
import fr.upem.andodab.table.TableCommon;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Diagram extends Activity {
	private ArrayList<Box> boxes;
	private DAOCommon daoCommon;
	private DAODictionary daoDictionary;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Create Vertical Layout
		LinearLayout VL = new LinearLayout(this);
		VL.setOrientation(LinearLayout.VERTICAL);

		daoCommon = new DAOCommon(getContentResolver());
		daoDictionary = new DAODictionary(getContentResolver());
		List<DBCommon> commons = daoCommon.findByAncestor(this.getIntent().getLongExtra(TableCommon.COL_ID, 1L));
		ArrayList<Box> boxes = new ArrayList<Box>();
		
		for(DBCommon common : commons) {
			boxes.add(new Box(this, Color.BLACK, 50, 50, common, daoDictionary.findByObject(common.getId()))); 
			VL.addView(new Box(this, Color.BLACK, 50, 50, common, daoDictionary.findByObject(common.getId())));
		}

		setContentView(VL);
	}
}