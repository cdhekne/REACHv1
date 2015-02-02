package asu.reach;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;


public class STOP extends Activity implements View.OnClickListener{

    private ImageButton respond,back,next,done,cancel,clear,voice;
    private LinearLayout nav,respBtns;
    private RelativeLayout blob,resp;
    private ImageView s,t,o,p,message;
    private EditText response;
    private int state = 0;
    private final int S_STATE = 0;
    private final int T_STATE = 1;
    private final int O_STATE = 2;
    private final int P_STATE = 3;
    private static final int SPEECH_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_stop);

        respond = (ImageButton)findViewById(R.id.doneBtn);
        back = (ImageButton)findViewById(R.id.backBtn);
        next = (ImageButton)findViewById(R.id.nextBtn);
        s = (ImageView)findViewById(R.id.sView);
        t = (ImageView)findViewById(R.id.tView);
        o = (ImageView)findViewById(R.id.oView);
        p = (ImageView)findViewById(R.id.pView);
        message = (ImageView)findViewById(R.id.messageView);
        response = (EditText)findViewById(R.id.responseTxt);
        done = (ImageButton)findViewById(R.id.doneBtn);
        cancel = (ImageButton)findViewById(R.id.cancelBtn);
        clear = (ImageButton)findViewById(R.id.clearBtn);
        voice = (ImageButton)findViewById(R.id.voiceBtn);
        nav = (LinearLayout)findViewById(R.id.navLayout);
        respBtns = (LinearLayout)findViewById(R.id.respBtnLayout);
        resp = (RelativeLayout)findViewById(R.id.respLayout);
        blob = (RelativeLayout)findViewById(R.id.blobLayout);

        response.setTypeface(Typeface.createFromAsset(getAssets(), "agentorange.ttf"));
        respond.setOnClickListener(this);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        done.setOnClickListener(this);
        cancel.setOnClickListener(this);
        clear.setOnClickListener(this);
        voice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == respond.getId()){
            respond.setActivated(true);
            nav.setVisibility(View.GONE);
            blob.setVisibility(View.GONE);
            resp.setVisibility(View.VISIBLE);
            respBtns.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
        }
        if(v.getId() == done.getId()){
            nav.setVisibility(View.VISIBLE);
            blob.setVisibility(View.VISIBLE);
            resp.setVisibility(View.GONE);
            respBtns.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        }
        if(v.getId() == cancel.getId()){
            nav.setVisibility(View.VISIBLE);
            blob.setVisibility(View.VISIBLE);
            resp.setVisibility(View.GONE);
            respBtns.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        }
        if(v.getId() == clear.getId()){
            response.setText("");
        }
        if(v.getId() == voice.getId()){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }
        if(v.getId() == next.getId()){
            switch(state){
                case S_STATE:{
                    if(respond.isActivated()) {
                        s.setBackgroundResource(R.drawable.s_white);
                        t.setBackgroundResource(R.drawable.t_yellow);
                        message.setBackgroundResource(R.drawable.t_message);
                        respond.setActivated(false);
                        state = T_STATE;
                    }else{
                        Toast.makeText(this, "Please respond first", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case T_STATE:{
                    if(respond.isActivated()) {
                        t.setBackgroundResource(R.drawable.t_white);
                        o.setBackgroundResource(R.drawable.o_yellow);
                        message.setBackgroundResource(R.drawable.o_message);
                        respond.setActivated(false);
                        state=O_STATE;
                    }else{
                        Toast.makeText(this, "Please respond first", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case O_STATE:{
                    if(respond.isActivated()) {
                        o.setBackgroundResource(R.drawable.o_white);
                        p.setBackgroundResource(R.drawable.p_yellow);
                        message.setBackgroundResource(R.drawable.p_message);
                        respond.setActivated(false);
                        state=P_STATE;
                    }else{
                        Toast.makeText(this, "Please respond first", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case P_STATE:{
                    finish();
                    break;
                }
            }
        }
        if(v.getId() == back.getId()){
            switch(state){
                case S_STATE:{
                    finish();
                    break;
                }
                case T_STATE:{
                    t.setBackgroundResource(R.drawable.t_white);
                    s.setBackgroundResource(R.drawable.s_yellow);
                    message.setBackgroundResource(R.drawable.s_message);
                    respond.setActivated(true);
                    state=S_STATE;
                    break;
                }
                case O_STATE:{
                    o.setBackgroundResource(R.drawable.o_white);
                    t.setBackgroundResource(R.drawable.t_yellow);
                    message.setBackgroundResource(R.drawable.t_message);
                    respond.setActivated(true);
                    state=T_STATE;
                    break;
                }
                case P_STATE:{
                    p.setBackgroundResource(R.drawable.p_white);
                    o.setBackgroundResource(R.drawable.o_yellow);
                    message.setBackgroundResource(R.drawable.o_message);
                    respond.setActivated(true);
                    state=O_STATE;
                    break;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            response.setText(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}