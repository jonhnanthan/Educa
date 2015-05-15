
package com.educa.activity;

import java.util.ArrayList;
import java.util.Locale;

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.alljoyn.bus.SignalEmitter;
import org.alljoyn.bus.Status;
import org.alljoyn.bus.annotation.BusSignalHandler;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.educa.R;
import com.educa.adapter.ExerciseStudentAdapter;
import com.educa.database.DataBaseAluno;

public class StudentHomeActivity extends Activity {
    private ListView listview;
    private static ExerciseStudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        
        TextView tv = (TextView) findViewById(R.id.textView1);
        if (Locale.getDefault().toString().equalsIgnoreCase("pt_br")){
        	tv.setText(getResources().getString(R.string.my_exercises) + " " + getSharedPreferences("Preferences", 0).getString("StudentName", ""));
        } else{
        	tv.setText(getSharedPreferences("Preferences", 0).getString("StudentName", "") + " " +getResources().getString(R.string.my_exercises));
        }

        listview = (ListView) findViewById(R.id.lv_exercise);

        DataBaseAluno db = DataBaseAluno.getInstance(getApplicationContext());
        ArrayList<String> exercises = db.getActivities();

        adapter = new ExerciseStudentAdapter(getApplicationContext(), exercises, StudentHomeActivity.this);
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
        
        /* Make all AllJoyn calls through a separate handler thread to prevent blocking the UI. */
        HandlerThread busThread = new HandlerThread("BusHandler");
        busThread.start();
        mBusHandler = new Handler(busThread.getLooper(),new BusHandlerCallback());

        mBusHandler.sendEmptyMessage(BusHandlerCallback.CONNECT);
        Log.i("Chat", "conectado");

    }

    public static ExerciseStudentAdapter getAdapter() {
        return adapter;
    }

    public static void setAdapter(ExerciseStudentAdapter adapter) {
        StudentHomeActivity.adapter = adapter;
        StudentHomeActivity.adapter.notifyDataSetChanged();
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(getApplicationContext(),
//                MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_activity_menu, menu);
        return true;
    }

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.refresh:
			setAdapter(new ExerciseStudentAdapter(getApplicationContext(), 
					DataBaseAluno.getInstance(getApplicationContext()).getActivities(), StudentHomeActivity.this));
			return true;
		}
		return super.onOptionsItemSelected(item);
    }

    ////////////////////////////////////
    //CHAT
    ////////////////////////////////////
    
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

    /* ArrayAdapter used to manage the chat messages
     * received by this application.
     */
    private ArrayAdapter<String> mListViewArrayAdapter;

    /* Handler used to make calls to AllJoyn methods. See onCreate(). */
    private Handler mBusHandler;
    
//    private ListView listview;
//    private static ExerciseStudentAdapter adapter;

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
            Log.i(TAG, message);
            //TODO aqui que sera feito o add a interface, esse Message é o json
            if (!message.isEmpty()){
            	JSONObject exercise;
				try {
					exercise = new JSONObject(message);
					DataBaseAluno.getInstance(getApplicationContext()).addActivity(exercise.getString("name"), exercise.getString("type") , message);
					Log.i("Chat", "database atualizado");

//                	mBusHandler.sendEmptyMessage(BusHandlerCallback.DISCONNECT);
//			        
//                	/*refresh for�ado para atualizar a lista*/
//                	finish();
//                	startActivity(getIntent());
                	
			        Log.i("Chat", "NOVO ADAPTER E DISCONNECT");

				} catch (JSONException e) {
					Log.e("Chat", e.getMessage());
				}
            }
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
