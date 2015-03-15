package fr.upem.andolab.gui;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

public class Diagram extends Activity {
	private ArrayList<Box> boxes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boxes = new ArrayList<>();
		
		Box box = new Box(this, 50, 50, 100, 100, Color.GREEN);
		setContentView(box);
	}
}