package ua.dashan.caloriescounter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import ua.dashan.caloriescounter.Database.DatabaseHelpher;

public class FoodAddActivity extends AppCompatActivity {
    //http://www.androidhive.info/2013/09/android-working-with-camera-api/
    //https://github.com/manishsri01/CameraGallerySqliteDemo/blob/master/CameraSQLiteDemo/src/com/manish/sqlite/SQLiteDemoActivity.java
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;

    private DatabaseHelpher helpher;

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    private Uri fileUri; // file url to store image/video
    private static int RESULT_LOAD_IMAGE = 1;
    private CircleImageView foodPhoto;
    private Button add_button;
    private EditText food_name;
    private EditText food_calories;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_food);
        setupWindowAnimations();

        foodPhoto=(CircleImageView)findViewById(R.id.food_image);
        add_button=(Button)findViewById(R.id.add_button);
        food_name=(EditText)findViewById(R.id.food_name);
        food_calories=(EditText)findViewById(R.id.food_calories);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=food_name.getText().toString();
                int calories = Integer.parseInt(food_calories.getText().toString());
                foodPhoto.buildDrawingCache();
                Bitmap bitmap=foodPhoto.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                helpher = new DatabaseHelpher(FoodAddActivity.this);
                helpher.insertIntoDB(name,calories,stream.toByteArray());
               UserFoodInformationFragment.adapter.notifyDataSetChanged();
                Toast toast =Toast.makeText(FoodAddActivity.this,"Продукт добавлен",Toast.LENGTH_SHORT);
                toast.show();
                 setResult(800);



                finish();




            }
        });
        //open dialog for choose camera/gallery
        final String[] option = new String[] { "Сделать снимок",
                "Загрузить из галереи" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // builder.setTitle("Выбрать фото");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                Log.e("Selected Item", String.valueOf(which));
                if (which == 0) {
                    //  callCamera();
                    captureImage();
                }
                if (which == 1) {
                    photoFromGallery();
                    // callGallery();
                }

            }
        });
        final AlertDialog dialog = builder.create();






        foodPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            }
            else {
                // failed to capture image
                Toast.makeText(this,
                        "Фото не сделано", Toast.LENGTH_SHORT)
                        .show();
            }}
        else if  (requestCode == RESULT_LOAD_IMAGE  ) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor =getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();


                foodPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            }
            else {
                // failed to capture image
                Toast.makeText(this,
                        "Фото не выбрано", Toast.LENGTH_SHORT)
                        .show();
            }}


    }
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    //метод для отображения фото
    private void previewCapturedImage() {
        try {


            foodPhoto.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            foodPhoto.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type ==MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");

        } else {
            return null;
        }

        return mediaFile;
    }

    private void photoFromGallery(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);

        Slide slide2 = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }


}






