package de.fuberlin.whitespace.regelbau;

import java.util.Vector;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

public class Satzanzeige extends LinearLayout {

	Vector<IButtonChangeListener> listeners = new Vector<IButtonChangeListener>();
	private AbsRule rule; 
	private Button buttona = new Button(getContext());
	private Button buttonb = new Button(getContext());
	private Button buttonc = new Button(getContext()); 
	
	public Satzanzeige(Context context, AbsRule rule) {
		super(context);
		
		/*if(rule == null){
			rule = new AbsRule("Meine super Regel") {
				
				@Override
				public boolean Triggered() {
					if(Math.random() > 0.5d) //? ! das ist wichtig!
						{Action();return true;}else{ return false;}
				}
				
				@Override
				public void Action() {
					System.out.println("Regel "+getName()+" ausgelöst!");
				}
			};
		}*/
		buttona.setLayoutParams(new GridView.LayoutParams(-1,-2));
		buttona.setPadding(20,20,20,20);
		buttonb.setLayoutParams(new GridView.LayoutParams(-1,-2));
		buttonb.setPadding(20,20,20,20);
		buttonc.setLayoutParams(new GridView.LayoutParams(-1,-2));
		buttonc.setPadding(20,20,20,20);
		this.rule = rule;
		this.setOrientation(LinearLayout.VERTICAL);
		
		buttona.setText("Zeige mir");
		buttona.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActionChanged();
				
			}
		});
		
		buttonb.setText("Raststätten");
		buttonb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ActionOptionsChanged();
			}
		});
		buttonc.setText("nach 100 Metern");
		buttonc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ConditionChanged();
			}
		});
		
		addView(buttona);addView(buttonb);addView(buttonc);
	}
	
	public void registerForButtonChanges(IButtonChangeListener bcl){
		listeners.add(bcl);
	}
	/**
	 * Soll aufgerufen werden, wenn der Button für die Aktion geändert wurde.
	 */
	public void ActionChanged(){
		for(int i = 0; i < listeners.size(); i++){
			listeners.get(i).aktionchanged((String)buttona.getText());
		}
	}
	/**
	 * Soll aufgerufen werden, wenn der Button für die Aktion geändert wurde.
	 */
	public void ActionOptionsChanged(){
		for(int i = 0; i < listeners.size(); i++){
			listeners.get(i).aktionoptionschanged((String)buttonb.getText(),(String)buttona.getText());
		}
	}
	/**
	 * Soll aufgerufen werden, wenn der Button für den Auslöser geändert wurde.
	 */
	public void ConditionChanged(){
		for(int i = 0; i < listeners.size(); i++){
			listeners.get(i).ausloeserchanged((String)buttonc.getText(), (String)buttona.getText(),(String)buttonb.getText());
		}
	}
	
	public void setButtonLabelEins(String res){
		
		buttona.setText(res);
	}
	
	public void setButtonLabelZwei(Object[] res){
		String label = "";
		for(Object s: res){
			label += " "+(String)s;
		}
		buttonb.setText(label);
	}
	
	public void setButtonLabelDrei(String res){
		buttonc.setText(res);
	}

}
