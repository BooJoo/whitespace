package de.fuberlin.whitespace;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import de.fuberlin.whitespace.regelbau.R;

public class GoogleMapsActivity extends Activity {

	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng BERLIN = new LatLng(52.506, 13.424);
	LatLng CARMEQ = new LatLng(52.523115,13.320013);
	private GoogleMap map;
	LatLng POINT1 = new LatLng(52.525798,13.325027);
	LatLng POINT2 = new LatLng(52.520729,13.333358);
	LatLng POINT3 = new LatLng(52.51445,13.322291);
	LatLng POINT4 = new LatLng(52.519761,13.341683);
	//52.525798,13.325027 Shell Kaiserin-Augusta-Allee
	//52.520729,13.333358 Aral Tankstelle
	//52.51445,13.322291 SB Tankstelle
	//52.527084,13.324742 Esso Lessingstraße
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		String[] orte;
		 try{
				orte = getIntent().getStringArrayExtra("url");
				
		    }catch(Exception e){
		    	orte = null;
		}
		
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
		   
		    
		    Marker point1 = map.addMarker(new MarkerOptions()
		    	.position(POINT1)
		        .title("Shell Kaiserin-Augusta-Allee"));
		    
		    Marker point2 = map.addMarker(new MarkerOptions()
    			.position(POINT2)
    			.title("Aral Tankstelle"));
		    
		    Marker point3 = map.addMarker(new MarkerOptions()
    			.position(POINT3)
    			.title("SB Tankstelle"));
		    
		    Marker point4 = map.addMarker(new MarkerOptions()
    			.position(POINT4)
    			.title("Esso Lessingstraße"));
		    
		    map.moveCamera(CameraUpdateFactory.newLatLngZoom(CARMEQ, 14));
		    
		    Marker here = map.addMarker(new MarkerOptions()
			.position(CARMEQ)
			.title("carmeq")
			.icon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
		    
		    
		    

		}
		else{
			System.out.println("Map ist nicht da!");
		}
	}


}
