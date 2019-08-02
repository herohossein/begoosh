package com.act.voicecommand.Dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.act.voicecommand.CommandAction;
import com.act.voicecommand.R;

import java.util.List;
import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VoiceDialog extends AppCompatActivity implements RecognitionListener {

    Handler mHandler;
    SpeechRecognizer recognizer;
    private static final String TAG = "VoiceDialog";
    CommandAction commandAction;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_voice_dialog);

        tv = findViewById(R.id.tv);

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        startVoiceRecognitionActivity();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void startVoiceRecognitionActivity() {
        Log.d("tag", "startVoiceRecognitionActivity: OK");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
//        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, Integer.valueOf(2));
//        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, Integer.valueOf(100));
//        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, Integer.valueOf(100));

        recognizer.setRecognitionListener(this);
        recognizer.startListening(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recognizer != null)
            recognizer.destroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (recognizer != null)
            recognizer.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (recognizer != null)
            recognizer.destroy();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d(TAG, "onReadyForSpeech: ");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d(TAG, "onBeginningOfSpeech: ");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech: ");
    }

    @Override
    public void onError(int error) {
        Log.d(TAG, "onError: " + error);

        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                Toast.makeText(this, "متوجه نشدم دوباره امتحان کن!", Toast.LENGTH_LONG).show();
                finish();
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                Toast.makeText(this, "اینترنت در دسترس نیست!", Toast.LENGTH_LONG).show();
                finish();
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                Toast.makeText(this, "اینترنت در دسترس نیست!", Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                Toast.makeText(this, "ببخشید مشکلی رخ داده!", Toast.LENGTH_LONG).show();
                finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResults(final Bundle results) {
        Log.d(TAG, "onResults: " + results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));

        List<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);


        commandAction = new CommandAction(this, matches);

        if (!commandAction.messageCommand()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                commandAction.flashLightCommand();
            }
            commandAction.cameraCommand();
            commandAction.callCommand();
            commandAction.reminderCommand();
            commandAction.changeWifiStateCommand();
            commandAction.doSilentCommand();
            commandAction.changeBluetoothStateCommand();
            commandAction.openAppCommand();
            commandAction.calculateCommand();
            commandAction.setAlarmCommand();
            commandAction.translateCommand();
            commandAction.weatherCommand();
            commandAction.prayerTimeCommand();
            commandAction.gap();
        }

       this.finish();

        recognizer.destroy();
    }

    @Override
    public void onPartialResults(final Bundle partialResults) {
        Log.d(TAG, "onPartialResults: " + Objects.requireNonNull(partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)).get(0));
        mHandler = new Handler();

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                tv.setText(Objects.requireNonNull(partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)).get(0));
            }
        };
        mHandler.post(r);
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d(TAG, "onEvent: ");
        Log.d(TAG, "onEvent: " + params.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
