package bas.com.curve.game.objects;

import java.util.ArrayList;

public class GameObjectManager {

	private static ArrayList<GameObjects> gameObjectList = new ArrayList<GameObjects>();
	
	private GameObjectManager() {}
	
	public static void update() {for(GameObjects obj : gameObjectList) obj.update();}
	public static void render() {for(GameObjects obj : gameObjectList) obj.render();}
}