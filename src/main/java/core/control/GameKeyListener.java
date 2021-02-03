package core.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public final class GameKeyListener extends KeyAdapter {
	
	private static GameKeyListener instance;
	
	private boolean up      = false;
	private boolean left    = false;
	private boolean right   = false;
	private boolean down    = false;

	private boolean space   = false;
	
	private boolean iKey    = false;
	private boolean bKey    = false;
    private boolean hKey    = false;

    private boolean f1Key   = false;
	
	private GameKeyListener() { }
	
	public static GameKeyListener getInstance() {
		if (null == instance) {
			instance = new GameKeyListener();
		}
		return instance;
	}
	
	public boolean isUp() {
		return up;
	}
	
	public boolean isLeft() {
		return left;
	}
	
	public boolean isRight() {
		return right;
	}
	
	public boolean isDown() {
		return down;
	}

	public boolean isUpReleased() {
		boolean out = up;
        up = false;
		return out;
	}
	
	public boolean isLeftReleased() {
		boolean out = left;
        left = false;
		return out;
	}
	
	public boolean isRightReleased() {
		boolean out = right;
        right = false;
		return out;
	}
	
	public boolean isDownReleased() {
		boolean out = down;
        down = false;
		return out;
	}
	
	public boolean isSpace() {
		return space;
	}
	
	public boolean isSpaceReleased() {
		boolean out = space;
        space = false;
		return out;
	}
	
	public void setSpace(final boolean b) {
        space = b;
	}
	
	public boolean isIKey() {
		return iKey;
	}
	
	public boolean isBKey() {
		return bKey;
	}

    public void setBKey(final boolean b) {
        bKey = b;
    }

    public boolean isF1Key() {
        return f1Key;
    }

    public boolean isHKey() {
        return hKey;
    }
	
	@Override
	public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> up = true;
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_DOWN -> down = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_SPACE -> space = true;
            case KeyEvent.VK_H -> hKey = true;
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> up = false;
            case KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_DOWN -> down = false;
            case KeyEvent.VK_RIGHT -> right = false;
            case KeyEvent.VK_SPACE -> space = false;
            case KeyEvent.VK_I -> iKey = !iKey;
            case KeyEvent.VK_B -> bKey = !bKey;
            case KeyEvent.VK_F1 -> f1Key = !f1Key;
            case KeyEvent.VK_H -> hKey = false;
        }
	}
}
