package com.example.mypaintapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.widget.LinearLayout;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawingView);

        Button penBtn = findViewById(R.id.penBtn);
        Button eraserBtn = findViewById(R.id.eraserBtn);
        Button undoBtn = findViewById(R.id.undoBtn);
        Button redoBtn = findViewById(R.id.redoBtn);
        Button clearBtn = findViewById(R.id.clearBtn);
        Button hideToolsBtn = findViewById(R.id.hideToolsBtn);
        LinearLayout toolsContainer = findViewById(R.id.toolsContainer);
        Button colorPickerBtn = findViewById(R.id.colorPickerBtn);
        Button zoomInBtn = findViewById(R.id.zoomInBtn);
        Button zoomOutBtn = findViewById(R.id.zoomOutBtn);

        colorPickerBtn.setOnClickListener(v -> {
            drawingView.setColor(Color.MAGENTA);
        });

        SeekBar brushSize = findViewById(R.id.brushSize);

        penBtn.setOnClickListener(v -> drawingView.setColor(Color.BLACK));

        eraserBtn.setOnClickListener(v -> { drawingView.enableEraser();});

        undoBtn.setOnClickListener(v -> drawingView.undo());

        redoBtn.setOnClickListener(v -> drawingView.redo());

        clearBtn.setOnClickListener(v -> drawingView.clearCanvas());

        hideToolsBtn.setOnClickListener(v -> {

            if (toolsContainer.getVisibility() == View.VISIBLE) {

                toolsContainer.setVisibility(View.GONE);

            } else {

                toolsContainer.setVisibility(View.VISIBLE);
                hideToolsBtn.setText("Hide");
            }

        });
        SeekBar redSeek = findViewById(R.id.redSeek);
        SeekBar greenSeek = findViewById(R.id.greenSeek);
        SeekBar blueSeek = findViewById(R.id.blueSeek);
        SeekBar.OnSeekBarChangeListener colorListener =
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress,
                                                  boolean fromUser) {

                        int red = redSeek.getProgress();
                        int green = greenSeek.getProgress();
                        int blue = blueSeek.getProgress();

                        int color = Color.rgb(red, green, blue);

                        drawingView.setColor(color);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                };

        redSeek.setOnSeekBarChangeListener(colorListener);
        greenSeek.setOnSeekBarChangeListener(colorListener);
        blueSeek.setOnSeekBarChangeListener(colorListener);



        zoomInBtn.setOnClickListener(v -> drawingView.zoomIn());

        zoomOutBtn.setOnClickListener(v -> drawingView.zoomOut());

        brushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawingView.setBrushSize(progress);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        findViewById(R.id.colorBlack).setOnClickListener(v -> drawingView.setColor(Color.BLACK));
        findViewById(R.id.colorRed).setOnClickListener(v -> drawingView.setColor(Color.RED));
        findViewById(R.id.colorGreen).setOnClickListener(v -> drawingView.setColor(Color.GREEN));
        findViewById(R.id.colorBlue).setOnClickListener(v -> drawingView.setColor(Color.BLUE));
        findViewById(R.id.colorYellow).setOnClickListener(v -> drawingView.setColor(Color.YELLOW));

    }
}