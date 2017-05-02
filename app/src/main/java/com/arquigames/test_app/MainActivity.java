package com.arquigames.test_app;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.arquigames.test_app.R;
import com.arquigames.test_app.application.comp.touches.TouchControlView;
import com.arquigames.test_app.application.utils.GameScreen;

public class MainActivity extends Activity {

    private GLSurfaceView mGLView;
    private ViewGroup hiddenPanel;
    private GameScreen gameScreen;
    private TouchControlView touchControlView;
    private MyGLSurfaceView myGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.gameScreen = GameScreen.getInstance(this);
        touchControlView = (TouchControlView) this.findViewById(R.id.touch_control_view);
        if (touchControlView != null) {
            touchControlView.configure(this);
        }
        this.myGLSurfaceView = (MyGLSurfaceView) this.findViewById(R.id.myGLSurfaceView);

        this.hiddenPanel = (ViewGroup)findViewById(R.id.hidden_panel);
        if(this.hiddenPanel!=null){
            this.hiddenPanel.setVisibility(View.GONE);
        }
    }
    public void slideUpDownSettings() {
        if(!isPanelShown()) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.settings_bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
        }
        else {
            // Hide the Panel
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.settings_bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            hiddenPanel.setVisibility(View.GONE);
        }
    }
    public boolean isPanelShown(){
        return hiddenPanel.getVisibility() == View.VISIBLE;
    }
    public void showSettings(View view){
        if(view.getId()==R.id.settings_btn_open){
            this.slideUpDownSettings();
        }else if(view.getId()==R.id.settings_btn_hide){
            this.slideUpDownSettings();
        }else{
            //TODO
        }
        if(this.myGLSurfaceView!=null){
            //this.myGLSurfaceView.requestRender();
        }
    }
}
