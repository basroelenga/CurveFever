package bas.com.curve.utils;

import bas.com.curve.shaders.Shader;
import bas.com.curve.shapes.Quad;
import bas.com.curve.texture.Texture;

import static android.opengl.GLES30.*;

public class DrawShapes {

	private DrawShapes() {}
	
	public static void drawQuad(Quad quad, Shader shader, Texture texture)
	{
		
		shader.bind();
		
		glBindVertexArray(quad.getVaoID());
		
		glEnableVertexAttribArray(0);
		
		glDrawArrays(GL_TRIANGLES, 0, 6);
		
		glDisableVertexAttribArray(0);
		
		glBindVertexArray(0);
		
		shader.unbind();
	}
}