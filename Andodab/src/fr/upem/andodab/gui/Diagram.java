package fr.upem.andodab.gui;

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
		
	}
}