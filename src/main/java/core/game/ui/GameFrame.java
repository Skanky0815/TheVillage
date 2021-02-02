package core.game.ui;

import com.google.inject.Injector;

import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class GameFrame extends JFrame implements WindowListener {

    private GamePanel gp;

	public static int pWidth, pHeight;

	public GameFrame(final String title, final Injector injector) {
		super(title);

        final int fps = 80;

		final long period = (long) 1000.0 / fps;
        this.calcSizes();
        this.setResizable(true);

        pWidth = 800;
        pHeight = 600;

        gp = new GamePanel((period * 1000000L), pWidth, pHeight, injector);
		this.add(gp, "Center");
        this.pack();
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addWindowListener(this);

//        this.addComponentListener(new ComponentAdapter() {
//			public void componentMoved(ComponentEvent e) {
//                setLocation(0, 0);
//			}
//		});

        this.setResizable(false);
        this.setVisible(true);
	}

	private void calcSizes() {
		final GraphicsConfiguration graphicsConfiguration = getGraphicsConfiguration();
		final Rectangle screenRect = graphicsConfiguration.getBounds();

		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Insets desktopInsets = toolkit.getScreenInsets(graphicsConfiguration);

		final Insets frameInsets = this.getInsets();

        pWidth = screenRect.width
                - (desktopInsets.left + desktopInsets.right)
				- (frameInsets.left + frameInsets.right);

        pHeight = screenRect.height
				- (desktopInsets.top + desktopInsets.bottom)
				- (frameInsets.top + frameInsets.bottom);
	}

	public void windowActivated(WindowEvent e) {
        gp.resumeGame();
	}

	public void windowDeactivated(WindowEvent e) {
        gp.pauseGame();
	}

	public void windowDeiconified(WindowEvent e) {
        gp.resumeGame();
	}

	public void windowIconified(WindowEvent e) {
        gp.pauseGame();
	}

	public void windowClosing(WindowEvent e) {
        gp.stopGame();
	}

	public void windowClosed(WindowEvent e) { }

	public void windowOpened(WindowEvent e) { }
}
