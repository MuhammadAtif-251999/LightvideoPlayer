package com.atif.lightvideoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView myRecyclerView;
    myAdaptor obj_adaptor;
    public static int REQUEST_PERMISSION = 1;
    File directory;
    boolean boolean_permission;
    public static ArrayList<File> fileArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRecyclerView = findViewById(R.id.listVideoRecycler);
        //phone and sd card
        directory = new File("/mnt");
        //phone and sd card
        // directory=new File("/storage");
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 5);
        myRecyclerView.setLayoutManager(manager);
        permissionForVideo();
    }

    private void permissionForVideo() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != getPackageManager().PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }
        else{
            boolean_permission=true;
            getFile(directory);
            obj_adaptor=new myAdaptor(getApplicationContext(),fileArrayList);
            myRecyclerView.setAdapter(obj_adaptor);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_PERMISSION){
            if (grantResults.length>0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED){
                boolean_permission=true;
                getFile(directory);
                obj_adaptor=new myAdaptor(getApplicationContext(),fileArrayList);
                myRecyclerView.setAdapter(obj_adaptor);

            }
            else {
                Toast.makeText(this, "Please Allow the permission", Toast.LENGTH_SHORT).show();
            }


        }
    }

   public ArrayList<File> getFile(File directory){
        File listFile[]=directory.listFiles();
        if (listFile!=null && listFile.length>0){
            for (int i =0 ; i<listFile.length;i++){
                if (listFile[i].isDirectory()){
                    getFile(listFile[i]);
                }else{
                    boolean_permission=false;
                    if (listFile[i].getName().endsWith(".mp4")){
                        for (int j=1;j<fileArrayList.size();j++){
                            if (fileArrayList.get(j).getName().equals(listFile[i].getName())){
                                boolean_permission=true;
                            }
                            else{

                            }
                        }
                        if (boolean_permission){
                            boolean_permission=false;
                        }else{
                            fileArrayList.add(listFile[i]);
                        }

                    }
                }

            }
        }
        return fileArrayList;
   }
}