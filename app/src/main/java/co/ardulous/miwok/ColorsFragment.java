package co.ardulous.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {
    private WordAdapter itemsAdaptertemplate;

    AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener()
    {
        @Override
        public void onAudioFocusChange(int i) {
            if(i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //temporary loss
                itemsAdaptertemplate.PauseMediaPlayer();
                itemsAdaptertemplate.SeekMediaPlayer(0);
            }
            else if(i==AudioManager.AUDIOFOCUS_GAIN)
            {
                //start
                itemsAdaptertemplate.startMediaPlayer();
            }
            else if(i==AudioManager.AUDIOFOCUS_LOSS) {
                //permanant loss
                itemsAdaptertemplate.releaseMediaResources();
                audioManager.abandonAudioFocus(audioFocusChangeListener);
            }
        }
    };

    public ColorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStop() {
        super.onStop();
        itemsAdaptertemplate.releaseMediaResources();
        audioManager.abandonAudioFocus(audioFocusChangeListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_colors, container, false);
        audioManager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        ArrayList<Word> colorList=new ArrayList<>();
        colorList.add(new Word("red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        colorList.add(new Word("green", "chokokki",R.drawable.color_green,R.raw.color_green));
        colorList.add(new Word("brown", "ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        colorList.add(new Word("gray", "ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        colorList.add(new Word("black", "kululli",R.drawable.color_black,R.raw.color_black));
        colorList.add(new Word("white", "kelelli",R.drawable.color_white,R.raw.color_white));
        colorList.add(new Word("dusty yellow", "ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        colorList.add(new Word("mustard yellow", "chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        WordAdapter itemsAdapter=new WordAdapter(getActivity(), colorList);
        int result=audioManager.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        itemsAdaptertemplate=itemsAdapter;
        itemsAdaptertemplate.sendFocusResult(result);
        ListView listView=(ListView)rootView.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
        return rootView;
    }

}
