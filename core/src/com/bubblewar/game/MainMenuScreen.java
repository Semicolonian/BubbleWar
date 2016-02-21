package com.bubblewar.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
import com.bubblewar.game.HexagonTile.TileType;
import com.bubblewar.game.Assets;

public class MainMenuScreen extends ScreenAdapter {
	BubbleWar game;
	OrthographicCamera guiCam;
	Circle mapEditorButtonBounds;
	Circle newGameButton;
	Circle loadGameButton;
	Circle optionsButton;
	Circle mapEditorButton;
	Circle exitButton;
	Vector3 touchPoint;
	HexagonMap hexagonMap;
	public static Color GREY;
	

	public MainMenuScreen (BubbleWar game) {
		this.game = game;

		guiCam = new OrthographicCamera(1280, 720);
		guiCam.position.set(1280 / 2, 720 / 2, 0);
		touchPoint = new Vector3();
		
		//folgende hexagontiles müssten eig nur kreise für bounds sein 
		newGameButton = new Circle(400,(float)(360 +( (Math.sqrt(3)*0.5)*160)),160);
		loadGameButton = new Circle(640,360,160);
		optionsButton = new Circle(880,(float)(360 +( (Math.sqrt(3)*0.5)*160)),160);
		mapEditorButton = new Circle(400,(float)(360 -( (Math.sqrt(3)*0.5)*160)),150);
		exitButton = new Circle(880,(float)(360 -( (Math.sqrt(3)*0.5)*160)),160);
		hexagonMap = new HexagonMap(3, 2, 400, (float)(360 +( (Math.sqrt(3)*0.5)*160)), 160,TileType.LAND);
	}

	public void update () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (newGameButton.contains(touchPoint.x, touchPoint.y)) {
				game.setScreen(new GameScreen(game));
				return;
			}
			if (loadGameButton.contains(touchPoint.x, touchPoint.y)) {

				return;
			}
			if (optionsButton.contains(touchPoint.x, touchPoint.y)) {

				return;
			}
			if (mapEditorButton.contains(touchPoint.x, touchPoint.y)) {
				game.setScreen(new MapEditorScreen(game));
				return;
			}
			if (exitButton.contains(touchPoint.x, touchPoint.y)) {

				return;
			}
			
		}
	}

	public void draw () {
		
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		guiCam.update();
		
		//Background
		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegion, 0, 0, 1280, 720);
		game.batcher.end();
		
		//New Game Button
		game.shapeRenderer.setProjectionMatrix(guiCam.combined);
		
		game.shapeRenderer.begin(ShapeType.Filled);
		game.shapeRenderer.setColor(1, 1, 2, 1);
		for(int i=0; i<=hexagonMap.index-1; i++){
			for(int j=0; j<5; j++){
				game.shapeRenderer.triangle(hexagonMap.Map[i].position.x, hexagonMap.Map[i].position.y, hexagonMap.Map[i].vertices[j].x,  hexagonMap.Map[i].vertices[j].y,  hexagonMap.Map[i].vertices[j+1].x,  hexagonMap.Map[i].vertices[j+1].y);
			}
			game.shapeRenderer.triangle(hexagonMap.Map[i].position.x, hexagonMap.Map[i].position.y, hexagonMap.Map[i].vertices[5].x,  hexagonMap.Map[i].vertices[5].y,  hexagonMap.Map[i].vertices[0].x,  hexagonMap.Map[i].vertices[0].y);
		}
		game.shapeRenderer.end();
		
		// Button Texts
		game.batcher.setProjectionMatrix(guiCam.combined);
		
		game.batcher.enableBlending();
		game.batcher.begin();
		Assets.font.draw(game.batcher, "New Game", hexagonMap.Map[0].position.x-Assets.font.getBounds("New Game").width*0.5f , hexagonMap.Map[0].position.y );
		Assets.font.draw(game.batcher, "Load Game", hexagonMap.Map[2].position.x-Assets.font.getBounds("Load Game").width*0.5f , hexagonMap.Map[2].position.y);
		Assets.font.draw(game.batcher, "Options", hexagonMap.Map[3].position.x-Assets.font.getBounds("Options").width*0.5f , hexagonMap.Map[3].position.y);
		Assets.font.draw(game.batcher, "Editor", hexagonMap.Map[1].position.x-Assets.font.getBounds("Editor").width*0.5f , hexagonMap.Map[1].position.y);
		Assets.font.draw(game.batcher, "Information", hexagonMap.Map[4].position.x-Assets.font.getBounds("Information").width*0.5f , hexagonMap.Map[4].position.y);
		game.batcher.end();
		
		 

	}

	@Override
	public void render (float delta) {
		update();
		draw();
	}

	@Override
	public void pause () {
	}
}
