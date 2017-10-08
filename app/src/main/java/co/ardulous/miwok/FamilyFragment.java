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
public class FamilyFragment extends Fragment {

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

    @Override
    public void onStop() {
        super.onStop();
        itemsAdaptertemplate.releaseMediaResources();
        audioManager.abandonAudioFocus(audioFocusChangeListener);
    }

    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_family, container, false);
        audioManager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        ArrayList<Word> familyList=new ArrayList<>();
        familyList.add(new Word("father","әpә",R.drawable.family_father,R.raw.family_father));
        familyList.add(new Word("mother", "әṭa",R.drawable.family_mother,R.raw.family_mother));
        familyList.add(new Word("son", "angsi",R.drawable.family_son,R.raw.family_son));
        familyList.add(new Word("daughter", "tune",R.drawable.family_daughter,R.raw.family_daughter));
        familyList.add(new Word("older brother", "taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        familyList.add(new Word("younger brother", "chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        familyList.add(new Word("older sister", "teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        familyList.add(new Word("younger sister", "kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        familyList.add(new Word("grandmother", "ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        familyList.add(new Word("grandfather", "paapa",R.drawable.family_grandfather,R.raw.family_grandfather));
        WordAdapter itemsAdapter=new WordAdapter(getActivity(), familyList);
        int result=audioManager.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        itemsAdaptertemplate=itemsAdapter;
        itemsAdaptertemplate.sendFocusResult(result);
        ListView listView=(ListView)rootView.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
        return rootView;
    }

}
