package bas.com.curve;

// Game: CurveFever
// Developed by: Bas Roelenga

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import bas.com.curve.game.Game;
import bas.com.curve.renderer.OpenGLRenderer;
import bas.com.curve.shaders.ShaderManager;

public class Main extends Activity {

	private Context context;
	private Activity activity;
	
	private GLSurfaceView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		System.out.println("=========================================");
		
		// Assign the context and activity to an object holder
		context = this;
		activity = this;
		
		// Check for OpenGL compatibility and construct the GLSurfaceView
		if(isOpenGLCompatible())
		{
			
			System.out.println("This device supports OpenGLES 3.0");
			
			// Create the view
			view = new GLSurfaceView(this);
			view.setPreserveEGLContextOnPause(true);
			
			// Set it to the correct version
			view.setEGLContextClientVersion(3);
			
			// Set the OpenGL renderer
			view.setRenderer(new OpenGLRenderer());
			view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		}
		else
		{
			
			System.err.println("This device does not support OpenGLES 3.0");
			return;
		}
		
		// Set the view in fullscreen and in landscape mode
		setFullscreenAndLandscape();
		
		// Start the logic/game thread
		logic();
		
		// Set the GLSurfaceView as the default view
		setContentView(view);
	}
	
	private boolean isOpenGLCompatible()
	{
		
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		ConfigurationInfo info = am.getDeviceConfigurationInfo();
		
		boolean hasOpenGL = info.reqGlEsVersion >= 0x30000;
		return hasOpenGL;
	}
	
	private void setFullscreenAndLandscape()
	{
		
		view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | 
        		View.SYSTEM_UI_FLAG_FULLSCREEN |
        		View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
        		View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
        		View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
        		View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
	}
	
	private void logic()
	{
		
		Thread logicThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				System.out.println("Starting logic thread: " + Thread.currentThread().getId());
				new Game(context, activity, view);
			}
		});
		
		logicThread.setName("Logic Thread");
		logicThread.start();
	}
	
	@Override
	protected void onResume()
	{
		
		super.onResume();
		view.onResume();
		
		System.out.println("onResume called");
	}
	
	@Override
	protected void onPause()
	{
		
		super.onPause();
		view.onPause();
		
		System.out.println("onPause called");
	}
	
	@Override
	protected void onDestroy()
	{
		
		super.onDestroy();
		view.destroyDrawingCache();
		
		// Destroy shaders and fbo
		ShaderManager.destroy();
		
		System.out.println("onDestroy called");
	}
}