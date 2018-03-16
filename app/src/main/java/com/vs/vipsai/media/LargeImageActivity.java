package com.vs.vipsai.media;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import com.vs.vipsai.R;
import com.vs.vipsai.AppOperator;
import com.vs.vipsai.base.activities.BaseActivity;
import com.vs.vipsai.common.utils.BitmapUtil;
import com.vs.vipsai.common.utils.StreamUtil;
import com.vs.vipsai.common.widget.Loading;

import java.io.File;
import java.util.List;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 大图预览
 * Created by huanghaibin on 2017/9/27.
 */

public class LargeImageActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.imageView)
    SubsamplingScaleImageView mImageView;

    @SuppressWarnings("unused")
    @BindView(R.id.iv_save)
    ImageView mImageSave;

    @BindView(R.id.loading)
    Loading mLoading;

    private String mPath;

    public static void show(Context context, String image) {
        Intent intent = new Intent(context, LargeImageActivity.class);
        intent.putExtra("image", image);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_large_image;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        mImageView.setMaxScale(15);
        mImageView.setZoomEnabled(true);
        mImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPath = getIntent().getStringExtra("image");
        getImageLoader()
                .load(mPath)
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        if (isDestroyed())
                            return;
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(resource.getPath(), options);
                        int w = options.outWidth;
                        int sw = Util.getScreenWidth(LargeImageActivity.this);
                        float scale = (float) sw / (float) w;
                        mImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
                        mImageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(scale,
                                new PointF(0, 0), com.vs.vipsai.common.utils.BitmapUtil.readPictureDegree(mPath)));
                        mImageSave.setVisibility(View.VISIBLE);
                        mLoading.stop();
                        mLoading.setVisibility(View.GONE);
                    }
                });
    }

    private static final int PERMISSION_ID = 0x0001;

    @SuppressWarnings("unused")
    @AfterPermissionGranted(PERMISSION_ID)
    @OnClick(R.id.iv_save)
    public void saveToFileByPermission() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, permissions)) {
            saveToFile();
        } else {
            EasyPermissions.requestPermissions(this, "请授予保存图片权限", PERMISSION_ID, permissions);
        }
    }

    private void saveToFile() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, R.string.gallery_save_file_not_have_external_storage, Toast.LENGTH_SHORT).show();
            return;
        }

        final Future<File> future = getImageLoader()
                .load(mPath)
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                try {
                    File sourceFile = future.get();
                    if (sourceFile == null || !sourceFile.exists())
                        return;
                    String extension = BitmapUtil.getExtension(sourceFile.getAbsolutePath());
                    String extDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            .getAbsolutePath() + File.separator + "开源中国";
                    File extDirFile = new File(extDir);
                    if (!extDirFile.exists()) {
                        if (!extDirFile.mkdirs()) {
                            // If mk dir error
                            callSaveStatus(false, null);
                            return;
                        }
                    }
                    final File saveFile = new File(extDirFile, String.format("IMG_%s.%s", System.currentTimeMillis(), extension));
                    final boolean isSuccess = StreamUtil.copyFile(sourceFile, saveFile);
                    callSaveStatus(isSuccess, saveFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    callSaveStatus(false, null);
                }
            }
        });
    }

    private void callSaveStatus(final boolean success, final File savePath) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (success) {
                    // notify
                    if (isDestroyed())
                        return;
                    Uri uri = Uri.fromFile(savePath);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    Toast.makeText(LargeImageActivity.this, R.string.gallery_save_file_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LargeImageActivity.this, R.string.gallery_save_file_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, R.string.gallery_save_file_not_have_external_storage_permission, Toast.LENGTH_SHORT).show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


}
