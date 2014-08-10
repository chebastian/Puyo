import java.awt.Graphics2D;
import java.awt.Rectangle;


public class RenderableEntity extends Entity
{
	Rectangle rect;
	public RenderableEntity(int id, Rectangle r)
	{
		super(id);
		rect = r;
	}
	
	public void Move(float x, float y)
	{
		rect.x += x;
		rect.y += y;
	}
	
	public void update(float time)
	{
		super.update(time);
	}
	
	public void render(Graphics2D g)
	{
		
	}
	
	public void SetPosition(int x, int y)
	{
		rect.x = x;
		rect.y = y;
	}
	
	public int getX()
	{
		return (int)rect.x;
	}
	
	public int getY()
	{
		return (int)rect.y;
	}
}
