package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static breakout.Constants.*;

/**
 * Class for the Breakout game.
 */
public class Breakout implements EventHandler<Event>{

    /**
     * The root of the scene graph that holds the board of the game.
     */
    private Pane _root;

    /**
     * The ball.
     */
    private Ball _ball;
    
    /**
     * The collection of blocks.
     */
    private ArrayList<Block> _blocks;
    
    /**
     * The paddle.
     */
    private Paddle _paddle;

    /**
     * The timeline for animation.
     */
    private Timeline _timeline;
    
    /**
     * Whether to draw the grid lines.
     */
    private boolean _drawGridLines;
    
    /**
     * If true the ball will bounce off the bottom of the screen (for testing)
     */
    private boolean _bounceBottom = false;
    
    /**
     * If true the ball will start above the bank of blocks (for testing)
     */
    private boolean _testTop = false;
    
    /**
     * Delay in milliseconds.
     */
    private final double delayMs = 1000/60;

    /**
     * Create a new Breakout game.
     * @param drawGridLines if true then draw the grid lines,
     *                     otherwise do not.
     */
    public Breakout(boolean drawGridLines) {
    	
    	// set the _drawGridLines variable equal to the argument passed
    	_drawGridLines = drawGridLines;
    	
    	// initialize the root pane
    	_root = new Pane();
    	_root.setPrefSize(SCENE_WIDTH + 2 * HORIZONTAL_GUTTER, VERTICAL_GUTTER + SCENE_HEIGHT);
    	
    	// initialize the paddle object
    	_paddle = new Paddle(_root);
    	_paddle.setX(HORIZONTAL_GUTTER + SCENE_WIDTH / 2 - _paddle.getHalfWidth());
    	_paddle.setY(VERTICAL_GUTTER + SCENE_HEIGHT - _paddle.getHeight());
    	
    	// initialize the ball object
    	_ball = new Ball(_root);
    	_ball.setX(randInRange((int)_ball.getHalfWidth(), 2 * HORIZONTAL_GUTTER + SCENE_WIDTH - (int)_ball.getHalfWidth()));
    	if(_testTop)
    		_ball.setY(randInRange((int)_ball.getHalfWidth(), VERTICAL_GUTTER - (int)_ball.getHalfWidth()));
    	else
    		_ball.setY(randInRange(VERTICAL_GUTTER + (ROW_HEIGHT * NUM_ROWS) + (int)_ball.getHalfWidth(), SCENE_HEIGHT - (int)_ball.getHalfWidth()));
    	
    	// initialize the blocks
    	_blocks = new ArrayList<Block>(NUM_ROWS * NUM_COLS);
    	for(int index = 0; index < NUM_ROWS * NUM_COLS; index++) {
    		Block newBlock = new Block(_root);
    		newBlock.setX((index % NUM_COLS) * COL_WIDTH + (BLOCK_WIDTH / 2) + HORIZONTAL_GUTTER);
    		newBlock.setY((index / NUM_COLS) * ROW_HEIGHT + (BLOCK_HEIGHT / 2) + VERTICAL_GUTTER);
    		_blocks.add(newBlock);
    	}
    	
    	// draw grid lines, if required
    	if(_drawGridLines)
    		addGridLines();
    	
    	// set this as the listener to mouse movement
    	_root.setOnMouseMoved(this);
    	
    	// Keyframe for the timeline object
    	KeyFrame oneFrame = new KeyFrame(new Duration(delayMs), new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {

    			// move ball
    			_ball.move();

    			// check for collision
    			checkCollisions();

    			// check if game ended
    			hasGameEnded();
    		}
    		
    	});
    	
    	// build the timeline.
    	_timeline = new Timeline();
    	_timeline.setCycleCount(Timeline.INDEFINITE);
    	_timeline.getKeyFrames().add(oneFrame);
    	_timeline.play();
    }
    
    /**
     * Event handler.
     */
    @Override
	public void handle(Event e) {
    	// Handle paddle movement through mouse.
    	if(e instanceof MouseEvent) {
    		if(e.getEventType() == MouseEvent.MOUSE_MOVED){
    			// set the position of the paddle according to the x-component of the mouse's current position
    			_paddle.setX(((MouseEvent) e).getX() < SCENE_WIDTH + 2 * HORIZONTAL_GUTTER - _paddle.getWidth() ? ((MouseEvent) e).getX() : SCENE_WIDTH + 2 * HORIZONTAL_GUTTER - _paddle.getWidth());
    			e.consume();
    		}
    	}
    }
    
    /**
     * Checks for collision of the ball with various objects in the game.
     */
    private void checkCollisions() {
    	
    	// Collision with blocks
    	ArrayList<Block> hit = new ArrayList<Block>(); // add all the blocks which were hit to this list
    	for(Block block: _blocks){
    		if(_ball.getBounds().intersects(block.getBounds())){
    			switch(whichPartHit(block)){
    			case BLOCK_LT_RT:
    				_ball.setDx(-1 * _ball.getDx()); // flip horizontal velocity only
    				break;
    			case BLOCK_TOP_BOT:
    				_ball.setDy(-1 * _ball.getDy()); // flip vertical velocity only
    				break;
    			case BLOCK_CORNER:
    				_ball.setDx(-1 * _ball.getDx()); // flip horizontal velocity
    				_ball.setDy(-1 * _ball.getDy()); // flip vertical velocity
    				break;
    			}
    			
    			hit.add(block);
    		}
    	}
    	
    	// remove all the blocks in the 'hit' list
    	for(Block block: hit){
    		block.getImageView().setVisible(false);;
    		_blocks.remove(block);
    	}
    	
    	// Collision with side walls.
    	if(_ball.getX() - _ball.getHalfWidth() <= 0 || _ball.getX() + _ball.getHalfWidth() >= SCENE_WIDTH + 2 * HORIZONTAL_GUTTER)
    		_ball.setDx(-1 * _ball.getDx());
    	
    	// Collision with upper wall or bottom wall, if bounce bottom is true.
    	if(_ball.getY() - _ball.getHalfHeight() <= 0 || (_bounceBottom && _ball.getY() + _ball.getHalfHeight() >= VERTICAL_GUTTER + SCENE_HEIGHT))
    		_ball.setDy(-1 * _ball.getDy());
    	
    	// Collision with the paddle.
    	if(_paddle.getBounds().intersects(_ball.getBounds()) && _ball.getY() <= VERTICAL_GUTTER + SCENE_HEIGHT - _paddle.getHeight()){
    		double horizontalVelocity = (_paddle.getX() + _paddle.getHalfWidth()) - (_ball.getX() + _ball.getHalfWidth());
    		horizontalVelocity /= _paddle.getHalfWidth();
    		horizontalVelocity *= -10;
    		_ball.setDx((int) horizontalVelocity);
    		_ball.setDy(-1 * _ball.getDy());
    	}
    	
    }
    
    /**
     * Check if the game has ended.
     */
    private void hasGameEnded() {
    	
    	// All blocks have been hit.
    	if(_blocks.isEmpty()) {
    		_timeline.stop();
    		
    		Label youWon = new Label();
    		youWon.setPrefSize(SCENE_WIDTH * 3 / 2, SCENE_HEIGHT * 3/ 2);
    		youWon.setAlignment(Pos.CENTER);
    		youWon.setTextFill(Color.ROSYBROWN);
    		youWon.setFont(new Font(100));
    		youWon.setText("You Won!");
    		_root.getChildren().add(youWon);
    	}
    	
    	// Ball has gone below the lower wall.
    	if(_ball.getY() > VERTICAL_GUTTER + SCENE_HEIGHT) {
    		_timeline.stop();
    		Label gameOver = new Label();
    		gameOver.setPrefSize(SCENE_WIDTH * 3 / 2, SCENE_HEIGHT * 3/ 2);
    		gameOver.setAlignment(Pos.CENTER);
    		gameOver.setTextFill(Color.ROSYBROWN);
    		gameOver.setFont(new Font(100));
    		gameOver.setText("Game Over!");
    		_root.getChildren().add(gameOver);
    	}
    }
	
    /**
     * Helper method to show angle of where the ball hit.
     * IMPORTANT NOTE: Your ball class must have a
     *                 getX and getY methods that return the coordinates of the
     *                 center point of the ball.
     *
     * Based on code taken from,
     * http://gamedev.stackexchange.com/a/22614,
     * last access 4/25/2017
     *
     * @param block the block to test against.
     * @return one of BlockSide.BLOCK_TOP_BOT when the ball hit the top or bottom,
     *                BlockSide.BLOCK_LT_RT when the ball hit either the left or right side,
     *                BlockSide.BLOCK_CORNER when the ball hit a corner
     */
    private BlockSide whichPartHit(Block block) {

        final double upcorner = 63.43;
        final double botcorner = 116.56;

        final double epsilon = 0.02;

        Point2D brickFacing = new Point2D(0, -1);
        brickFacing = brickFacing.normalize();

        Point2D ballToBrick = new
                Point2D(
                        _ball.getX() - (block.getX() + block.getHalfWidth()),
                        _ball.getY() - (block.getY() + block.getHalfHeight()));
        ballToBrick = ballToBrick.normalize();
        double angle = Math.toDegrees(Math.acos(ballToBrick.dotProduct(brickFacing)));

        if (upcorner - epsilon <= angle && angle <= upcorner + epsilon) {
            // if hit close to upper corners
            return BlockSide.BLOCK_CORNER;
        }
        else if (botcorner - epsilon <= angle && angle <= botcorner + epsilon) {
            // if hit close to lower corners
            return BlockSide.BLOCK_CORNER;
        }
        else if (0 <= angle && angle < upcorner + epsilon) {
            // check top
            return BlockSide.BLOCK_TOP_BOT;
        }
        else if (botcorner + epsilon < angle) {
            // check bottom
            return BlockSide.BLOCK_TOP_BOT;
        }
        else if (upcorner + epsilon < angle && angle < botcorner - epsilon) {
            // only need 1 for this since angle is the same regardless of which
            // side we're on
            return BlockSide.BLOCK_LT_RT;
        }
        else {
            // should never occur
            throw new IllegalStateException("whichPartHit failed!");
        }
    }

    /**
     * Helper method to draw grid lines onto the board.
     */
    private void addGridLines() {
        // draw vertical lines
        for (int x = COL_WIDTH; x < SCENE_WIDTH; x += COL_WIDTH) {
            Line line = new Line(x, 0, x, SCENE_HEIGHT);
            line.setStroke(Color.LIGHTGREY);
            _root.getChildren().add(line);
        }

        // draw horizontal lines
        for (int y = ROW_HEIGHT; y < SCENE_HEIGHT; y += ROW_HEIGHT) {
            Line line = new Line(0, y, SCENE_WIDTH, y);
            line.setStroke(Color.LIGHTGREY);
            _root.getChildren().add(line);
        }
    }

    /**
     * Returns a random number in the given range from min (inclusive)
     * to max (exclusive).
     * Code taken from
     * https://www.mkyong.com/java/java-generate-random-integers-in-a-range/,
     * last access 4/22/2017
     * 
     * @param min the min (inclusive) of the range.
     * @param max the max (exclusive) of the range.
     * @return a random number in the given range from min to max.
     */
    public static int randInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, (max + 1)).findFirst().getAsInt();
    }
    
    /**
     * Returns the root of the scene graph that holds the board of the game
     * @return root of the scene graph
     */
    public Pane getRoot() {
    	return _root;
    }

}
