package com.example.myteamcproject.Member;

import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myteamcproject.ATask.KakaoIdChk;
import com.example.myteamcproject.ATask.KakaoJoin;
import com.example.myteamcproject.ATask.KakaoLogin;
import com.example.myteamcproject.ATask.LoginSelect;
import com.example.myteamcproject.ATask.NaverIdChk;
import com.example.myteamcproject.ATask.NaverJoin;
import com.example.myteamcproject.ATask.NaverLogin;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "getKeyHash";

    Context context;
    Button btnJoin, btnLogin;
    com.kakao.usermgmt.LoginButton kakaoLogin;
    EditText etId, etpassword;
    OAuthLoginButton naverlogin;

    /*????????? ????????? ??????*/
    private ISessionCallback mSessionCallback;
    /*????????? ????????? : https://8iggy.tistory.com/61  https://bourbonkk.tistory.com/35*/
    private static String OAUTH_CLIENT_ID = "AGezgYXE5c7EflDIHCox";
    private static String OAUTH_CLIENT_SECRET = "G818jHGfVw";
    private static String OAUTH_CLIENT_NAME = "???????????????";

    @SuppressLint("StaticFieldLeak")
    private static OAuthLogin mOAuthLoginInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static Map<String, String> mUserInfoMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        /*????????? ?????????*/
        getHashKey();

        /*context ??????*/
        mContext = LoginActivity.this;

        /*????????? ????????? ?????????*/
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
        /*????????? ????????? ?????? ?????????*/
        naverlogin = findViewById(R.id.naverLogin);
        naverlogin.setOAuthLoginHandler(mOAuthLoginHanlder);

        checkDangerousPermissions();

        btnJoin = findViewById(R.id.btnJoin);
        btnLogin = findViewById(R.id.btnLogin);

        etId = findViewById(R.id.etId);
        etpassword = findViewById(R.id.etpassword);

        kakaoLogin = findViewById(R.id.kakaoLogin);

        /*?????????*/
        mSessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                UserManagement.getInstance().me(new MeV2ResponseCallback() {

                    @SuppressLint("WrongViewCast")
                    com.kakao.usermgmt.LoginButton loginButton = findViewById(R.id.kakaoLogin);
                    String state = null;

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        super.onFailure(errorResult);
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {

                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        //????????? ??????
                        UserAccount kakaoAcount = result.getKakaoAccount();
                        Profile profile = kakaoAcount.getProfile();
                        if(kakaoAcount != null && profile != null){
                            long kakao_id = result.getId();
                            String kakao_name = profile.getNickname();
                            String kakao_email = kakaoAcount.getEmail();
                            //????????? ???????????? DB??? ????????? ?????? ????????? ??????????????????.
                            KakaoIdChk kakaoIdChk = new KakaoIdChk(kakao_id);
                            try {
                                state = kakaoIdChk.execute().get();
                            }catch (Exception e){
                                e.printStackTrace();
                                e.getMessage();
                            }

                            if(state.trim().equals("false")){    //????????????????????? ?????????
                                KakaoLogin kakaoLogin = new KakaoLogin(kakao_id, kakao_name, kakao_email);
                                try {
                                    loginDTO = kakaoLogin.execute().get();
                                }catch (Exception e){
                                    e.printStackTrace();
                                    e.getMessage();
                                }//try

                                if(loginDTO != null){
                                    Toast.makeText(LoginActivity.this, "????????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    Log.d(TAG, "onSuccess: ????????? ????????? ??????");
                                }
                            }else {
                                KakaoJoin kakaoJoin = new KakaoJoin(kakao_id, kakao_name);
                                try {
                                    state = kakaoJoin.execute().get();
                                }catch (Exception e){
                                    e.printStackTrace();
                                    e.getMessage();
                                }//try

                                if (state.trim().equals("false")) { //??????????????? ????????? ?????? false
                                    Toast.makeText(LoginActivity.this, "?????? ??????", Toast.LENGTH_SHORT).show();
                                } else if(state.trim().equals("true")) {
                                    Toast.makeText(LoginActivity.this, "????????? ?????????????????????. ?????? ?????????????????????", Toast.LENGTH_SHORT).show();
                                    KakaoLogin kakaoLogin = new KakaoLogin(kakao_id, kakao_name, kakao_email);
                                    try {
                                        loginDTO = kakaoLogin.execute().get();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                        e.getMessage();
                                    }//try
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }

                    }//onSuccess
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {

            }
        };

        // ????????????
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });

        // ?????????
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etId.getText().toString().length() != 0 && etpassword.getText().toString().length() != 0){
                    String id = etId.getText().toString();
                    String password = etpassword.getText().toString();

                    LoginSelect loginSelect = new LoginSelect(id, password);
                    try {
                        loginSelect.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "???????????? ????????? ?????? ???????????????", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "???????????? ????????? ?????? ??????????????? !!!");
                    return;
                }

                // ????????? ????????? ?????? ????????? ???????????? ??????????????? ?????????????????? ??????
                if(loginDTO != null){
                    if(loginDTO.getId() != null){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "???????????? ??????????????? ???????????? !!!", Toast.LENGTH_SHORT).show();
                        Log.d("main:login", "???????????? ??????????????? ???????????? !!!");
                        etId.setText(""); etpassword.setText("");
                        etId.requestFocus();
                }
                }
            }//onclick
        });//btnLogin

        /*????????? ????????? ??????*/
        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        Log.d(TAG, "onCreate: ????????? " + OAuthLoginState.NEED_LOGIN.equals(mOAuthLoginInstance.getState(getApplicationContext())));
        Log.d(TAG, "onCreate: ????????? " + OAuthLoginState.NEED_INIT.equals(mOAuthLoginInstance.getState(getApplicationContext())));
        /*????????? ?????? ????????? : ??????????????? ????????? : ????????? ??? ??????????????? */
        HasNaverSession();
        if (!HasNaverSession()) {
            // ???????????? ?????? ?????? ??? ????????? ????????????.
        } else if (HasNaverSession()) {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }//onCreate

    /*????????? ????????? ?????????*/
    @SuppressLint("HandlerLeak")
    private OAuthLoginHandler mOAuthLoginHanlder = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            /*????????? ?????? ????????? */
            if(success){
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);

                RequestApiTask rat = new RequestApiTask(mContext, mOAuthLoginInstance);
                rat.execute();
            }else{  /*????????? ?????? ?????????*/
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode : " + errorCode + ", errorDesc : " + errorDesc, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "run: ??????" + errorCode);
            }
        }
    };

    /*????????? ?????? ?????????*/
    private boolean HasNaverSession() {
        mContext = LoginActivity.this;
        if (mOAuthLoginInstance == null) {
            mOAuthLoginInstance = OAuthLogin.getInstance();
        } else {
            if (OAuthLoginState.NEED_LOGIN.equals(mOAuthLoginInstance.getState(getApplicationContext())) == true &&
                    OAuthLoginState.NEED_INIT.equals(mOAuthLoginInstance.getState(getApplicationContext()))== true) {
                return true;
            }
        }
        return false;
    }

    /*????????? ????????? :  RequestApiTask -> ???????????????*/
    public class RequestApiTask extends AsyncTask<Void, Void, String> {
        private final Context mContext;
        private final OAuthLogin mOAuthLoginModule;
        public RequestApiTask(Context mContext, OAuthLogin mOAuthLoginModule) {
            this.mContext = mContext;
            this.mOAuthLoginModule = mOAuthLoginModule;
        }

        String id;
        String naver_id;
        String naver_email;
        String naver_name ;
        String naver_phone;
        String naver = "naver";

        @Override
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(Void... voids) {
            String apiurl = "https://openapi.naver.com/v1/nid/me";
            String at = mOAuthLoginInstance.getAccessToken(mContext);
            return mOAuthLoginInstance.requestApi(mContext,at, apiurl);
        }

        protected void onPostExecute(String content) {
            try {
                JSONObject loginResult = new JSONObject(content);
                String state = null;
                /*???????????? ???????????? ???*/
                if(loginResult.getString("resultcode").equals("00")){
                    JSONObject response = loginResult.getJSONObject("response");
                    naver_id = response.getString("id");
                    naver_email = response.getString("email");
                    naver_name = response.getString("name");
                    naver_phone = response.getString("mobile");
                    /*DB??? ????????????????????? ???????????? ?????? ??????.*/
                    NaverIdChk naverIdChk = new NaverIdChk(naver_id);
                    try {
                        state = naverIdChk.execute().get(); //??????
                    }catch (Exception e){
                        e.getMessage();
                        e.printStackTrace();
                    }

                    if(state.trim().equals("false")){
                        /*?????? ????????? ???????????? ?????? ????????? ????????? ???????????? DB??? ????????? ????????????*/
                        /*????????? ?????? ??? ?????? ?????????*/
                        NaverLogin naverLogin = new NaverLogin(naver_id, naver_email, naver_name, naver_phone);
                        try {
                            loginDTO = naverLogin.execute().get();
                        }catch (Exception e){
                            e.getMessage();
                            e.printStackTrace();
                        }
                        HasNaverSession();
                        final Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        /*????????? ??????*/
                        Log.d(TAG, "onPostExecute: " + id);
                        state = null;
                        NaverJoin naverJoin = new NaverJoin (naver_id, naver_email, naver_name, naver_phone, naver);
                        try {
                            state = naverJoin.execute().get();
                        }catch (Exception e){
                            e.printStackTrace();
                            e.getMessage();
                        }//try
                        Log.d(TAG, "onPostExecute: ????????? : " + state);
                        if(state.trim().equals("true")){    //?????????
                            NaverLogin naverLogin = new NaverLogin(naver_id, naver_email, naver_name, naver_phone);
                            try {
                                loginDTO = naverLogin.execute().get();
                            }catch (Exception e){
                                e.getMessage();
                                e.printStackTrace();
                            }
                            HasNaverSession();
                            Toast.makeText(LoginActivity.this, "??????????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else { //?????? ??????
                            Toast.makeText(LoginActivity.this, "???????????? ?????????????????????. ?????? ??? ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                }else{  //???????????? ???????????? ???
                    Toast.makeText(LoginActivity.this, "???????????? ?????????????????????. ?????? ??? ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    return;
                }

            } catch (JSONException e) {
                e.getMessage();
                e.printStackTrace();
            }
        }

    }   //RequestApiTask

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }//getHashKey

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(mSessionCallback);
    }

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
            Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "?????? ?????? ?????????.", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(this, permissions[i] + " ????????? ?????????.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " ????????? ???????????? ??????.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }//onRequestPermissionsResult


}
