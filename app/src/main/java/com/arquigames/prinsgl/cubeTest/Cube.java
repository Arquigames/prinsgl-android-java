package com.arquigames.prinsgl.cubeTest;

/**
 * Created by usuario on 06/08/2016.
 */
import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * A shaded cube rendered in OpenGL ES 2.0
 */
public class Cube {

    private static final String TAG = "Cube";

    private String VERTEX_SHADER_CODE =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec4 vColor;" +
                    "varying vec4 aColor;" +
                    "void main() {" +
                    "aColor = vColor;" +
                    // The matrix must be included as a modifier of gl_Position.
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private String FRAGMENT_SHADER_CODE =
            "precision mediump float;" +
                    "varying vec4 aColor;" +
                    "void main() {" +
                    "  gl_FragColor = aColor;" +
                    "}";

    private int BYTES_PER_FLOAT = 4;
    private int BYTES_PER_SHORT = 2;

    // Number of coordinates per vertex in this array
    private int COORDS_PER_VERTEX = 3;
    private int VERTEX_STRIDE = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private int COORDS_PER_COLORS = 4;
    private int COLORS_STRIDE = COORDS_PER_COLORS * 4; // 4 bytes per vertex

    private float VERTICES[] = {
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
    };

    private float COLORS1[] = {
            0, 0, 0, 1.0f,
            1.0f, 0, 0, 1.0f,
            1.0f, 1.0f, 0, 1.0f,
            0, 1.0f, 0, 1.0f,
            0, 0, 1.0f, 1.0f,
            1.0f, 0, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            0, 1.0f, 1.0f, 1.0f,
    };

    private float COLORS2[] = {
            0, 0, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            0, 0, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
    };

    private short INDICES[] = {
            0, 4, 5, 0, 5, 1,
            1, 5, 6, 1, 6, 2,
            2, 6, 7, 2, 7, 3,
            3, 7, 4, 3, 4, 0,
            4, 7, 6, 4, 6, 5,
            3, 0, 1, 3, 1, 2
    };

    private int mProgram;
    private int mPositionHandle;
    private int mColor;
    private int mMVPMatrixHandle;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mColor1Buffer;
    private FloatBuffer mColor2Buffer;
    private ShortBuffer mIndexBuffer;

    public Cube() {
        // Prepare shaders and OpenGL program
        int vertexShader = loadShader(
                GLES20.GL_VERTEX_SHADER,
                VERTEX_SHADER_CODE);
        if (vertexShader == 0) {
            Log.e(TAG, "Vertex shader failed");
            return;
        }
        int fragmentShader = loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                FRAGMENT_SHADER_CODE);
        if (fragmentShader == 0) {
            Log.e(TAG, "Fragment shader failed");
            return;
        }

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables

        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GLES20.GL_TRUE) {
            Log.e(TAG, "Could not link program: ");
            Log.e(TAG, GLES20.glGetProgramInfoLog(mProgram));
            GLES20.glDeleteProgram(mProgram);
            mProgram = 0;
            return;
        }
        GLES20.glDeleteShader(vertexShader);
        GLES20.glDeleteShader(fragmentShader);

        // Initialize vertex byte buffer for shape coordinates
        mVertexBuffer = allocateFloatBuffer(VERTICES);

        // Initialize byte buffer for the colors
        mColor1Buffer = allocateFloatBuffer(COLORS1);
        mColor2Buffer = allocateFloatBuffer(COLORS2);

        // Initialize byte buffer for the draw list
        mIndexBuffer = allocateShortBuffer(INDICES);
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     *                  this shape.
     */
    public void draw(float[] mvpMatrix, boolean changeColor) {
        if (mProgram != 0) {
            // Add program to OpenGL environment
            GLES20.glUseProgram(mProgram);

            // Get handle to vertex shader's vPosition member
            mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(mPositionHandle);

            // Get handle to fragment shader's vColor member
            mColor = GLES20.glGetAttribLocation(mProgram, "vColor");

            // Enable a handle to the color vertices
            GLES20.glEnableVertexAttribArray(mColor);

            // Get handle to shape's transformation matrix
            mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
            checkGlError("glGetUniformLocation");

            // Apply the projection and view transformation
            GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
            checkGlError("glUniformMatrix4fv");

            // Prepare the coordinate data
            GLES20.glVertexAttribPointer(
                    mPositionHandle, COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT, false,
                    VERTEX_STRIDE, mVertexBuffer);

            // Prepare the color data
            if (changeColor) {
                GLES20.glVertexAttribPointer(
                        mColor, COORDS_PER_COLORS,
                        GLES20.GL_FLOAT, false,
                        COLORS_STRIDE, mColor2Buffer);
            } else {
                GLES20.glVertexAttribPointer(
                        mColor, COORDS_PER_COLORS,
                        GLES20.GL_FLOAT, false,
                        COLORS_STRIDE, mColor1Buffer);
            }

            // Draw the shape
            GLES20.glDrawElements(
                    GLES20.GL_TRIANGLES, INDICES.length,
                    GLES20.GL_UNSIGNED_SHORT, mIndexBuffer);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(mPositionHandle);
            // Disable color array
            GLES20.glDisableVertexAttribArray(mColor);
        }
    }

    /**
     * Utility method for compiling a OpenGL shader.
     *
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type       - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an mId for the shader.
     */
    public static int loadShader(int type, String shaderCode) {

        // Create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        checkGlError("glCreateShader type=" + type);

        // Add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e(TAG, "Could not compile shader " + type + ":");
            Log.e(TAG, " " + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            shader = 0;
        }

        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColor = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error = GLES20.glGetError();
        if (error != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    /**
     * Creates a direct float buffer, and copy coords into it.
     *
     * @param coords - data to be copied.
     */
    public FloatBuffer allocateFloatBuffer(float[] coords) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(coords.length * this.BYTES_PER_FLOAT);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(coords);
        floatBuffer.position(0);
        return floatBuffer;
    }

    /**
     * Creates a direct short buffer, and copy coords into it.
     *
     * @param coords - data to be copied.
     */
    public ShortBuffer allocateShortBuffer(short[] coords) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(coords.length * this.BYTES_PER_SHORT);
        byteBuffer.order(ByteOrder.nativeOrder());
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        shortBuffer.put(coords);
        shortBuffer.position(0);
        return shortBuffer;
    }

}