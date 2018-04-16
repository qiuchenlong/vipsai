package com.vs.vipsai.publish.viewmodels;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;


import com.vs.vipsai.R;
import com.vs.vipsai.bean.AwardBean;
import com.vs.vipsai.widget.GlidImageView;

/**
 * * Author: chends
 * Created on 3/28/18 5:33 PM
 * Description:
 *
 *  奖项列表项数据模型，绑定R.layout.list_item_award_cash
 */
public abstract class VMAwardItem {

    public ObservableField<Boolean> selected = new ObservableField<>(false);
    public VMImageItem[] images = new VMImageItem[4];

    private OnImageItemClick mClickCallback;

    private int mDefaultImageRes;

    public VMAwardItem() {
        resetImage();
    }

    private void resetImage() {
        View.OnClickListener[] clicks = new View.OnClickListener[]{
                mImageClick1, mImageClick2, mImageClick3, mImageClick4
        };
        View.OnClickListener[] delClicks = new View.OnClickListener[]{
                mDelClick1, mDelClick2, mDelClick3, mDelClick4
        };
        for(int i = 0; i < images.length; i++) {
            images[i] = new VMImageItem();
            onBuildImage(i, images[i]);
            images[i].onClick = clicks[i];
            images[i].onDelClick = delClicks[i];
            images[i].setShowDelBtn(true);
        }

        if(mDefaultImageRes != 0) {
            setDefaultImage(mDefaultImageRes);
        }
    }

    protected void onBuildImage(int index, VMImageItem imageItem) {
        imageItem.defaultImageRes = R.drawable.default_image;
        imageItem.nullVisiable = View.INVISIBLE;
    }

    public void setDefaultImage(int resId) {
        for (VMImageItem item : images) {
            item.defaultImageRes = resId;
        }
        mDefaultImageRes = resId;
    }

    public void setLocalImage(int index, String path) {
        if(index >= 0 && index < images.length) {
            images[index].localPath.set(path);
        }
    }

    public void setRemoteImage(int index, String path) {
        if(index >= 0 && index < images.length) {
            images[index].url = path;
        }
    }

    protected void onDelClick(View v, int index) {}

//    /**
//     * 图片是否可以点击
//     * @param index
//     * @param clickable
//     */
//    public void setImageClickable(int index, boolean clickable) {
//        if(index >= 0 && index < images.length) {
//            images[index].setClickable(clickable);
//        }
//    }

    /**
     * 設置圖片為空的可見屬性
     * @param index
     * @param visiable
     */
    public void setImageNullVisiable(int index, int visiable) {
        if(index >= 0 && index < images.length) {
            images[index].nullVisiable = visiable;
        }
    }

    /**布局绑定， 图片数量*/
    public abstract int getIconSize();

    /**布局绑定，本地图片数量*/
    public abstract int getLocalPicSize();

    /**布局绑定， 图片地址*/
    public abstract String getIconUrl(int index);

    /**
     * 布局绑定，本地图片地址
     * @param index
     * @return
     */
    public abstract String getLocalPath(int index);

    private View.OnClickListener mImageClick1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mClickCallback != null) {
                mClickCallback.onImageItemClick(v, 0, VMAwardItem.this);
            }
        }
    };

    private View.OnClickListener mImageClick2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mClickCallback != null) {
                mClickCallback.onImageItemClick(v, 1, VMAwardItem.this);
            }
        }
    };

    private View.OnClickListener mImageClick3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mClickCallback != null) {
                mClickCallback.onImageItemClick(v, 2, VMAwardItem.this);
            }
        }
    };

    private View.OnClickListener mImageClick4 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mClickCallback != null) {
                mClickCallback.onImageItemClick(v, 3, VMAwardItem.this);
            }
        }
    };

    public void setOnImageItemClick(OnImageItemClick listener) {
        mClickCallback = listener;
    }

    public interface OnImageItemClick {
        void onImageItemClick(View view, int index, Object data);
        void onItemDelClick(View view, int index, Object data);
    }

    private View.OnClickListener mDelClick1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onDelClick(v, 0);
            resetImage();
            if(mClickCallback != null) {
                mClickCallback.onItemDelClick(v, 0, VMAwardItem.this);
            }
        }
    };

    private View.OnClickListener mDelClick2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onDelClick(v, 1);
            resetImage();
            if(mClickCallback != null) {
                mClickCallback.onItemDelClick(v, 1, VMAwardItem.this);
            }
        }
    };

    private View.OnClickListener mDelClick3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onDelClick(v, 2);
            resetImage();
            if(mClickCallback != null) {
                mClickCallback.onItemDelClick(v, 2, VMAwardItem.this);
            }
        }
    };

    private View.OnClickListener mDelClick4 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onDelClick(v, 3);
            resetImage();
            if(mClickCallback != null) {
                mClickCallback.onItemDelClick(v, 3, VMAwardItem.this);
            }
        }
    };

}
