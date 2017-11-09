package Grafica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import inputEditor.MyInput;


public class Mirino {
	
	public MyInput i; // stage
	public Skin skin;
	public SpriteBatch batch;
	
	
	public Mirino(PerspectiveCamera Camera, Vector3 Pos, Vector3 Rot) {
		batch = new SpriteBatch();
		i = new MyInput(Camera, Pos, Rot,null);
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		

		Pixmap pix = new Pixmap(1, 1, Format.RGBA8888);
		pix.setColor(Color.WHITE);
		pix.fill();
		skin.add("Checcah", new Texture(pix));

		skin.add("default", new BitmapFont());
		
		Table table = new Table();
		table.setPosition(0, 0);

		table.setFillParent(true);
		i.addActor(table);
		Image img = new Image(skin.newDrawable("Checcah", Color.WHITE));
		
		table.add(img).size(4);
		
	}
	
	public Skin getSkin() {
		return skin;
	}
	
	public MyInput getMyInput() {
		return i;
	}

}
