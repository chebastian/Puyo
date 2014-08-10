import java.awt.Graphics;
import java.awt.Graphics2D;


public class FadingState extends BlockState{
	
	static int FadingStateID = 4; 
	float mFadeTime; 
	float mElapsedTime;
	
	public FadingState(PuzzleBlock block)
	{
		super(block);
		mFadeTime = 0.5f;
		mElapsedTime = 0.0f;
		StateID = FadingStateID;
	}
	
	public void update(float time)
	{
		mElapsedTime += time;
		if(mFadeTime <= mElapsedTime)
		{
			onExit();
		}
			
	}
	
	public void render(Graphics2D g)
	{
		
	}
	
	public void onExit()
	{
		this.Block.Kill();
	}

}
