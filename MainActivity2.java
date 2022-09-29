package org.techtown.hear_sample3;

import static android.speech.tts.TextToSpeech.ERROR;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    static boolean run = true;

    // 스피너에 재생 속도 선택 입력
    private Spinner spinner;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    // 블루투스 활성화 여부
    private Switch switch2;
    BluetoothAdapter bluetoothAdapter;
    SeekBar seekBar;
    final String TAG = "MainActivity";
    int REQUEST_ENABLE_BT = 1;

    // 기능 사용 여부 및 tts 설정
    private Switch switch1;
    private TextToSpeech tts;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // 스피너에 재생 속도 선택 입력
        arrayList = new ArrayList<>();
        arrayList.add("0.5");
        arrayList.add("1.0");
        arrayList.add("2.0");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList);

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 블루투스 활성화 여부
        switch2 = (Switch) findViewById(R.id.switch2);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    bluetoothAdapter.enable();
                    Toast.makeText(getApplicationContext(), "블루투스가 활성화 되었습니다.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    bluetoothAdapter.disable();
                    Toast.makeText(getApplicationContext(), "블루투스가 비활성화 되었습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // seekBar 활용 볼륨 조절
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int nMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int nCurrentVolumn = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBar.setMax(nMax);
        seekBar.setProgress(nCurrentVolumn);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        editText = (EditText) findViewById(R.id.editText);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != ERROR) {
                    tts.setLanguage(Locale.KOREA);
                }
            }
        });
        switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    if(spinner.getSelectedItem().toString() == "0.5") {
                        tts.setPitch(1.0f);
                        tts.setSpeechRate(0.5f);
                        tts.speak(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    } else if(spinner.getSelectedItem().toString() == "1.0") {
                        tts.setPitch(1.0f);
                        tts.setSpeechRate(1.0f);
                        tts.speak(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    } else if(spinner.getSelectedItem().toString() == "2.0") {
                        tts.setPitch(1.0f);
                        tts.setSpeechRate(2.0f);
                        tts.speak(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
        });

    }
}