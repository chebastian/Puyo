import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Test;


public class FieldTest {

	/**
	 * @param args
	 */
	public PuzzleField mField;
	
	@Test
	public void ShouldConvertToFieldIndexes()
	{
		mField = new PuzzleField(8, 18);
		PuzzleBlock block = new PuzzleBlock(new Point2D.Float(0,0));
		
		assertTrue("Failed to convert to index",mField.CollumnPosition(block) == 0);
		assertTrue("Failed to convert to index",mField.RowPosition(block) == mField.FIELD_HEIGHT-1);
		
		block.SetPosition(16, 0);
		assertTrue("Failed to convert to index", mField.CollumnPosition(block) == 1);
		
		block.SetPosition(0, 16);
		assertTrue("Failed to convert to index",mField.RowPosition(block) == mField.FIELD_HEIGHT-2);
		
		block.SetPosition(16, 16);
		assertFalse("X affects Row position",mField.RowPosition(block) == mField.FIELD_HEIGHT-3);
		assertTrue("X affects Row position",mField.RowPosition(block) == mField.FIELD_HEIGHT-2);
	}
	
	@Test
	public void ShouldSnappIntoField()
	{
		mField = new PuzzleField(10,10);
		PuzzleBlock block = new PuzzleBlock(new Point2D.Float(0,0));
		
		block.SnapToPosition(0, 0);
		assertTrue("Snepped to top",mField.RowPosition(block) == mField.FIELD_HEIGHT-1);
		
		block.SnapToPosition(0, 1);
		assertTrue("Snapped to row 1",mField.RowPosition(block) == mField.FIELD_HEIGHT-1);
		assertTrue("Snapping didnt update position",block.getY() == 0);
		
		block.SnapToPosition(0, 38);
		assertTrue("Snapped to row 1",mField.RowPosition(block) == mField.FIELD_HEIGHT-2);
		assertTrue("Snapping didnt update position",block.getY() == 32);
		
		block.SnapToPosition(33, 20);
		assertTrue("Snapped to row 1",mField.CollumnPosition(block) == 1);
		assertTrue("Snapping didnt update position",block.getY() == 0);
		assertTrue("Snapping didnt snap pos",block.getX() == block.BLOCK_W);
		
	}
	
	@Test
	public void ShouldBeOutOfBounds()
	{
		mField = new PuzzleField(8, 18);
		
		assertFalse("Check that negaative values are not in map",mField.IsInField(-1, 0));
		assertFalse("Width + 1 is not in map", mField.IsInField(mField.FIELD_WIDTH, 0));
	}
	
	@Test
	public void ShouldUpdateColSize()
	{
		mField = new PuzzleField(8, 18);
		PuzzleBlock block = new PuzzleBlock(new Point2D.Float(0,0));
		mField.AddBlockToBottom(block);
		
		assertTrue("Height is not updated",mField.CollumnSize(0) == 1);
		
		mField.AddBlockToBottom(block);
		
		assertFalse("Height is not updated",mField.CollumnSize(0) == 1);
		assertTrue("Height is not updated",mField.CollumnSize(0) == 2);
		assertTrue("Unused collumn changed size", mField.CollumnSize(1) == 0);
	}
	
	@Test
	public void ShouldBeValidMoves()
	{
		mField = new PuzzleField(10, 10);
		PuzzleBlock moveable = new PuzzleBlock(new Point2D.Float(0, 0));
		assertTrue("Invalid move",mField.CanMoveBlockToCollumn(moveable, 1));
		assertTrue("Invalid move",mField.CanMoveBlockToCollumn(moveable, 0));
		
		assertFalse("Invalid move into -1",mField.CanMoveBlockToCollumn(moveable, -1));
		assertFalse("Invalid move out of bounds",mField.CanMoveBlockToCollumn(moveable, mField.FIELD_WIDTH+1));
	}
	
	@Test
	public void ShouldBeAddedToTop()
	{
		mField = new PuzzleField(3, 3);
		
		PuzzleBlock block = new PuzzleBlock(new Point2D.Float(0, 0));
		mField.AddBlockToBottom(block);
		mField.AddBlockToBottom(block);
		assertTrue("Blocks are not in field", mField.CollumnSize(0) == 2);
		
		PuzzleBlock toMove = new PuzzleBlock(new Point2D.Float(32, 32));
		//assertTrue("block slided into collumn", mField.CanMoveBlockToCollumn(toMove, 2));
		assertFalse("block slided into collumn", mField.CanMoveBlockToCollumn(toMove, 0));
	}
	
	@Test
	public void ShouldRemoveBlocksFromField()
	{
		mField = new PuzzleField(4, 4);
		PuzzleBlock block = new PuzzleBlock(new Point2D.Float(0, 0));
		block.SetType(2);
		
		mField.AddBlockToBottom(block);
		mField.AddBlockToBottom(block);
		
		/*See that both blocks are added to the collumn*/
		assertTrue("",mField.CollumnSize(0)==2);

		/*Remove one and see that size decreased*/
		mField.RemoveBlock(block);
		assertTrue("",mField.CollumnSize(0) == 1);
		
		/*Add a second block ontop of first*/
		PuzzleBlock block2 = new PuzzleBlock(new Point2D.Float(0, 0));
		mField.AddBlockToBottom(block2);
		
		/*Remove and check that size decreased*/
		mField.RemoveBlock(block2);
		assertTrue("",mField.CollumnSize(0) == 1);
		
		/*Remove last block and see that size is 0*/
		mField.RemoveBlock(block);
		assertTrue("",mField.CollumnSize(0) == 0);
		
		/*Try and remove last block again, nothing should happen*/
		mField.RemoveBlock(block2);
		assertTrue("",mField.RemoveBlock(block2) == false);
		
		/*
		 * Test of function for adding without providing a block
		 * */
		mField.AddBlock(0, 0, 0);
		System.out.print(mField.CollumnSize(0));
		assertTrue(mField.CollumnSize(0) == 0);
	}
	
	@Test
	public void ShouldMakeBlocksFall()
	{
		mField = new PuzzleField(4, 4);
		PuzzleBlock block = new PuzzleBlock(new Point2D.Float(0, 0));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("TESTING");
		FieldTest test = new FieldTest();
		
		test.ShouldBeOutOfBounds();
		test.ShouldBeValidMoves();
		test.ShouldRemoveBlocksFromField();

	}

}
