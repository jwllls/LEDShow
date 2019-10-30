package com.led.ledshow.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.led.ledshow.R;

public class ItemCommomView extends LinearLayout implements View.OnClickListener {

    public IconClickCallbakc clickCallbakc;

    public void setClickCallbakc(IconClickCallbakc clickCallbakc) {
        this.clickCallbakc = clickCallbakc;
    }

    private View view;

    private TextView tv_title, tv_right_text;

    private ImageView iv_left_icon, iv_right_icon,iv_icon_other;

    private String title,leftText;
    private int titleTextSize,titleColor;
    private int leftResId, rightResId;
    private int leftIconSize, rightIconSize;


    public ItemCommomView(Context context) {
        super(context);
        initView();
    }

    public ItemCommomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.settingItem);
        title = array.getString(R.styleable.settingItem_title);
        leftText = array.getString(R.styleable.settingItem_leftText);
        leftResId = array.getResourceId(R.styleable.settingItem_leftIcon, R.drawable.picture_back);
        rightResId = array.getResourceId(R.styleable.settingItem_rightIcon,  R.drawable.icon_arrow_right);
        initView();
        array.recycle();
    }

    public ItemCommomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.item_common_view, null);
        tv_title = view.findViewById(R.id.tv_title);
        tv_right_text = view.findViewById(R.id.tv_right_text);
        iv_left_icon = view.findViewById(R.id.iv_left_icon);
        iv_right_icon = view.findViewById(R.id.iv_right_icon);
        iv_icon_other = view.findViewById(R.id.iv_left_icon_other);

        tv_title.setText(title);
        iv_left_icon.setImageDrawable(ContextCompat.getDrawable(getContext(),leftResId));
        iv_right_icon.setImageDrawable(ContextCompat.getDrawable(getContext(),rightResId));
        this.addView(view);
    }


    public void setleftIcon(int iconRes) {
//        iv_left_icon.setImageDrawable(ContextCompat.getDrawable(getContext(), iconRes));
        iv_left_icon.setImageResource(iconRes);
    }

    public void setOhter(int res){
        iv_icon_other.setVisibility(VISIBLE);
        iv_icon_other.setBackgroundColor(res);
    }

    public void setRIghtIcon(int iconRes) {
//        iv_right_icon.setImageDrawable(ContextCompat.getDrawable(getContext(), iconRes));
        iv_right_icon.setImageResource(iconRes);
    }



    public void setRightText(String text){
        tv_right_text.setText(text);
    }


    public void setTitle(String title) {
        tv_title.setText(title);
    }

    @Override
    public void onClick(View v) {
        clickCallbakc.onIconClick(v);
    }

    public interface IconClickCallbakc {
        void onIconClick(View view);
    }


}
