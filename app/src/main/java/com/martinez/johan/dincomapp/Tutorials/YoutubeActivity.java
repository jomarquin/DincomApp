package com.martinez.johan.dincomapp.Tutorials;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.martinez.johan.dincomapp.R;
import com.martinez.johan.dincomapp.Utilities.Utilities;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {


    YouTubePlayerView youTubePlayerView;
    String keyYoutube="AIzaSyBDFf2MN7UPVTFrlVNhyUapOzAvLujScVY";//Key API youtube

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        youTubePlayerView=findViewById(R.id.id_youtube);
        youTubePlayerView.initialize(keyYoutube, this);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored){
            youTubePlayer.cueVideo(Utilities.ID_VIDEO);//Play the video selected
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this,1).show();
        }else {
            Toast.makeText(this, "Error al inicializar Youtube",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1){
            getYoutubePlayerProvider().initialize(keyYoutube, this);
        }
    }

    public YouTubePlayer.Provider getYoutubePlayerProvider(){
        return youTubePlayerView;
    }

}
