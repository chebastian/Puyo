import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

//THIS IS MY LASTEST COMMENT
public class GameState extends State implements MouseListener,MouseMotionListener, KeyListener{

	
	PuzzleBlock block;
	PuzzleField Field; 
	float mLastKeyDown;
	
	Font mScoreFont;
	Point mScorePosition; 

	public GameState(Game game)
	{
		super(game);
		Field = new PuzzleField(7, 13);
		block = new PuzzleBlock(new Point2D.Double(100,100));
		game.App.addKeyListener(Field);
		mLastKeyDown = 0.0f;
		
		mScoreFont = new Font("Consolas", 0, 48);
		mScorePosition = new Point(300, 80);
	}
	
	public void Update(float time)
	{
		Field.update(time);
		mLastKeyDown += time;
		HandleInput();
	}
	
	public void HandleInput()
	{
		if(mLastKeyDown	<= 0.1f)
		{
			return;
		}

		//this is a not so major change 
		
		if(mGame.IsKeyPressed('d') && Field.CanMoveActiveBlockInDirection(1))
			Field.MoveActiveBlock(32, 0);
		
		else if(mGame.IsKeyPressed('a') && Field.CanMoveActiveBlockInDirection(-1))
			Field.MoveActiveBlock(-32, 0);
		
		else if(mGame.IsKeyPressed('s'))
			Field.DropActiveBlock();
		else if(mGame.IsKeyPressed('1'))
			Field.SetActiveBlockType(1);
		else if(mGame.IsKeyPressed('2'))
			Field.SetActiveBlockType(0);
		
		else if(mGame.IsKeyPressed('r'))
			Field.RotateActiveBlock();
		
		else if(mGame.IsKeyPressed('t'))
		{
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void Render(Graphics2D g)
	{
		Field.render(g);
		
		g.setFont(mScoreFont);
		g.drawString("LastSz: " + Field.getLastClusterSize(), mScorePosition.x, mScorePosition.y);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
