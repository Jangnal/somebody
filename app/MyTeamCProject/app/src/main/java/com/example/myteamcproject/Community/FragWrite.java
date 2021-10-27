package com.example.myteamcproject.Community;


import static com.example.myteamcproject.Common.CommonMethod.loginDTO;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.ATask.CommunityATask;
import com.example.myteamcproject.Common.CommonMethod;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FragWrite extends Fragment {

    private static final String TAG = "FragWrite";

    private final int GET_GALLERY_IMAGE = 200;
    public int reqPicCode = 1004;

    private View view;
    private Button submit, reset;
    private EditText co_title, co_content;
    private ImageView imgfile;
    public String imageRealPathA, imageDbPathA;
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.frag_write, container, false);
        submit = view.findViewById(R.id.submit);
        reset = view.findViewById(R.id.reset);
        co_title = view.findViewById(R.id.cod_title);
        co_content = view.findViewById(R.id.cod_content);
        imgfile = view.findViewById(R.id.imgfile);

        activity = new MainActivity();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragChat fragChat = new FragChat();

                String co_title_c = co_title.getText().toString();
                String co_content_c = co_content.getText().toString();

                Log.d(TAG, "onClick: " + co_title_c + ", " + co_content_c);

                if (co_title_c.trim().equals("")) {
                    Toast.makeText(view.getContext(), "제목이 입력되지 않았습니다.", Toast.LENGTH_LONG).show();
                } else if (co_content_c.trim().equals("")) {
                    Toast.makeText(view.getContext(), "내용이 입력되지 않았습니다.", Toast.LENGTH_LONG).show();
                } else {
                    CommunityATask communityATask = new CommunityATask("insert", co_title_c, co_content_c, imageDbPathA, loginDTO.getId(), imageRealPathA);

                    try {
                        communityATask.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }//try & catch

                    transaction.replace(R.id.main_frag, fragChat);
                    transaction.commit();
                }//if
            }//onClick
        });//submit.setOnClickListener

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragChat fragChat = new FragChat();
                transaction.replace(R.id.main_frag, fragChat);
                transaction.commit();
            }
        });

        imgfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GET_GALLERY_IMAGE);
            }
        });

        Log.d("FragWrite", "onCreateView: ");
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == activity.RESULT_OK && data != null && data.getData() != null) {

            try {
                String path = "";
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                }
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
                if(newBitmap != null){
                    imgfile.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(getContext(), "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPathA = path;
                Log.d("Sub1Add", "imageFilePathA Path : " + imageRealPathA);
                String uploadFileName = imageRealPathA.split("/")[imageRealPathA.split("/").length - 1];
                imageDbPathA = ipConfig + "/resources/" + uploadFileName;

            } catch (Exception e){
                Log.d(TAG, "onActivityResult: " + e.getMessage() + " : " + e.getStackTrace());
            }

        }
    }//onActivityResult

    // Get the real path from the URI
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }//getPathFromURI


}//class