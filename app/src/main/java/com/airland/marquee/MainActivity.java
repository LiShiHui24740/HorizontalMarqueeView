package com.airland.marquee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airland.marqueeview.AbstractMarqueeAdapter;
import com.airland.marqueeview.HorizontalMarqueeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private HorizontalMarqueeView horizontalMarqueeView1;
    private HorizontalMarqueeView horizontalMarqueeView2;
    private HorizontalMarqueeView horizontalMarqueeView3;
    private List<String> data;
    private AbstractMarqueeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        horizontalMarqueeView1 = findViewById(R.id.hmv_test1);
        horizontalMarqueeView2 = findViewById(R.id.hmv_test2);
        horizontalMarqueeView3 = findViewById(R.id.hmv_test3);
        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("test-" + (i + 1));
        }
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
        horizontalMarqueeView2.setAdapter(adapter);
        horizontalMarqueeView3.setAdapter(adapter);
    }
}
