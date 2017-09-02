package bas.com.curve.game;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.Display;
import android.view.WindowManager;
import bas.com.curve.game.objects.GameObjectManager;
import bas.com.curve.renderer.OpenGLRenderer;

public class Game {

	private Context context;
	private Activity activity;
	
	private GLSurfaceView view;
	
	private boolean isRunning = false;
	
	private boolean vSync = true;
	
	private long timeStart;
	private long timeEnd;
	
	private double nanoToSecond = 1000000000d;
	private double secondToMilli = 1000d;

	private float refreshRate;
	
	public Game(Context context, Activity activity, GLSurfaceView view)
	{
		
		this.context = context;
		this.activity = activity;
		
		this.view = view;
		
		loop();
	}
	
	private void loop()
	{
		
		// The loop will sync to the refresh rate of the screen
		Display display = ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		refreshRate = display.getRefreshRate();
		
		// The loop cannot start before the GLSurfaceView is created
		waitForRenderThread();
	
		System.out.println("Starting game loop");
		isRunning = true;
		
		while(isRunning)
		{
			
			// Start time of iteration
			timeStart = System.nanoTime();
			
			// Updating all logic
			GameObjectManager.update();
			
			// After the update request a render
			view.requestRender();
			
			// End time of iteration
			timeEnd = System.nanoTime();
			
			// Wait for sync
			if((timeEnd - timeStart) / nanoToSecond < (1 / refreshRate) && vSync){
				try 
				{
					
					Thread.sleep((long) (((1 / refreshRate) - ((timeEnd - timeStart) / nanoToSecond)) * secondToMilli));
				} catch (InterruptedException e) 
				{
					
					e.printStackTrace();
				}
			}
		}
		
		// Call the onDestroy method
		activity.finish();
	}
	
	private void waitForRenderThread()
	{
		
		while(!OpenGLRenderer.isOpenGLRendererCreated())
		{
			
			try 
			{
				
				// Wait 10 ms for trying again
				Thread.sleep(10);
			} catch (InterruptedException e) 
			{
				
				e.printStackTrace();
			}
		}
		
		System.out.println("Threads synced");
	}
}