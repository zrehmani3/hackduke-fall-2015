package com.example.zain.foodbank;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public static final int TAKE_PICTURE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri imageUri;

    List<Item> items = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetItemsListTask getCurrentItems = new GetItemsListTask();
        getCurrentItems.execute();
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
        String qty = ((EditText) findViewById(R.id.item_quantity)).getText().toString();
        String name = ((EditText) findViewById(R.id.new_item)).getText().toString();
        String currSpinnerValue = ((Spinner) findViewById(R.id.item_name_list)).getSelectedItem().toString();
        if (qty.length() != 0 && (name.length() != 0 || currSpinnerValue.length() != 0)) {
            onModifyInventory(Integer.parseInt(((EditText) findViewById(R.id.item_quantity)).getText().toString()) * -1);
        }
    }

    public void onAddInventoryButtonClicked() {
        String qty = ((EditText) findViewById(R.id.item_quantity)).getText().toString();
        String name = ((EditText) findViewById(R.id.new_item)).getText().toString();
        String currSpinnerValue = ((Spinner) findViewById(R.id.item_name_list)).getSelectedItem().toString();
        if (qty.length() != 0 && (name.length() != 0 || currSpinnerValue.length() != 0)) {
            onModifyInventory(Integer.parseInt(((EditText) findViewById(R.id.item_quantity)).getText().toString()));
        }
    }

    private void onModifyInventory(int quantity) {
        String itemName = ((EditText) findViewById(R.id.new_item)).getText().toString();
        boolean duplicate = false;
        for (Item i : items) {
            if (i.getItemName().equals(itemName)) {
                duplicate = true;
            }
        }
        if (!duplicate) {
            if (itemName.trim().length() == 0) {
                int currSpinnerValue = items.get(((Spinner) findViewById(R.id.item_name_list)).getSelectedItemPosition()).getItemId();
                ModifyItemsTask modifyItems = new ModifyItemsTask();
                String postData = "item=" + currSpinnerValue + "&qty=" + quantity;
                modifyItems.execute(postData);
            } else {
                AddItemsTask addItems = new AddItemsTask();
                String postData = "name=" + itemName + "&qty=" + quantity;
                addItems.execute(postData);
            }
            EditText itemNameEditText = (EditText) findViewById(R.id.new_item);
            itemNameEditText.setText("");
            EditText itemQuantityEditText = (EditText) findViewById(R.id.item_quantity);
            itemQuantityEditText.setText("");
        } else {
            EditText itemNameEditText = (EditText) findViewById(R.id.new_item);
            itemNameEditText.setText("");
        }
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

    private class GetItemsListTask extends AsyncTask<String, Void, String> {

        Network network = new Network();

        @Override
        protected String doInBackground(String... params) {
            try {
                return network.get("http://ec2-52-23-206-188.compute-1.amazonaws.com/items");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray arr = new JSONArray(result);
                items.clear();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject curr = arr.getJSONObject(i);
                    items.add(new Item(curr.getString("name"), curr.getInt("id")));
                }
                List<CharSequence> itemNames = new ArrayList<>();
                for (Item i : items) {
                    itemNames.add(i.getItemName());
                }
                Spinner spinner = (Spinner) findViewById(R.id.item_name_list);
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(MainActivity.this,
                        android.R.layout.simple_spinner_item, itemNames);

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    private class Item {
        private String itemName;
        private int itemId;

        public Item(String name, int id) {
            itemId = id;
            itemName = name;
        }

        public String getItemName() {
            return itemName;
        }

        public int getItemId() {
            return itemId;
        }
    }

    private class ModifyItemsTask extends AsyncTask<String, Void, String> {

        Network network = new Network();

        @Override
        protected String doInBackground(String... params) {
            try {
                return network.post("http://ec2-52-23-206-188.compute-1.amazonaws.com/qty_item", params[0]);
            } catch (Exception e) {

            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    private class AddItemsTask extends AsyncTask<String, Void, String> {

        Network network = new Network();

        @Override
        protected String doInBackground(String... params) {
            try {
                return network.post("http://ec2-52-23-206-188.compute-1.amazonaws.com/additemrest", params[0]);
            } catch (Exception e) {

            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            GetItemsListTask getNewItems = new GetItemsListTask();
            getNewItems.execute();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}