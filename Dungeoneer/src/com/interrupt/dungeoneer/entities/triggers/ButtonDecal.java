package com.interrupt.dungeoneer.entities.triggers;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.interrupt.dungeoneer.Audio;
import com.interrupt.dungeoneer.annotations.EditorProperty;
import com.interrupt.dungeoneer.entities.Actor;
import com.interrupt.dungeoneer.entities.Entity;
import com.interrupt.dungeoneer.entities.Player;
import com.interrupt.dungeoneer.entities.triggers.Trigger.TriggerStatus;
import com.interrupt.dungeoneer.entities.triggers.Trigger.TriggerType;
import com.interrupt.dungeoneer.game.Level;
import com.interrupt.dungeoneer.gfx.drawables.DrawableSprite;

public class ButtonDecal extends Trigger {
	
	public ButtonDecal() { spriteAtlas = "texture"; selfDestructs = false; hidden = false; }
	
	@EditorProperty
	public Vector3 direction = new Vector3(Vector3.Z);
	
	@EditorProperty
	public int buttonState = 0;
	
	@EditorProperty
	public int stateCount = 2;
	
	@EditorProperty
	public int buttonTex = 0;
	
	@Override
	public void use(Player p, float projx, float projy) {
		if(tryActivate()) {
			fire(Integer.toString(buttonState));
		}
	}
	
	@Override
	public void updateDrawable() {
		if(drawable != null) {
			tex = buttonTex + buttonState;
			drawable.update(this);
			drawable.dir.set(direction);
		}
		else if(artType != ArtType.hidden) {
			DrawableSprite drbls = new DrawableSprite(tex, artType);
			drbls.billboard = false;
			
			drawable = drbls;
			drawable.update(this);
		}
	}
	
	@Override
	public void onTrigger(Entity instigator, String value) {
		tryActivate();
	}
	
	public boolean tryActivate() {
		buttonState++;
		if(buttonState + 1 > stateCount) {
			if(triggerResets)
				buttonState = 0;
			else {
				buttonState = stateCount - 1;
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void rotate90() {
		super.rotate90();
		direction.rot(new Matrix4().rotate(Vector3.Y, 90f));
	}
}
