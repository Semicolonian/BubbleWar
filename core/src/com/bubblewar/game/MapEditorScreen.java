package com.bubblewar.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bubblewar.game.HexagonTile.TileType;

public class MapEditorScreen extends ScreenAdapter {

	static public enum EditorState {
		DEFINE_MAPSIZE, DEFINE_MAPCONTENT
	}

	private static final float SCENE_WIDTH = 1280f;
	private static final float SCENE_HEIGHT = 720f;

	private static final float CAMERA_SPEED = 400f;
	private static final float CAMERA_ZOOM_SPEED = 2.0f;
	private static final float CAMERA_ZOOM_MAX = 5.0f;
	private static final float CAMERA_ZOOM_MIN = 0.1f;
	private static final float CAMERA_MOVE_EDGE = 0.2f;

	BubbleWar game;
	OrthographicCamera camera;
	OrthographicCamera hudCamera;
	Vector3 touchPoint;
	Rectangle confirmMapButton;
	int mapWidth, mapHeight;
	public static HexagonMap hexagonMap;
	Viewport viewport;
	Viewport hudViewport;
	Dialog dialog;
	private Stage stage;
	EditorState editorState;

	public MapEditorScreen(BubbleWar game) {
		this.game = game;
		camera = new OrthographicCamera(SCENE_WIDTH, SCENE_HEIGHT);
		camera.position.set(SCENE_WIDTH / 2, SCENE_HEIGHT / 2, 0);
		hudCamera = new OrthographicCamera(SCENE_WIDTH, SCENE_HEIGHT);
		hudCamera.position.set(SCENE_WIDTH / 2, SCENE_HEIGHT / 2, 0);
		viewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT, camera);
		hudViewport = new FitViewport(SCENE_WIDTH, SCENE_HEIGHT,hudCamera);
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		confirmMapButton = new Rectangle(700, 30, 80, 30);
		touchPoint = new Vector3();
		mapWidth = 15;
		mapHeight = 15;
		editorState = EditorState.DEFINE_MAPSIZE;

		// Slider

		Slider.SliderStyle ss = new Slider.SliderStyle();

		ss.background = new TextureRegionDrawable(new TextureRegion(
				Assets.slider_backgroundRegion));
		ss.knob = new TextureRegionDrawable(new TextureRegion(
				Assets.slider_knob));

		final Slider sliderForWidth = new Slider(1, 50, 1, false, ss);
		final Slider sliderForHeight = new Slider(1, 50, 1, false, ss);
		sliderForWidth.setValue(15);
		sliderForHeight.setValue(15);
		sliderForWidth.setWidth(400);

		// Button with background and text
		// actor = new Texture(Gdx.files.internal("data/scene2d/myactor.png"));
		TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
		// buttonStyle.up = new TextureRegionDrawable(new TextureRegion(actor));
		tbs.font = Assets.font;
		TextButton confirmMapsizeButton = new TextButton("Confirm", tbs);

		Window.WindowStyle ws = new Window.WindowStyle();
		ws.titleFont = Assets.font;
		ws.titleFontColor = Color.WHITE;
		TextureRegionDrawable trd = new TextureRegionDrawable(
				new TextureRegion(Assets.dialog_background));

		Label.LabelStyle ls = new Label.LabelStyle(Assets.font, Color.WHITE);
		final Label labelDialog = new Label("Define the mapsize", ls);
		final Label labelForWidth = new Label("Width", ls);
		final Label labelForHeight = new Label("Height", ls);
		final Label labelCurrentWidth = new Label(Integer.toString(mapWidth),
				ls);
		final Label labelCurrentHeight = new Label(Integer.toString(mapHeight),
				ls);

		dialog = new Dialog("", ws);
		dialog.setKeepWithinStage(false);

		// dialog.getContentTable().setSize(580, 380);
		// dialog.getContentTable().setPosition(10,10);
		dialog.getContentTable().row().pad(0, 0, 80, 0).center();
		dialog.getContentTable().add();
		dialog.getContentTable().add(labelDialog);
		dialog.getContentTable().row().pad(0, 0, 50, 0);
		dialog.getContentTable().add(labelForWidth);
		dialog.getContentTable().add(sliderForWidth);
		dialog.getContentTable().add(labelCurrentWidth);
		dialog.getContentTable().row();
		dialog.getContentTable().add(labelForHeight);
		dialog.getContentTable().add(sliderForHeight);
		dialog.getContentTable().add(labelCurrentHeight);

		dialog.getContentTable().getCell(sliderForWidth).width(400);
		dialog.getContentTable().getCell(sliderForHeight).width(400);

		dialog.getContentTable().getCell(labelForHeight).width(100);
		dialog.getContentTable().getCell(labelCurrentHeight).width(30);

		dialog.getContentTable().getCell(labelForWidth).width(100);
		dialog.getContentTable().getCell(labelCurrentWidth).width(30);

		dialog.row();
		dialog.button(confirmMapsizeButton);

		dialog.setModal(true);
		dialog.setBackground(trd);
		dialog.pack();
		dialog.setWidth(600);
		dialog.setHeight(400);
		dialog.setPosition(340, 600 - dialog.getHeight());

		sliderForWidth.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				mapWidth = (int) sliderForWidth.getValue();
				labelCurrentWidth.setText(Integer.toString(mapWidth));

			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			};
		});
		sliderForHeight.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				mapHeight = (int) sliderForHeight.getValue();
				labelCurrentHeight.setText(Integer.toString(mapHeight));
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			};
		});
		confirmMapsizeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hexagonMap = new HexagonMap(mapWidth, mapHeight, 100, 650, 60,
						TileType.LAND);
				editorState = EditorState.DEFINE_MAPCONTENT;
			};
		});

		stage.addActor(dialog);
	}

	public void update() {

		if (Gdx.input.justTouched()) {
			hudCamera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));

			if (confirmMapButton.contains(touchPoint.x, touchPoint.y)) {
				hexagonMap.findMapBounds();

				// save MAp
				game.setScreen(new MainMenuScreen(game));

				return;
			}
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));
			for (int i = 0; i < hexagonMap.index; i++) {
				if (hexagonMap.Map[i].bounds.contains(touchPoint.x,
						touchPoint.y)) {

					if (hexagonMap.Map[i].tileType == TileType.LAND) {
						hexagonMap.Map[i].setTileType(TileType.NOLAND);
					} else if (hexagonMap.Map[i].tileType == TileType.NOLAND) {
						hexagonMap.Map[i].setTileType(TileType.HQ);
					} else if (hexagonMap.Map[i].tileType == TileType.HQ) {
						hexagonMap.Map[i].setTileType(TileType.LAND);
					}
					return;
				}
			}
		}
	}

	public void draw() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		float deltaTime = Gdx.graphics.getDeltaTime();

		// Arrow keys move the camera
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			camera.position.x -= CAMERA_SPEED * deltaTime;
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			camera.position.x += CAMERA_SPEED * deltaTime;
		}

		if (Gdx.input.isKeyPressed(Keys.UP)) {
			camera.position.y += CAMERA_SPEED * deltaTime;
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			camera.position.y -= CAMERA_SPEED * deltaTime;
		}
		
		
		// Touching on the edges also moves the camera
		// if (Gdx.input.isTouched()) {
		// touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0.0f);
		// camera.unproject(touchPoint);
		//
		// if (touchPoint.x > SCENE_WIDTH * (1.0f - CAMERA_MOVE_EDGE)) {
		// camera.position.x += CAMERA_SPEED * deltaTime;
		// }
		// else if (touchPoint.x < SCENE_WIDTH * CAMERA_MOVE_EDGE) {
		// camera.position.x -= CAMERA_SPEED * deltaTime;
		// }
		//
		// if (touchPoint.y > SCENE_HEIGHT * (1.0f - CAMERA_MOVE_EDGE)) {
		// camera.position.y += CAMERA_SPEED * deltaTime;
		// }
		// else if (touchPoint.y < SCENE_HEIGHT * CAMERA_MOVE_EDGE) {
		// camera.position.y -= CAMERA_SPEED * deltaTime;
		// }
		// }

		// Page up/down control the zoom
		if (Gdx.input.isKeyPressed(Keys.PAGE_UP)) {
			camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime;
		} else if (Gdx.input.isKeyPressed(Keys.PAGE_DOWN)) {
			camera.zoom += CAMERA_ZOOM_SPEED * deltaTime;
		}

		// Clamp position
		// float halfWidth = SCENE_WIDTH * 0.5f;
		// // float halfHeight = SCENE_HEIGHT * 0.5f;
		//
		// camera.position.x = MathUtils.clamp(camera.position.x,
		// halfWidth * camera.zoom,
		// SCENE_WIDTH - halfWidth * camera.zoom);
		// camera.position.y = MathUtils.clamp(camera.position.y,
		// halfHeight * camera.zoom,
		// SCENE_HEIGHT - halfHeight * camera.zoom);

		// Clamp zoom
		camera.zoom = MathUtils.clamp(camera.zoom, CAMERA_ZOOM_MIN,
				CAMERA_ZOOM_MAX);

		camera.update();
		game.batcher.setProjectionMatrix(camera.combined);

		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.shapeRenderer.setProjectionMatrix(camera.combined);

		// Draw Filled HexagonMap
		game.shapeRenderer.begin(ShapeType.Filled);

		for (int i = 0; i <= hexagonMap.index - 1; i++) {

			for (int j = 0; j < 5; j++) {
				if (hexagonMap.Map[i].tileType == TileType.LAND) {
					game.shapeRenderer.setColor(hexagonMap.Map[i].landColor);
				} else if (hexagonMap.Map[i].tileType == TileType.NOLAND) {
					game.shapeRenderer.setColor(hexagonMap.Map[i].noLandColor);
				} else if (hexagonMap.Map[i].tileType == TileType.HQ) {
					game.shapeRenderer.setColor(hexagonMap.Map[i].HQColor);
				}
				game.shapeRenderer.triangle(hexagonMap.Map[i].position.x,
						hexagonMap.Map[i].position.y,
						hexagonMap.Map[i].vertices[j].x,
						hexagonMap.Map[i].vertices[j].y,
						hexagonMap.Map[i].vertices[j + 1].x,
						hexagonMap.Map[i].vertices[j + 1].y);
			}
			game.shapeRenderer.triangle(hexagonMap.Map[i].position.x,
					hexagonMap.Map[i].position.y,
					hexagonMap.Map[i].vertices[5].x,
					hexagonMap.Map[i].vertices[5].y,
					hexagonMap.Map[i].vertices[0].x,
					hexagonMap.Map[i].vertices[0].y);
		}
		game.shapeRenderer.end();

		// Draw Map Bounds

		// game.shapeRenderer.begin(ShapeType.Line);
		// game.shapeRenderer.setColor(1,1,1, 1);
		//
		// for(int i=0; i<=hexagonMap.lineIndex;i=i+2){
		// if(hexagonMap.mapBounds[i]!=null||hexagonMap.mapBounds[i+1]!=null){
		// game.shapeRenderer.line(hexagonMap.mapBounds[i],
		// hexagonMap.mapBounds[i+1]);
		// }
		// }
		// game.shapeRenderer.end();

		// Confirm and save and transform Map
		
		hudCamera.update();
		game.shapeRenderer.setProjectionMatrix(hudCamera.combined);
		
		game.shapeRenderer.begin(ShapeType.Filled);
		game.shapeRenderer.setColor(1, (float) 0.3, (float) 0.5, 1);
		game.shapeRenderer.rect(confirmMapButton.x, confirmMapButton.y,
				confirmMapButton.width, confirmMapButton.height);
		game.shapeRenderer.end();
	}

	@Override
	public void render(float delta) {
		switch (editorState) {
		case DEFINE_MAPSIZE:
			GL20 gl = Gdx.gl;
			gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			gl.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
			stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
			stage.draw();
			break;
		case DEFINE_MAPCONTENT:
			update();
			draw();
			break;
		}

	}

}