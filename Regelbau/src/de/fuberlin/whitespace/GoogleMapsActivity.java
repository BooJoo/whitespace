package de.fuberlin.whitespace;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import de.fuberlin.whitespace.regelbau.R;

public class GoogleMapsActivity extends Activity {

	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng BERLIN = new LatLng(52.506, 13.424);
	LatLng CARMEQ;
	private GoogleMap map;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		//GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		System.out.println("Google Connection = "+GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()));
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		if (map != null) {	
			map.setMyLocationEnabled(true);
			
			LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		    Criteria criteria = new Criteria();
		    String provider = service.getBestProvider(criteria, false);
		    Location location = service.getLastKnownLocation(provider);
		    LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
		    
		    
			

		}
		else{
			System.out.println("Map ist nicht da!");
		}
	}


}
