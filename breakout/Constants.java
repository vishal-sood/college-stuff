package breakout;

/**
 * Various useful constants for Breakout.
 */
public interface Constants {
    /**
     * Number of pixels around the block that is empty.
     */
    public static final int BLOCK_BORDER = 1;

    /**
     * Width of all blocks.
     */
    public static final int BLOCK_WIDTH = 76;

    /**
     * Height of all blocks.
     */
    public static final int BLOCK_HEIGHT = 38;

    /**
     * The number of rows of blocks.
     */
    public static final int NUM_ROWS = 5;

    /**
     * The number of columns on the screen.
     */
    public static final int NUM_COLS = 10;

    /**
     * Width of the scene.
     */
    public static final int SCENE_WIDTH = (BLOCK_WIDTH + BLOCK_BORDER * 2) * NUM_COLS;

    /**
     * Height of the scene.
     */
    public static final int SCENE_HEIGHT = 600;

    /**
     * The height of a row.
     */
    public static final int ROW_HEIGHT = BLOCK_HEIGHT + BLOCK_BORDER * 2;

    /**
     * The width of a column.
     */
    public static final int COL_WIDTH = BLOCK_WIDTH + BLOCK_BORDER * 2;

    /**
     * Number of blank rows above the blocks.
     */
    public static final int TOP_BLANK_ROWS = 2;

    /**
     * Number of blank cols on the left and right between the sides of screen and the blocks.
     */
    public static final int SIDE_BLANK_COLS = 1;

    /**
     * Number of pixels taken up by the top blank rows.
     */
    public static final int VERTICAL_GUTTER = TOP_BLANK_ROWS * ROW_HEIGHT;

    /**
     * Number of pixels used by one of the side blank columns.
     */
    public static final int HORIZONTAL_GUTTER = SIDE_BLANK_COLS * COL_WIDTH;

} // end Constants
