package breakout;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Models game objects on the screen displayed using an image view.
 */
public class ImageViewGameObj {

    /**
     * The image view that holds the image.
     */
    private ImageView _imageView;

    /**
     * Creates a new ImageViewGameObject.
     * @param root the root of the scene graph.
     * @param url the string representing the URL to use in fetching the pixel data.
     */
    public ImageViewGameObj(Pane root, String url) {
        _imageView = new ImageView(url);
        _imageView.setCache(true);
        root.getChildren().add(_imageView);
    }

    /**
     * Creates a new ImageViewGameObject of the specified size.
     * @param root the root of the scene graph.
     * @param url the string representing the URL to use in fetching the pixel data.
     * @param width the desired width of the image.
     * @param height the desired height of the image.
     */
    public ImageViewGameObj(Pane root, String url, double width, double height) {
        _imageView = new ImageView(new Image(url, width, height, false, true));
        _imageView.setCache(true);
        root.getChildren().add(_imageView);
    }

    /**
     * Returns the ImageView.
     * @return Returns the ImageView.
     */
    protected ImageView getImageView() {
        return _imageView;
    }

    /**
     * Returns the x position of the ImageView.
     * @return the x position of the ImageView.
     */
    public double getX() {
        return _imageView.getX();
    }

    /**
     * Returns the y position of the ImageView.
     * @return the y position of the ImageView.
     */
    public double getY() {
        return _imageView.getY();
    }

    /**
     * Returns the width of the image.
     * @return the width of the image.
     */
    public double getWidth() {
        return _imageView.getImage().getWidth();
    }

    /**
     * Returns the height of the image.
     * @return the height of the image.
     */
    public double getHeight() {
        return _imageView.getImage().getHeight();
    }

    /**
     * Returns half the width of the image.
     * @return half the width of the image.
     */
    public double getHalfWidth() { return this.getWidth() / 2; }

    /**
     * Returns half the height of the image.
     * @return half the height of the image.
     */
    public double getHalfHeight() { return this.getHeight() / 2; }

    /**
     * Sets the x location of the image.
     * @param x the new x location.
     */
    public void setX(double x) {
        _imageView.setX(x);
    }

    /**
     * Sets the y location of the image.
     * @param y the new y location.
     */
    public void setY(double y) {
        _imageView.setY(y);
    }

    /**
     * Returns the bounds (in parent) of the image(view).
     * @return the bounds (in parent) of the image(view).
     */
    public Bounds getBounds() {
        return _imageView.getBoundsInParent();
    }

    /**
     * Returns a printable representation of the object.
     * @return a printable representation of the object.
     */
    @Override
    public String toString() {
        return "[GameObj: (" + this.getX() + ", " + this.getY() + ")]";
    }

} // end class ImageViewGameObject
