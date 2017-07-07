package com.hosung.drawpadandepubreader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hosung.drawpadandepubreader.models.DrawNote;
import com.hosung.drawpadandepubreader.models.DrawPath;
import com.hosung.drawpadandepubreader.models.DrawPoint;
import com.hosung.drawpadandepubreader.util.Places;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import hosung.colorpickerlib.ColorPal;
import hosung.colorpickerlib.ColorPicker;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Hosung, Lee on 2017. 5. 23..
 */

public class DrawPadActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {
    private static final String TAG = "DrawPadActivity";
    private static final int REQUEST_BACKIMG = 0;

    private static final int EDGE_WIDTH = 683;
    private SurfaceView surfaceView;
    private volatile Realm realm;
    private double ratio = -1;
    private double marginLeft;
    private double marginTop;
    private double padWidth = 0;
    private double padHeight = 0;
    private DrawThread drawThread;
    private Boolean drawMode = true;
    private Integer currentColor = Color.BLACK;
    private Integer currentBushSize = 4;
    private DrawNote currentNote = null;
    private DrawPath currentPath  = null;
    private int currentNoteId = 0;
    private boolean isClearing = false;
    Bitmap currentBitmap = null;

    private List<Integer> colorList = new ArrayList<Integer>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_draw:
                    drawMode = true;
                    return true;
                case R.id.navigation_erase:
                    drawMode = false;
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_pad);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        surfaceView.getHolder().addCallback(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        realm = Realm.getDefaultInstance();            
        currentNoteId = getIntent().getIntExtra("NoteID", 0);
        if (currentNoteId != 0) {
            currentNote = realm.where(DrawNote.class).equalTo("id", currentNoteId).findFirst();
        } else {
            realm.beginTransaction();
            int nextID = 1;
            try {
                nextID = realm.where(DrawNote.class).max("id").intValue() + 1;
            } catch (Exception exp) {
                exp.printStackTrace();
            }
            currentNote = realm.createObject(DrawNote.class, nextID);
            currentNoteId = currentNote.getId();
            currentNote.setUser(MainActivity.userEmail);
            realm.commitTransaction();
        }

        if (savedInstanceState != null) {
            currentBitmap = savedInstanceState.getParcelable("BitmapImage");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawpad, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.optmenuClear);
        if(currentNote!=null && currentNote.isSaved())
            item.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.optmenuColor) {
            callColorPicker();
            return true;
        } else if (id == R.id.optmenuBrushSize) {
            callBrushSizeDlg();
            return true;
        } else if (id == R.id.optmenuSave) {
            saveNote();
            return true;
        } else if (id == R.id.optmenuClear) {
            if(!isClearing) clearNote();
            return true;
        } else if (id == R.id.optmenuBackImage){
            Intent intent = new Intent( );
            intent.setType( "image/*" );
            intent.setAction( Intent.ACTION_GET_CONTENT );
            intent.putExtra( Intent.EXTRA_ALLOW_MULTIPLE, false );
            startActivityForResult( Intent.createChooser( intent, getResources().getString( R.string.select_image_to_open ) ), REQUEST_BACKIMG );
            return true;
//        } else if (id == R.id.optmenuCapture){
//            takeScreenshot(true);
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawThread != null) {
            drawThread.shutdown();
            drawThread = null;
        }
        realm.beginTransaction();
        RealmList<DrawPath> drawPaths =  currentNote.getPaths();
        for(int i=0; i<drawPaths.size();i++){
            if(!drawPaths.get(i).isSaved()) {
                RealmList<DrawPoint> drawPoints = drawPaths.get(i).getPoints();
                drawPoints.deleteAllFromRealm();
                drawPaths.get(i).deleteFromRealm();
            }
        }
        if(!currentNote.isSaved()) currentNote.deleteFromRealm();
        realm.commitTransaction();

        Intent intent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK, intent);
        finish();

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (realm == null) {
            return false; // if we are in the middle of a rotation, realm may be null.
        }
        if (isClearing||currentNote==null) return false;

        int[] viewLocation = new int[2];
        surfaceView.getLocationInWindow(viewLocation);
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN
                || action == MotionEvent.ACTION_MOVE
                || action == MotionEvent.ACTION_UP
                || action == MotionEvent.ACTION_CANCEL) {
            float x = event.getRawX();
            float y = event.getRawY();
            double pointX = (x - marginLeft - viewLocation[0]) * ratio;
            double pointY = (y - marginTop - viewLocation[1]) * ratio;

            if (action == MotionEvent.ACTION_DOWN) {
                realm.beginTransaction();
                currentPath = realm.createObject(DrawPath.class);
                currentNote.getPaths().add(currentPath);
                if (!drawMode)
                    currentPath.setColor(Color.WHITE);
                else
                    currentPath.setColor(currentColor);
                currentPath.setBushsize(currentBushSize);
                DrawPoint point = realm.createObject(DrawPoint.class);
                point.setX(pointX);
                point.setY(pointY);
                currentPath.getPoints().add(point);
                realm.commitTransaction();
            } else if (action == MotionEvent.ACTION_MOVE) {
                realm.beginTransaction();
                DrawPoint point = realm.createObject(DrawPoint.class);
                point.setX(pointX);
                point.setY(pointY);
                currentPath.getPoints().add(point);
                realm.commitTransaction();
            } else if (action == MotionEvent.ACTION_UP) {
                realm.beginTransaction();
                currentPath.setCompleted(true);
                DrawPoint point = realm.createObject(DrawPoint.class);
                point.setX(pointX);
                point.setY(pointY);
                currentPath.getPoints().add(point);
                realm.commitTransaction();
                currentPath = null;
            } else {
                realm.beginTransaction();
                currentPath.setCompleted(true);
                realm.commitTransaction();
                currentPath = null;
            }
            return true;

        }
        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (drawThread == null) {
            drawThread = new DrawThread(currentNote.getId());
            drawThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        boolean isPortrait = width < height;
        if (isPortrait) {
            ratio = (double) EDGE_WIDTH / height;
        } else {
            ratio = (double) EDGE_WIDTH / width;
        }
        if (isPortrait) {
            marginLeft = (width - height) / 2.0;
            marginTop = 0;

            padWidth = width;
            padHeight = height; // * ratio;
        } else {
            marginLeft = 0;
            marginTop = (height - width) / 2.0;

            padWidth = width; // * ratio;
            padHeight = height;
        }
//        Log.d(TAG, String.format("radio: %.0f, marginLeft: %.0f, marginTop: %.0f, width: %.0f, height: %.0f"
//                ,ratio, marginLeft, marginTop, padWidth, padHeight));
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (drawThread != null) {
            drawThread.shutdown();
            drawThread = null;
        }
        ratio = -1;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode != RESULT_CANCELED ) {
            switch( requestCode ) {
                case REQUEST_BACKIMG: {
                    setBackgroundUri( data.getData() );
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("BitmapImage", currentBitmap);
    }

    public void setBackgroundUri(Uri uri) {
        if (uri == null) {
            return;
        }

        try {
            Bitmap fullsize = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            Bitmap resized = Bitmap.createScaledBitmap(fullsize, (int)padWidth, (int)padHeight, true);
            currentBitmap = resized;
        } catch (IOException exception) {
            //TODO: How should we handle this exception?
        }
    }

    class DrawThread extends Thread {
        private int mDrawNoteId = 0;
        private Realm bgRealm;

        public DrawThread(int drawNoteId) {
            this.mDrawNoteId = drawNoteId;
        }

        public void shutdown() {
            synchronized (this) {
                if (bgRealm != null) {
                    bgRealm.stopWaitForChange();
                }
            }
            interrupt();
        }

        @Override
        public void run() {
            while (ratio < 0 && !isInterrupted()) {
            }

            if (isInterrupted()) {
                return;
            }

            Canvas canvas = null;

            try {
                final SurfaceHolder holder = surfaceView.getHolder();
                canvas = holder.lockCanvas();
                canvas.drawColor(Color.WHITE);
            } finally {
                if (canvas != null) {
                    surfaceView.getHolder().unlockCanvasAndPost(canvas);
                }
            }

            while (realm == null && !isInterrupted()) {
            }

            if (isInterrupted()) {
                return;
            }

            if (mDrawNoteId == 0) return;

            bgRealm = Realm.getDefaultInstance();
            final DrawNote resultNote = bgRealm.where(DrawNote.class).equalTo("id", mDrawNoteId).findFirst();
            final RealmList<DrawPath> paths = resultNote.getPaths();
            while (!isInterrupted()) {
                try {
                    final SurfaceHolder holder = surfaceView.getHolder();
                    canvas = holder.lockCanvas();

                    synchronized (holder) {
//                        try {
                            canvas.drawColor(Color.WHITE);
                            final Paint paint = new Paint();
                            if(currentBitmap!=null) canvas.drawBitmap(currentBitmap, (float)0, (float)0, paint);
                            for (DrawPath drawPath : paths) {
                                final RealmList<DrawPoint> points = drawPath.getPoints();
                                final Integer color = new Integer(drawPath.getColor());
                                if (color != null) {
                                    paint.setColor(color);
                                } else {
                                    if (!drawMode)
                                        paint.setColor(Color.WHITE);
                                    else
                                        paint.setColor(currentColor);
                                }
                                paint.setStyle(Paint.Style.STROKE);
                                final Integer brushsize = drawPath.getBushsize();
                                if (brushsize > 0) {
                                    paint.setStrokeWidth((float) (brushsize.floatValue() / ratio));
                                } else {
                                    paint.setStrokeWidth((float) (currentBushSize.floatValue() / ratio));
                                }
                                final Iterator<DrawPoint> iterator = points.iterator();
                                final DrawPoint firstPoint = iterator.next();
                                final Path path = new Path();
                                final float firstX = (float) ((firstPoint.getX() / ratio) + marginLeft);
                                final float firstY = (float) ((firstPoint.getY() / ratio) + marginTop);
                                path.moveTo(firstX, firstY);
                                while (iterator.hasNext()) {
                                    DrawPoint point = iterator.next();
                                        final float x = (float) ((point.getX() / ratio) + marginLeft);
                                    final float y = (float) ((point.getY() / ratio) + marginTop);
                                    path.lineTo(x, y);
                                }
                                canvas.drawPath(path, paint);
                            }
//                        } catch (Exception exp){
//                            exp.printStackTrace();
//                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceView.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
                bgRealm.waitForChange();
            }

            synchronized (this) {
                bgRealm.close();
            }
        }
    }

    public void callColorPicker() {
        final ColorPicker colorPicker = new ColorPicker(this);
        if (colorList.size() == 0) {
            colorPicker.setColors();
            ArrayList<ColorPal> colors = colorPicker.getColors();
            for (ColorPal colorPal : colors) {
                colorList.add(new Integer(colorPal.getColor()));
            }
        }
        colorPicker.setDefaultColorButton(currentColor);
//        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
        colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
            @Override
            public void setOnFastChooseColorListener(int position, int color) {
                currentColor = color;
            }

            @Override
            public void onCancel() {
                Log.i(TAG,"close");
            }
        });
        colorPicker.setRoundColorButton(true);
        colorPicker.show();
    }

    public void clearNote() {
        if (realm != null) {
            if (drawThread != null) {
                drawThread.shutdown();
                drawThread = null;
            }
            isClearing = true;
            realm.beginTransaction();
            DrawNote drawNote = realm.where(DrawNote.class).equalTo("id",currentNoteId).findFirst();
            RealmList<DrawPath> drawPaths =  drawNote.getPaths();
            for(int i=0; i<drawPaths.size();i++){
                RealmList<DrawPoint> drawPoints = drawPaths.get(i).getPoints();
                if(drawPoints.size()>0) drawPoints.deleteAllFromRealm();
            }
            if(drawPaths.size()>0)  drawNote.getPaths().deleteAllFromRealm();
            realm.commitTransaction();
            isClearing = false;
            if(drawThread == null) {
                drawThread = new DrawThread(currentNote.getId());
                drawThread.start();
            }
        }
    }

    public void callBrushSizeDlg() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dlg_brushsize, (ViewGroup) findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(layout);
        builder.setTitle("Brush Size");
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        SeekBar sb = (SeekBar) layout.findViewById(R.id.brushSizeSeekBar);
        sb.setProgress(currentBushSize);
        final TextView txt = (TextView) layout.findViewById(R.id.sizeValueTextView);
        txt.setText(String.format("Selected size: %1$s", currentBushSize + 1));
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentBushSize = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void saveNote() {
        if (currentNote == null) return;

        if(currentNote.isSaved()){
            realm.beginTransaction();
            RealmList<DrawPath> paths = currentNote.getPaths();
            for (DrawPath drawPath : paths) {
                if(!drawPath.isSaved())
                    drawPath.setSaved(true);
            }
            realm.commitTransaction();
            Toast.makeText(DrawPadActivity.this, "It's saved!", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Save");
            View inflater = LayoutInflater.from(this).inflate(R.layout.dlg_save, (ViewGroup) findViewById(R.id.save_dlg_frame), false);
            final EditText note_title = (EditText) inflater.findViewById(R.id.save_note_title);
            builder.setView(inflater);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    realm.beginTransaction();
                    if (note_title.getText().toString().equals("")) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        currentNote.setTitle("Note_" + sdf.format(new Date()));
                    } else {
                        currentNote.setTitle(note_title.getText().toString());
                    }
                    currentNote.setSaved(true);
                    RealmList<DrawPath> paths = currentNote.getPaths();
                    for (DrawPath drawPath : paths) {
                        drawPath.setSaved(true);
                    }
                    realm.commitTransaction();
                    Toast.makeText(DrawPadActivity.this, "It's saved!", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }

    private File takeScreenshot(boolean showToast) {
        View v = findViewById(R.id.surface_view);
        v.setDrawingCacheEnabled(true);
        Bitmap cachedBitmap = v.getDrawingCache();
        Bitmap copyBitmap = cachedBitmap.copy(Bitmap.Config.RGB_565, true);
        v.destroyDrawingCache();
        FileOutputStream output = null;
        File file = null;
        try {
            File path = Places.getScreenshotFolder();
            Calendar cal = Calendar.getInstance();

            file = new File(path,

                    cal.get(Calendar.YEAR) + "_" + (1 + cal.get(Calendar.MONTH)) + "_"
                            + cal.get(Calendar.DAY_OF_MONTH) + "_"
                            + cal.get(Calendar.HOUR_OF_DAY) + "_"
                            + cal.get(Calendar.MINUTE) + "_" + cal.get(Calendar.SECOND)
                            + ".png");
            output = new FileOutputStream(file);
            copyBitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        } catch (FileNotFoundException e) {
            file = null;
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        if (file != null) {
            if (showToast)
                Toast.makeText(
                        getApplicationContext(),
                        String.format(getResources().getString(R.string.saved_your_location_to), file.getAbsolutePath()), Toast.LENGTH_LONG)
                        .show();
            Intent requestScan = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            requestScan.setData(Uri.fromFile(file));
            sendBroadcast(requestScan);
            return file;
        } else {
            return null;
        }
    }
}
