package com.example.zain.foodbank;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends BaseActivity {

    public static final int TAKE_PICTURE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = (Spinner) findViewById(R.id.item_name_list);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.item_name_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        findViewById(R.id.add_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddInventoryButtonClicked();
            }
        });

        findViewById(R.id.remove_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveInventoryButtonClicked();
            }
        });

        findViewById(R.id.camera_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenCVButtonClicked();
            }
        });
    }

    private void onRemoveInventoryButtonClicked() {
        onModifyInventory(Integer.parseInt(((EditText) findViewById(R.id.item_quantity)).toString()) * -1);
    }

    public void onAddInventoryButtonClicked() {
        onModifyInventory(Integer.parseInt(((EditText) findViewById(R.id.item_quantity)).toString()));
    }

    private void onModifyInventory(int quantity) {
        String itemName = ((EditText) findViewById(R.id.new_item)).toString();
    }

    private void onOpenCVButtonClicked() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, "my_item_img_opencv.jpg");
        imageUri = Uri.fromFile(image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}