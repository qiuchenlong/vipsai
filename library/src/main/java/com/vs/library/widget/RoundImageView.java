package com.vs.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
//import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.vs.library.R;


/**
 * 圆角ImageView
 * @author chends
 *
 */
public class RoundImageView extends ImageView {

	private int type;
	public static final int TYPE_CIRCLE = 0;
	public static final int TYPE_ROUND = 1;
	public static final int TYPE_ROUND_TOP = 2;
	private static final int BODER_RADIUS_DEFAULT = 0;
	private int mBorderRadius;

	private Paint mBitmapPaint;
	private int mRadius;
	private Matrix mShaderMatrix;
	private BitmapShader mBitmapShader;
	private int mWidth;
	private RectF mRoundRect;
	private int mCircleBorder;
	private RectF mTmpRect;

	public RoundImageView(Context context, AttributeSet attrs) {

		super(context, attrs);
//		mShaderMatrix = new Matrix();
		mBitmapPaint = new Paint();
		mBitmapPaint.setAntiAlias(true);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RoundImageView);

		mBorderRadius = a.getDimensionPixelSize(
				R.styleable.RoundImageView_borderRadius, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
								BODER_RADIUS_DEFAULT, getResources()
										.getDisplayMetrics()));
		type = a.getInt(R.styleable.RoundImageView_type, TYPE_ROUND);
		mCircleBorder = a.getDimensionPixelSize(
				R.styleable.RoundImageView_circleBorder, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0,
								getResources().getDisplayMetrics()));
		a.recycle();
	}

	public RoundImageView(Context context) {
		this(context, null);
	}

//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//		if (type == TYPE_CIRCLE) {
//			mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
//			mRadius = mWidth / 2;
////			setMeasuredDimension(mWidth, mWidth);
//		}
//
//	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		mShaderMatrix = null;
		super.setImageDrawable(drawable);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		mShaderMatrix = null;
		super.setImageBitmap(bm);
	}

	@Override
	public void setImageResource(int resId) {
		mShaderMatrix = null;
		super.setImageResource(resId);
	}

	@Override
	public void setImageURI(Uri uri) {
		mShaderMatrix = null;
		super.setImageURI(uri);
	}

	private void setUpShader() {
//		Drawable drawable = getDrawable();
//		if (drawable == null || mMatrix != null) {
//			return;
//		}

		if (mShaderMatrix != null) {
			return;
		}

		mShaderMatrix = new Matrix();
//		mShaderMatrix.reset();

		Bitmap bmp = drawableToBitamp(getDrawable());
		if(bmp == null) {
			return;
		}
		mBitmapShader = new BitmapShader(bmp, TileMode.REPEAT, TileMode.REPEAT);
//		float scale = 1.0f;

		float scaleY = 1.0f;
		float scaleX = 1.0f;
//		if(getScaleType() == ScaleType.FIT_XY) {
//			scaleY = getHeight() * 1.0f / bmp.getHeight();
//			scaleX = getWidth() * 1.0f / bmp.getWidth();
//			mMatrix.setScale(scaleX, scaleY, bmp.getWidth() / 2, bmp.getHeight() / 2);
//
//		}else if(getScaleType() == ScaleType.CENTER_CROP) {
//
//			int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
//			scaleX = mWidth * 1.0f / bSize;
//			scaleY = mWidth * 1.0f / bSize;
//
//			mMatrix.setScale(scaleX, scaleY, bmp.getWidth() / 2, bmp.getHeight() / 2);
//		}

		if (type == TYPE_CIRCLE) {
//			int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
//			scale = mWidth * 1.0f / bSize;
			scaleY = getHeight() * 1.0f / bmp.getHeight();
			scaleX = getWidth() * 1.0f / bmp.getWidth();

		} else if (type == TYPE_ROUND || type == TYPE_ROUND_TOP) {
//			Log.e("TAG",
//					"b'w = " + bmp.getWidth() + " , " + "b'h = "
//							+ bmp.getHeight());
			if (!(bmp.getWidth() == getWidth() && bmp.getHeight() == getHeight())) {
				scaleX = getWidth() * 1.0f / bmp.getWidth();
				scaleY = getHeight() * 1.0f / bmp.getHeight();
			}

		}

//		mMatrix.setScale(scaleX, scaleY, getWidth() / 2, getHeight() / 2);
		mShaderMatrix.setScale(scaleX, scaleY);

		int tx = (int)((bmp.getWidth() * scaleX - getWidth()) / 2f);
		int ty = (int)((bmp.getHeight() * scaleY - getHeight()) / 2f);
		mShaderMatrix.postTranslate(0 - tx, 0 - ty);

		mBitmapShader.setLocalMatrix(mShaderMatrix);
		mBitmapPaint.setShader(mBitmapShader);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (getDrawable() == null) {
			return;
		}
		setUpShader();

		if (type == TYPE_ROUND_TOP) {

			canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,
					mBitmapPaint);

			mTmpRect.set(mRoundRect.left, mRoundRect.bottom - mBorderRadius, mRoundRect.right, mRoundRect.bottom);
			canvas.drawRect(mTmpRect, mBitmapPaint);

		}if (type == TYPE_ROUND) {
			canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,
					mBitmapPaint);

		} else {
			canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
			// drawSomeThing(canvas);
			if (mCircleBorder > 0) {
				Paint paint = new Paint();
				paint.setAntiAlias(true);
				paint.setColor(Color.parseColor("#aacccccc"));
				paint.setStyle(Paint.Style.STROKE);
				paint.setStrokeWidth(mCircleBorder);
				canvas.drawCircle(mRadius, mRadius,
						mRadius - mCircleBorder / 2, paint);
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		if (type == TYPE_ROUND || type == TYPE_ROUND_TOP) {
			mRoundRect = new RectF(0, 0, w, h);
			mTmpRect = new RectF(0,0, w,h);
		}else if (type == TYPE_CIRCLE) {
				mRadius = Math.min(w, h) / 2;
		}
	}

	/**
	 * drawableתbitmap
	 *
	 * @param drawable
	 * @return
	 */
	private Bitmap drawableToBitamp(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap bitmap = null;
		try {
			bitmap = Bitmap.createBitmap(w, h, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
//			bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, w, h);
			drawable.draw(canvas);
		}catch(OutOfMemoryError e){}
		return bitmap;
	}

	private static final String STATE_INSTANCE = "state_instance";
	private static final String STATE_TYPE = "state_type";
	private static final String STATE_BORDER_RADIUS = "state_border_radius";

	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
		bundle.putInt(STATE_TYPE, type);
		bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			super.onRestoreInstanceState(((Bundle) state)
					.getParcelable(STATE_INSTANCE));
			this.type = bundle.getInt(STATE_TYPE);
			this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
		} else {
			super.onRestoreInstanceState(state);
		}

	}

	public void setBorderRadius(int borderRadius) {
		int pxVal = dp2px(borderRadius);
		if (this.mBorderRadius != pxVal) {
			this.mBorderRadius = pxVal;
			invalidate();
		}
	}

	public void setType(int type) {
		if (this.type != type) {
			this.type = type;
			if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE && this.type != TYPE_ROUND_TOP) {
				this.type = TYPE_CIRCLE;
			}
			requestLayout();
		}

	}

	public int dp2px(int dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, getResources().getDisplayMetrics());
	}

}