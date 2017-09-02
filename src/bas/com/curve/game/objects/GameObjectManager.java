package bas.com.curve.game.objects;

import java.util.ArrayList;

import bas.com.curve.shapes.Quad;

public class GameObjectManager {

	private static ArrayList<GameObjects> gameObjectList = new ArrayList<GameObjects>();
	
	private static Quad quad;
	
	private GameObjectManager() {}
	
	public static void update() {for(GameObjects obj : gameObjectList) obj.update();}
	public static void render() {for(GameObjects obj : gameObjectList) obj.render();}
	
	public static void createPrimitives()
	{
		
		quad = new Quad();
	}
	
	public static Quad getQuad()
	{
		return quad;
	}
}