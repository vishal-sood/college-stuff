package breakout;

import javafx.scene.layout.Pane;

public class Block extends ImageViewGameObj {
	
	/**
	 * The urls for different blocks.
	 */
	private static final String[] _urls = {	"/breakout/images/blue-block.png",
											"/breakout/images/green-block.png",
											"/breakout/images/orange-block.png",
											"/breakout/images/purple-block.png",
											"/breakout/images/red-block.png"	};
	
	/**
     * Creates a new block object.
     * @param root the root of the scene graph.
     */
    public Block(Pane root) {
        super(root, _urls[Breakout.randInRange(0, _urls.length - 1)], Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
    }
	
	/**
     * Sets the center x point of the block.
     * @param cx the new center x
     */
    @Override
    public void setX(double cx) {
        super.setX(cx - this.getHalfWidth());
    }

    /**
     * Sets the center y point of the block.
     * @param cy the new center y
     */
    @Override
    public void setY(double cy) {
        super.setY(cy - this.getHalfHeight());
    }
    
    /**
     * Returns a printable representation of the object.
     * @return a printable representation of the object.
     */
    @Override
    public String toString() {
        return super.toString()
                .replace("GameObj", "Block");
    }
	
}
