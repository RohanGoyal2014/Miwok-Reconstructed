package co.ardulous.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static co.ardulous.miwok.R.drawable.color_black;

public class NumbersActivity extends AppCompatActivity {

    WordAdapter itemsAdaptertemplate;
    AudioManager audioManager;
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
        setContentView(R.layout.activity_numbers);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ArrayList<Word> numberList = new ArrayList<>();
        numberList.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        numberList.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        numberList.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        numberList.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        numberList.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        numberList.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        numberList.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        numberList.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        numberList.add(new Word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        numberList.add(new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));
        WordAdapter itemsAdapter = new WordAdapter(this, numberList);
        int result = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        itemsAdaptertemplate = itemsAdapter;
        itemsAdaptertemplate.sendFocusResult(result);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

    }
}
