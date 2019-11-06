package com.airland.marqueeview;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author AirLand
 * @time on 2019-11-05 14:16
 * @email lish_air@163.com
 * @jianshu https://www.jianshu.com/u/816932948905
 * @gitHub https://github.com/LiShiHui24740
 * @describe:
 */
public abstract class AbstractMarqueeAdapter {
    public abstract int getItemCount();

    public abstract View onCreateView(ViewGroup viewGroup, int position);

    public abstract void onBindViewHolder(ViewGroup viewGroup,View view,int position);
}
