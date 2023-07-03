package com.folderselector;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.mikdantech.filepathpoc.databinding.ActivityMainBinding;

@ReactModule(name = FolderSelectorModule.NAME)
public class FolderSelectorModule extends ReactContextBaseJavaModule {
  public static final String NAME = "FolderSelector";
   private ActivityResultLauncher<Intent> launcher;
   private Promise promise;

  public FolderSelectorModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void showFolderSelector(double a, double b, Promise promise) {
    this.promise = promise;
openFileDirectory();
launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            int resultCode = result.getResultCode();
            Intent data = result.getData();
            if (resultCode == RESULT_OK && data != null) {
                showFileDirectory(data);
            }
        });
  }


  private void openFileDirectory() {
        // Create an intent to open the file directory
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        // Optionally, specify a starting directory
        // Uri uri = Uri.parse("/sdcard"); // Example starting directory
        // intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
        // Start the intent and wait for the result
//        startActivityForResult(intent, 123);
        launcher.launch(intent);
    }

    private void showFileDirectory(Intent data){
        // Get the selected folder URI
        Uri treeUri = data.getData();
        // Get the DocumentFile object for the selected folder
        DocumentFile documentFile = DocumentFile.fromTreeUri(this, treeUri);
        // Retrieve the folder path from the DocumentFile
        String folderName = getFolderPath(documentFile);
        String folderPath = Environment.getExternalStorageDirectory().getPath() + "/" + folderName;
        // Set the folder path to the TextView
        assert documentFile != null;
         promise.resolve(folderPath);
    }

    private String getFolderPath(DocumentFile documentFile) {
        String folderPath = "";
        if (documentFile != null && documentFile.isDirectory()) {
            Uri uri = documentFile.getUri();
            String documentId = DocumentsContract.getTreeDocumentId(uri);
            String[] parts = documentId.split(":");
            if (parts.length > 1) {
                String storageId = parts[0];
                String folderId = parts[1];
//                folderPath = "/storage/" + storageId + "/" + folderId; // this path not needed.
                folderPath = folderId;
            }
        }
        return folderPath;
    }
}
