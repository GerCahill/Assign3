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
         * radiogGroup oncheckedChange() was adapted from the information found on the webpage:
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
                dispatchTakePictureIntent(v);
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




//Take a photo note the view is being passed so we can get context because it is a fragment.
    //update this to save the image so it can be sent via email
    private void dispatchTakePictureIntent(View v)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);


        }
    }


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
             * The following code was adapted from the following webpage:
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