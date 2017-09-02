package bas.com.curve.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {

	private static final int bytesPerFloat = 4;

	private BufferUtils() {}
	
	public static IntBuffer createIntBuffer(int length){
		
		IntBuffer buf = ByteBuffer.allocateDirect(length * bytesPerFloat).order(ByteOrder.nativeOrder()).asIntBuffer();
		return buf;
	}
	
	public static FloatBuffer createFloatBuffer(int length){
		
		FloatBuffer buf = ByteBuffer.allocateDirect(length * bytesPerFloat).order(ByteOrder.nativeOrder()).asFloatBuffer();
		return buf;
	}	
}