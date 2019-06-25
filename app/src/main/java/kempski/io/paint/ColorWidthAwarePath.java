package kempski.io.paint;

import android.graphics.Path;

public class ColorWidthAwarePath extends Path {
    private final int color;
    private final float width;

    ColorWidthAwarePath(final int color, final float width) {
        this.color = color;
        this.width = width;
    }

    public int getColor() {
        return color;
    }

    public float getWidth() {
        return width;
    }
}
