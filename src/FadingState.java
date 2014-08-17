import java.awt.Graphics;
import java.awt.Graphics2D;


public class FadingState extends BlockState{
	
	static int FadingStateID = 4; 
	float mFadeTime; 
	float mElapsedTime;
	Game mGame;
	
	public FadingState(PuzzleBlock block,Game game)
	{
		super(block);
		mGame = game;
		mFadeTime = 0.5f;
		mElapsedTime = 0.0f;
		StateID = FadingStateID;
	}
	
	public void onEnter()
	{
        mGame.addBlockToFadeCounter(Block);
	}
	
	public void update(float time)
	{
		mElapsedTime += time;
		if(mFadeTime <= mElapsedTime)
		{
			onExit();
		}
			
		Block.Scale = 1.0f - (mElapsedTime/mFadeTime);
	}
	
	public void render(Graphics2D g)
	{
		
	}
	
	public void onExit()
	{
		mGame.removeBlockToFadeCounter(Block); 
		this.Block.Kill();
	}

}
