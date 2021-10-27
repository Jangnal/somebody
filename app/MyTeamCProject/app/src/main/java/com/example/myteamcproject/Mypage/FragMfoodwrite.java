package com.example.myteamcproject.Mypage;

import static android.app.Activity.RESULT_OK;
import static com.example.myteamcproject.Common.CommonMethod.foodDTO;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myteamcproject.ATask.FoodATask;
import com.example.myteamcproject.Common.CommonMethod;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class FragMfoodwrite extends Fragment {

    private View view;
    private ImageView m_food_pic, l_food_pic, d_food_pic;
    private Button cancelbtn;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragMfood frag_mfood;

    final int DIALOG_DATE = 1;
    SimpleDateFormat sdf = new SimpleDateFormat("yy"+ "년" +"MM" + "월" + "dd" + "일");
    TextView date_text;
    long mNow;
    Date mDate;
    Button insert_btn;
    EditText m_food_menu, l_food_menu, d_food_menu, food_content;
    File imgFile = null;
    String imgFilePath1, imgFilePath2, imgFilePath3;
    String imgrealpath1, imgrealpath2, imgrealpath3;

    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_mfoodwrite, container, false);

        context = getContext();

        cancelbtn= view.findViewById(R.id.cancelbtn);

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragMfood fragMfood = new FragMfood();
                transaction.replace(R.id.main_frag, fragMfood);
                transaction.commit();
            }
        });

        date_text = view.findViewById(R.id.date_text);
        date_text.setText(getTime());



        m_food_pic = (ImageView)view.findViewById(R.id.m_food_pic);
        m_food_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentm = new Intent(Intent.ACTION_PICK);
                intentm.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intentm, 200);
            }
        });

        l_food_pic = (ImageView)view.findViewById(R.id.l_food_pic);
        l_food_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentl = new Intent(Intent.ACTION_PICK);
                intentl.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intentl, 300);
            }
        });

        d_food_pic = (ImageView)view.findViewById(R.id.d_food_pic);
        d_food_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentd = new Intent(Intent.ACTION_PICK);
                intentd.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intentd, 400);
            }
        });

        insert_btn = view.findViewById(R.id.insert_btn);
        m_food_menu = view.findViewById(R.id.m_food_menu);
        l_food_menu = view.findViewById(R.id.l_food_menu);
        d_food_menu = view.findViewById(R.id.d_food_menu);
        food_content = view.findViewById(R.id.food_content);



        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mornig = m_food_menu.getText().toString();
                String lunch = l_food_menu.getText().toString();
                String dinner = d_food_menu.getText().toString();
                String content = food_content.getText().toString();

                FoodDTO dto = new FoodDTO();
                dto.setMorning(mornig);
                dto.setLunch(lunch);
                dto.setDinner(dinner);
                dto.setContent(content);
                dto.setM_filepath(imgFilePath1);
                dto.setL_filepath(imgFilePath2);
                dto.setD_filepath(imgFilePath3);
                dto.setImgrealpath1(imgrealpath1);
                dto.setImgrealpath2(imgrealpath2);
                dto.setImgrealpath3(imgrealpath3);

                // 이 정보를 비동기 Task 로 넘겨 서버에게 전달한다.
                FoodATask FoodATask = new FoodATask("foodMinsert", loginDTO.getId(), dto);
                try {
                    FoodATask.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "작성이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                frag_mfood = new FragMfood();
                fragmentManager = getParentFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                MainActivity activity = (MainActivity) getActivity();

                fragmentTransaction.replace(R.id.main_frag,frag_mfood);
                fragmentTransaction.commit();
            }
        });

        return view;
    }//oncreatview

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if(requestCode == 200
                && resultCode == RESULT_OK && intent != null
                && intent.getData() != null){
            imgrealpath1 = getImgrealpath(intent.getData(), m_food_pic);
            imgFilePath1 = getImgFilePath(imgrealpath1);
        }else if(requestCode == 300
                && resultCode == RESULT_OK && intent != null
                && intent.getData() != null){
            imgrealpath2 = getImgrealpath(intent.getData(), l_food_pic);
            imgFilePath2 = getImgFilePath(imgrealpath2);
        }else if(requestCode == 400
                && resultCode == RESULT_OK && intent != null
                && intent.getData() != null){
            imgrealpath3 = getImgrealpath(intent.getData(), d_food_pic);
            imgFilePath3 = getImgFilePath(imgrealpath3);
        }
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return sdf.format(mDate);
    }

    public String getImgrealpath(Uri data, ImageView pic){
        String path = "";
        // Get the url from data
        Uri selectedImageUri = data;
        if (selectedImageUri != null) {
            // Get the path from the Uri
            path = getPathFromURI(selectedImageUri);
        }
        // 이미지 돌리기 및 리사이즈
        Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
        if(newBitmap != null){
            pic.setImageBitmap(newBitmap);
        }else{
            Toast.makeText(getContext(), "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
        }

        return path;
    }

    public String getImgFilePath(String imgrealpath){

        Log.d("Sub1Add", "imageFilePathA Path : " + imgrealpath);
        String uploadFileName = imgrealpath.split("/")[imgrealpath.split("/").length - 1];
        return ipConfig + "/resources/" + uploadFileName;
    }

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

} // end of class

