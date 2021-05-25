package com.example.projetofinal1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetofinal1.glidestuff.GlideApp;
import com.example.projetofinal1.models.Restaurant;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {

    public static final String RES_ID = "res_id";

    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView nameView;
    private TextView openHoursView;
    private TextView addressView;
    private TextView restaurantRatings;
    private ImageView bannerView;
    private String id;

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Intent intent = getIntent();
        id = getIntent().getStringExtra(RES_ID);
        nameView = findViewById(R.id.restaurantName);
        openHoursView = findViewById(R.id.openHours);
        addressView = findViewById(R.id.restaurantAddress);
        bannerView = findViewById(R.id.restaurantImage);
        restaurantRatings = findViewById(R.id.restaurantRating);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        IMapController mapController = map.getController();
        mapController.setZoom(18.0);

        db.collection("restaurants")
                .document(id)
                .get().addOnCompleteListener(task -> {
            Restaurant r = task.getResult().toObject(Restaurant.class);
            nameView.setText(r.getName());
            openHoursView.setText(r.getOpenHours());
            addressView.setText(r.getAddress());
            restaurantRatings.setText("" + r.getRating());

            org.osmdroid.util.GeoPoint geo = new org.osmdroid.util.GeoPoint(r.getLocation().getLatitude(), r.getLocation().getLongitude());
            map.getController().setCenter(new org.osmdroid.util.GeoPoint(geo.getLatitude(), geo.getLongitude()));

            Marker startMarker = new Marker(map);
            startMarker.setPosition(geo);
            //startMarker.setIcon(getResources().getDrawable(R.drawable.fuel));
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            startMarker.setTitle(r.getName());
            map.getOverlays().add(startMarker);

            if (r.getImagePath() != null) {
                GlideApp.with(this)
                        .load(storage.getReferenceFromUrl(r.getImagePath()))
                        .placeholder(R.drawable.appname)
                        .into(bannerView);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    public void goToMenu(View v) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra(RES_ID, id);
        startActivity(intent);
    }




}