package com.act.voicecommand;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rm.rmswitch.RMSwitch;

import java.util.Objects;

public class Speech2TextActivity extends BaseActivity implements RecognitionListener {
    SpeechRecognizer recognizer;
    RMSwitch mSwitch;
    TextView fa;
    TextView en;
    EditText text;
    EditText partial;
    LinearLayout copy;
    ImageView button;
    Handler mHandler;
    final StringBuilder s = new StringBuilder();

    private static final String TAG = "Speech2TextActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_2_text);

        init();

        fa.setTextColor(Color.parseColor("#FFFCC956"));

        mSwitch.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if (isChecked) {
                    fa.setTextColor(Color.parseColor("#FFFCC956"));
                    en.setTextColor(Color.parseColor("#FF9164C9"));
                } else {
                    fa.setTextColor(Color.parseColor("#FF9164C9"));
                    en.setTextColor(Color.parseColor("#FFFCC956"));
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startVoiceRecognitionActivity(!mSwitch.isChecked());
            }
        });
    }

    private void init() {
        mSwitch = findViewById(R.id.your_id);
        fa = findViewById(R.id.fa_tv);
        en = findViewById(R.id.en_tv);
        text = findViewById(R.id.speech_et);
        partial = findViewById(R.id.partial_result);
        copy = findViewById(R.id.copy);
        button = findViewById(R.id.imageView);
        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
//        Toast.makeText(this, "!!بگو! دارم یادداشت میکنم", Toast.LENGTH_SHORT).show();
        partial.setVisibility(View.VISIBLE);
        partial.setText("بگو دارم یادداشت میکنم!!");
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(final Bundle results) {
        mHandler = new Handler();
        partial.setVisibility(View.GONE);
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                text.append(Objects.requireNonNull(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)).get(0).concat(" "));
            }
        };
        mHandler.post(r);
    }
    @Override
    public void onPartialResults(final Bundle partialResults) {
        mHandler = new Handler();
        partial.setVisibility(View.VISIBLE);
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                partial.setText(Objects.requireNonNull(partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)).get(0));
            }
        };
        mHandler.post(r);
    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    public void startVoiceRecognitionActivity(boolean persian) {
        Log.d("tag", "startVoiceRecognitionActivity: OK");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        if (persian) {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
        } else {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
        }

//        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
//        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 100);
//        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 100);

        recognizer.setRecognitionListener(this);
        recognizer.startListening(intent);

    }
}
