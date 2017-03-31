package com.example.hariharavarma.cognitiveaudio;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AndroidAudioRecordActivity extends Activity {

    Button startRec, stopRec;
    TextView name;
    Boolean recording;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startRec = (Button)findViewById(R.id.startrec);
        stopRec = (Button)findViewById(R.id.stoprec);
        name = (TextView) findViewById(R.id.editText);


        startRec.setOnClickListener(startRecOnClickListener);
        stopRec.setOnClickListener(stopRecOnClickListener);

    }

    View.OnClickListener startRecOnClickListener
            = new View.OnClickListener(){

        @Override
        public void onClick(View arg0) {

            Thread recordThread = new Thread(new Runnable(){

                @Override
                public void run() {
                    recording = true;
                    startRecord();
                }

            });

            recordThread.start();
            name.setText("Hello");

        }};

    View.OnClickListener stopRecOnClickListener
            = new View.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            recording = false;
        }};

    private void startRecord(){

//        File file = new File(Environment.getExternalStorageDirectory(), "test.pcm");
//
//        try {
//            file.createNewFile();
//
//            OutputStream outputStream = new FileOutputStream(file);
//            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
//            DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);

        int minBufferSize = AudioRecord.getMinBufferSize(48000,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        short[] audioData = new short[minBufferSize];

        AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT,
                44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                minBufferSize);

        AudioTrack audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                44100,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                minBufferSize,
                AudioTrack.MODE_STREAM);

        //audioTrack.play();

        audioRecord.startRecording();

        while(recording){
            int numberOfShort = audioRecord.read(audioData, 0, minBufferSize);
            //audioTrack.write(audioData, 0, minBufferSize);
        }
        audioRecord.stop();
        //dataOutputStream.close();

//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

//    void playRecord(){
//
//        File file = new File(Environment.getExternalStorageDirectory(), "test.pcm");
//
//        int shortSizeInBytes = Short.SIZE/Byte.SIZE;
//
//        int bufferSizeInBytes = (int)(file.length()/shortSizeInBytes);
//        short[] audioData = new short[bufferSizeInBytes];
//
//        try {
//            InputStream inputStream = new FileInputStream(file);
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//            DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
//
//            int i = 0;
//            while(dataInputStream.available() > 0){
//                audioData[i] = dataInputStream.readShort();
//                i++;
//            }
//
//            dataInputStream.close();
//
//            AudioTrack audioTrack = new AudioTrack(
//                    AudioManager.STREAM_MUSIC,
//                    48000,
//                    AudioFormat.CHANNEL_IN_MONO,
//                    AudioFormat.ENCODING_PCM_16BIT,
//                    bufferSizeInBytes,
//                    AudioTrack.MODE_STREAM);
//
//            audioTrack.play();
//            audioTrack.write(audioData, 0, bufferSizeInBytes);
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}