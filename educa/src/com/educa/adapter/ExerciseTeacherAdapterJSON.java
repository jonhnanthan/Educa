
package com.educa.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.educa.R;
import com.educa.activity.*;
import com.educa.database.DataBaseProfessor;
import org.alljoyn.bus.*;
import org.alljoyn.bus.annotation.BusSignalHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExerciseTeacherAdapterJSON extends BaseAdapter {

    private static final String LOG = "LOGs";
    private static final String TAG = "LOGsB";
    private static List<String> mListExercise;

    /* Handler for UI messages
        * This handler updates the UI depending on the message received.
        */
    private static final int MESSAGE_CHAT = 1;
    private static final int MESSAGE_POST_TOAST = 2;

    private LayoutInflater mInflater;
    private Context mcontext;
    private Activity parentActivity;
    private ArrayAdapter<String> mListViewArrayAdapter;

    /* Handler used to make calls to AllJoyn methods. See onCreate(). */
    private Handler mBusHandler;

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
                    Toast.makeText(mcontext.getApplicationContext(), (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }

            return true;
        }
    });

    public ExerciseTeacherAdapterJSON(Context context, List<String> listExercise, Activity parentActivity) {
        mListExercise = listExercise;
        mInflater = LayoutInflater.from(context);
        mcontext = context;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return mListExercise.size();
    }

    @Override
    public String getItem(int position) {
        return mListExercise.get(position);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @SuppressLint({ "ViewHolder", "InflateParams" }) @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        view = mInflater.inflate(R.layout.exercise_adapter_teacher_item, null);

        TextView tv_exercise_name = (TextView) view.findViewById(R.id.tv_exercise_name);
        TextView tv_exercise_status = (TextView) view.findViewById(R.id.tv_exercise_status);
        TextView tv_exercise_correction = (TextView) view.findViewById(R.id.tv_exercise_correction);
        TextView tv_exercise_date = (TextView) view.findViewById(R.id.tv_exercise_date);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView1);
        
        final String json = mListExercise.get(position);
        JSONObject exercise;
		try {
			exercise = new JSONObject(json);
	        tv_exercise_name.setText(exercise.getString("name"));
	        tv_exercise_status.setText(exercise.getString("status"));
	        tv_exercise_correction.setText(exercise.getString("correction"));
	        tv_exercise_date.setText(exercise.getString("date"));

	        if (exercise.getString("type").equals(DataBaseProfessor.getInstance(mcontext).COLOR_MATCH_EXERCISE_TYPECODE)) {
	            icon.setImageResource(R.drawable.colorthumb);
	        } else if (exercise.getString("type").equals(DataBaseProfessor.getInstance(mcontext).MULTIPLE_CHOICE_EXERCISE_TYPECODE)) {
	            icon.setImageResource(R.drawable.multiplethumb);
	        } else if (exercise.getString("type").equals(DataBaseProfessor.getInstance(mcontext).COMPLETE_EXERCISE_TYPECODE)) {
	            icon.setImageResource(R.drawable.completethumb);
	        }
		} catch (JSONException e) {
			Log.e("CREATE VIEW ERROR", e.getMessage());
		}

        ImageView bt_options = (ImageView)
                view.findViewById(R.id.bt_options);
        bt_options.setOnClickListener(new
                View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupMenu(v, json);
                    }
                });

        return view;
    }

    private void showPopupMenu(View v, final String json) {
        PopupMenu popupMenu = new PopupMenu(mcontext, v);
        popupMenu.getMenuInflater().inflate(R.menu.teacher_exercise_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
					JSONObject exercise;
					try {
								exercise = new JSONObject(json);
								if (exercise.getString("type").equals(DataBaseProfessor.getInstance(mcontext).MULTIPLE_CHOICE_EXERCISE_TYPECODE)) {
									Intent intent = new Intent(parentActivity, EditMultipleChoiceExerciseActivity.class);

									List<CharSequence> listMultipleChoiceExercise = new ArrayList<CharSequence>();

									listMultipleChoiceExercise.add(exercise.getString("name"));
									listMultipleChoiceExercise.add(exercise.getString("question"));
									listMultipleChoiceExercise.add(exercise.getString("alternative1"));
									listMultipleChoiceExercise.add(exercise.getString("alternative2"));
									listMultipleChoiceExercise.add(exercise.getString("alternative3"));
									listMultipleChoiceExercise.add(exercise.getString("alternative4"));
									listMultipleChoiceExercise.add(exercise.getString("answer"));
									listMultipleChoiceExercise.add(exercise.getString("date"));

									intent.putCharSequenceArrayListExtra("EditMultipleChoiseExercise", (ArrayList<CharSequence>) listMultipleChoiceExercise);
									parentActivity.startActivity(intent);
								}
								if (exercise.getString("type").equals(DataBaseProfessor.getInstance(mcontext).COMPLETE_EXERCISE_TYPECODE)) {
									Intent intent = new Intent(parentActivity, EditCompleteExerciseStep1Activity.class);

									List<CharSequence> listCmpleteExercise = new ArrayList<CharSequence>();

									listCmpleteExercise.add(exercise.getString("name"));
									listCmpleteExercise.add(exercise.getString("word"));
									listCmpleteExercise.add(exercise.getString("question"));
									listCmpleteExercise.add(exercise.getString("hiddenIndexes"));
									listCmpleteExercise.add(exercise.getString("date"));

									intent.putCharSequenceArrayListExtra("EditCompleteExercise", (ArrayList<CharSequence>) listCmpleteExercise);
									parentActivity.startActivity(intent);
								}
		                        if (exercise.getString("type").equals(DataBaseProfessor.getInstance(mcontext).COLOR_MATCH_EXERCISE_TYPECODE)) {
	                            Intent intent = new Intent(parentActivity, EditColorMatchExerciseActivity.class);
	
	                            List<CharSequence> listColorMatchExercise = new ArrayList<CharSequence>();
	
	                            listColorMatchExercise.add(exercise.getString("name"));
	                            listColorMatchExercise.add(exercise.getString("color"));
	                            listColorMatchExercise.add(exercise.getString("question"));
	                            listColorMatchExercise.add(exercise.getString("alternative1"));
	                            listColorMatchExercise.add(exercise.getString("alternative2"));
	                            listColorMatchExercise.add(exercise.getString("alternative3"));
	                            listColorMatchExercise.add(exercise.getString("alternative4"));
	                            listColorMatchExercise.add(exercise.getString("answer"));
	                            listColorMatchExercise.add(exercise.getString("date"));
	
	                            intent.putCharSequenceArrayListExtra("EditColorMatchExercise", (ArrayList<CharSequence>) listColorMatchExercise);
	                            parentActivity.startActivity(intent);
	                        }

					} catch (JSONException e) {
						Log.e("EDIT ERROR", e.getMessage());
					}
                        return true;
                    case R.id.delete:
                        deleteAlert(json);
                        break;
                    case R.id.send:
//                        sendAlert(json);
                            Intent chooseModelIntent = new Intent(parentActivity,
                                    Chat.class);
                            List<CharSequence> listColorMatchExercise = new ArrayList<CharSequence>();
                            listColorMatchExercise.add(json);
                            chooseModelIntent.putCharSequenceArrayListExtra("Chat", (ArrayList<CharSequence>) listColorMatchExercise);
                            parentActivity.startActivity(chooseModelIntent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void removeAndNotify(final String json) {
    	JSONObject exercise;
		try {
			exercise = new JSONObject(json);
			DataBaseProfessor.getInstance(mcontext).removeActivity(exercise.getString("name"));
		} catch (JSONException e) {
			Log.e(LOG, e.getMessage());
		}
        mListExercise.remove(json);
        notifyDataSetChanged();
        
    }
    public void sendAndNotify(final String json) {
//        JSONObject exercise;

//            exercise = new JSONObject(json);
//            getAlbumStorageDir(mcontext,exercise.getString("name"), json);
        String senderName = "professor";
        String message = "SOMETHING";
        Log.v("SEND", message);
        Message msg = mBusHandler.obtainMessage(BusHandlerCallback.CHAT,
                new PingInfo(senderName,message));

        try {
            mBusHandler.sendMessage(msg);
        }catch (Exception e){
            Log.e(LOG, e.getMessage());
        };

//            mMessageEditText.setText("");


    }

    public void getAlbumStorageDir(Context context, String exerciseName, String json) {
//        File file = new File(context.getFilesDir(), exerciseName);
        FileOutputStream outputStream;
        String caminhoAbsolutoDoArquivo;

        try {
            caminhoAbsolutoDoArquivo = mcontext.getApplicationContext().getFilesDir().getPath() + "/" +exerciseName;
            outputStream = new FileOutputStream(caminhoAbsolutoDoArquivo);
            outputStream.write(json.getBytes());
            Log.v("LOGS", context.getFilesDir().getAbsolutePath() );
            outputStream.close();
            Toast.makeText(mcontext,context.getString(R.string.fileSent),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("LOGS", e.getMessage());
            Toast.makeText(mcontext,context.getString(R.string.ErrorSend),
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void sendAlert(final String json){
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        builder.setTitle(parentActivity.getResources().getString(R.string.send_alert_title));
        builder.setMessage(parentActivity.getResources().getString(R.string.send_alert_message));
        builder.setPositiveButton(parentActivity.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        sendAndNotify(json);
                    }
                });

        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void deleteAlert(final String json) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        builder.setTitle(parentActivity.getResources().getString(R.string.delete_alert_title));
        builder.setMessage(parentActivity.getResources().getString(R.string.delete_alert_message));

        builder.setPositiveButton(parentActivity.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        removeAndNotify(json);
                    }
                });

        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

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
                    Log.i(TAG, mcontext.getApplicationContext().toString());
                    org.alljoyn.bus.alljoyn.DaemonInit.PrepareDaemon(mcontext.getApplicationContext());
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
                    mBus = new BusAttachment(mcontext.getPackageName(), BusAttachment.RemoteMessage.Receive);

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
                        parentActivity.finish();
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
