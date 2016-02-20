package com.educaTio.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.educaTio.R;
import com.educaTio.adapter.ExerciseTeacherAdapterJSON;
import com.educaTio.adapter.FoldersTeacherAdapter;
import com.educaTio.database.DataBaseProfessor;
import com.educaTio.services.AdsService;
import com.educaTio.utils.ActiveSession;

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;
import org.alljoyn.bus.SignalEmitter;
import org.alljoyn.bus.Status;
import org.alljoyn.bus.annotation.BusSignalHandler;

import java.util.List;

public class TeacherHomeActivity extends Activity {
	
	private static Context contx;
	private List<String> exercises1;
	private static Dialog dialog;
	private static TextView tvMsgToShow;
	private static ListView listView;
	private static Activity activity;
	private boolean intoFolder;
	private Menu menu;
	private static final String URL_JSON = "https://jonhnanthan.pythonanywhere.com/Educa/default/api/atividade.json";
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_home);
		contx = getApplicationContext();
        ActiveSession.setContext(this);
		activity = TeacherHomeActivity.this;
		intoFolder = false;

        startService(new Intent(this, AdsService.class));

        listView = (ListView) findViewById(R.id.lv_exercise);
        listView.setOnItemClickListener(new OnItemClickListener() {

        	@Override
        	public final void onItemClick(final AdapterView<?> parent, final View view,
        			final int position, final long id) {
        		
        		if (parent.getAdapter() instanceof FoldersTeacherAdapter) {
        			intoFolder = true;
        			menu.findItem(R.id.web_sync).setVisible(false);
        			menu.findItem(R.id.new_folder).setVisible(false);
        			String folder = ((TextView) view.findViewById(R.id.folder_name)).getText().toString();
        			exercises1 = DataBaseProfessor.getInstance(
        					TeacherHomeActivity.this).getActivitiesByFolder(ActiveSession.getActiveLogin(), folder);

        	        ExerciseTeacherAdapterJSON adapter = new ExerciseTeacherAdapterJSON(getApplicationContext(),
        	                exercises1, activity);
        	        
        			listView.setAdapter(adapter);

        		} else {
            		final Dialog dialog = new Dialog(TeacherHomeActivity.this);
            		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            		dialog.setContentView(R.layout.dialog_yes_no_sentence);
            	    TextView tvMsgToShow =
            	            (TextView) dialog.findViewById(R.id.tvYesNoAlertDialog);
            	    tvMsgToShow.setText( R.string.confirmation_send );
            	
            	    Button btYes =
            	            (Button) dialog.findViewById(R.id.btYes);
            	    btYes.setOnClickListener(new OnClickListener() {
            	
            	        @Override
            	        public void onClick(final View v) {
            	            Toast.makeText(contx, R.string.sending, Toast.LENGTH_SHORT).show();
            	            Log.i("Enviando", exercises1.get(position));
            	            send(exercises1.get(position));
            	            dialog.dismiss();
            	        }
            	    });
            	
            	    Button btNo =
            	            (Button) dialog.findViewById(R.id.btNo);
            	    btNo.setOnClickListener(new OnClickListener() {
            	
            	        @Override
            	        public void onClick(final View v) {
            	            dialog.dismiss();
            	        }
            	    });
            	    dialog.show();
            	}
          	}
		});

        FoldersTeacherAdapter folderAdapter = new FoldersTeacherAdapter(getApplicationContext(), TeacherHomeActivity.this,DataBaseProfessor.getInstance(getApplicationContext()).getFoldersList(ActiveSession.getActiveLogin()));
        
		listView.setAdapter(folderAdapter);
		
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_sync);
	    tvMsgToShow =
	            (TextView) dialog.findViewById(R.id.tvYesNoAlertDialog);
	    tvMsgToShow.setText(getResources().getString(R.string.sync_init));

		
        /* Make all AllJoyn calls through a separate handler thread to prevent blocking the UI. */
        HandlerThread busThread = new HandlerThread("BusHandler");
        busThread.start();
        mBusHandler = new Handler(busThread.getLooper(),new BusHandlerCallback());

        mBusHandler.sendEmptyMessage(BusHandlerCallback.CONNECT);

	}

    public static void updateAdapter(){
        FoldersTeacherAdapter folderAdapter = new FoldersTeacherAdapter(contx, activity, DataBaseProfessor.getInstance(contx).getFoldersList(ActiveSession.getActiveLogin()));
		listView.setAdapter(folderAdapter);
		
		if (dialog.isShowing()){
			dialog.dismiss();
		}
    }
    
    public static void updateDialogMessage(String message){
	    tvMsgToShow =
	            (TextView) dialog.findViewById(R.id.tvYesNoAlertDialog);
	    tvMsgToShow.setText( message );
    }
    
    private Handler hToast = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "recebi do aluno", Toast.LENGTH_LONG).show();
            return true;
        }
    });
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.teacher_actions, menu);
		this.menu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.new_exercise:
            Intent chooseModelIntent = new Intent(getApplicationContext(),
                    ChooseModelActivity.class);
            startActivity(chooseModelIntent);
            break;
		case R.id.web_sync:
        	Sync s = new Sync();
        	s.setContext(getApplicationContext());
        	s.execute(URL_JSON);
        	dialog.show();
			break;
		case R.id.new_folder:
    		final Dialog dialog = new Dialog(TeacherHomeActivity.this);
    		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    		dialog.setContentView(R.layout.dialog_new_folder);
    		final EditText folderName = (EditText) dialog.findViewById(R.id.etNewFolderName);
    	
    	    Button btYes =
    	            (Button) dialog.findViewById(R.id.btOk);
    	    btYes.setOnClickListener(new OnClickListener() {
    	
    	        @Override
    	        public void onClick(final View v) {
    				DataBaseProfessor.getInstance(getApplicationContext()).addActivity(ActiveSession.getActiveLogin(), folderName.getText().toString(), 
    						null, null, null);
    		        FoldersTeacherAdapter folderAdapter = new FoldersTeacherAdapter(getApplicationContext(), activity, DataBaseProfessor.getInstance(getApplicationContext()).getFoldersList(ActiveSession.getActiveLogin()));
    				listView.setAdapter(folderAdapter);
    				dialog.dismiss();
    	        }
    	    });
    	
    	    Button btNo =
    	            (Button) dialog.findViewById(R.id.btCancel);
    	    btNo.setOnClickListener(new OnClickListener() {
    	
    	        @Override
    	        public void onClick(final View v) {
    	            dialog.dismiss();
    	        }
    	    });
    	    dialog.show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	@Override
	public void onBackPressed() {
		if (intoFolder) {
	        FoldersTeacherAdapter folderAdapter = new FoldersTeacherAdapter(getApplicationContext(), activity, DataBaseProfessor.getInstance(getApplicationContext()).getFoldersList(ActiveSession.getActiveLogin()));
			listView.setAdapter(folderAdapter);
			menu.findItem(R.id.web_sync).setVisible(true);
			menu.findItem(R.id.new_folder).setVisible(true);
			intoFolder = false;
		} else {
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}
	
	private void send(String json) {
		String senderName = "PROFESSOR";
		String message = json;
		Log.v("SEND", message);
		Message msg = mBusHandler.obtainMessage(BusHandlerCallback.CHAT,
				new PingInfo(senderName, message));
		
		mBusHandler.sendMessage(msg);
		
		Toast.makeText(contx, R.string.fileSent, Toast.LENGTH_SHORT).show();
	}
	
	/////////////////////////////////////////////////////////
	/* CHAT */
	/////////////////////////////////////////////////////////
	
    /* Load the native alljoyn_java library. */
    static {
        System.loadLibrary("alljoyn_java");
    }
//    private List<CharSequence> exercise;
    private static final String TAG = "SessionlessChat";

    /* Handler for UI messages
     * This handler updates the UI depending on the message received.
     */
    private static final int MESSAGE_CHAT = 1;
    private static final int MESSAGE_POST_TOAST = 2;
    private ArrayAdapter<String> mListViewArrayAdapter;

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
//                mListViewArrayAdapter.add(ping);
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

    /* Handler used to make calls to AllJoyn methods. See onCreate(). */
    private Handler mBusHandler;

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
            if (!message.isEmpty() && !senderName.equalsIgnoreCase("Professor")){
            	DataBaseProfessor.getInstance(getApplicationContext()).addReport(senderName, message);
				Message msg = new Message();
				hToast.sendMessage(msg);
            }

//            Log.i(TAG, "Signal  : " + senderName +": "+ message);
//            sendUiMessage(MESSAGE_CHAT, senderName + ": "+ message);
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
                	Log.e(".registerBus()", status+"");
                    return false;
                }

                /*
                 * Connect the BusAttachment to the bus.
                 */
                status = mBus.connect();
                Log.e("BusAttachment.connect()", status+"");
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
                	Log.e("ChatInterface.Chat()", ex+"");
                }
                break;
            }
            default:
                break;
            }
            return true;
        }
    }

//    private void logStatus(String msg, Status status) {
//        String log = String.format("%s: %s", msg, status);
//        if (status == Status.OK) {
//            Log.i(TAG, log);
//        } else {
//            Message toastMsg = mHandler.obtainMessage(MESSAGE_POST_TOAST, log);
//            mHandler.sendMessage(toastMsg);
//            Log.e(TAG, log);
//        }
//    }
//    private void logException(String msg, BusException ex) {
//        String log = String.format("%s: %s", msg, ex);
//        Message toastMsg = mHandler.obtainMessage(MESSAGE_POST_TOAST, log);
//        mHandler.sendMessage(toastMsg);
//        Log.e(TAG, log, ex);
//    }
//
	
	
}
