package kempski.io.paint;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_COLOR = Color.BLACK;
    public static final float DEFAULT_WIDTH = 5f;

    private Toolbar toolbar;
    private Canvas canvas;
    private AmbilWarnaDialog dialog;
    private int previousColor = DEFAULT_COLOR;
    final Activity activity = this;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvas = findViewById(R.id.canvas);
        final Context context = this;

        toolbar = findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);


        findViewById(R.id.undoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                canvas.undo();
            }
        });

        findViewById(R.id.redoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                canvas.redo();
            }
        });

        findViewById(R.id.eraseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvas.enableEraseMode();
            }
        });

        findViewById(R.id.pencilButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvas.disableEraseMode();
                findViewById(R.id.widthComponent).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.select1pxButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvas.setActualWidth(1f);
                findViewById(R.id.widthComponent).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.select3pxButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvas.setActualWidth(3f);
                findViewById(R.id.widthComponent).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.select10pxButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvas.setActualWidth(10f);
                findViewById(R.id.widthComponent).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.select25pxButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvas.setActualWidth(25f);
                findViewById(R.id.widthComponent).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.select50pxButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvas.setActualWidth(50f);
                findViewById(R.id.widthComponent).setVisibility(View.INVISIBLE);
            }
        });


        findViewById(R.id.colorPickButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                dialog = new AmbilWarnaDialog(context, previousColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onOk(final AmbilWarnaDialog dialog, final int color) {
                        previousColor = color;
                        canvas.setColor(color);
                        final ImageButton btn = findViewById(R.id.colorPickButton);
                        btn.setColorFilter(color);
                    }

                    @Override
                    public void onCancel(final AmbilWarnaDialog dialog) {
                        // ignore, closes dialog
                    }

                });
                dialog.show();
            }
        });
    }

}
