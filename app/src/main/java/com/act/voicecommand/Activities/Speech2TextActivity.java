package com.act.voicecommand.Activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.act.voicecommand.BaseActivity;
import com.act.voicecommand.CommandAction;
import com.act.voicecommand.R;
import com.rm.rmswitch.RMSwitch;

import java.util.Objects;

public class Speech2TextActivity extends BaseActivity implements RecognitionListener {
    SpeechRecognizer recognizer;
    RMSwitch mSwitch;
    TextView fa;
    TextView en;
    EditText text;
    TextView partial;
    TextView copy;
    TextView clear;
    ImageView button;
    Handler mHandler;
    SharedPreferences prefs = null;
    ClipboardManager myClipboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_2_text);

        init();

        fa.setTextColor(Color.parseColor("#FFFCC956"));

        mSwitch.setChecked(prefs.getBoolean("mswitch", true));
        mSwitch.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if (isChecked) {
                    fa.setTextColor(Color.parseColor("#FFFCC956"));
                    en.setTextColor(Color.parseColor("#FF9164C9"));
                    prefs.edit().putBoolean("mswitch", true).apply();
                } else {
                    fa.setTextColor(Color.parseColor("#FF9164C9"));
                    en.setTextColor(Color.parseColor("#FFFCC956"));
                    prefs.edit().putBoolean("mswitch", false).apply();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity(!mSwitch.isChecked());
            }
        });

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData myClip;
                myClip = ClipData.newPlainText("text", text.getText());
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(Speech2TextActivity.this, "متن در کلیپ بورد ذخیره شد", Toast.LENGTH_SHORT).show();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.getText().clear();
            }
        });

        Intent i = getIntent();
        if (i.getBooleanExtra(CommandAction.TYPE, false)) {
            button.performClick();
        }
    }

    private void init() {
        mSwitch = findViewById(R.id.your_id);
        fa = findViewById(R.id.fa_tv);
        en = findViewById(R.id.en_tv);
        text = findViewById(R.id.speech_et);
        partial = findViewById(R.id.partial_result);
        copy = findViewById(R.id.copy_tv);
        clear = findViewById(R.id.clear_tv);
        button = findViewById(R.id.imageView);
        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        prefs = getSharedPreferences("com.act.voicecommand", MODE_PRIVATE);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        partial.setVisibility(View.VISIBLE);
        partial.setText("بگو! دارم یادداشت میکنم!!");
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
        final String[] getText = {Objects.requireNonNull(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)).get(0)};
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                if (getText[0].contains("خط بعدی")) {
                    getText[0] = getText[0].replace("خط بعدی ", "\n").concat(" ");
                }
                if (getText[0].contains("نقطه")) {
                    getText[0] = getText[0].replace("نقطه ", ".").concat(" ");
                }
                if (getText[0].contains("نقطه ویرگول")) {
                    getText[0] = getText[0].replace("نقطه ویرگول ", "؛").concat(" ");
                }
                if (getText[0].contains("علامت تعجب")) {
                    getText[0] = getText[0].replace("علامت تعجب ", "!").concat(" ");
                }
                if (getText[0].contains("علامت سوال")) {
                    getText[0] = getText[0].replace("علامت سوال ", "؟").concat(" ");
                }
                if (getText[0].contains("ویرگول")) {
                    getText[0] = getText[0].replace("ویرگول ", "،").concat(" ");
                }
                if (getText[0].contains("پرانتز باز")) {
                    getText[0] = getText[0].replace("پرانتز باز ", ")").concat(" ");
                }
                if (getText[0].contains("پرانتز بسته")) {
                    getText[0] = getText[0].replace("پرانتز بسته ", "(").concat(" ");
                }
//                if (getText[0].contains("فاصله")) {
//                    getText[0] = getText[0].replace("فاصله ", " ");
//                }
                if (getText[0].contains("متن") && getText[0].contains("کپی")) {
                    ClipData myClip;
                    myClip = ClipData.newPlainText("text", text.getText());
                    myClipboard.setPrimaryClip(myClip);
                    Toast.makeText(Speech2TextActivity.this, "متن در کلیپ بورد ذخیره شد", Toast.LENGTH_LONG).show();
                }
                if (getText[0].contains("اشتراک گذاری متن")) {
                    Intent intent;
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, text.getText().toString());
                    intent.setType("text/plain");
                    startActivity(Intent.createChooser(intent, "لطفا برای اشتراک گذاری متن یکی از گزینه های زیر را انتخاب کنید :"));
                    getText[0] = getText[0].replace("اشتراک گذاری متن", "").concat(" ");
                }
                if (getText[0].contains("پاک") && getText[0].contains("متن")) {
                    text.getText().clear();
                } else
                    text.append(getText[0].concat(" "));
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
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        if (persian) {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
        } else {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
        }

        recognizer.setRecognitionListener(this);
        recognizer.startListening(intent);
    }
}
