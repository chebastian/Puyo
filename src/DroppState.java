
public class DroppState extends BlockState {

	int mStepsToFall;
	int mStartingPosY;
	public static int DroppStateID = 5;
	float mFallSpeedChanger;
	public DroppState(PuzzleBlock block) {
		super(block);
		// TODO Auto-generated constructor stub
	}

	public DroppState(PuzzleBlock block, int steps)
	{
		super(block);
		mStepsToFall = steps;
		mStartingPosY = block.getY();
		StateID = DroppStateID;
		mFallSpeedChanger = 1.5f;
	}
	
	public void update(float time)
	{ 
		Block.Move(0, (mFallSpeedChanger*PuzzleBlock.FallSpeed)*time);
		if(mStepsToFall == -1)
			return;
		
		if((Block.getY() - mStartingPosY) / PuzzleBlock.BLOCK_H >= mStepsToFall)
		{
			int yindex = Block.getY()%32;
			Block.SetPosition(Block.getX(),Block.getY()+yindex);
			Block.ChangeState(new IdleState(Block));
		}

	}
	
	public void onExit()
	{ 
		Block.SnapToPosition(Block.getX(), Block.getY());
		Block.StateIsChanged(true);
		System.out.print(" exit falling ");
	}

}