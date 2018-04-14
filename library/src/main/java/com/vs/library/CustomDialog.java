package com.vs.library;

import android.app.Dialog;
import android.content.Context;
import android.text.Spannable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.vs.library.utils.SystemUtil;

/**
 * 统一对话框
 * @author chends
 * @date 2015-8-22
 */
public class CustomDialog extends Dialog{

	/**
	 * 窗口大小
	 * @author 258
	 *
	 */
	public enum WidthType {
		/**自动*/
		AUTO, 
		/**适应内容*/
		WRAP_CONTENT, 
		/**全屏*/
		FULL
	}
	
	private View.OnClickListener mPositiveClick;
	private View.OnClickListener mNegativeClick;

	/**
	 * 对话框返回数据寄存处
	 */
	private Object[] mResult;
	/**
	 * 输入参数寄存处
	 */
	private Object[] mParams;

	public CustomDialog(Context context, int theme) {
		super(context, theme);
		init(context, WidthType.AUTO, Gravity.CENTER);
	}
	
	public CustomDialog(Context context, int theme, WidthType autoSize, int gravity) {
		super(context, theme);
		init(context, autoSize, gravity);
	}
	
	public CustomDialog(Context context, int theme, WidthType autoSize) {
		super(context, theme);
		init(context, autoSize, Gravity.CENTER);
	}

	protected void init(Context context, WidthType autoSize, int gravity) {

		Window win = getWindow();
		win.getDecorView().setPadding(0, 0, 0, 0);

		int screenWidth = SystemUtil.getScreenWidth(context);
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.gravity = gravity;

		if(autoSize == WidthType.AUTO) {
			lp.width = (int) Math.min((int) (screenWidth * 0.9f), SystemUtil.convertDpToPixel(400, context));
			lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		}else if(autoSize == WidthType.WRAP_CONTENT){
			lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
			lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		}else if(autoSize == WidthType.FULL) {
			lp.width = screenWidth;
			lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		}

		win.setAttributes(lp);

		super.setContentView(R.layout.custom_dialog_lay);

		((TextView) findViewById(R.id.negative_btn)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mNegativeClick != null) {
					mNegativeClick.onClick(v);
				}

				cancel();
			}
		});

		((TextView) findViewById(R.id.positive_btn)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mPositiveClick != null) {
					mPositiveClick.onClick(v);
				}

				dismiss();
			}
		});
		
		findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		setCanceledOnTouchOutside(false);
	}

	/**
	 * 对话框返回结果
	 * @param object
	 */
	public void setResult(Object...object) {
		mResult = object;
	}
	
	public void setCloseBtnVisibile(int visible) {
		findViewById(R.id.close_btn).setVisibility(visible);
	}
	
	/**
	 * 对话框返回结果
	 */
	public Object[] getResult() {
		return mResult;
	}
	
	/**设置入参*/
	public void setParams(Object...params) {
		mParams = params;
	}

    public void dividerVisiable(boolean visiable) {
        findViewById(R.id.dialog_title_divider).setVisibility(visiable ? View.VISIBLE : View.GONE);
        findViewById(R.id.dialog_button_divider).setVisibility(visiable ? View.VISIBLE : View.GONE);
    }

	/**
	 * 获取入参
	 * @return
	 */
	public Object[] getParams() {
		return mParams;
	}
	
	@Override
	public void setTitle(CharSequence title) {
		findViewById(R.id.title_lay).setVisibility(View.VISIBLE);
		TextView titleView = (TextView) findViewById(R.id.dialog_title);
		titleView.setText(title);
	}

	@Override
	public void setTitle(int titleId) {
		TextView titleView = (TextView) findViewById(R.id.dialog_title);
		findViewById(R.id.title_lay).setVisibility(View.VISIBLE);
		titleView.setText(getContext().getString(titleId));
	}

	public void setTitle(int titleId, int color) {
		TextView titleView = (TextView) findViewById(R.id.dialog_title);
		findViewById(R.id.title_lay).setVisibility(View.VISIBLE);
		titleView.setTextColor(color);
		titleView.setText(getContext().getString(titleId));
	}

	@Override
	public void setContentView(View view) {
		ViewGroup container = (ViewGroup) findViewById(R.id.content_container);
		container.setVisibility(View.VISIBLE);
		findViewById(R.id.simple_lay).setVisibility(View.GONE);
		if (container.getChildCount() > 0) {
			container.removeAllViews();
		}

		container.addView(view);
	}
	
	public void setContentText(String text, int gravity) {
		findViewById(R.id.content_container).setVisibility(View.GONE);
		findViewById(R.id.simple_lay).setVisibility(View.VISIBLE);

		TextView content = (TextView)findViewById(R.id.simple_text);
		content.setGravity(gravity);
		content.setText(text);
	}

	public void setContentText(Spannable text, int gravity) {
		findViewById(R.id.content_container).setVisibility(View.GONE);
		findViewById(R.id.simple_lay).setVisibility(View.VISIBLE);

		TextView content = (TextView)findViewById(R.id.simple_text);
		content.setGravity(gravity);
		content.setText(text);
	}

	@Override
	public void setContentView(int layoutResID) {
		ViewGroup container = (ViewGroup) findViewById(R.id.content_container);
		container.setVisibility(View.VISIBLE);
		findViewById(R.id.simple_lay).setVisibility(View.GONE);
		if (container.getChildCount() > 0) {
			container.removeAllViews();
		}

		LayoutInflater.from(getContext()).inflate(layoutResID, container, true);
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		ViewGroup container = (ViewGroup) findViewById(R.id.content_container);
		container.setVisibility(View.VISIBLE);
		findViewById(R.id.simple_lay).setVisibility(View.GONE);
		if (container.getChildCount() > 0) {
			container.removeAllViews();
		}

		container.addView(view, params);
	}

	public ViewGroup getContentContainer() {
		return (ViewGroup) findViewById(R.id.content_container);
	}
	
	public void setContentView(int layoutResID, OnBindContentListener listener) {
		ViewGroup container = (ViewGroup) findViewById(R.id.content_container);
		container.setVisibility(View.VISIBLE);
		findViewById(R.id.simple_lay).setVisibility(View.GONE);
		if (container.getChildCount() > 0) {
			container.removeAllViews();
		}

		View view = LayoutInflater.from(getContext()).inflate(layoutResID, container, false);
		container.addView(view);

		if (listener != null) {
			listener.onBind(container, view);
		}
	}

	public void setPositiveClickListener(String txt, View.OnClickListener listener) {
		mPositiveClick = listener;
		findViewById(R.id.dialog_button_layout).setVisibility(View.VISIBLE);
		TextView button = (TextView) findViewById(R.id.positive_btn);
		button.setVisibility(View.VISIBLE);
		button.setText(txt);

//		if (findViewById(R.id.negative_btn).getVisibility() == View.VISIBLE) {
//			findViewById(R.id.btn_separator).setVisibility(View.VISIBLE);
//		}
	}

	public void setNegativeClickListener(String txt, View.OnClickListener listener) {
		mNegativeClick = listener;
		findViewById(R.id.dialog_button_layout).setVisibility(View.VISIBLE);
		TextView button = (TextView) findViewById(R.id.negative_btn);
		button.setVisibility(View.VISIBLE);
		button.setText(txt);

//		if (findViewById(R.id.positive_btn).getVisibility() == View.VISIBLE) {
//			findViewById(R.id.btn_separator).setVisibility(View.VISIBLE);
//		}
	}

	public void setPositiveClickListenerWithoutDismiss(String text, View.OnClickListener listener) {
		TextView confirm = (TextView) findViewById(R.id.positive_btn);
		confirm.setVisibility(View.VISIBLE);
		confirm.setOnClickListener(listener);
		confirm.setText(text);
//		if (findViewById(R.id.negative_btn).getVisibility() == View.VISIBLE) {
//			findViewById(R.id.btn_separator).setVisibility(View.VISIBLE);
//		}
	}
	
	public void setSingleNegativeButton(String txt, final View.OnClickListener listener) {
		findViewById(R.id.dialog_button_layout).setVisibility(View.VISIBLE);
		TextView button = (TextView) findViewById(R.id.negative_btn);
		button.setVisibility(View.VISIBLE);
		button.setText(txt);

		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null) {
					listener.onClick(v);
				}
				cancel();
			}

		});
		
		findViewById(R.id.positive_btn).setVisibility(View.GONE);
//		findViewById(R.id.btn_separator).setVisibility(View.GONE);
	}
	
	public void setSinglePositiveButton(String txt, final View.OnClickListener listener) {
		findViewById(R.id.dialog_button_layout).setVisibility(View.VISIBLE);
		TextView button = (TextView) findViewById(R.id.positive_btn);
		button.setVisibility(View.VISIBLE);
		button.setText(txt);

		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null) {
					listener.onClick(v);
				}
				cancel();
			}

		});
		
		findViewById(R.id.negative_btn).setVisibility(View.GONE);
//		findViewById(R.id.btn_separator).setVisibility(View.GONE);
	}

	/**
	 * 布局解析监听器
	 * @author 258
	 *
	 */
	public interface OnBindContentListener {
		public void onBind(ViewGroup parent, View content);
	}

}
