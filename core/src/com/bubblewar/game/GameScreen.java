package com.bubblewar.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubblewar.game.HexagonTile.TileType;
import com.bubblewar.game.Assets;

public class GameScreen extends ScreenAdapter {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	
	//private static final float WORLD_TO_SCREEN = 1.0f / 100.0f;
	private static final float SCENE_WIDTH = 1280f;
	private static final float SCENE_HEIGHT = 720f;
	
	private static final float CAMERA_SPEED = 400f;
	private static final float CAMERA_ZOOM_SPEED = 2.0f;
	private static final float CAMERA_ZOOM_MAX = 5.0f;
	private static final float CAMERA_ZOOM_MIN = 0.1f;
	private static final float CAMERA_MOVE_EDGE = 0.2f;
	

	BubbleWar game;

	int state;
	OrthographicCamera guiCam;
	private Viewport viewport;
	Vector3 touch;
	static public Army[] armys = new Army[3];
	public HexagonTile[] HQs = new HexagonTile[armys.length];
	HexagonMap hexagonMap;
	int armyCounter;
	

	public GameScreen (BubbleWar game) {
		this.game = game;

		state = GAME_READY;
		guiCam = new OrthographicCamera(SCENE_WIDTH, SCENE_HEIGHT);
		guiCam.position.set(SCENE_WIDTH * 0.5f, SCENE_HEIGHT * 0.5f, 0);
		viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, guiCam);
		touch = new Vector3();
		hexagonMap=MapEditorScreen.hexagonMap;
		armyCounter=0;
		
		
		for(int i=0; i<hexagonMap.index;i++){
			if(hexagonMap.Map[i].tileType==TileType.HQ){
				HQs[armyCounter]=hexagonMap.Map[i];
				if(armyCounter==0){
					armys[armyCounter]=new Army(hexagonMap.Map[i].position,Fraction.BLUE);
				}
				else if(armyCounter==1){
					armys[armyCounter]=new Army(hexagonMap.Map[i].position,Fraction.RED);
				}
				else if(armyCounter==2){
					armys[armyCounter]=new Army(hexagonMap.Map[i].position,Fraction.YELLOW);
				}
				
				armyCounter++;
			}
		}
		
	}

	public void update () {
		
		for(int i=0; i<armys.length;i++){
			if(armys[i].realArmySize>0){
				armys[i].updateArmy();
			}
			
		}
		
		
		
		
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touch.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			for(armyCounter=0;armyCounter<HQs.length;armyCounter++){
				if (HQs[armyCounter].bounds.contains(touch.x, touch.y)) {
					if (armyCounter == 0) {
						armys[armyCounter].units[0] = new Unit(
								armys[armyCounter].headquaterPosition.x,
								armys[armyCounter].headquaterPosition.y,
								10, armys[armyCounter].fraction);
						
						armys[armyCounter].units[0].Attack=6;
						armys[armyCounter].units[0].radius=40;
						armys[armyCounter].units[0].bounds=new Circle(armys[armyCounter].units[0].position, armys[armyCounter].units[0].radius);
						
						for(int i =0;i<armys[armyCounter].realArmySize;i++){
							
							armys[armyCounter].units[i].Attack=6;
							armys[armyCounter].units[i].radius=MathUtils.random(10, 50);
							armys[armyCounter].units[i].bounds=new Circle(armys[armyCounter].units[i].position, armys[armyCounter].units[i].radius);
						}
						
						
					} else if (armyCounter == 1) {
						armys[armyCounter].units[0] = new Unit(
								armys[armyCounter].headquaterPosition.x,
								armys[armyCounter].headquaterPosition.y,
								10, armys[armyCounter].fraction);
						
						armys[armyCounter].units[0].Attack=6;
						armys[armyCounter].units[0].radius=40;
						armys[armyCounter].units[0].bounds=new Circle(armys[armyCounter].units[0].position, armys[armyCounter].units[0].radius);
						
						for(int i =0;i<armys[armyCounter].realArmySize;i++){
							
							armys[armyCounter].units[i].Attack=6;
							armys[armyCounter].units[i].radius=MathUtils.random(10, 50);;
							armys[armyCounter].units[i].bounds=new Circle(armys[armyCounter].units[i].position, armys[armyCounter].units[i].radius);
						}
						
					} else if (armyCounter == 2) {
						armys[armyCounter].units[0] = new Unit(
								armys[armyCounter].headquaterPosition.x,
								armys[armyCounter].headquaterPosition.y,
								10, armys[armyCounter].fraction);
						
						armys[armyCounter].units[0].Attack=6;
						armys[armyCounter].units[0].radius=40;
						armys[armyCounter].units[0].bounds=new Circle(armys[armyCounter].units[0].position, armys[armyCounter].units[0].radius);
						
						for(int i =0;i<armys[armyCounter].realArmySize;i++){
							
							armys[armyCounter].units[i].Attack=6;
							armys[armyCounter].units[i].radius=MathUtils.random(10, 50);;
							armys[armyCounter].units[i].bounds=new Circle(armys[armyCounter].units[i].position, armys[armyCounter].units[i].radius);
						}
						
					}
				}
			}
		}
		
		for(int k=0; k<armys.length;k++){
			for(int j=0; j<armys[k].realArmySize;j++){
				for(int i=0; i<=hexagonMap.lineIndex;i=i+2){
					if(hexagonMap.mapBounds[i]!=null||hexagonMap.mapBounds[i+1]!=null){
						if(armys[k].units[j]==null){
							
						}
						else if(Intersector.intersectSegmentCircle(hexagonMap.mapBounds[i], hexagonMap.mapBounds[i+1], armys[k].units[j].position, armys[k].units[j].radius*armys[k].units[j].radius)){
							armys[k].units[j].handleCollisionMap(hexagonMap.mapBounds[i], hexagonMap.mapBounds[i+1]);
						}
					}
				}
			}
		}
		
		//remove glitching balls
		for(int k=0; k<armys.length;k++){
			for(int j=0; j<armys[k].realArmySize;j++){
				for(int i=0; i<=hexagonMap.lineIndex;i=i+2){
					if(hexagonMap.mapBounds[i]!=null||hexagonMap.mapBounds[i+1]!=null){
						if(armys[k].units[j]==null){
							
						}
						else if(Intersector.distanceSegmentPoint(hexagonMap.mapBounds[i], hexagonMap.mapBounds[i+1], armys[k].units[j].position)<armys[k].units[j].radius*0.9){
							armys[k].units[j].position=armys[k].headquaterPosition.cpy();
							
							
//							armys[k].units[j]=armys[k].units[armys[k].realArmySize-1];
//							armys[k].units[armys[k].realArmySize-1]=null;
//							armys[k].realArmySize--;
						}
					}
				}
			}
		}
	
		for(int k=0; k<armys.length;k++){
			for(int j=k;j<armys.length;j++){//evtl k durch 0 ersetzen
				for(int i =0; i<armys[k].realArmySize;i++){
					for(int q=0; q<armys[j].realArmySize;q++){

						if(k==j&&i==q){
							
						}
						else if(armys[j].units[q]==null||armys[k].units[i]==null){
							
						}
						else if(armys[j].units[q].bounds.overlaps(armys[k].units[i].bounds)){
							if(armys[k].units[i].fraction.equals( armys[j].units[q].fraction)){
								
//								Vector2 positionTemp=new Vector2(armys[k].units[i].position);
//								Vector2 speedTemp=new Vector2(armys[k].units[i].speed);
//								
//								armys[k].units[i].handleCollisionFriend( armys[j].units[q].position,armys[j].units[q].speed  );//position muss erst gespeichert werden sonst prallt die 2. kugel falsch ab
//								armys[j].units[q].handleCollisionFriend( positionTemp, speedTemp);
							}
							else{
								
								armys[k].units[i].fight(armys[j].units[q]);
								
								if(armys[k].units[i].currentHP<=0){
									if(armys[j].units[q].currentHP>0){
										armys[j].units[q].levelUP(armys[k].units[i]);
									}
	
								}
								if(armys[j].units[q].currentHP<=0){
									
									if(armys[k].units[i].currentHP>0){
										armys[k].units[i].levelUP(armys[j].units[q]);
									}
									
									armys[j].units[q]=armys[j].units[armys[j].realArmySize-1];
									armys[j].units[armys[j].realArmySize-1]=null;
									armys[j].realArmySize--;

								}
								
								if(armys[k].units[i].currentHP<=0){
									armys[k].units[i]=armys[k].units[armys[k].realArmySize-1];
									armys[k].units[armys[k].realArmySize-1]=null;
									armys[k].realArmySize--;
								}
								
								if(armys[j].units[q]==null||armys[k].units[i]==null){
									
								}
								else if(armys[j].units[q].currentHP>0&&armys[k].units[i].currentHP>0){
									
									Vector2 positionTemp=new Vector2(armys[k].units[i].position);
									
									armys[k].units[i].handleCollisionEnemy( armys[j].units[q].position);//position muss erst gespeichert werden sonst prallt die 2. kugel falsch ab
									armys[j].units[q].handleCollisionEnemy(positionTemp);
								}
									
							}
							
						}
					}
				}
			}
		}


	}


	public void draw () {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		// Arrow keys move the camera
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			guiCam.position.x -= CAMERA_SPEED * deltaTime;
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			guiCam.position.x += CAMERA_SPEED * deltaTime;
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			guiCam.position.y += CAMERA_SPEED * deltaTime;
		}
		else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			guiCam.position.y -= CAMERA_SPEED * deltaTime;
		}
		
		// Touching on the edges also moves the camera
//		if (Gdx.input.isTouched()) {
//			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0.0f);
//			guiCam.unproject(touch);
//			
//			if (touch.x > SCENE_WIDTH * (1.0f - CAMERA_MOVE_EDGE)) {
//				guiCam.position.x += CAMERA_SPEED * deltaTime;
//			}
//			else if (touch.x < SCENE_WIDTH * CAMERA_MOVE_EDGE) {
//				guiCam.position.x -= CAMERA_SPEED * deltaTime;
//			}
//			
//			if (touch.y > SCENE_HEIGHT * (1.0f - CAMERA_MOVE_EDGE)) {
//				guiCam.position.y += CAMERA_SPEED * deltaTime;
//			}
//			else if (touch.y < SCENE_HEIGHT * CAMERA_MOVE_EDGE) {
//				guiCam.position.y -= CAMERA_SPEED * deltaTime;
//			}
//		}
		
		// Page up/down control the zoom
		if (Gdx.input.isKeyPressed(Keys.PAGE_UP)) {
			guiCam.zoom -= CAMERA_ZOOM_SPEED * deltaTime;
		}
		else if (Gdx.input.isKeyPressed(Keys.PAGE_DOWN)) {
			guiCam.zoom += CAMERA_ZOOM_SPEED * deltaTime;
		}
		
		//Clamp position
//		float halfWidth = SCENE_WIDTH * 0.5f;
//		float halfHeight = SCENE_HEIGHT * 0.5f; 
//		
//		guiCam.position.x = MathUtils.clamp(guiCam.position.x, 
//											halfWidth * guiCam.zoom,
//											SCENE_WIDTH - halfWidth * guiCam.zoom);
//		guiCam.position.y = MathUtils.clamp(guiCam.position.y,
//											halfHeight * guiCam.zoom,
//											SCENE_HEIGHT - halfHeight * guiCam.zoom);
//		
		// Clamp zoom
		guiCam.zoom = MathUtils.clamp(guiCam.zoom, CAMERA_ZOOM_MIN, CAMERA_ZOOM_MAX);
		
		
		guiCam.update();
		game.batcher.setProjectionMatrix(guiCam.combined);
		
		//Draw Filled HexagonMap
		game.batcher.enableBlending();
		game.batcher.begin();
		
		
		float rad=hexagonMap.hexRadius;
		for(int i=0; i<=hexagonMap.index-1; i++){
				
			
			if(hexagonMap.Map[i].tileType==TileType.LAND){
				game.batcher.draw(Assets.hexagonlandRegion, hexagonMap.Map[i].position.x-rad, hexagonMap.Map[i].position.y-(float)(rad*Math.sqrt(3)*0.5), 2*rad, (float)(rad*Math.sqrt(3)));
			}
			else if(hexagonMap.Map[i].tileType==TileType.NOLAND){
				game.batcher.draw(Assets.hexagonnolandRegion,  hexagonMap.Map[i].position.x-rad, hexagonMap.Map[i].position.y-(float)(rad*Math.sqrt(3)*0.5), 2*rad, (float)(rad*Math.sqrt(3)));
			}
			else if(hexagonMap.Map[i].tileType==TileType.HQ){
				game.batcher.draw(Assets.hqRegion,  hexagonMap.Map[i].position.x-rad, hexagonMap.Map[i].position.y-(float)(rad*Math.sqrt(3)*0.5), 2*rad, (float)(rad*Math.sqrt(3)));
			}
//				game.shapeRenderer.triangle(hexagonMap.Map[i].x, hexagonMap.Map[i].y, hexagonMap.Map[i].vertices[j].x,  hexagonMap.Map[i].vertices[j].y,  hexagonMap.Map[i].vertices[j+1].x,  hexagonMap.Map[i].vertices[j+1].y);
//			}
//			game.shapeRenderer.triangle(hexagonMap.Map[i].x, hexagonMap.Map[i].y, hexagonMap.Map[i].vertices[5].x,  hexagonMap.Map[i].vertices[5].y,  hexagonMap.Map[i].vertices[0].x,  hexagonMap.Map[i].vertices[0].y);
		}
		game.batcher.end();
		
		
		
		guiCam.update();
		game.shapeRenderer.setProjectionMatrix(guiCam.combined);
		
		
		//Draw Map Bounds
		
		game.shapeRenderer.begin(ShapeType.Line);
		game.shapeRenderer.setColor(1,1,1, 1);
		
		for(int i=0; i<=hexagonMap.lineIndex;i=i+2){
			if(hexagonMap.mapBounds[i]!=null||hexagonMap.mapBounds[i+1]!=null){
				game.shapeRenderer.line(hexagonMap.mapBounds[i], hexagonMap.mapBounds[i+1]);
			}
		}
		game.shapeRenderer.end();
		
		//draw Armys maxHP
		for(int i=0;i<armys.length;i++){
			game.shapeRenderer.begin(ShapeType.Line);
			
			if(i==0){
				game.shapeRenderer.setColor(0,0,1, 1);
			}
			else if(i==1){
				game.shapeRenderer.setColor(1,0,0, 1);
			}
			else if(i==2){
				game.shapeRenderer.setColor(Color.YELLOW);
			}
			for(int j=0; j<armys[i].realArmySize;j++){
				if(armys[i].units[j]!=null){
					game.shapeRenderer.circle(armys[i].units[j].position.x, armys[i].units[j].position.y, armys[i].units[j].radius);
				}
			}
			game.shapeRenderer.end();
		}
		
		//draw army currentHP
		for(int i=0;i<armys.length;i++){
			game.shapeRenderer.begin(ShapeType.Filled);
			
			if(i==0){
				game.shapeRenderer.setColor(0,0,1, 1);
			}
			else if(i==1){
				game.shapeRenderer.setColor(1,0,0, 1);
			}
			else if(i==2){
				game.shapeRenderer.setColor(Color.YELLOW);
			}
			for(int j=0; j<armys[i].realArmySize;j++){
				if(armys[i].units[j]!=null){
					float temp=((110/armys[i].units[j].maxHP)*armys[i].units[j].currentHP);
					game.shapeRenderer.circle(armys[i].units[j].position.x, armys[i].units[j].position.y, ((armys[i].units[j].radius/100)*temp));
				}
			}
			game.shapeRenderer.end();
		}
		
	}


	@Override
	public void render (float delta) {
		update();
		draw();
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	

	@Override
	public void pause () {
		if (state == GAME_RUNNING) state = GAME_PAUSED;
	}
}
