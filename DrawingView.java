package com.example.mypaintapp;
import android.util.AttributeSet;
import android.util.Pair;
import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import java.util.ArrayList;

public class DrawingView extends View {

    private Paint paint;
    private Path path;
    private ArrayList<Pair<Path,Paint>> paths = new ArrayList<>();
    private ArrayList<Pair<Path,Paint>> undonePaths = new ArrayList<>();

    private int currentColor = Color.BLACK;
    private float brushSize = 10f;
    private float canvasScale = 1.0f;

    public DrawingView(Context context) {
        super(context);
        setupPaint();
    }
    public DrawingView(Context context, AttributeSet attrs)
    {
        super (context, attrs);
        setupPaint();
    }

    private void setupPaint() {
        paint = new Paint();
        paint.setColor(currentColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(brushSize);
    }

    public void enableEraser(){
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.scale(canvasScale,canvasScale);
        for (Pair<Path,Paint> p : paths) {
            canvas.drawPath(p.first,p.second);
        }
        if (path != null) {
            canvas.drawPath(path, paint);
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX() / canvasScale;
        float y = event.getY() / canvasScale;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                performClick();
                path = new Path();
                path.moveTo(x, y);
                undonePaths.clear();
                break;

            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;

            case MotionEvent.ACTION_UP:
                paths.add(new Pair<>(path, new Paint(paint)));
                path = null;
                break;
        }

        invalidate();
        return true;
    }

    // ===== FEATURES =====

    public void setColor(int color) {
        currentColor = color;
        paint.setXfermode(null);
        paint.setColor(color);
    }

    public void setBrushSize(float size) {
        brushSize = size;
        paint.setStrokeWidth(size);
    }

    public void zoomIn() {
        canvasScale += 0.2f;
        invalidate();
    }

    public void zoomOut() {
        if (canvasScale > 0.4f){
            canvasScale -= 0.2f;
            invalidate();
        }
    }

    public void clearCanvas() {
        paths.clear();
        undonePaths.clear();
        path = new Path();
        invalidate();
    }

    public void undo() {
        if (!paths.isEmpty()) {
            undonePaths.add(paths.remove(paths.size() - 1));
            invalidate();
        }
    }

    public void redo() {
        if (!undonePaths.isEmpty()) {
            paths.add(undonePaths.remove(undonePaths.size() - 1));
            invalidate();
        }
    }
}