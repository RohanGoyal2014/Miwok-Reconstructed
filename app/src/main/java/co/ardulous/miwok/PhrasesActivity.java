package co.ardulous.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    AudioManager audioManager;
    private WordAdapter itemsAdaptertemplate;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //temporary loss
                itemsAdaptertemplate.PauseMediaPlayer();
                itemsAdaptertemplate.SeekMediaPlayer(0);
            } else if (i == AudioManager.AUDIOFOCUS_GAIN) {
                //start
                itemsAdaptertemplate.startMediaPlayer();
            } else if (i == AudioManager.AUDIOFOCUS_LOSS) {
                //permanant loss
                itemsAdaptertemplate.releaseMediaResources();
                audioManager.abandonAudioFocus(audioFocusChangeListener);
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        itemsAdaptertemplate.releaseMediaResources();
        audioManager.abandonAudioFocus(audioFocusChangeListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ArrayList<Word> phraseList = new ArrayList<>();
        phraseList.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        phraseList.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phraseList.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        phraseList.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        phraseList.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        phraseList.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        phraseList.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phraseList.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        phraseList.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        phraseList.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));
        WordAdapter itemsAdapter = new WordAdapter(this, phraseList);
        int result = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        itemsAdaptertemplate = itemsAdapter;
        itemsAdaptertemplate.sendFocusResult(result);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
    }
}
