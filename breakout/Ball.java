package breakout;

import javafx.scene.layout.Pane;

/**
 * A ball in Breakout.
 */
public class Ball extends ImageViewGameObj {

    /**
     * The horizontal velocity.
     */
    private int _dx;

    /**
     * The vertical velocity.
     */
    private int _dy;

    /**
     * Creates a new ball object.
     * @param root the root of the scene graph.
     */
    public Ball(Pane root) {
        super(root, "/breakout/images/bigger-ball.png");
        _dy = 3;
        _dx = Breakout.randInRange(-10, 11);
    }

    /**
     * Moves the ball according to it's velocity without any bouncing.
     */
    public void move() {
        this.setY(this.getY() + this.getDy());
        this.setX(this.getX() + this.getDx());
    }

    /**
     * Returns the center x point of the ball.
     * @return  the center x point of the ball.
     */
    @Override
    public double getX() {
        return super.getX() + this.getHalfWidth();
    }

    /**
     * Returns the center y point of the ball.
     * @return  the center y point of the ball.
     */
    @Override
    public double getY() {
        return super.getY() + this.getHalfHeight();
    }

    /**
     * Sets the center x point of the ball.
     * @param cx the new center x
     */
    @Override
    public void setX(double cx) {
        super.setX(cx - this.getHalfWidth());
    }

    /**
     * Sets the center y point of the ball.
     * @param cy the new center y
     */
    @Override
    public void setY(double cy) {
        super.setY(cy - this.getHalfHeight());
    }

    /**
     * Returns the velocity in the x direction.
     * @return the velocity in the x direction.
     */
    public int getDx() {
        return _dx;
    }

    /**
     * Returns the velocity in the y direction.
     * @return the velocity in the y direction.
     */
    public int getDy() {
        return _dy;
    }

    /**
     * Sets the velocity in the x direction.
     * @param dx the new x velocity.
     */
    public void setDx(int dx) {
        _dx = dx;
    }

    /**
     * Sets the velocity in the y direction.
     * @param dy the new y velocity.
     */
    public void setDy(int dy) {
        _dy = dy;
    }

    /**
     * Returns a printable representation of the object.
     * @return a printable representation of the object.
     */
    @Override
    public String toString() {
        return super.toString()
                .replace("GameObj", "Ball")
                .replace("]",
                        ", [dx:" + this.getDx()
                                + ", dy:" + this.getDy() + "]]");
    }
}
