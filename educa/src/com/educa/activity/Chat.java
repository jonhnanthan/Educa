/*
 * Copyright (c) 2013, AllSeen Alliance. All rights reserved.
 *
 *    Permission to use, copy, modify, and/or distribute this software for any
 *    purpose with or without fee is hereby granted, provided that the above
 *    copyright notice and this permission notice appear in all copies.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 *    WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 *    MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 *    ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 *    WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 *    ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 *    OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.educa.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.educa.R;
import com.educa.adapter.ExerciseStudentAdapter;
import com.educa.database.DataBaseAluno;

import org.alljoyn.bus.*;
import org.alljoyn.bus.annotation.BusSignalHandler;
import org.json.JSONException;
import org.json.JSONObject;

public class Chat extends Activity {
    /* Load the native alljoyn_java library. */
    static {
        System.loadLibrary("alljoyn_java");
    }

    private static final String TAG = "SessionlessChat";

    /* Handler for UI messages
     * This handler updates the UI depending on the message received.
     */
    private static final int MESSAGE_CHAT = 1;
    private static final int MESSAGE_POST_TOAST = 2;


    private class PingInfo{
        private String senderName;
        private String message;
        public PingInfo(String senderName, String message){
            this.senderName = senderName;
            this.message = message;
        }
        public String getSenderName(){
            return this.senderName;
        }
        public String getMessage(){
            return this.message;
        }
    }
    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_CHAT:
                /* Add the chat message received to the List View */
                String ping = (String) msg.obj;
                mListViewArrayAdapter.add(ping);
                break;
            case MESSAGE_POST_TOAST:
                /* Post a toast to the UI */
                Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_LONG).show();
                break;
            default:
                break;
            }

            return true;
        }
    });

    /* UI elements */
    private ListView mListView;
    private EditText mNameEditText;
    private EditText mMessageEditText;
    private Button mButton;
    /* ArrayAdapter used to manage the chat messages
     * received by this application.
     */
    private ArrayAdapter<String> mListViewArrayAdapter;

    /* Handler used to make calls to AllJoyn methods. See onCreate(). */
    private Handler mBusHandler;
    
    private ListView listview;
    private static ExerciseStudentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        listview = (ListView) findViewById(R.id.lv_exercise);

        DataBaseAluno db = DataBaseAluno.getInstance(getApplicationContext());
        ArrayList<String> exercises = db.getActivities();

        adapter = new ExerciseStudentAdapter(getApplicationContext(), exercises, Chat.this);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("EDUCA", "ENTROU NO LISTENER");

                String json = adapter.getItem(position);
                try{
                JSONObject exercise = new JSONObject(json);

                if (exercise.getString("type").equals(DataBaseAluno.getInstance(getApplicationContext()).MULTIPLE_CHOICE_EXERCISE_TYPECODE)) {
					ArrayList<CharSequence> listMultipleChoiceExercise = new ArrayList<CharSequence>();

					listMultipleChoiceExercise.add(exercise.getString("name"));
					listMultipleChoiceExercise.add(exercise.getString("question"));
					listMultipleChoiceExercise.add(exercise.getString("alternative1"));
					listMultipleChoiceExercise.add(exercise.getString("alternative2"));
					listMultipleChoiceExercise.add(exercise.getString("alternative3"));
					listMultipleChoiceExercise.add(exercise.getString("alternative4"));
					listMultipleChoiceExercise.add(exercise.getString("answer"));
					listMultipleChoiceExercise.add(exercise.getString("date"));

                    Intent i = new Intent(getApplicationContext(), AnswerMultipleChoiceExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerMatch", listMultipleChoiceExercise);
                    startActivity(i);
                }
                if (exercise.getString("type").equals(DataBaseAluno.getInstance(getApplicationContext()).COMPLETE_EXERCISE_TYPECODE)) {
					ArrayList<CharSequence> listCompleteExercise = new ArrayList<CharSequence>();

					listCompleteExercise.add(exercise.getString("name"));
					listCompleteExercise.add(exercise.getString("word"));
					listCompleteExercise.add(exercise.getString("question"));
					listCompleteExercise.add(exercise.getString("hiddenIndexes"));
					listCompleteExercise.add(exercise.getString("date"));

                    Intent i = new Intent(getApplicationContext(), AnswerCompleteExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerComplete", listCompleteExercise);
                    startActivity(i);
                }
                if (exercise.getString("type").equals(DataBaseAluno.getInstance(getApplicationContext()).COLOR_MATCH_EXERCISE_TYPECODE)) {
                    ArrayList<CharSequence> listColorMatchExercise = new ArrayList<CharSequence>();

                    listColorMatchExercise.add(exercise.getString("name"));
                    listColorMatchExercise.add(exercise.getString("color"));
                    listColorMatchExercise.add(exercise.getString("question"));
                    listColorMatchExercise.add(exercise.getString("alternative1"));
                    listColorMatchExercise.add(exercise.getString("alternative2"));
                    listColorMatchExercise.add(exercise.getString("alternative3"));
                    listColorMatchExercise.add(exercise.getString("alternative4"));
                    listColorMatchExercise.add(exercise.getString("answer"));
                    listColorMatchExercise.add(exercise.getString("date"));

                    Intent i = new Intent(getApplicationContext(), AnswerColorMatchExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerColor", listColorMatchExercise);
                    startActivity(i);
                }
                if (exercise.getString("type").equals(DataBaseAluno.getInstance(getApplicationContext()).NUM_MATCH_EXERCISE_TYPECODE)) {
                    ArrayList<CharSequence> listNumberMatchExercise = new ArrayList<CharSequence>();

                    listNumberMatchExercise.add(exercise.getString("name"));
                    listNumberMatchExercise.add(exercise.getString("color"));
                    listNumberMatchExercise.add(exercise.getString("question"));
                    listNumberMatchExercise.add(exercise.getString("alternative1"));
                    listNumberMatchExercise.add(exercise.getString("alternative2"));
                    listNumberMatchExercise.add(exercise.getString("alternative3"));
                    listNumberMatchExercise.add(exercise.getString("alternative4"));
                    listNumberMatchExercise.add(exercise.getString("answer"));
                    listNumberMatchExercise.add(exercise.getString("date"));

                    Intent i = new Intent(getApplicationContext(), AnswerNumberMatchExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerNumber", listNumberMatchExercise);
                    startActivity(i);
                }
                if (exercise.getString("type").equals(DataBaseAluno.getInstance(getApplicationContext()).IMAGE_MATCH_EXERCISE_TYPECODE)) {
                    ArrayList<CharSequence> listImageMatchExercise = new ArrayList<CharSequence>();

                    listImageMatchExercise.add(exercise.getString("name"));
                    listImageMatchExercise.add(exercise.getString("color"));
                    listImageMatchExercise.add(exercise.getString("question"));
                    listImageMatchExercise.add(exercise.getString("answer"));
                    listImageMatchExercise.add(exercise.getString("date"));

                    Intent i = new Intent(getApplicationContext(), AnswerImageMatchExercise.class);
                    i.putCharSequenceArrayListExtra("QuestionToAnswerImage", listImageMatchExercise);
                    startActivity(i);
                }

                } catch (JSONException e) {
					Log.e("STUDENT HOME ERROR", e.getMessage());
				}
                }
        });
//        setContentView(R.layout.main);
//
//        /* Create an adapter to manage the list view of chat messages */
//        mListViewArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
//
//        /* Set the adapter for the list view */
//        mListView = (ListView) findViewById(R.id.ListView);
//        mListView.setAdapter(mListViewArrayAdapter);
//
//        mNameEditText = (EditText) findViewById(R.id.NameEditText);
//
//        mButton = (Button) findViewById(R.id.Button);
//        mButton.setOnClickListener(new OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                String senderName = mNameEditText.getText().toString();
//                String message = "Sample";
//                Log.v("SEND", message);
//                Message msg = mBusHandler.obtainMessage(BusHandlerCallback.CHAT,
//                        new PingInfo(senderName,message));
//
//                mBusHandler.sendMessage(msg);
//                mMessageEditText.setText("");
//            }
//        });
//        /* Set the action to be taken when the 'Return' key is pressed in the text box */
//        mMessageEditText = (EditText) findViewById(R.id.MessageEditText);
//        mMessageEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
//                Log.v("SEND",  "button:" +event.getAction()+"excpeted: " +KeyEvent.ACTION_UP);
//                if (actionId == EditorInfo.IME_NULL
//                        && event.getAction() == KeyEvent.ACTION_UP) {
//                    /* Send a sessionless signal chat message using the mBusHandler. */
//                    String senderName = mNameEditText.getText().toString();
//                    String message = view.getText().toString();
//                    Log.v("SEND", message);
//                    Message msg = mBusHandler.obtainMessage(BusHandlerCallback.CHAT,
//                                                            new PingInfo(senderName,message));
//
//                    mBusHandler.sendMessage(msg);
//                    mMessageEditText.setText("");
//                }
//                return true;
//            }
//        });

        /* Make all AllJoyn calls through a separate handler thread to prevent blocking the UI. */
        HandlerThread busThread = new HandlerThread("BusHandler");
        busThread.start();
        mBusHandler = new Handler(busThread.getLooper(),new BusHandlerCallback());

        mBusHandler.sendEmptyMessage(BusHandlerCallback.CONNECT);
    }

    public static ExerciseStudentAdapter getAdapter() {
        return adapter;
    }

    public static void setAdapter(ExerciseStudentAdapter adapter) {
        Chat.adapter = adapter;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.mainmenu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        /* Handle item selection */
//        switch (item.getItemId()) {
//        case R.id.quit:
//            finish();
//            return true;
//        default:
//            return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /* Disconnect to prevent any resource leaks. */
        mBusHandler.sendEmptyMessage(BusHandlerCallback.DISCONNECT);
    }

    /* The class that is our AllJoyn service.  It implements the ChatInterface. */
    class ChatService implements ChatInterface, BusObject {
        public ChatService(BusAttachment bus){
            this.bus = bus;
        }
        /*
         * This is the Signal Handler code which has the interface name and the name of the signal
         * which is sent by the client. It prints out the string it receives as parameter in the
         * signal on the UI.
         *
         * This code also prints the string it received from the user and the string it is
         * returning to the user to the screen.
         */
        @BusSignalHandler(iface = "org.alljoyn.bus.samples.slchat", signal = "Chat")
        public void Chat(String senderName, String message) {
            Log.i(TAG, "Signal  : " + senderName +": "+ message);
            //TODO aqui que sera feito o add a interface, esse Message é o json
            if (!message.isEmpty()){
                for (String  linha : message.split("}")) {
                	JSONObject exercise;
    				try {
    					exercise = new JSONObject(linha);
    					DataBaseAluno.getInstance(getApplicationContext()).addActivity(exercise.getString("name"), exercise.getString("type") ,linha);
    				} catch (JSONException e) {
    					Log.e("Erro ao processar mensagem", e.getMessage());
    				}
    			}
                setAdapter(new ExerciseStudentAdapter(getApplicationContext(), DataBaseAluno.getInstance(getApplicationContext()).getActivities(), Chat.this));
                getAdapter().notifyDataSetChanged();
            }
            sendUiMessage(MESSAGE_CHAT, senderName + ": "+ message);
        }

        /* Helper function to send a message to the UI thread. */
        private void sendUiMessage(int what, Object obj) {
            mHandler.sendMessage(mHandler.obtainMessage(what, obj));
        }

        private BusAttachment bus;
    }

    /* This Callback class will handle all AllJoyn calls. See onCreate(). */
    class BusHandlerCallback implements Handler.Callback {

        /* The AllJoyn BusAttachment */
        private BusAttachment mBus;

        /* The AllJoyn SignalEmitter used to emit sessionless signals */
        private SignalEmitter emitter;

        private ChatInterface mChatInterface = null;
        private ChatService myChatService = null;

        /* These are the messages sent to the BusHandlerCallback from the UI. */
        public static final int CONNECT = 1;
        public static final int DISCONNECT = 2;
        public static final int CHAT = 3;

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
            /* Connect to the bus and register to obtain chat messages. */
            case CONNECT: {
                org.alljoyn.bus.alljoyn.DaemonInit.PrepareDaemon(getApplicationContext());
                /*
                 * All communication through AllJoyn begins with a BusAttachment.
                 *
                 * A BusAttachment needs a name. The actual name is unimportant except for internal
                 * security. As a default we use the class name as the name.
                 *
                 * By default AllJoyn does not allow communication between devices (i.e. bus to bus
                 * communication).  The second argument must be set to Receive to allow
                 * communication between devices.
                 */
                mBus = new BusAttachment(getPackageName(), BusAttachment.RemoteMessage.Receive);

                /*
                 * Create and register a bus object
                 */
                myChatService = new ChatService(mBus);
                Status status = mBus.registerBusObject(myChatService, "/ChatService");
                if (Status.OK != status) {
                    logStatus("BusAttachment.registerBusObject()", status);
                    return false;
                }

                /*
                 * Connect the BusAttachment to the bus.
                 */
                status = mBus.connect();
                logStatus("BusAttachment.connect()", status);
                if (status != Status.OK) {
                    finish();
                    return false;
                }

                /*
                 *  We register our signal handler which is implemented inside the ChatService
                 */
                status = mBus.registerSignalHandlers(myChatService);
                if (status != Status.OK) {
                    Log.i(TAG, "Problem while registering signal handler");
                    return false;
                }

                /*
                 *  Add rule to receive chat messages(sessionless signals)
                 */
                status = mBus.addMatch("sessionless='t'");
                if (status == Status.OK) {
                    Log.i(TAG,"AddMatch was called successfully");
                }

                break;
            }

            /* Release all resources acquired in connect. */
            case DISCONNECT: {
                /*
                 * It is important to unregister the BusObject before disconnecting from the bus.
                 * Failing to do so could result in a resource leak.
                 */
                mBus.disconnect();
                mBusHandler.getLooper().quit();
                break;
            }
            /*
             * Call the service's Ping method through the ProxyBusObject.
             *
             * This will also print the String that was sent to the service and the String that was
             * received from the service to the user interface.
             */
            case CHAT: {
                try {
                    if(emitter == null){
                        /* Create an emitter to emit a sessionless signal with the desired message.
                         * The session ID is set to zero and the sessionless flag is set to true.
                         */
                        emitter = new SignalEmitter(myChatService, 0, SignalEmitter.GlobalBroadcast.Off);
                        emitter.setSessionlessFlag(true);
                        /* Get the ChatInterface for the emitter */
                        mChatInterface = emitter.getInterface(ChatInterface.class);
                    }
                    if (mChatInterface != null) {
                        PingInfo info = (PingInfo) msg.obj;
                        /* Send a sessionless signal using the chat interface we obtained. */
                        Log.i(TAG,"Sending chat "+info.getSenderName()+": " + info.getMessage());
                        mChatInterface.Chat(info.getSenderName(), info.getMessage());
                    }
                } catch (BusException ex) {
                    logException("ChatInterface.Chat()", ex);
                }
                break;
            }
            default:
                break;
            }
            return true;
        }
    }

    private void logStatus(String msg, Status status) {
        String log = String.format("%s: %s", msg, status);
        if (status == Status.OK) {
            Log.i(TAG, log);
        } else {
            Message toastMsg = mHandler.obtainMessage(MESSAGE_POST_TOAST, log);
            mHandler.sendMessage(toastMsg);
            Log.e(TAG, log);
        }
    }
    private void logException(String msg, BusException ex) {
        String log = String.format("%s: %s", msg, ex);
        Message toastMsg = mHandler.obtainMessage(MESSAGE_POST_TOAST, log);
        mHandler.sendMessage(toastMsg);
        Log.e(TAG, log, ex);
    }
}
