package bas.com.curve.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import bas.com.curve.Main;
import bas.com.curve.game.objects.GameObjectManager;
import bas.com.curve.shaders.ShaderManager;
import bas.com.curve.texture.TextureManager;

import static android.opengl.GLES30.*;

public class OpenGLRenderer implements Renderer {

	private static boolean isCreated = false;
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		System.out.println("Starting OpenGL render thread: " + Thread.currentThread().getId());
		
		// Set the clear color
		glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		
		// Enable OpenGL stuff
		glEnable(GL_DEPTH_TEST);
		
		// Create shaders and load textures
		System.out.println("Constructing shaders");
		ShaderManager.constructShaders(Main.getContext());
		
		System.out.println("Loading data");
		TextureManager.loadTextures(Main.getContext());
		
		// Signal that the GLSurfaceView is created
		isCreated = true;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		// Change the viewport
		glViewport(0, 0, width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		
		// Clear the default framebuffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		// Render all game objects
		GameObjectManager.render();
	}

	public static boolean isOpenGLRendererCreated()
	{
		return isCreated;
	}
}