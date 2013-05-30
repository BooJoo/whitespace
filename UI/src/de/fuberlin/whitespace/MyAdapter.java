package de.fuberlin.whitespace;

import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.sax.StartElementListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {
	private Context mContext;
	Vector<Rule> rules = new Vector<Rule>();
	//private int numberOfRules = 3;
	
	public MyAdapter(Context c) {
		mContext = c;
		Integer[] links = {R.drawable.a9,R.drawable.a7};
		Integer[] links3 = {R.drawable.a9,R.drawable.a6,R.drawable.a7};
		Integer[] rechts = {R.drawable.a1};
		rules.add(new Rule("Regel 1",links,rechts));
		rules.add(new Rule("Regel 2",links3,rechts));
		rules.add(new Rule("Meine Super Regel 3",R.drawable.a7,R.drawable.a2));
		rules.add(new Rule("Regel 4",R.drawable.a6,R.drawable.a4));
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rules.size()*2+1; 
	}
	
	/***
	 * 
	 */
	public void addItem(Integer[] images){
	
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		int row = (int) Math.floor(position/2);
		if(position < getCount()-1){
			if(position % 2 != 0){
				return rules.get(row).getRightView(mContext);
			}else{
				return rules.get(row).getLeftView(mContext);
			}
		}else{
			// ADD BUTTON
			ImageView imageView = new ImageView(mContext);
		    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
	        imageView.setPadding(8, 8, 8, 8);
	        imageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					rules.add(new Rule("Regel",R.drawable.a6,R.drawable.a4));
					notifyDataSetChanged();  
				}
			});
	        imageView.setLayoutParams(new GridView.LayoutParams(-1,150));
	        imageView.setImageResource(R.drawable.a5);
	        return imageView;
		}
        
	}
	 // references to our images
    private Integer[] mThumbIds = {
         R.drawable.a9,R.drawable.a1,
         R.drawable.a7,R.drawable.a2,
         R.drawable.a6,R.drawable.a4,
         R.drawable.a5
    };
}
