package de.fuberlin.whitespace;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import de.fuberlin.whitespace.regelbau.R;

public class GoogleMapsActivity extends Activity {

	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng BERLIN = new LatLng(52.506, 13.424);
	private GoogleMap map;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		//GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		System.out.println("Google Connection = "+GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()));
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		if (map != null) {			
			System.out.println("Map ist da!");
			map.addMarker(new MarkerOptions().position(HAMBURG).title("Moin moin, du Landradde!").icon(BitmapDescriptorFactory
		              .fromResource(R.drawable.marker)));
			map.addMarker(new MarkerOptions().position(BERLIN).title("Watt kieckstn so?").icon(BitmapDescriptorFactory
		              .fromResource(R.drawable.marker)));
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
			map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		}
		else{
			System.out.println("Map ist nicht da!");
		}
	}


}
