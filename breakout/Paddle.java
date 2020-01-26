package breakout;

import javafx.scene.layout.Pane;

public class Paddle extends ImageViewGameObj {
	
	/**
     * Creates a new paddle object.
     * @param root the root of the scene graph.
     */
    public Paddle(Pane root) {
        super(root, "/breakout/images/paddle.png");
    }
    
    /**
     * Returns a printable representation of the object.
     * @return a printable representation of the object.
     */
    @Override
    public String toString() {
        return super.toString()
                .replace("GameObj", "Paddle");
    }

}
