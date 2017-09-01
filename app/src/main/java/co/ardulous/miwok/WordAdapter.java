package co.ardulous.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by ardulous on 28/8/17.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    MediaPlayer mediaPlayer;
    private boolean playInstruction = false;

    public WordAdapter(Activity context, ArrayList<Word> words) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
    }

    public void releaseMediaResources() {
        releaseMedia();
    }

    public void SeekMediaPlayer(int pos) {
        mediaPlayer.seekTo(pos);
    }

    public void startMediaPlayer() {
        mediaPlayer.start();
    }

    public void PauseMediaPlayer() {
        mediaPlayer.pause();
    }

    public void sendFocusResult(int result) {
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            playInstruction = true;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link AndroidFlavor} object located at this position in the list
        final Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwoktranslation);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        miwokTextView.setText(currentWord.getmiwok());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.defaulttranslation);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        defaultTextView.setText(currentWord.getDefault());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        /*ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        iconView.setImageResource(currentWord.getImageResourceId());*/
        if (currentWord.hasImage()) {
            ImageView iconView = (ImageView) listItemView.findViewById(R.id.coImg);
            iconView.setImageResource(currentWord.getImage());
        }
        LinearLayout musicButton = (LinearLayout) listItemView.findViewById(R.id.playbutton);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releaseMedia();
                if (playInstruction == true) {
                    mediaPlayer = MediaPlayer.create(getContext(), currentWord.getMusic());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMedia();
                        }
                    });
                }
            }
        });
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

    private void releaseMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void onStop() {

    }
}
