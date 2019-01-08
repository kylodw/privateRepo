package com.bumptech.glide.samples.sourcecode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.iv_test);

        Glide.with(this).load(R.drawable.ic_launcher_foreground).into(imageView);

        TestFactory testFactory = new ImpleTestFactory();
        TestBean testBean = testFactory.build("hah");
    }
}
