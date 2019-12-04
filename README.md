# HorizontalMarqueeView
最近项目需求要实现图片以及各种布局的类似跑马灯效果，网上搜索了一下没有想要的，于是就自己写了一个。实现了View的复用，只创建了可显示区域的View。

### 效果
![github](https://github.com/LiShiHui24740/HorizontalMarqueeView/blob/master/img/marquee.gif)  

### 集成
```
dependencies {
    implementation 'com.github.airland:horizontalmarqueeview:1.0.4'
}
```

### 使用
```
 1.布局，自定义属性 ltr：是否从左向右滚动，默认false;  speed: 滚动速度，默认为2
 
 <com.airland.marqueeview.HorizontalMarqueeView
        android:id="@+id/hmv_test1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:ltr="true"
        app:speed="5" />
        
 2. 在界面中使用和RecyclerView,ListView类似，设置Adapter,自己可以继承AbstractMarqueeAdapter
 
 horizontalMarqueeView1 = findViewById(R.id.hmv_test1);
 
  adapter = new AbstractMarqueeAdapter() {
            @Override
            public int getItemCount() {
                return data.size();
            }

            @Override
            public View onCreateView(ViewGroup viewGroup, int position) {
                return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adpter_layout, viewGroup, false);
            }

            @Override
            public void onBindViewHolder(ViewGroup viewGroup, View view, int position) {
                TextView textView = view.findViewById(R.id.tv_test);
                textView.setText(data.get(position));
            }
        };
        
 horizontalMarqueeView1.setAdapter(adapter);
 
```
