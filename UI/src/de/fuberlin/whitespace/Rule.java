package de.fuberlin.whitespace;

import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

public class Rule {
	Integer[] left;
	Integer[] right;
	public Rule(Integer[] left,Integer[] right){
		this.left = left;
		this.right = right;
	}
	public Rule(int a, int b)
	{
		Integer[] left = {a};
		Integer[] right = {b};
		this.left = left;
		this.right = right;
	}
	public Integer[] getLeft() {
		return left;
	}
	public Integer[] getRight() {
		return right;
	}
	public View getLeftView(final Context c){
		/*ImageView imageView = new ImageView(c);
	    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(8, 8, 8, 8);
        imageView.setLayoutParams(new GridView.LayoutParams(-1,150));
        imageView.setImageResource(left[0]);*/
		MultipleImagesCell muli = new MultipleImagesCell(c, left);
		return muli;
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
