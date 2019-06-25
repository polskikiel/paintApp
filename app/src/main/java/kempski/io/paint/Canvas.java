package kempski.io.paint;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

import static kempski.io.paint.MainActivity.DEFAULT_COLOR;
import static kempski.io.paint.MainActivity.DEFAULT_WIDTH;

public class Canvas extends View {
    private final Paint paint;
    private final List<ColorWidthAwarePath> paths;
    private final List<ColorWidthAwarePath> undonePaths;
    private ColorWidthAwarePath currentPath;
    private int actualColor;
    private float actualWidth;
    private boolean eraseMode;
    private boolean initialized;

    public Canvas(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        this.setDrawingCacheEnabled(true);

        paint = new Paint();
        actualColor = DEFAULT_COLOR;
        actualWidth = DEFAULT_WIDTH;
        currentPath = new ColorWidthAwarePath(actualColor, actualWidth);
        paint.setAntiAlias(true);
        paint.setColor(actualColor);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        paths = new LinkedList<>();
        undonePaths = new LinkedList<>();
        eraseMode = false;
        initialized = false;
    }

    public void undo() {
        if (paths.isEmpty()) {
            return;
        }

        undonePaths.add(lastElementOf(paths));
        paths.remove(lastElementOf(paths));
        invalidate();
    }

    public void redo() {
        if (undonePaths.isEmpty()) {
            return;
        }

        paths.add(lastElementOf(undonePaths));
        undonePaths.remove(lastElementOf(undonePaths));
        invalidate();
    }

    public void setColor(final int color) {
        this.actualColor = color;
        currentPath = new ColorWidthAwarePath(color, actualWidth);
    }

    public void setActualWidth(final float width) {
        this.actualWidth = width;
        if (!eraseMode) {
            currentPath = new ColorWidthAwarePath(actualColor, width);
        } else {
            currentPath = new ColorWidthAwarePath(Color.WHITE, width);
        }
    }

    public void enableEraseMode() {
        this.eraseMode = true;
        currentPath = new ColorWidthAwarePath(Color.WHITE, actualWidth);
    }

    public void disableEraseMode() {
        this.eraseMode = false;
        currentPath = new ColorWidthAwarePath(actualColor, actualWidth);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        float xPos = event.getX();
        float yPos = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath.moveTo(xPos, yPos);
                return true;
            case MotionEvent.ACTION_MOVE:
                currentPath.lineTo(xPos, yPos);
                break;
            case MotionEvent.ACTION_UP:
                paths.add(currentPath);
                undonePaths.clear();
                if (!eraseMode) {
                    currentPath = new ColorWidthAwarePath(actualColor, actualWidth);
                } else {
                    currentPath = new ColorWidthAwarePath(Color.WHITE, 50f);
                }
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(final android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        if (!initialized) {
            canvas.drawColor(Color.WHITE);
        }

        canvas.drawPath(currentPath, paint);

        for (final ColorWidthAwarePath path : paths) {
            paint.setColor(path.getColor());
            paint.setStrokeWidth(path.getWidth());
            canvas.drawPath(path, paint);
        }

        paint.setColor(currentPath.getColor());
        paint.setStrokeWidth(currentPath.getWidth());
        canvas.drawPath(currentPath, paint);
    }

    private <T> T lastElementOf(final List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalStateException("Can't get last element of empty list");
        }
        return list.get(list.size() - 1);
    }
}
