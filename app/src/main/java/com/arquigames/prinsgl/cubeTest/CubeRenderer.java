package com.arquigames.prinsgl.cubeTest;

/**
 * Created by usuario on 06/08/2016.
 */
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Render a pair of tumbling cubes using OpenGL ES 2.0.
 */
public class CubeRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "CubeRenderer";

    private float ANGLE_INCREMENT = 1.2f;
    private boolean CALCULATE_FPS = false;

    private Cube mCube;
    private float mAngle;
    private boolean mChangeColor;
    private long mLastTime;
    private long mFpsCounter;

    protected float[] mMMatrix = new float[16];
    protected float[] mMVMatrix = new float[16];
    protected float[] mMVPMatrix = new float[16];
    protected float[] mProjectionMatrix = new float[16];
    protected float[] mViewMatrix = new float[16];
    protected float[] mRotationMatrix = new float[16];

    public void onDrawFrame(GL10 unused) {
        if (CALCULATE_FPS) {
            long currentTime = SystemClock.uptimeMillis();
            if (mLastTime == 0) {
                mLastTime = currentTime;
            } else {
                mFpsCounter++;
                long diffTime = currentTime - mLastTime;
                if (diffTime >= 1000) {
                    Log.d(TAG, "fps=" + mFpsCounter);
                    mFpsCounter = 0;
                    mLastTime = currentTime;
                }
            }
        }

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -10, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Configure matrices for first cube
        Matrix.setIdentityM(mMMatrix, 0);

        Matrix.translateM(mMMatrix, 0, 0.0f, -0.5f, -1.5f);

        Matrix.setRotateM(mRotationMatrix, 0, 2 * mAngle, 0.0f, 1.0f, 1.0f);
        Matrix.multiplyMM(mMMatrix, 0, mRotationMatrix, 0, mMMatrix, 0);

        Matrix.multiplyMM(mMVMatrix, 0, mViewMatrix, 0, mMMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVMatrix, 0);

        mCube.draw(mMVPMatrix, mChangeColor);

        // Configure matrices for second cube
        Matrix.setIdentityM(mMMatrix, 0);

        Matrix.translateM(mMMatrix, 0, 0.0f, 2.0f, 0.0f);

        Matrix.setRotateM(mRotationMatrix, 0, -mAngle, 0.0f, 1.0f, 1.0f);
        Matrix.multiplyMM(mMMatrix, 0, mRotationMatrix, 0, mMMatrix, 0);

        Matrix.multiplyMM(mMVMatrix, 0, mViewMatrix, 0, mMMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVMatrix, 0);

        mCube.draw(mMVPMatrix, mChangeColor);

        mAngle += ANGLE_INCREMENT;
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        float ratio = (float) width / height;
        GLES20.glViewport(0, 0, width, height);

        // Configure perspective with field of view
        float fov = 30.0f;
        float near = 1.0f;
        float far = 100.0f;
        float top = (float) Math.tan(fov * Math.PI / 360.0f) * near;
        float bottom = -top;
        float left = ratio * bottom;
        float right = ratio * top;
        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set background color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Depth handling
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);

        // Set anti-aliasing
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        // Important to initialize the graphics on the GL thread
        mCube = new Cube();
    }

    /**
     * Utility method to allow the user to change the cube color.
     */
    public void changeColor() {
        mChangeColor = !mChangeColor;
    }
}
