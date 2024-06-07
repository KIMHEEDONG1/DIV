package com.example.dcu_image_viewr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private ArrayList<String> imagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        imagePaths = new ArrayList<>();

        // 이미지 로드
        loadImages();
    }

    private void loadImages() {
        File directory = new File(Environment.getExternalStorageDirectory(), "Download");
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".jpg")) {
                        imagePaths.add(file.getAbsolutePath());
                    }
                }
            }
        }

        gridView.setAdapter(new ImageAdapter());
    }

    private class ImageAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return imagePaths.size();
        }

        @Override
        public Object getItem(int position) {
            return imagePaths.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(MainActivity.this);
                imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 500));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;
            }

            final String imagePath = imagePaths.get(position);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ImageDisplayActivity.class);
                    intent.putExtra("imagePath", imagePath);
                    startActivity(intent);
                }
            });
            return imageView;
        }
    }
}

