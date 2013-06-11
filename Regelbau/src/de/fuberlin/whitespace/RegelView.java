package de.fuberlin.whitespace;

import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegelView {
	Integer[] left;
	Integer[] right;
	private String name;
	public static KnightRider r;
	
	public RegelView(String name,Integer[] left,Integer[] right){
		this.name = name;
		this.left = left;
		this.right = right;
	}
	public RegelView(String name,int a, int b)
	{
		Integer[] left = {a};
		Integer[] right = {b};
		this.left = left;
		this.right = right;
		this.name = name;
	}
	public Integer[] getLeft() {
		return left;
	}
	public Integer[] getRight() {
		return right;
	}
	public View getLeftView(final Context c){ 
		LinearLayout l = new LinearLayout(c);
		l.setOrientation(LinearLayout.VERTICAL);
		TextView txt = new TextView(c);
		txt.setText(name);
		MultipleImagesCell muli = new MultipleImagesCell(c, left);
		r = new KnightRider(c);
		muli.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				r.say("Hakuna Matata");
			//	r.say("");
			}
		});
		l.addView(muli);
		l.addView(txt);
		return l;
	}
	public View getRightView(final Context c){
		ImageView imageView = new ImageView(c);
	    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(8, 8, 8, 8);
        imageView.setLayoutParams(new GridView.LayoutParams(-1,150));
        imageView.setImageResource(right[0]);
        imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(c,RuleConstruction.class);
				c.startActivity(i); 
			}
		});
		return imageView;
	}
}
