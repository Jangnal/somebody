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
import com.example.myteamcproject.ATask.KakaoAtask;
import com.example.myteamcproject.ATask.KakaoLogin;
import com.example.myteamcproject.ATask.MemberATask;
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

    /*카카오 로그인 관련*/
    private ISessionCallback mSessionCallback;
    /*네이버 로그인 : https://8iggy.tistory.com/61  https://bourbonkk.tistory.com/35*/
    private static String OAUTH_CLIENT_ID = "AGezgYXE5c7EflDIHCox";
    private static String OAUTH_CLIENT_SECRET = "G818jHGfVw";
    private static String OAUTH_CLIENT_NAME = "썸바디헬스";

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

        /*카카오 해시키*/
        getHashKey();

        /*context 저장*/
        mContext = LoginActivity.this;

        /*네이버 로그인 초기화*/
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
        /*네이버 로그인 버튼 클릭시*/
        naverlogin = findViewById(R.id.naverLogin);
        naverlogin.setOAuthLoginHandler(mOAuthLoginHanlder);

        checkDangerousPermissions();

        btnJoin = findViewById(R.id.btnJoin);
        btnLogin = findViewById(R.id.btnLogin);

        etId = findViewById(R.id.etId);
        etpassword = findViewById(R.id.etpassword);

        kakaoLogin = findViewById(R.id.kakaoLogin);

        /*카카오*/
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
                        //로그인 성공
                        UserAccount kakaoAcount = result.getKakaoAccount();
                        Profile profile = kakaoAcount.getProfile();
                        if(kakaoAcount != null && profile != null){
                            long kakao_id = result.getId();
                            String kakao_name = profile.getNickname();
                            String kakao_email = kakaoAcount.getEmail();
                            //사용자 아이디가 DB에 저장된 값과 같은지 비교해야한다.
                            KakaoIdChk kakaoIdChk = new KakaoIdChk(kakao_id);
                            try {
                                state = kakaoIdChk.execute().get();
                            }catch (Exception e){
                                e.printStackTrace();
                                e.getMessage();
                            }

                            if(state.trim().equals("false")){    //카카오아이디가 있다면
                                KakaoLogin kakaoLogin = new KakaoLogin(kakao_id, kakao_name, kakao_email);
                                try {
                                    loginDTO = kakaoLogin.execute().get();
                                }catch (Exception e){
                                    e.printStackTrace();
                                    e.getMessage();
                                }//try

                                if(loginDTO != null){
                                    Toast.makeText(LoginActivity.this, "카카오 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    Log.d(TAG, "onSuccess: 카카오 로그인 성공");
                                }
                            }else {
                                KakaoAtask kakaoJoin = new KakaoAtask(kakao_id, kakao_name);
                                try {
                                    state = kakaoJoin.execute().get();
                                }catch (Exception e){
                                    e.printStackTrace();
                                    e.getMessage();
                                }//try

                                if (state.trim().equals("false")) { //스프링에서 받아온 값이 false
                                    Toast.makeText(LoginActivity.this, "가입 실패", Toast.LENGTH_SHORT).show();
                                } else if(state.trim().equals("true")) {
                                    Toast.makeText(LoginActivity.this, "가입이 완료되었습니다. 다시 로그인해주세요", Toast.LENGTH_SHORT).show();
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
                if(etId.getText().toString().length() != 0 && etpassword.getText().toString().length() != 0){
                    String id = etId.getText().toString();
                    String password = etpassword.getText().toString();

                    MemberATask aTask = new MemberATask("login",id, password);
                    try {
                        aTask.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "아이디와 암호를 모두 입력하세요", Toast.LENGTH_SHORT).show();
                    Log.d("main:login", "아이디와 암호를 모두 입력하세요 !!!");
                    return;
                }

                // 로그인 정보에 값이 있으면 로그인이 되었으므로 메인화면으로 이동
                if(loginDTO != null){
                    if(loginDTO.getId() != null){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "아이디나 비밀번호가 일치안함 !!!", Toast.LENGTH_SHORT).show();
                        Log.d("main:login", "아이디나 비밀번호가 일치안함 !!!");
                        etId.setText(""); etpassword.setText("");
                        etId.requestFocus();
                }
                }
            }//onclick
        });//btnLogin

        /*카카오 로그인 콜백*/
        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        Log.d(TAG, "onCreate: 네이버 " + OAuthLoginState.NEED_LOGIN.equals(mOAuthLoginInstance.getState(getApplicationContext())));
        Log.d(TAG, "onCreate: 네이버 " + OAuthLoginState.NEED_INIT.equals(mOAuthLoginInstance.getState(getApplicationContext())));
        /*네이버 자동 로그인 : 실단말기로 해볼것 : 네이버 앱 이용해야함 */
        HasNaverSession();
        if (!HasNaverSession()) {
            // 핸들러가 세션 없을 시 동작을 대체한다.
        } else if (HasNaverSession()) {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }//onCreate

    /*네이버 로그인 핸들러*/
    @SuppressLint("HandlerLeak")
    private OAuthLoginHandler mOAuthLoginHanlder = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            /*로그인 인증 성공시 */
            if(success){
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);

                RequestApiTask rat = new RequestApiTask(mContext, mOAuthLoginInstance);
                rat.execute();
            }else{  /*로그인 인증 실패시*/
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode : " + errorCode + ", errorDesc : " + errorDesc, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "run: 에러" + errorCode);
            }
        }
    };

    /*네이버 자동 로그인*/
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

    /*네이버 로그인 :  RequestApiTask -> 비동기통신*/
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
                /*로그인에 성공했을 때*/
                if(loginResult.getString("resultcode").equals("00")){
                    JSONObject response = loginResult.getJSONObject("response");
                    naver_id = response.getString("id");
                    naver_email = response.getString("email");
                    naver_name = response.getString("name");
                    naver_phone = response.getString("mobile");
                    /*DB에 저장되어있는지 없는지를 확인 한다.*/
                    NaverIdChk naverIdChk = new NaverIdChk(naver_id);
                    try {
                        state = naverIdChk.execute().get(); //실행
                    }catch (Exception e){
                        e.getMessage();
                        e.printStackTrace();
                    }

                    if(state.trim().equals("false")){
                        /*전에 네이버 로그인을 했기 때문에 네이버 아이디가 DB에 저장이 되어있음*/
                        /*로그인 처리 및 자동 로그인*/
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
                        /*새로운 가입*/
                        Log.d(TAG, "onPostExecute: " + id);
                        state = null;
                        NaverJoin naverJoin = new NaverJoin (naver_id, naver_email, naver_name, naver_phone, naver);
                        try {
                            state = naverJoin.execute().get();
                        }catch (Exception e){
                            e.printStackTrace();
                            e.getMessage();
                        }//try
                        Log.d(TAG, "onPostExecute: 네이버 : " + state);
                        if(state.trim().equals("true")){    //삽입됨
                            NaverLogin naverLogin = new NaverLogin(naver_id, naver_email, naver_name, naver_phone);
                            try {
                                loginDTO = naverLogin.execute().get();
                            }catch (Exception e){
                                e.getMessage();
                                e.printStackTrace();
                            }
                            HasNaverSession();
                            Toast.makeText(LoginActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else { //삽입 실패
                            Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                }else{  //로그인에 실패했을 때
                    Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
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
    }//onRequestPermissionsResult


}
