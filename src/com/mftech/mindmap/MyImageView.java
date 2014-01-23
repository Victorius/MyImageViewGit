package com.mftech.mindmap;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

public class MyImageView extends ImageView{
	public static final int SIZE = 300;
	public static final float TOP = 0.0f;
	public static final float LEFT = 0.0f;
	public static final float RIGHT = 1.0f;
	public static final float BOTTOM = 1.0f;
	public static final float CENTER = 0.5f;

	public static final float SCALE_POSITION = 0.025f;
	public static final float SCALE_START_VALUE = 0.0f;
	public static final float SCALE_END_VALUE = 100.0f;
	public static final float SCALE_START_ANGLE = 30.0f;
	public static final int SCALE_DIVISIONS = 10;
	public static final int SCALE_SUBDIVISIONS = 5;
	
	private Bitmap background;
	private Paint backPaint;
	private Paint scalePaint;
	private Paint needlePaint;
	private Paint needlePathPaint;
	private Path needlePath;
	private RectF borderRectF;
	private float mScalePosition;
	private float mScaleStartValue;
	private float mScaleEndValue;
	private float mScaleStartAngle;
	private int mDivisions;
	private int mSubdivisions;
	private float mScaleRotation;
	private float mDivisionValue;
	private float mSubdivisionValue;
	private float mSubdivisionAngle;
	
	
	private void init(){
		backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		scalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		needlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		needlePathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		needlePath = new Path();
	}
	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public MyImageView(Context context) {
		super(context);
		init();
	}
	
	@Override
	protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
		initBackground();
	}
	
	@Override
	public void onDraw(final Canvas canvas){
		drawBackground(canvas);
		final float scale = Math.min(getWidth(),getHeight());
		canvas.scale(scale, scale);
		canvas.translate(
				 (scale == getHeight()) ? ((getWidth()-scale) /2)/scale : 0 
			   , (scale == getWidth()) ? ((getHeight()-scale) /2 )/scale: 0
			   );
		drawScale(canvas);
		
	}
	
	private void drawScale(final Canvas canvas){
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.rotate(mScaleRotation, 0.5f, 0.5f);
		final int totalTicks = mDivisions * mSubdivisions + 1;
		for (int i = 0; i < totalTicks; i++) {
			final float y1 = borderRectF.top;
			final float y2 = y1 + 0.015f; // height of division
			final float y3 = y1 + 0.045f; // height of subdivision

			final float value = getValueForTick(i);
			
			if (0 == value % mDivisions) {
				// Draw a division tick
				canvas.drawLine(0.5f, y1, 0.5f, y3, scalePaint);
				// Draw the text 0.15 away from the division tick
				canvas.drawText(valueString(value), 0.5f, y3 + 0.045f, scalePaint);
			}
			else {
				// Draw a subdivision tick
				canvas.drawLine(0.5f, y1, 0.5f, y2, scalePaint);
			}
			canvas.rotate(mSubdivisionAngle, 0.5f, 0.5f);
		}
		canvas.restore();
	}
	private String valueString(final float value) {
		return String.format("%d", (int) value);
	}
	
	private float getValueForTick(final int tick) {
		return tick * (mDivisionValue / mSubdivisions);
	}
	
	private void drawBackground(final Canvas canvas){
		if(background!=null){
			canvas.drawBitmap(background, 0,0,backPaint);
		}else{
			Log.d("AHTUNG", "SHAIZE");
		}
	}
	
	private void initBackground(){
		if(background!=null){
			background.recycle();
		}
		background = Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
		final Canvas ca = new Canvas(background);
		final float scale = Math.min(getWidth(),getHeight());
		ca.scale(scale, scale);
		ca.translate(
				 (scale == getHeight()) ? ((getWidth()-scale) /2)/scale : 0 
			   , (scale == getWidth()) ? ((getHeight()-scale) /2 )/scale: 0
			   );
		borderRectF = new RectF(LEFT+mScalePosition,TOP+mScalePosition,RIGHT-mScalePosition,BOTTOM-mScalePosition);
		backPaint.setShader(new RadialGradient(0.5f, 0.5f, borderRectF.width() / 2, new int[] { Color.rgb(50, 132, 206), Color.rgb(36, 89, 162),
			Color.rgb(27, 59, 131) }, new float[] { 0.5f, 0.96f, 0.99f }, TileMode.MIRROR));
		ca.drawOval(borderRectF, backPaint);
		
	}
	
	@Override
	protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		final int chosenWidth = chooseDimension(widthMode, widthSize);
		final int chosenHeight = chooseDimension(heightMode, heightSize);
		setMeasuredDimension(chosenWidth, chosenHeight);
	}
	
	private int chooseDimension(final int mode, final int size) {
		switch (mode) {
		case View.MeasureSpec.AT_MOST:
		case View.MeasureSpec.EXACTLY:
			return size;
		case View.MeasureSpec.UNSPECIFIED:
		default:
			return SIZE;
		}
	}
	
	private void initScale(){
		mScalePosition = SCALE_POSITION;
		mScaleStartValue =  SCALE_START_VALUE;
		mScaleEndValue = SCALE_END_VALUE;
		mScaleStartAngle = SCALE_START_ANGLE;
		mDivisions =  SCALE_DIVISIONS;
		mSubdivisions =SCALE_SUBDIVISIONS;		
		mScaleRotation = (mScaleStartAngle + 180) % 360;
		mDivisionValue = (mScaleEndValue - mScaleStartValue) / mDivisions;
		mSubdivisionValue = mDivisionValue / mSubdivisions;
		mSubdivisionAngle = (360 - 2 * mScaleStartAngle) / (mDivisions * mSubdivisions);
		scalePaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		scalePaint.setColor(Color.rgb(197, 249, 107));
		scalePaint.setStyle(Paint.Style.STROKE);
		scalePaint.setStrokeWidth(0.005f);
		scalePaint.setTextSize(0.05f);
		scalePaint.setTypeface(Typeface.SANS_SERIF);
		scalePaint.setTextAlign(Align.CENTER);		
	}
}
