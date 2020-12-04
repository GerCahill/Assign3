package com.example.sdaassign32020_Gerard_Cahill;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.os.Environment.DIRECTORY_PICTURES;


/*
 * A simple {@link Fragment} subclass.
 * @author Chris Coughlan 2019
 */
public class OrderTshirt extends Fragment {


    public OrderTshirt() {
        // Required empty public constructor
    }

    //class wide variables
    private String mPhotoPath;
    private Spinner mSpinner;
    private EditText mCustomerName;
    private EditText meditDelivery;
    private ImageView mCameraImage;
    private File image = null;
    private RadioButton delivery;
    private RadioButton collection;
    private TextView mEditCollection;
    private RadioGroup radioGroup;
    private boolean setCollectionMessage;
    private boolean setDeliveryMessage = true;
    private Uri imageUri;

    //static keys
    private static final int REQUEST_TAKE_PHOTO = 2;
    private static final String TAG = "OrderTshirt";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment get the root view.
        final View root = inflater.inflate(R.layout.fragment_order_tshirt, container, false);

        mCustomerName = root.findViewById(R.id.editCustomer);
        meditDelivery = root.findViewById(R.id.editDeliver);
        meditDelivery.setImeOptions(EditorInfo.IME_ACTION_DONE);
        meditDelivery.setRawInputType(InputType.TYPE_CLASS_TEXT);
       // Button mSendButton = root.findViewById(R.id.sendButton);
        radioGroup = root.findViewById(R.id.radioGroup);
        delivery = root.findViewById(R.id.deliveryButton);
        collection = root.findViewById(R.id.collectionButton);
        mEditCollection = root.findViewById(R.id.editCollect);
       // mSpinner = root.findViewById(R.id.spinner);


        /**
         * radiogGroup oncheckedChange() was adapted from the information found here:
         *https://stackoverflow.com/questions/9748070/radio-group-onclick-event-not-firing-how-do-i-tell-which-is-selected
         */
        //set a listener on radio buttons to execute code based on radio selection
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    //collection button selected
                    case R.id.collectionButton:
                        mEditCollection.setVisibility(View.VISIBLE);
                        meditDelivery.setVisibility(View.INVISIBLE);
                        mSpinner.setVisibility(View.VISIBLE);
                        setCollectionMessage = true;
                        setDeliveryMessage = false;
                        break;
                    //delivery button selected
                    case R.id.deliveryButton:
                        meditDelivery.setVisibility(View.VISIBLE);
                        mEditCollection.setVisibility(View.INVISIBLE);
                        mSpinner.setVisibility(View.INVISIBLE);
                        setDeliveryMessage = true;
                        setCollectionMessage = false;
                }
            }
        });

        mCameraImage = root.findViewById(R.id.imageView);;
        Button mSendButton = root.findViewById(R.id.sendButton);

        //set a listener on the the camera image
        mCameraImage.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        //set a listener to start the email intent.
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(v);
            }
        });


        //initialise spinner using the integer array
        mSpinner = root.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(), R.array.ui_time_entries, R.layout.spinner_days);
        mSpinner.setAdapter(adapter);
        mSpinner.setEnabled(true);

        return root;
    }





//Image Begin

    //The dispatchTakePictureIntent starts the camera and creates a file where the image can be saved upon taking the photo
    //update this to save the image so it can be sent via email
    private void dispatchTakePictureIntent() {
        //updated: added save photo functionality and test to make sure camera activity there to handle the intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createMediaFile(REQUEST_TAKE_PHOTO);
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG, "dispatchTakePictureIntent: Could not create Image file: " + ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.sdaassign32019johndoe.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    /***
     *  <p>method to create a time stamped picture file called by the dispatchTakePictureIntent methods </p> <br>
     *  Adapted from https://developer.android.com/training/camera/photobasics
     */
    String currentPhotoPath;
    private File createMediaFile(int requestCode) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        if(requestCode == REQUEST_TAKE_PHOTO){
            String imageFileName = "JPEG_" + timeStamp + "_";
            String exState = Environment.getExternalStorageState();
            if(exState.equals(Environment.MEDIA_MOUNTED)){
                File storageDir = getActivity().getExternalFilesDir(DIRECTORY_PICTURES);
                image = File.createTempFile(
                        imageFileName,      /* prefix */
                        ".jpg",        /* suffix */
                        storageDir           /* directory */
                );
            } else {
                Log.e(TAG, "createImageFile: External Storage is not available");
            }
            currentPhotoPath = image.getAbsolutePath();
            Log.i(TAG, "createImageFile: "+ currentPhotoPath);
        }
        return image;
    }

    /**
     * <p>the galleryAddPic method adds the photo to the gallery and makes it available to the user immediately</p> <br>
     * following code was adapted from: https://developer.android.com/training/camera/photobasics
     *
     */
    public void galleryAddPic()
    {
        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(getActivity(),
                new String[] { image.toString() }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                        imageUri = uri;
                    }
                });
    }

    /**
     * <p>@param path is passed to this method with a string object of the absolute path of the image file created. <br>
     *      * The purpose of this method is to get the dimensions of the imageView and make sure the image taken will fit inside of it
     *      </p><br>
     * <p>this setPic method was adapted from the following:
     *          <span margin-left: 40px;>- MediaIntentActivity project from the SDA-2019 folder downloaded from github</span><br>
     *          <span margin-left: 40px;>- Android documentation: https://developer.android.com/training/camera/photobasics</span><br>
     *          </p>
     */
    //formats image to fit in the imageview
    private void setPic(String path) {

        // Get the dimensions of the View
        int targetW = mCameraImage.getWidth();
        int targetH = mCameraImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        // Get the dimensions of the image
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        // Determine how much to scale down the image
        if(targetW > 0 || targetH > 0)
        {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }else
        {
            Log.e(TAG, "setPic: scaleFactor is 1 continuing ...");
        }

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap mBitMap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        mCameraImage.setImageBitmap(mBitMap);

    }


    /**
     * <p>onActivityResult method returns the image taken by the camera. It also executes the galleryAddPic(),
     * setPic() method, and sets the image in the imageView replacing the default image </p>
     * <p>The code used in this onActivityResult method was adapted from a few sources including: <br>
     *              <span margin-left: 40px;>- MediaIntentActivity project from the SDA-2019 folder downloaded from github</span><br>
     *              <span margin-left: 40px;>- Android Documentation: https://developer.android.com/training/camera/photobasics</span><br>
     * </p>
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_TAKE_PHOTO){
            if (resultCode == RESULT_OK) {
                //add image to gallery
                galleryAddPic();
                //fit the returned image into the imageview
                setPic(currentPhotoPath);
                //crates a bitmap from the path of the returned image
                Bitmap bp = BitmapFactory.decodeFile(currentPhotoPath);
                //sets the bitmap int the imageview
                mCameraImage.setImageBitmap(bp);
            }
        } else if(resultCode == RESULT_CANCELED){
            Toast.makeText(getActivity(), "picture capture fail", Toast.LENGTH_LONG).show();
        }
    }

// Image end


    /**
     * <p>Returns the Email Body, updated this to handle either collection or delivery<br>
     * Adapted from the Assignment three project</p>
     */
    private String createOrderSummary(View v)
    {
        String orderMessage = "";
        String deliveryInstruction = meditDelivery.getText().toString();
        String customerName = getString(R.string.customer_name) + ": " + mCustomerName.getText().toString();

        orderMessage += customerName + "\n" + "\n" + getString(R.string.order_message_1);

        //The following code sets the email message content based on the users choice delivery or collection method
        if(setDeliveryMessage){
            orderMessage += "\n" + getString(R.string.order_message_1) + "\n";
            orderMessage += "\n" + deliveryInstruction + "\n";
        } else if(setCollectionMessage){
            orderMessage += "\n" + getString(R.string.order_message_collect) + mSpinner.getSelectedItem().toString() + " days." + "\n";
        }


        orderMessage += "\n" + getString(R.string.order_message_end) + "\n" + mCustomerName.getText().toString();

        return orderMessage;
    }

    //Send email method starts the email intent and populates email with information collected
    private void sendEmail(View v)
    {
        //The following is a check to see if the name field is blank or not
        String name = mCustomerName.getText().toString();
        String address = meditDelivery.getText().toString();
        if (name.equals(null) || name.equals("")) {
            /**
             * The following toast message code was adapted from this webpage:
             * https://www.javatpoint.com/android-toast-example
             */
            Toast.makeText(getContext(),"Please enter your name", Toast.LENGTH_SHORT).show();

            //checks if the address is filled in when only the delivery option is selected
        } else if(setDeliveryMessage == true && (address.equals(null) || address.equals(""))) {
            Toast.makeText(getContext(),"Please enter your address", Toast.LENGTH_SHORT).show();
        } else {
            /***
             * The following code was adapted from here:
             * https://www.javatpoint.com/how-to-send-email-in-android-using-intent
             */
            //opens email an populates email with data collected in t-shirt order fragment
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"SportsJerseysDirect@ie"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Order Request");
            email.putExtra(Intent.EXTRA_TEXT, createOrderSummary(v));
            email.putExtra(Intent.EXTRA_STREAM, imageUri);

            Log.d(TAG, "sendEmail: should be sending an email with "+ createOrderSummary(v));
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Choose an Email client: "));
        }
    }
}