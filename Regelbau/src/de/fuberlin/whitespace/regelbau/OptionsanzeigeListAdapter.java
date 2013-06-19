package de.fuberlin.whitespace.regelbau;


import android.app.LauncherActivity.ListItem;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class OptionsanzeigeListAdapter implements ListAdapter {

	protected static final int PICK_CONTACT_REQUEST = 1337;
	String[] elemente;
	private Context context;
	private DataSetObserver observer;
	private MainActivity rbm;
	private ArrayAdapter adapter;
	
	public OptionsanzeigeListAdapter(Context context,String[] elemente,MainActivity rbm){
		if(elemente == null){
			String[] tmp = {"Hallo Welt"};
			elemente = tmp;
		}
		this.rbm = rbm;
		this.elemente = elemente;
		this.context = context;
		adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,elemente);
	}
	
	/**
	 * Ersetzt die in der Liste angezeigten Elemente durch neue Versionen.
	 * @param neueElemente
	 */
	public void setNewElements(String[] neueElemente){
		elemente = neueElemente;
		adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,elemente);
		observer.onInvalidated();
	}
	
	@Override
	public int getCount() {
		return elemente.length;
	}

	@Override
	public Object getItem(int position) {
		return elemente[position];
	}

	@Override
	public long getItemId(int position) { 
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItem li = new ListItem();
	/*	TextView v = new TextView(context);
		v.setText(elemente[position]);
		v.setTextSize(30);*/
		View v = adapter.getView(position, convertView, parent);
		
		View hauptfenster = MainActivity.act.findViewById(R.layout.activity_regelbau_main);
	    //ColorDrawable clo = (ColorDrawable)(hauptfenster.getBackground());
		TypedValue a = new TypedValue();
		context.getTheme().resolveAttribute(android.R.attr.windowBackground, a, true);
		int color;
		if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT) {
		    // windowBackground is a color
		     color = a.data;
		} else {
		    // windowBackground is not a color, probably a drawable
		    //Drawable d = activity.getResources().getDrawable(a.resourceId);
		    
			color = Color.WHITE;
		}
	    v.setBackgroundColor(color);
		
		v.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT,-2));
		v.setPadding(0, 3, 0, 0);
		v.setOnClickListener(new View.OnClickListener() {

			

			@Override
			public void onClick(View v) {
				//Toast.makeText(context, ((TextView)v).getText(), Toast.LENGTH_LONG).show();
				// Bitte =) Binde mal deines ein, oder was muss ich tun?
				//Intent i = new Intent(context, multiselect_with_checkboxes.MultiSelectListViewActivity.class);
				//context.startActivity(i); 
		  
				TextView tv = (TextView)v;
				rbm.getSatzanzeige().setButtonLabelEins((String)tv.getText());
				 String[] elemente = {"Raststätten","Sehenswürdigkeiten","Tankstellen","Wetter"};
					rbm.rufeActivityAuf(elemente);
				// pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
				//startActivityForResult(i, PICK_CONTACT_REQUEST);
			}
		});
		return v; //Hoffentlich klappt das
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
	  this.observer = observer;
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return false;
	}

}
