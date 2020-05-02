package com.act.voicecommand.Dialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.act.voicecommand.CommandAction;
import com.act.voicecommand.R;

import java.util.ArrayList;
import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VoiceDialog extends AppCompatActivity implements RecognitionListener {

    Handler mHandler;
    SpeechRecognizer recognizer;
    private static final String TAG = "VoiceDialog";
    CommandAction commandAction;
    TextView tv;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_voice_dialog);

        tv = findViewById(R.id.tv);

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        startVoiceRecognitionActivity();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = this.getWindow();
//        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        pref = getSharedPreferences("com.act.voicecommand", MODE_PRIVATE);
    }


    public void startVoiceRecognitionActivity() {
        Log.d("tag", "startVoiceRecognitionActivity: OK");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
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
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                Toast.makeText(this, "متوجه نشدم دوباره امتحان کن!", Toast.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                Toast.makeText(this, "متوجه نشدم دوباره امتحان کن!", Toast.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                Toast.makeText(this, "متوجه نشدم دوباره امتحان کن!", Toast.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_SERVER:
                Toast.makeText(this, "متوجه نشدم دوباره امتحان کن!", Toast.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                Toast.makeText(this, "متوجه نشدم دوباره امتحان کن!", Toast.LENGTH_LONG).show();
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                Toast.makeText(this, "متوجه نشدم دوباره امتحان کن!", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "ببخشید مشکلی رخ داده!", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    ArrayList<String> matches;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResults(final Bundle results) {
        Log.d(TAG, "onResults: " + results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));

        matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        setCommand();

        if (!pref.getBoolean("perState", false)) {
            this.finish();
        }

        recognizer.destroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setCommand() {

        commandAction = new CommandAction(this, matches);
        try {
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
                commandAction.searchCommand();
                commandAction.typeCommand();
                commandAction.gap();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "hi: ");
            ArrayList<String> icons = new ArrayList<>();
            assert matches != null;
            for (int i = 0; i < matches.size(); i++) {
                icons.add("check");
            }
            Intent i = new Intent(VoiceDialog.this, MyCustomDialog.class);
            i.putExtra(CommandAction.TITLE, "دستورت رو متجه نشدم!! لطفا دستور درست رو انتخاب کن...");
            i.putStringArrayListExtra(CommandAction.TEXT, matches);
            i.putStringArrayListExtra(CommandAction.ICONS, icons);
            i.putExtra(CommandAction.CONDITION, 4);
            startActivity(i);
        }

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e(TAG, "onRequestPermissionsResult: " + requestCode);
        switch (requestCode) {
            case CommandAction.REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setCommand();
                break;

            case CommandAction.REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setCommand();
                break;

            case CommandAction.REQUEST_CALENDAR:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setCommand();
                break;

            case CommandAction.REQUEST_PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setCommand();
                break;

            case CommandAction.REQUEST_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setCommand();
                break;
        }
        this.finish();
    }
}
