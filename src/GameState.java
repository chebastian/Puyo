import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;


public class GameState extends State implements MouseListener,MouseMotionListener, KeyListener{

	
	PuzzleBlock block;
	PuzzleField Field;
	
	float mLastKeyDown;
	public GameState(Game game)
	{
		super(game);
		Field = new PuzzleField(7, 13);
		block = new PuzzleBlock(new Point2D.Double(100,100));
		game.App.addKeyListener(Field);
		mLastKeyDown = 0.0f;
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
		
		if(mGame.IsKeyPressed('d'))
			Field.MoveActiveBlock(32, 0);
		
		else if(mGame.IsKeyPressed('a'))
			Field.MoveActiveBlock(-32, 0);
		else if(mGame.IsKeyPressed('s'))
			Field.DropActiveBlock();
		else if(mGame.IsKeyPressed('1'))
			Field.SetActiveBlockType(1);
		else if(mGame.IsKeyPressed('2'))
			Field.SetActiveBlockType(0);
		
		else if(mGame.IsKeyPressed('r'))
			Field.RotateActiveBlock();
	}
	
	public void Render(Graphics2D g)
	{
		Field.render(g);
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
