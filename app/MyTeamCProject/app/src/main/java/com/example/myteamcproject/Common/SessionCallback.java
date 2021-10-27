package com.example.myteamcproject.Common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.myteamcproject.ATask.KakaoJoin;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.Member.MemberDTO;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;

public class SessionCallback implements ISessionCallback {
    private static final String TAG = "SessionCallback";
    Context context;
    public static MemberDTO KakaoLoginDTO = null;

    String state;
    public SessionCallback(Context context) {
        this.context = context;
    }

    //카카오 로그인 성공
    @Override
    public void onSessionOpened() {
        Log.d(TAG, "onSessionOpened: SessionCallback 카카오 로그인 시작");
        requestMe();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //로그인 실패
    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
    }
    //사용자 정보 요청
    private void requestMe() {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);
            }

            @Override
            public void onSuccess(MeV2Response result) {
                Log.i("KAKAO_API", "사용자 아이디: " + result.getId());
                UserAccount kakaoAcount = result.getKakaoAccount();
                Profile profile = kakaoAcount.getProfile();
                if(kakaoAcount != null && profile != null){
                    //카카오 계정이 있다면
                    long kakao_id = result.getId();   //아이디
                    String kakao_name = profile.getNickname();
                    String kakao_phone = kakaoAcount.getPhoneNumber();
                    String kakao_email = kakaoAcount.getEmail();

                    /*카카오 로그인을 한다*/

                    /*카카오 로그인을 한 적이 없다면? -> DB에 저장해야한다.*/
                    if(KakaoLoginDTO == null){
                        KakaoJoin kakaoJoin = new KakaoJoin(kakao_id,kakao_name);
                        try{
                            kakaoJoin.execute().get();
                        }catch (Exception e){
                            e.printStackTrace();
                            e.getMessage();
                        }
                        Log.d(TAG, "onSuccess1: 카카오로그인됐습니다");
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }else {
                        Log.d(TAG, "onSuccess2: 카카오로그인됐습니다");
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }



                }

            }
        });
    }
}
