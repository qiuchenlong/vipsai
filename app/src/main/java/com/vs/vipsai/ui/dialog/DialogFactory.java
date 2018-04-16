package com.vs.vipsai.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.vs.library.CustomDialog;
import com.vs.library.OnViewClickListener;
import com.vs.library.utils.CompatibilityUtil;
import com.vs.library.utils.SystemUtil;
import com.vs.library.widget.FunctionBar;
import com.vs.vipsai.R;
import com.vs.vipsai.adapter.ArrayWheelAdapter;
import com.vs.vipsai.widget.dialog.AlertWheelDialog;
import com.vs.vipsai.widget.wheel.WheelView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by chends on 2017/7/31.
 */

public class DialogFactory {

    private static List<NumberPicker> findNumberPicker(ViewGroup viewGroup){
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if(null != viewGroup){
            for(int i = 0;i<viewGroup.getChildCount();i++){
                child = viewGroup.getChildAt(i);
                if(child instanceof NumberPicker){
                    npList.add((NumberPicker)child);
                }
                else if(child instanceof LinearLayout){
                    List<NumberPicker> result = findNumberPicker((ViewGroup)child);
                    if(result.size()>0){
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    public static CustomDialog showTimeDurationDialog(Context context, int titleRes, OnViewClickListener<String> callback) {
        final CustomDialog dialog = new CustomDialog(context, R.style.common_dialog_theme, CustomDialog.WidthType.AUTO);
        final SoftReference<OnViewClickListener<String>> soft = new SoftReference<>(callback);
        dialog.setTitle(titleRes);

        dialog.setContentView(R.layout.dialog_time_duration_input);

        dialog.setPositiveClickListener(context.getString(R.string.pickerview_submit), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnViewClickListener<String> callback = soft.get();


                if(callback != null) {
                    FunctionBar f = dialog.findViewById(R.id.function_bar);
                    int duration = 0;
                    try {
                        duration = Integer.parseInt(f.getText());
                        if(duration > 0) {
                            Spinner spinner = dialog.findViewById(R.id.spinner);
                            callback.onViewClick(v, new StringBuilder().append(f.getText())
                                    .append((String) spinner.getSelectedItem()).toString());
                        }

                    }catch(NumberFormatException e){}

                }
            }
        });

        dialog.setNegativeClickListener(context.getString(R.string.pickerview_cancel), null);

        dialog.show();
        return dialog;
    }

    /**
     * 简单的滚轮选择器
     * @param context
     * @param data
     * @param callback
     */
    public static void showSimpleWheel(Context context, final String[] data, OnViewClickListener<String> callback) {
        final CustomDialog dialog = createFlipBottom(context);
        final SoftReference<OnViewClickListener<String>> soft = new SoftReference<>(callback);

        dialog.setContentView(R.layout.dialog_simple_wheel_pick, new CustomDialog.OnBindContentListener() {
            @Override
            public void onBind(ViewGroup parent, View content) {
                content.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                content.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        final OnViewClickListener<String> callback = soft.get();
                        if(callback != null) {
                            NumberPicker num = dialog.findViewById(R.id.number_picker);
                            callback.onViewClick(v, num.getDisplayedValues()[num.getValue()]);
                        }
                    }
                });

                NumberPicker num = content.findViewById(R.id.number_picker);
                num.setMinValue(0);
                num.setMaxValue(data.length - 1);
                num.setDisplayedValues(data);
                num.setWrapSelectorWheel(false); //禁止循环
            }
        });



        dialog.show();
    }

    /**
     * 显示名次选择滚轮
     * @param context
     * @param click
     */
    public static void showRankingsWheel(Context context, OnViewClickListener<String> click) {
        final CustomDialog dialog = createFlipBottom(context);
        final SoftReference<OnViewClickListener<String>> soft = new SoftReference<>(click);

        dialog.setContentView(R.layout.dialog_rankings_pick, new CustomDialog.OnBindContentListener() {
            @Override
            public void onBind(ViewGroup parent, View content) {

                String[] grade = content.getResources().getStringArray(R.array.publish_grade);
                NumberPicker num = content.findViewById(R.id.number_picker);
                num.setMinValue(0);
                num.setMaxValue(grade.length - 1);
                num.setDisplayedValues(grade);
                num.setWrapSelectorWheel(false); //禁止循环

                content.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        OnViewClickListener<String> callback = soft.get();
                        if(callback != null) {
                            NumberPicker num = dialog.findViewById(R.id.number_picker);
                            callback.onViewClick(view, num.getDisplayedValues()[num.getValue()]);
                        }
                    }
                });

                content.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }

    private static CustomDialog createFlipBottom(Context context) {
        CustomDialog dialog = new CustomDialog(context, R.style.dialog_flip_bottom_theme, CustomDialog.WidthType.FULL);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.anim_dialog_flip_bottom); // 添加动画
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    /**
     * 显示日期时间选择器
     * @param context
     * @param callback
     * @return
     */
    public static CustomDialog showDateTimePicker(Context context, OnViewClickListener<Long> callback) {
        final CustomDialog dialog = createFlipBottom(context);
//        dialog.setTitle(R.string.pick_date);

        final SoftReference<OnViewClickListener<Long>> callbackR = new SoftReference<OnViewClickListener<Long>>(callback);

        dialog.setContentView(R.layout.dialog_date_time_pick, new CustomDialog.OnBindContentListener() {
            @Override
            public void onBind(ViewGroup parent, View content) {

                content.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textView = (TextView)v;
                        if(v.getResources().getString(R.string.pickerview_cancel).equals(textView.getText())) {
                            //取消
                            dialog.dismiss();
                        }else if(v.getResources().getString(R.string.pre_step).equals(textView.getText())) {
                            //上一步
                            ((TextView)dialog.findViewById(R.id.btnSubmit)).setText(R.string.retrieve_pwd_step_hint);
                            dialog.findViewById(R.id.pick_data).setVisibility(View.VISIBLE);
                            dialog.findViewById(R.id.pick_time).setVisibility(View.GONE);
                            textView.setText(R.string.pickerview_cancel);
                        }

                    }
                });

                content.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textView = (TextView)v;

                        if(v.getResources().getString(R.string.retrieve_pwd_step_hint)
                                .equals(textView.getText())) {
                            //下一步
                            textView.setText(R.string.pickerview_submit);
                            ((TextView)dialog.findViewById(R.id.btn_cancel)).setText(R.string.pre_step);
                            dialog.findViewById(R.id.pick_data).setVisibility(View.GONE);
                            dialog.findViewById(R.id.pick_time).setVisibility(View.VISIBLE);

                        }else if(v.getResources().getString(R.string.pickerview_submit)
                                .equals(textView.getText())) {
                            //确定
                            dialog.dismiss();

                            final OnViewClickListener<Long> callback = callbackR.get();
                            if(callback != null) {
                                DatePicker date = dialog.findViewById(R.id.pick_data);
                                TimePicker timePicker = dialog.findViewById(R.id.pick_time);

                                Calendar c = Calendar.getInstance();
                                c.set(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                                        CompatibilityUtil.getHour(timePicker), CompatibilityUtil.getMinute(timePicker));
                                callback.onViewClick(v, c.getTimeInMillis());
                            }
                        }
                    }
                });

                int itemWidth = (int)(SystemUtil.getScreenWidth(content.getContext()) * 0.8f / 3);
                DatePicker date = content.findViewById(R.id.pick_data);
                List<NumberPicker> dateNumber = findNumberPicker(date);
                for (int i =0; i < dateNumber.size(); i++) {
                    NumberPicker num = dateNumber.get(i);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                    num.setLayoutParams(params);
                }

                TimePicker timePicker = content.findViewById(R.id.pick_time);
                timePicker.setIs24HourView(true);


                dateNumber = findNumberPicker(timePicker);
                for (NumberPicker num : dateNumber) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                    num.setLayoutParams(params);
                }

            }
        });

        dialog.show();

        return dialog;
    }

    public static void showYesOrNoDialog(Context context, OnViewClickListener<Boolean> callback) {
        final SoftReference<OnViewClickListener<Boolean>> soft = new SoftReference<>(callback);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setSingleChoiceItems(new String[]{context.getString(R.string.yes),
                        context.getString(R.string.no)}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        OnViewClickListener<Boolean> callback = soft.get();
                        if(callback != null) {
                            callback.onViewClick(null, ((AlertDialog)d).getListView().getCheckedItemPosition() == 0 ?
                                        true : false);
                        }
                        d.dismiss();
                    }
                }).create();

        dialog.show();
    }

//    public static CustomDialog showSingleBtnTip(Context context, String content, String btnText, View.OnClickListener clickListener) {
//        CustomDialog dialog = new CustomDialog(context, R.style.common_dialog_theme, CustomDialog.WidthType.WRAP_CONTENT);
//        dialog.setTitle(R.string.tip);
//
//        dialog.setContentText(content, Gravity.CENTER);
//
//        dialog.setSingleNegativeButton(btnText, clickListener);
//
//        dialog.show();
//
//        return dialog;
//    }
//
//    /**
//     * 显示等待对话框
//     * @param context
//     * @return
//     */
//    public static CustomDialog showWaiting(Context context) {
//
//        CustomDialog dialog = new CustomDialog(context, R.style.wait_dialog_theme, CustomDialog.WidthType.WRAP_CONTENT);
//        dialog.setContentView(R.layout.simple_wait_lay);
//
//        dialog.show();
//
//        return dialog;
//    }
//
//    public static CustomDialog showNetworkUnavailable(Context context) {
//        final CustomDialog dialog = new CustomDialog(context, R.style.common_dialog_theme, CustomDialog.WidthType.WRAP_CONTENT);
//        dialog.dividerVisiable(false);
//        dialog.setContentView(R.layout.network_unavailable_lay);
//        dialog.show();
//        return dialog;
//    }
//
//    public static CustomDialog showTipDialog(Context context, String content, int positiveTxt, View.OnClickListener positive,
//                                             int negativeTxt, View.OnClickListener negative) {
//        final CustomDialog dialog = new CustomDialog(context, R.style.common_dialog_theme, CustomDialog.WidthType.AUTO);
//        dialog.dividerVisiable(false);
//        dialog.setTitle(R.string.tip);
//
//        dialog.setContentText(content, Gravity.CENTER);
//        if(positiveTxt > 0) {
//            dialog.setPositiveClickListener(context.getString(positiveTxt), positive);
//        }
//        if(negativeTxt > 0) {
//            dialog.setNegativeClickListener(context.getString(negativeTxt), negative);
//        }
//        dialog.show();
//        return dialog;
//    }
//
//    public static CustomDialog showTipDialog(Context context, String content, View.OnClickListener positive) {
//        return showTipDialog(context,content,R.string.confirm,positive, R.string.cancel, null);
//    }
//
//    public static CustomDialog showInputPwdDialog(Context context, final View.OnClickListener positive) {
//        final CustomDialog dialog = new CustomDialog(context, R.style.common_dialog_theme, CustomDialog.WidthType.AUTO);
//        dialog.dividerVisiable(false);
//        dialog.setTitle(R.string.tip);
//
//        ViewGroup content = (ViewGroup)LayoutInflater.from(context).inflate(R.layout.input_bar, null);
//        final InputBarController controller = InputBarController.wrapper(content)
//                .setHint(R.string.password)
//                .setText("")
//                .setBarBackground(R.drawable.round_frame_white_shape)
//                .setBarHeight(content.getResources().getDimensionPixelSize(R.dimen.item_bar_height_small))
//                .setType(InputBarController.Type.PASSWORD)
//                .setMinLength(6)
//                .request();
//
//        dialog.setContentView(content);
//
//        dialog.setPositiveClickListener(context.getString(R.string.confirm), new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(controller.checkValidateAndToast(true)) {
//                    dialog.setResult(controller.getText());
//                    if(positive != null) {
//                        positive.onClick(view);
//                    }
//                }
//            }
//        });
//        dialog.setNegativeClickListener(context.getString(R.string.cancel), null);
//
//        dialog.show();
//        return dialog;
//    }
//
//    /**
//     * 显示输入对话框，输入文字保存在CustomDialog.getParams
//     * @param context
//     * @param title
//     * @param hint
//     * @param positive
//     * @return
//     */
//    public static CustomDialog showInputDialog(Context context, CharSequence title, String hint, CharSequence preContent, final View.OnClickListener positive) {
//        final CustomDialog dialog = new CustomDialog(context, R.style.common_dialog_theme, CustomDialog.WidthType.AUTO);
//        dialog.dividerVisiable(false);
//        dialog.setTitle(title);
//
//        ViewGroup content = (ViewGroup)LayoutInflater.from(context).inflate(R.layout.input_bar, null);
//        final InputBarController controller = InputBarController.wrapper(content)
//                .setHint(hint)
//                .setText(preContent)
//                .setBarBackground(R.drawable.round_frame_white_shape)
//                .setBarHeight(content.getResources().getDimensionPixelSize(R.dimen.item_bar_height_small));
//
//        dialog.setContentView(content);
//
//        dialog.setPositiveClickListener(context.getString(R.string.confirm), new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String input = controller.getText();
//
//                if(TextUtils.isEmpty(input)) {
//                    Toast.makeText(view.getContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
//                }else {
//                    dialog.setResult(input);
//                    if(positive != null) {
//                        positive.onClick(view);
//                    }
//                }
//            }
//        });
//        dialog.setNegativeClickListener(context.getString(R.string.cancel), null);
//
//        dialog.show();
//        return dialog;
//    }
//
//    public static CustomDialog showEnterpriseInvitationDialog(Context context, BeanEntDept ent, final View.OnClickListener positive) {
//        final CustomDialog dialog = new CustomDialog(context, R.style.common_dialog_theme, CustomDialog.WidthType.WRAP_CONTENT);
//        dialog.dividerVisiable(false);
//        dialog.setParams(ent);
//
//        dialog.setContentView(R.layout.enterprise_invitation_dialog_lay, new CustomDialog.OnBindContentListener() {
//            @Override
//            public void onBind(ViewGroup parent, View content) {
//                BeanEntDept ent = (BeanEntDept)dialog.getParams()[0];
//                BeanUserInfo account = AccountManager.getInstance().getActivityAccount(parent.getContext());
//
//                TextView textView = (TextView)content.findViewById(R.id.title);
//                textView.setText(parent.getContext().getString(R.string.somebody_invite_to_enterprise, account.getNickname(), ent.getName()));
//            }
//        });
//
//        dialog.setPositiveClickListener(context.getString(R.string.send), new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText editText = (EditText)dialog.getContentContainer().findViewById(R.id.reason);
//                String reason = "";
//                if(editText.getText().length() > 0) {
//                    reason = editText.getText().toString().trim();
//                }
//
//                dialog.setResult(reason);
//                if(positive != null) {
//                    positive.onClick(view);
//                }
//            }
//        });
//        dialog.setNegativeClickListener(context.getString(R.string.cancel), null);
//
//        dialog.show();
//        return dialog;
//    }
//
//    public static CustomDialog showDownloadFileDialog(final Context context, final YunFileEntity file, Runnable callback) {
//        final CustomDialog dialog = new CustomDialog(context, R.style.common_dialog_theme, CustomDialog.WidthType.AUTO);
//        dialog.dividerVisiable(false);
//        dialog.setTitle(R.string.download_file);
//
//        dialog.setContentView(R.layout.progress_lay);
//        dialog.findViewById(R.id.progress_root).setBackgroundResource(R.color.default_bg);
//
//        final DownloadFileListener downloadListener = new DownloadFileListener(dialog, file.getId(), callback);
//
//        dialog.setSingleNegativeButton(context.getString(R.string.cancel), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FileDownloadManager.getInstance().stopDownload(file.getId());
//            }
//        });
//
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                FileDownloadManager fd = FileDownloadManager.getInstance();
//                fd.addListener(downloadListener);
//                fd.startDownload(context, file);
//            }
//        });
//
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                FileDownloadManager.getInstance().removeListener(downloadListener);
//            }
//        });
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
//        return dialog;
//    }
//
//    private static class DownloadFileListener implements FileDownloadListener {
//
//        private CustomDialog mDialog;
//        private int mFileId;
//        private SoftReference<Runnable> mCallback;
//
//        public DownloadFileListener(CustomDialog dialog, int fileId, Runnable callback) {
//            mFileId = fileId;
//            mDialog = dialog;
//            mCallback = new SoftReference<Runnable>(callback);
//        }
//
//        @Override
//        public void onStart(int fileId, long progress, long max) {
//            if(fileId == mFileId && mDialog != null) {
//                int percent = StringUtil.longDivPercent(progress, max);
//                ((TextView)mDialog.findViewById(R.id.progress_txt)).setText(String.valueOf(percent) + "%");
//
//                ProgressBar pb = (ProgressBar)mDialog.findViewById(R.id.progress);
//                pb.setMax((int)max);
//                pb.setProgress((int)progress);
//            }
//        }
//
//        @Override
//        public void onError(int fileId, String msg, long progress, long max) {
//            if(mDialog != null) {
//                Toast.makeText(mDialog.getContext(), R.string.download_file_fail, Toast.LENGTH_SHORT).show();
//                mDialog.dismiss();
//            }
//        }
//
//        @Override
//        public void onStop(int fileId, String localFile, long progress, long max) {
//            Runnable callback = mCallback.get();
//            if(mFileId == fileId && callback != null && progress >= max) {
//                callback.run();
//            }
//            if(mDialog != null) {
//                mDialog.dismiss();
//            }
//        }
//
//        @Override
//        public void onUpdateProgress(int fileId, long progress, long max) {
//            if(mDialog != null && fileId == mFileId) {
//                int percent = StringUtil.longDivPercent(progress, max);
//                ((TextView)mDialog.findViewById(R.id.progress_txt)).setText(String.valueOf(percent) + "%");
//
//                ProgressBar pb = (ProgressBar)mDialog.findViewById(R.id.progress);
//                pb.setMax((int)max);
//                pb.setProgress((int)progress);
//            }
//        }
//    }
}
