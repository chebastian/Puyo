import java.awt.Graphics2D;


public class BlockState {
	
	PuzzleBlock Block;
	int StateID;
	
	public BlockState(PuzzleBlock block)
	{
		Block = block;
		StateID = 0;
	}
	
	public void onEnter()
	{
		
	}
	
	public void update(float time)
	{
		
	}
	
	public void render(Graphics2D g)
	{
		
	}
	
	public void onExit()
	{
		Block.StateIsChanged(true);
	}

}
