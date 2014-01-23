package com.mftech.mindmap;

import java.util.Locale;

import com.mftech.mindmap.settingsactivites.MainPreferanceActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements AnimationListener{
	public static final int result_settings=1;
	private TextView text;
	private MyImageView myImageView;
	private float curScale =1F;
	private float curRotate = 0F;
	private Bitmap bitmap;
	private int bmpHeight;
	private float x,y;
	Animation animation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		text = (TextView)findViewById(R.id.main_textview);
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ExpletiveDeleted.ttf");
		myImageView = (MyImageView)findViewById(R.id.imageview);
//		myImageView.setScaleType(ImageView.ScaleType.MATRIX);
		text.setTypeface(tf);
//		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
//		bmpHeight = bitmap.getHeight();		
//		drawMatrix();
		Button b = (Button) findViewById(R.id.button);
		b.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				curRotate += (float) 10;
				Log.d("OnClick",""+curRotate);
				draw();
//			    drawMatrix();
//				onAnimationStart();
			}
			
		});
////		ImageView image = (ImageView) findViewById(R.id.imageview); 
//		animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
//		myImageView.setImageResource(R.drawable.arrow);
//		myImageView.startAnimation(animation); 
//		
		
//		AssetManager assetManager = getAssets();
//		text.setText("Pls take this gift from My heart to you.+\r\n");
//		text.setText(Locale.getDefault().getDisplayLanguage());
		
	}
	
	public void draw(){
		Path path = new Path();
		path.moveTo(10.0f, 10.0f);
		path.arcTo(new RectF(0.0f,0.0f,50.0f,50.0f), 0, 180);
//		path.lineTo(50.0f, 10.0f);
//		path.lineTo(10.0f, 50.0f);
		path.lineTo(10.0f, 10.0f);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(Color.rgb(255, 255, 255));
		Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		c.drawPath(path, p);
		myImageView.setImageBitmap(b);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.add(Menu.NONE, R.anim.alpha, Menu.NONE, "Alpha")
        .setAlphabeticShortcut('a');
    menu.add(Menu.NONE, R.anim.scale, Menu.NONE, "Scale")
        .setAlphabeticShortcut('s');
    menu.add(Menu.NONE, R.anim.translate, Menu.NONE, "Translate")
        .setAlphabeticShortcut('t');
    menu.add(Menu.NONE, R.anim.rotate, Menu.NONE, "Rotate")
        .setAlphabeticShortcut('r');
		return (super.onCreateOptionsMenu(menu));
	}
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
		 animation = AnimationUtils.loadAnimation(this, item.getItemId());
	     animation.setAnimationListener(this);
	     myImageView.startAnimation(animation); 
//	        switch (item.getItemId()) {
//	        case R.id.menu_settings:
//	            Intent i = new Intent(this, MainPreferanceActivity.class);
//	            startActivityForResult(i, result_settings);
//	            break;
//	 
//	        }
	 
	        return true;
	    }
	 
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        switch(requestCode){
	        	case result_settings:{
	        		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
	        		if(sp.getBoolean("darkmode",false)){
	        			text = (TextView) getLayoutInflater().inflate(R.layout.darktextview, null);
	        			
//	        			text.setTextAppearance(MainActivity.this, R.style.DarkStyle);
	        			text.setText("FeelThePowerOfDark");
	        		}
	        		else
	        			text.setText("FeelThePowerOfLight");
	        		break;
	        	}
	        		
	        		
	        }
	    }
	    
	    private void drawMatrix(){
	    	Matrix ma = new Matrix();
	    	Matrix mb = new Matrix();
	    	Matrix mc = new Matrix();
	    	ma.postScale(curScale, curScale);
	    	mb.setRotate(curRotate,100, bmpHeight);
	    	mc.setTranslate(100, 0);
	    	ma.setConcat(mb, mc);
	    	Bitmap targetBitmap = Bitmap.createBitmap(200, bmpHeight, bitmap.getConfig());
	    	Canvas canvas = new Canvas(targetBitmap);
////	    	canvas.drawLine(0, 0, x, y, new Paint());
	    	Paint p = new Paint();
	    	p.setAntiAlias(true);
	    	canvas.drawBitmap(bitmap, ma, p);
	    	canvas.setDensity(0);
	    	Canvas myc = new Canvas();
	    	myc.drawLine(0, 0, 50, 50, new Paint());
	    	myImageView.setImageBitmap(targetBitmap);	    	
	    	
//	    	myImageView.setDrawingCacheQuality(ImageView.DRAWING_CACHE_QUALITY_HIGH);
//	    	myImageView.
	    }

		@Override
		public void onAnimationEnd(Animation arg0) {
			myImageView.setVisibility(View.VISIBLE);
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			myImageView.setVisibility(View.VISIBLE);
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			myImageView.setVisibility(View.VISIBLE);
			
		}

}
