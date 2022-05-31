package com.codeclinic.agent.utils;

import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.codeclinic.agent.utils.CommonMethods.compressImage;
import static com.codeclinic.agent.utils.Constants.IMAGE_TYPE;
import static com.codeclinic.agent.utils.Constants.PROFILE_PHOTO;
import static com.codeclinic.agent.utils.Constants.REQUEST_FROM_GALLERY;
import static com.codeclinic.agent.utils.Constants.REQUEST_TAKE_PHOTO;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.zelory.compressor.Compressor;


public class AccessMediaUtil extends AppCompatActivity {

    Compressor compressedImage;
    String currentPhotoPath;
    Uri photoURI;
    private int aspectRatioX = 16, aspectRatioY = 9;
    private int width = 600, height = 400;
    private boolean isFixAspectRatio = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compressedImage = new Compressor(this);
        selectImage();
        if (getIntent().hasExtra(IMAGE_TYPE)) {
            isFixAspectRatio = true;
            if (getIntent().getStringExtra(IMAGE_TYPE).equals(PROFILE_PHOTO)) {
                aspectRatioX = 1;
                aspectRatioY = 1;
                width = 500;
                height = 500;
            }
        }

    }

    public void selectImage() {
        //dispatchPickFromGalleryIntent();
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo !!");
        builder.setCancelable(false);
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                dispatchTakePictureIntent();
            } else if (options[item].equals("Choose from Gallery")) {
                dispatchPickFromGalleryIntent();
            } else {
                dialog.dismiss();
                onBackPressed();
            }
        });
        builder.show();
    }

    private void dispatchPickFromGalleryIntent() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(photoPickerIntent, REQUEST_FROM_GALLERY);

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent

        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File...
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(this, "com.codeclinic.agent" + ".fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }

    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);

        Intent returnFromGalleryIntent = new Intent();
        returnFromGalleryIntent.putExtra("picturePath", currentPhotoPath);
        setResult(RESULT_OK, returnFromGalleryIntent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
                CropImage.activity(photoURI)
                        .setAspectRatio(aspectRatioX, aspectRatioY)
                        .setMinCropResultSize(width, height)
                        .setFixAspectRatio(isFixAspectRatio)
                        .start(this);
            } else if (requestCode == REQUEST_FROM_GALLERY && resultCode == RESULT_OK) {
                photoURI = data.getData();
                //CropImage.activity(photoURI).start(this);
                CropImage.activity(photoURI)
                        .setAspectRatio(aspectRatioX, aspectRatioY)
                        .setMinCropResultSize(width, height)
                        .setFixAspectRatio(isFixAspectRatio)
                        .start(this);
            } else {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                photoURI = result.getUri();
                if (resultCode == RESULT_OK) {
                    currentPhotoPath = compressImage(photoURI, compressedImage).getPath();
                    galleryAddPic();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            onBackPressed();
        }
    }

}
