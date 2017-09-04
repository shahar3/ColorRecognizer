package com.example.shaha.colorrecognizer;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    static final int CAM_REQUEST = 1;
    ImageView capImgView;
    TextView posTxt;
    Map<String, Colour> colorsMap = new HashMap<>();
    ColorPreview colorBox;
    TextView descTxt;
    LinearLayout colorLayout;
    TextView colorHexTxt;
    Button similarPopBtn;
    TextView colorNameTxt;
    SimilarColors simCols;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the variables from the main screen
        posTxt = (TextView) findViewById(R.id.posTxt);
        capImgView = (ImageView) findViewById(R.id.capImgView);
        colorBox = (ColorPreview) findViewById(R.id.colorBox);
        descTxt = (TextView) findViewById(R.id.descTxt);
        colorLayout = (LinearLayout) findViewById(R.id.colorLayout);
        colorHexTxt = (TextView) findViewById(R.id.colorHexTxt);
        similarPopBtn = (Button) findViewById(R.id.similarPopBtn);
        colorNameTxt = (TextView) findViewById(R.id.colorNameTxt);

        //hide the color layout
        colorNameTxt.setVisibility(View.GONE);
        colorLayout.setVisibility(View.GONE);
        //read the color csv from the assets folder
        readColorCsv();
        //initiate similar colors class
        simCols = new SimilarColors(colorsMap);
        //set a touch listener
        capImgView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                setTouchEvent((ImageView) view, event);
                return true;
            }
        });

        //set a click listener to the similar colors button
        similarPopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSimilarPopup();
            }
        });
    }

    /**
     * open a popup window with the similar colors of the selected pixel
     */
    private void openSimilarPopup(){
        Intent intent = new Intent(MainActivity.this,SimilarityPop.class);
        //calculate the colors similar to the selected pixel
        //send the arrayList of the similar colors
        //intent.putExtra()
        startActivity(intent);
    }

    /**
     * Get the co-ordinates of the user touch and extract the image pixel data.
     * Shows all the details of the desired colored extracted from the picture
     * @param imageView
     * @param event
     */
    private void setTouchEvent(ImageView imageView, MotionEvent event) {
        //get the absolute co-ordinates of the touch
        float eventX = event.getX();
        float eventY = event.getY();
        float[] eventXY = new float[]{eventX, eventY};

        //get the relative point in the scaled bitmap
        Matrix invertMatrix = new Matrix();
        imageView.getImageMatrix().invert(invertMatrix);
        invertMatrix.mapPoints(eventXY);

        int x = Integer.valueOf((int) eventXY[0]);
        int y = Integer.valueOf((int) eventXY[1]);

        //get the bitmap and extract the pixel
        Bitmap bitmap = ((BitmapDrawable) capImgView.getDrawable()).getBitmap();
        //assure that the touch is inside the bitmap
        x = adjustPoint(x, bitmap);
        y = adjustPoint(y, bitmap);

        //get the color of the pixel and convert it to hex
        int pixel = bitmap.getPixel(x, y);
        int r = Color.red(pixel);
        int g = Color.green(pixel);
        int b = Color.blue(pixel);
        String posStr = "X: " + x + " Y: " + y+ " R: " + r + " G: " + g + " B: " + b;

        //create the hex value of the color
        String hex = String.format("#%02x%02x%02x", r, g, b);
        posTxt.setText(posStr);
        colorBox.setColor(r,g,b);
        colorHexTxt.setText(hex);
    }

    /**
     * Check if the touched point is inside the image.
     * if not, adjust it to the closest edge
     * @param point
     * @param bitmap
     * @return
     */
    private int adjustPoint(int point, Bitmap bitmap) {
        if (point < 0) {
            point = 0;
        } else if (point > bitmap.getWidth() - 1) {
            point = bitmap.getWidth() - 1;
        }
        return point;
    }

    /**
     * Read the ral color csv and store the data inside a dictionary
     */
    private void readColorCsv() {
        AssetManager am = this.getAssets();
        try {
            Scanner inputStream = new Scanner(am.open("ral_standard.csv"));
            String firstLine = inputStream.nextLine();
            while (inputStream.hasNext()) {
                String data = inputStream.nextLine();
                String[] values = data.split(",");
                String[] rgbValues = values[1].split("-");
                Colour col = new Colour(values[0], values[2], values[4],Integer.valueOf(rgbValues[0]),Integer.valueOf(rgbValues[1]),Integer.valueOf(rgbValues[2]));
                colorsMap.put(values[2], col);
            }
        } catch (Exception e) {

        }
    }

    /**
     * open the camera when the button pressed
     * @param view
     */
    public void openCamera(View view) {
        descTxt.setVisibility(View.GONE);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile();
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(cameraIntent, CAM_REQUEST);
    }

    /**
     * an helper method to get the captured image from the phone's camera
     * @return
     */
    private File getFile() {
        File folder = new File("sdcard/colorRecognizer");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File imgFile = new File(folder, "cam_img.jpg");
        return imgFile;
    }

    /**
     * Handles the image. scale it to fit the screen and reduce it's
     * size in the memory.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        colorLayout.setVisibility(View.VISIBLE);
        String path = "sdcard/colorRecognizer/cam_img.jpg";
        File img = new File(path);
        BitmapFactory.Options bmpOpts = new BitmapFactory.Options();
        Bitmap d = BitmapFactory.decodeFile(img.getAbsolutePath(), bmpOpts);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int nh = (int) (d.getHeight() * (((double) metrics.widthPixels) / d.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(d, metrics.widthPixels, nh, true);
        //rotate the image 90 degrees
        capImgView.setImageBitmap(RotateBitmap(scaled, 90));
    }

    /**
     * Rotate the picture (in 90 degrees in this case)
     * @param source
     * @param angle
     * @return
     */
    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
