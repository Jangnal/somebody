package com.example.myteamcproject.Member;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myteamcproject.ATask.JoinInsert;
import com.example.myteamcproject.Common.CommonMethod;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity {

    private static final String TAG = "JoinActivity";

    private final int GET_GALLERY_IMAGE = 200;
    Button btnJoin, btnCancel;
    EditText et_id, et_password, et_email, et_name, et_phonenumber, et_height, et_weight;
    ImageView imgjoinprofile;
    public String imageRealPathA, imageDbPathA;
    String state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        checkDangerousPermissions();

        btnJoin = findViewById(R.id.btnJoin);
        btnCancel = findViewById(R.id.btnCancel);
        et_id = findViewById(R.id.et_id);
        et_password = findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_phonenumber = findViewById(R.id.et_phonenumber);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        imgjoinprofile = findViewById(R.id.imgjoinprofile);

        // 가입
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입에 필요한 정보를 가져온다
                String id = et_id.getText().toString();
                String password = et_password.getText().toString();
                String email = et_email.getText().toString();
                String name = et_name.getText().toString();
                String phonenumber = et_phonenumber.getText().toString();
                int height = Integer.parseInt(et_height.getText().toString());
                int weight = Integer.parseInt(et_weight.getText().toString());
                float bmi = weight / ((height / 100) * (height / 100));
                MemberDTO dto = new MemberDTO(id, password, email, name, phonenumber, bmi, height, weight);

                // 이 정보를 비동기 Task 로 넘겨 서버에게 전달한다.
                JoinInsert joinInsert = new JoinInsert(dto, imageRealPathA, imageDbPathA);
                try {
                    state = joinInsert.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finish();


            }
        });

        // 취소
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);


            }
        });

        imgjoinprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GET_GALLERY_IMAGE);
            }
        });

    }//oncreate

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

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
                    imgjoinprofile.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(getApplicationContext(), "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
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
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }//getPathFromURI

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}



