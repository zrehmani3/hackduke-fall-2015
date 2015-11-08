package com.example.zain.foodbank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

import java.util.List;

public class ConfirmFragment extends DialogFragment {

    private String json;
    private MainActivity activity;
    private List<MainActivity.Item> items;
    String changes;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        changes = getChanges();
        builder.setMessage("Are you sure you would like to make these changes? \n\n" + changes)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onConfirmationClick();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goToMainActivity();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
    }

    private void onConfirmationClick() {
        if (changes.length() != 0) {
            try {
                JSONObject obj = new JSONObject(json);
                if (obj.getInt("carrots") != 0) {
                    int id = Integer.MIN_VALUE;
                    for (MainActivity.Item i : items) {
                        if (i.getItemName().equalsIgnoreCase("carrots")) {
                            id = i.getItemId();
                            break;
                        }
                    }
                    if (id == Integer.MIN_VALUE) {
                        MainActivity.AddItemsTask addItemsTask = activity.new AddItemsTask();
                        addItemsTask.execute("name=Carrots" + "&qty=" + obj.getInt("carrots"));
                    } else {
                        MainActivity.ModifyItemsTask modifyItemsTask = activity.new ModifyItemsTask();
                        modifyItemsTask.execute("item=" + id + "&qty=" + obj.getInt("carrots"));
                    }
                }
                if (obj.getInt("peas") != 0) {
                    int id = Integer.MIN_VALUE;
                    for (MainActivity.Item i : items) {
                        if (i.getItemName().equalsIgnoreCase("peas")) {
                            id = i.getItemId();
                            break;
                        }
                    }
                    if (id == Integer.MIN_VALUE) {
                        MainActivity.AddItemsTask addItemsTask = activity.new AddItemsTask();
                        addItemsTask.execute("name=Peas" + "&qty=" + obj.getInt("peas"));
                    } else {
                        MainActivity.ModifyItemsTask modifyItemsTask = activity.new ModifyItemsTask();
                        modifyItemsTask.execute("item=" + id + "&qty=" + obj.getInt("peas"));
                    }
                }
                if (obj.getInt("corn") != 0) {
                    int id = Integer.MIN_VALUE;
                    for (MainActivity.Item i : items) {
                        if (i.getItemName().equalsIgnoreCase("corn")) {
                            id = i.getItemId();
                            break;
                        }
                    }
                    if (id == Integer.MIN_VALUE) {
                        MainActivity.AddItemsTask addItemsTask = activity.new AddItemsTask();
                        addItemsTask.execute("name=Corn" + "&qty=" + obj.getInt("corn"));
                    } else {
                        MainActivity.ModifyItemsTask modifyItemsTask = activity.new ModifyItemsTask();
                        modifyItemsTask.execute("item=" + id + "&qty=" + obj.getInt("corn"));
                    }
                }
                goToMainActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            goToMainActivity();
        }
    }

    public void setJson(String json) {
        this.json = json;
    }
    public void setMainActivity(MainActivity a) {
        this.activity = a;
    }

    public void setItems(List<MainActivity.Item> items) {
        this.items = items;
    }

    public String getChanges() {
        try {
            JSONObject obj = new JSONObject(json);
            System.out.println(json);
            String result = "";
            if (obj.getInt("carrots") != 0) {
                result += "Carrots: " + obj.getInt("carrots") + "\n";
            }
            if (obj.getInt("corn") != 0) {
                result += "Corn: " + obj.getInt("corn") + "\n";
            }
            if (obj.getInt("peas") != 0) {
                result += "Peas: " + obj.getInt("peas") + "\n";
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}