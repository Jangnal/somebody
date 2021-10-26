package com.example.myteamcproject.Community;

import static com.example.myteamcproject.Common.CommonMethod.coDto;
import static com.example.myteamcproject.Common.CommonMethod.colist;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.ATask.CommunityATask;
import com.example.myteamcproject.Exercise.FragChat;
import com.example.myteamcproject.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragCoView extends Fragment {

    private static final String TAG = "FragCoView";

    Button return_coview, co_delete, co_update;
    TextView cod_title, cod_content, cod_readcount;
    ImageView co_imagefile;

    File file;
    public String imageRealPathA;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_coview, container, false);

        Bundle bundle = getArguments();
        int c_numb = bundle.getInt("c_numb");
        int c_readcount = bundle.getInt("c_readcount");
        int position = bundle.getInt("positon");

        CommunityATask communityATask = new CommunityATask("coview", c_numb, c_readcount);

        try {
            communityATask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        cod_content = viewGroup.findViewById(R.id.cod_content);
        cod_title = viewGroup.findViewById(R.id.cod_title);
        cod_readcount = viewGroup.findViewById(R.id.cod_readcount);
        co_delete = viewGroup.findViewById(R.id.co_delete);
        co_update = viewGroup.findViewById(R.id.co_update);
        co_imagefile = viewGroup.findViewById(R.id.co_imagefile);

        if(coDto != null){
            cod_title.setText(coDto.getC_title());
            cod_content.setText(coDto.getC_content());
            cod_readcount.setText(String.valueOf(coDto.getC_readcount()));
            String filepath = ipConfig + "/resources/" + coDto.c_filepath;
            Log.d(TAG, "setDto: " + filepath);
            Glide.with(this).load(filepath).into(co_imagefile);
        }

        co_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    FragComm fragChat = new FragComm();
                    int co_delete_c = coDto.c_numb;
                    Log.d(TAG, "onClick: " + co_delete_c);
                    CommunityAdapter adapter = new CommunityAdapter((ArrayList<CommunityDTO>) colist, getContext(), getParentFragmentManager());

                    adapter.removeDto(position);
                    adapter.Refresh();

                    CommunityATask communityATask = new CommunityATask("delete", co_delete_c);

                    try {
                        communityATask.execute().get();
                    } catch (ExecutionException e) {
                        e.getMessage();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }

                    transaction.replace(R.id.main_frag, fragChat);
                    transaction.commit();
                }
            });//holder.co_delete.setOnClickListener

        co_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragCoUpdate fragCoUpdate = new FragCoUpdate();
                transaction.replace(R.id.main_frag, fragCoUpdate);
                transaction.commit();
            }
        });

        // 버튼클릭 메소드
        return_coview = viewGroup.findViewById(R.id.return_coview);
        return_coview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragChat fragChat = new FragChat();
                transaction.replace(R.id.main_frag, fragChat);
                transaction.commit();
            }
        });

        co_imagefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file = null;
                try{
                    file = createFile();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                galleryAddPic();
            }
        });

        return viewGroup;
    }

    private File createFile() throws IOException {

        String imageFileName = coDto.c_filename;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File curFile = null;
        try {
            curFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageRealPathA = curFile.getAbsolutePath();
        Log.d(TAG, "createFile: " + imageRealPathA);

        return curFile;
    }//createFile

    private void galleryAddPic() {
        Intent mediaScanIntent =
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(coDto.c_filepath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

}
