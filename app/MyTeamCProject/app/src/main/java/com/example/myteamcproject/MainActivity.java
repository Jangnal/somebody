package com.example.myteamcproject;

import static com.example.myteamcproject.Common.CommonMethod.loginDTO;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.Common.FragHome;
import com.example.myteamcproject.Community.FragComm;
import com.example.myteamcproject.Gift.FragGift;
import com.example.myteamcproject.Member.JoinActivity;
import com.example.myteamcproject.Member.LoginActivity;
import com.example.myteamcproject.Member.MemberDTO;
import com.example.myteamcproject.Mypage.FragCartList;
import com.example.myteamcproject.Mypage.FragMfood;
import com.example.myteamcproject.Mypage.FragMyPage;
import com.example.myteamcproject.ServiceCenter.FragService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button btnLogin, btnJoin, btnLogout, btnMypage, btnCart, btnScenter, btnmfood;

    //바텀 네비게이션 뷰
    private BottomNavigationView bottomNavigationView;

    DrawerLayout drawer;
    View drawerView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    //프래그먼트 변수
    private FragHome frag_home;
    private FragComm frag_chat;
    private FragGift frag_gift;
    private FragMyPage fragMyPage;

    private ImageView imgProfile;

    public Bundle mBundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        //프래그먼트 객체체생성
        frag_home = new FragHome();
        frag_chat = new FragComm();
        frag_gift = new FragGift();
        fragMyPage = new FragMyPage();
        setFrag(frag_home, null); // 첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택.

        imgProfile = findViewById(R.id.imgProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginDTO != null){
                    setFrag(fragMyPage, null);
                }else{
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }//onClick
        });//imgProfile.setOnClickListener

        //사이드바 열어줌
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer2);

        btnJoin = drawerView.findViewById(R.id.btnJoin);
        btnLogin = drawerView.findViewById(R.id.btnLogin);
        btnLogout = drawerView.findViewById(R.id.btnLogout);
        btnScenter = drawerView.findViewById(R.id.btnScenter);
        btnMypage = drawerView.findViewById(R.id.btnMypage);
        btnCart = drawerView.findViewById(R.id.btnCart);
        btnmfood = drawerView.findViewById(R.id.btnmfood);

        //drawerLayout.setDrawerListener(listener);
        drawer.addDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                int itemid = menuItem.getItemId();
                if(itemid == R.id.item_cart){
                   if(loginDTO == null){
                       Toast.makeText(getApplicationContext(), "로그인이 필요한 서비스입니다.", Toast.LENGTH_LONG).show();
                       return false;
                   }//if login dto
                }//if

                switch (itemid)
                {
                    case R.id.item_home:
                        setFrag(frag_home, null);
                        break;
                    case R.id.item_chat:
                        setFrag(frag_chat, null);
                        break;
                    case R.id.item_cart:
                        setFrag(frag_gift, null);
                        break;
                    case R.id.item_account:
                        drawer.openDrawer(drawerView);
                        break;
                }
                return true;
            }
        });

    }//onCreate

    // 프레그먼트 교체가 일어나는 실행문.
    public void setFrag(Fragment fragment, Bundle bundle)
    {
        this.mBundle = bundle;

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.main_frag, fragment);
        fragmentTransaction.commit();
    }//setFrag

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {

        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            //슬라이드 했을때
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
            //Drawer가 오픈된 상황일때 호출

            //마이페이지
            btnMypage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction= fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.main_frag,fragMyPage);
                    fragmentTransaction.commit();
                    drawer.closeDrawer(drawerView);
                }
            });


            // 회원가입
            btnJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                    startActivity(intent);
                }
            });

            // 로그인
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });

            //로그아웃
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginDTO = null;
                    finish();
                    startActivity(getIntent());
                }
            });

            //고객센터
            btnScenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragService fragService = new FragService();
                    setFrag(fragService, null);
                }
            });

            btnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    FragCartList fragCartList = new FragCartList();
                    fragmentTransaction.replace(R.id.main_frag, fragCartList);
                    fragmentTransaction.commit();
                    drawer.closeDrawer(drawerView);
                }
            });

            btnmfood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragMfood frag_Mfood = new FragMfood();
                    setFrag(frag_Mfood, null);
                }
            });


        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
            // 닫힌 상황일 때 호출
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            // 특정상태가 변결될 때 호출
        }
    };

    // 뒤로가기를 눌렀을 때 만약 드로어 창이 열려 있으면 드로우 창을 닫고
    // 아니면 그냥 뒤로가기 원래 작업을 한다. => 앱종료
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(drawerView)){
            drawer.closeDrawer(drawerView);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(loginDTO != null){
            if(loginDTO.getMember_c_file_path() != null && loginDTO.getMember_c_file_name() != null) {
                String filepath = ipConfig + "/resources/" + loginDTO.getMember_c_file_path();
                Log.d(TAG, "setDto: " + filepath);
                Glide.with(this).load(filepath).circleCrop().into(imgProfile);
            }//if file null

            btnLogout.setVisibility(View.VISIBLE);
            btnCart.setVisibility(View.VISIBLE);
            btnMypage.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
            btnJoin.setVisibility(View.GONE);
            btnmfood.setVisibility(View.VISIBLE);
        }//if dto null
    }//onResume

    //위험권한
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
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
        }//if permissionCheck
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