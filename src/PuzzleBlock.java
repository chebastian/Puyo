import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Vector;


public class PuzzleBlock extends RenderableEntity{

	static int BLOCK_W = 32;
	static int BLOCK_H = 32;
	
	public int BlockType;
	BlockState CurrentState;
	public Color Colour;
	public boolean Checked;
	private boolean StateChanged;
	public boolean Empty;
	public Point2D Index;
	public int num; 
	public int UniqueId;
	public int NextPositonY;
	public float Scale;
	protected static int UniqueIdCounter = 0;
	public static int FallSpeed = 100;
	
	public Color colors[] = {Color.GREEN,Color.blue,Color.RED,Color.PINK, Color.white};
	static Random rand = new Random();
	
	public PuzzleBlock(Point2D index, int w, int h)
	{
		super(0,new Rectangle((int)index.getX()*w, (int)index.getY()*h, w, h) );
		BlockType = 0;
		CurrentState = new IdleState(this);
		
		Colour = colors[4];
		Checked = false;
		
		BlockType = 4;
		NextPositonY = -1;
		num = 0;
		UniqueId = UniqueIdCounter++;
		Empty = true;
		Scale = 1.0f;
		StateChanged = false;
	}
	
	public PuzzleBlock(Point2D pos)
	{
		super(0,new Rectangle((int)pos.getX(), (int)pos.getY(), BLOCK_W, BLOCK_H) );
		BlockType = 0;
		CurrentState = new FallingState(this);
		
		int r = rand.nextInt(3);
		Colour = colors[r];
		Checked = false;
		
		BlockType = r;
		NextPositonY = -1;
		num = 0;
		UniqueId = UniqueIdCounter++;
		Empty = false;
		Scale = 1.0f;
	}
	
	public PuzzleBlock(PuzzleBlock b)
	{
		super(0,b.rect);
	}
	
	public void SnapToPosition(int x, int y)
	{
		int nx = x / BLOCK_W;
		int ny = y / BLOCK_H;
		SetPosition(nx * BLOCK_W, ny * BLOCK_H);
	}
	
	public void SetType(int t)
	{
		Colour = colors[t];
		BlockType = t;
	}
	
	public void DropBlockTo(int row)
	{
		NextPositonY = row;
		ChangeState(new FallingState(this));
	}
	
	public void update(float time)
	{
		CurrentState.update(time);
	}
	
	public void render(Graphics2D g)
	{
		int x = (int)rect.getX();
		int y = (int)rect.getY();
		g.setColor(Colour);
		
		if(!Alive)
			g.setColor(Color.black);
		
		g.fillRect(x + (int)((BLOCK_W-2)*1.0f-Scale), y + (int)((BLOCK_H-2)*1.0-Scale), (int)((BLOCK_W-2)*Scale), (int)((BLOCK_H-2)*Scale));
		
		CurrentState.render(g);
		

		//g.setColor(Color.black);
		//g.drawString(Integer.toString(num), x + BLOCK_W/2, y + BLOCK_H/2);
	}
	
	public void ChangeState(BlockState state)
	{
		if(CurrentState != null)
			CurrentState.onExit();
		
		CurrentState = state;
		CurrentState.onEnter();
	}
	
	public void StateIsChanged(boolean j)
	{
		StateChanged = j;
	}
	
	public boolean StateChanged()
	{
		return StateChanged;
	}
	public boolean BlockEquals(PuzzleBlock block)
	{
		return block.UniqueId == this.UniqueId;
	}
	public boolean BlocksTypeEqual(PuzzleBlock block)
	{
		return block.BlockType == BlockType;
	}
	
	public boolean IsInState(int state)
	{
		return state == CurrentState.StateID;
	}
	
	public void StepLeft()
	{
		rect.x -= BLOCK_W;
	}
	
	public void StepRight()
	{
		rect.x += BLOCK_W;
	}
	
	public int bottom()
	{
		return rect.y + rect.height;
	}
	
	public int top()
	{
		return rect.y;
	}

	
	
}
