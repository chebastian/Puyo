import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Game implements KeyListener, MouseMotionListener, MouseListener
{
	public int WIDTH , HEIGHT;
	boolean mKeyArr[];
	public Random Random = new Random();
	//public Applet App;
	public BufferedImage InvaderImg, AstroidImg, TilesImg;
	public boolean Paused;
	public MapEdit App;
	public State mCurrentState;
	public int TILE_WIDTH, TILE_HEIGHT;
	
	public int MouseX, MouseY;
	public boolean MouseLeft, MouseRight;
	
	public int mNumFadingBlocks;
	
	public Game(Dimension size, MapEdit frame)
	{
		App = frame;
		/*File f = new File(".");
		try {
			System.out.print(f.getCanonicalPath());
		    InvaderImg = ImageIO.read(new File("./media/images/invader2x.png"));
		} catch (IOException e) {	
			System.out.print(e.getMessage());
		}
		
		try {
		    AstroidImg = ImageIO.read(new File("./media/images/astroids.png"));
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}*/
		
		try{
			TilesImg = ImageIO.read(new File("./media/blocks.bmp"));
		} catch (IOException e){
			System.out.print(e.getMessage());
		}
		
		TILE_HEIGHT = 16;
		TILE_WIDTH = 16;
		WIDTH = size.width;
		HEIGHT = size.height;
		mKeyArr = new boolean[256];
		Paused = false;
		GameState state = new GameState(this);
		mCurrentState = state;
		ChangeState(state);
		App.addMouseListener(state);
		App.addMouseMotionListener(state);
		App.addMouseMotionListener(this);
		App.addMouseListener(this);
		MouseX = 0; MouseY = 0;
		MouseLeft = false;
		MouseRight = false;
		mNumFadingBlocks = 0;
		
	}
	
	public void ChangeState(State s)
	{
		mCurrentState.OnExit(this);
		mCurrentState = s;
		s.OnEnter(this);
	}
	
	
	public Dimension getSize()
	{
		return new Dimension(WIDTH, HEIGHT);
	}
	
	public void Update(float time)
	{	
		if(!Paused)
			mCurrentState.Update(time);
		
		if(App.IsKeyDown('p'))
			Paused = !Paused;
		
	}
	
	public void Render(Graphics2D g)
	{
		mCurrentState.Render(g);
		if(Paused)
		{
			g.setColor(Color.red);
			g.drawString("PAUSED", 100, 200);
		}
	}
	
	public boolean IsKeyDown(char c)
	{
		return App.IsKeyDown(c);
	}
	
	public boolean IsKeyPressed(char c)
	{	
		return App.IsKeyPressed(c);
	}
	
	public void HandleInput()
	{
		
	}
	
	public void addBlockToFadeCounter(PuzzleBlock block){
		mNumFadingBlocks += 1;
	}

	public void removeBlockToFadeCounter(PuzzleBlock block){
		mNumFadingBlocks -= 1;
	}
	
	public void clearComboCounter()
	{
		mNumFadingBlocks = 0;
	}
	
	public int numBlocksFading()
	{
		return mNumFadingBlocks;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		//mKeyArr[arg0.getKeyChar()] = true;
		
		if(arg0.getKeyChar() == 'p')
			Paused = !Paused;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		//mKeyArr[arg0.getKeyChar()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseDragged(MouseEvent evt)
	{
		MouseX = evt.getX() - 10;
		MouseY = evt.getY() - 30;
	}
	
	public void mouseMoved(MouseEvent evt)
	{
		MouseX = evt.getX() - 10;
		MouseY = evt.getY() - 30;
	}
	
	public void mouseClicked(MouseEvent evt)
	{

	}
	
	public void mouseExited(MouseEvent evt)
	{
		
	}
	
	public void mousePressed(MouseEvent evt)
	{
		if(evt.getButton() == MouseEvent.BUTTON1)
			MouseLeft = true;
		else if(evt.getButton() == MouseEvent.BUTTON2	|| evt.getButton() == MouseEvent.BUTTON3)
			MouseRight = true;
	}
	
	public void mouseEntered(MouseEvent evt)
	{
		
	}
	
	public void mouseReleased(MouseEvent evt)
	{
		if(evt.getButton() == MouseEvent.BUTTON1)
			MouseLeft = false;
		else if(evt.getButton() == MouseEvent.BUTTON2	|| evt.getButton() == MouseEvent.BUTTON3)
			MouseRight = false;
	}
}



 class MapEdit extends JFrame{

	/**
	 * @param args
	 */
	
	public static final int WIN_WIDTH = 800;
	public static final int WIN_HEIGHT = 600;
	MyPanel panel;
	JFrame window;
	boolean mKeyArr[];
	boolean mLastKeyArr[];
	long lastTime = 0;
	float elapsedTime = 0.0f;
	Game game;
	
	float x = 0;
	public MapEdit()
	{
		super("Puyo");
		panel = new MyPanel();
		setSize(WIN_WIDTH,WIN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		setVisible(true);
		setBackground(Color.black);
		
		mKeyArr = new boolean[256];
		mLastKeyArr = new boolean[256];
		lastTime = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public boolean IsKeyPressed(char k)
	{	
		boolean now = mKeyArr[k];
		boolean last = mLastKeyArr[k];
		if((now) && (!last))
			return true;
		
		return false;
	}
	public boolean IsKeyDown(char k)
	{
		return mKeyArr[k];
	}
	
	public void GameStart()
	{
		game = new Game(new Dimension(WIN_WIDTH, WIN_HEIGHT),this);
		addKeyListener(game);
		Thread gameThread = new Thread()
		{
			@Override
			public void run()
			{
				GameLoop();
			}
		};
		
		gameThread.start();
	}
	
	int DESIRED_FPS = 60;
	int SKIPPED_TICKS = 1000 / DESIRED_FPS;
	int MAX_SKIPPED = 10;
	float MAX_FPS = 1.0f / 30.0f;
	long nextFrame = 0;
	float lastElapsed = 1.0f / 60.0f;
	public void GameLoop()
	{
		while(true)
		{
			long time = System.currentTimeMillis();
			float elapsed = (System.currentTimeMillis() - lastTime )/ 1000.0f;
			
			long sinceLast = time - lastTime;
			long toNext = time + SKIPPED_TICKS;
			long toWait = 15;
			if(elapsed >= MAX_FPS || elapsed < 0.0f)
			{
				elapsed = lastElapsed;
			}
			else
			{
				float current = (System.currentTimeMillis() - lastTime)/1000.0f;
				float desire = MAX_FPS;
				float wait = desire - current;
				toWait = (long)(wait*1000.0);
				lastElapsed = MAX_FPS;
			}
			
			elapsedTime = elapsed;
			GameUpdate(elapsed);
			repaint();
			
			try
			{
				Thread.sleep(toWait);
				lastTime = time;
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			lastTime = time;

		}
	}
	
	float seconds = 0.0f;
	public void GameUpdate(float time)
	{
		seconds += time;
		x += time * 25.0f;
		totalTime += time;
		
		game.Update(time);
		
		
		for(int i = 0; i < mKeyArr.length; i++)
		{
			if(mKeyArr[i])
			{
				if(!mLastKeyArr[i])
				{
					mLastKeyArr[i] = mKeyArr[i];
				}
			}
		}
	}
	
	int frame = 0;
	int fps = 0;
	float totalTime = 0.0f;
	public void GameDraw(Graphics2D g)
	{
		
		if(game == null)
		{
			
		}
		else
		{
			game.Render(g);
		}
		
		//g.setColor(Color.white);
		//g.drawString("FPS: " + fps, 10, 50);
		frame++;
		
		if(totalTime >= 1.0f)
		{
			totalTime = 0.0f;
			fps = frame;
			frame = 0;
		}
	}
	
	

	private class MyPanel extends JPanel implements KeyListener
	{
		
		Graphics mBuffer;
		BufferedImage off_Image;
		
		final int APPWIDTH = WIN_HEIGHT;
		final int APPHEIGHT = WIN_WIDTH;
	
		long lastTime = 0;
		boolean mRunning = true;
		
		
	    public MyPanel() 
	    {
	        setBorder(BorderFactory.createLineBorder(Color.black));
	        setFocusable(true);
	        addKeyListener(this);
	    }
		
		public void init()
		{
			off_Image = new BufferedImage(WIN_WIDTH,WIN_HEIGHT,BufferedImage.TYPE_INT_RGB);
			mBuffer = off_Image.getGraphics();
		}
		
	    @Override
		public Dimension getPreferredSize() {
	        return new Dimension(WIDTH,HEIGHT);
	    }

	    @Override
		public void paintComponent(Graphics g) {
	        Graphics2D g2d = (Graphics2D)g;
	        super.paintComponent(g2d);
	        
	        GameDraw(g2d);
	        
	    }

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			mKeyArr[e.getKeyChar()] = false;
			mLastKeyArr[e.getKeyChar()] = false;
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

			/*if( (mLastKeyArr[e.getKeyChar()] == false) )
			{
				System.out.print(" HELLLO ");
				System.out.print(mKeyArr[e.getKeyChar()]);
			}
			
			
			if(mKeyArr[e.getKeyChar()])
				mLastKeyArr[e.getKeyChar()] = true;
			else
				mLastKeyArr[e.getKeyChar()] = false;*/
			

			
			mKeyArr[e.getKeyChar()] = true;
		}
	}
	
	public static void main(String[] args) {
		
		MapEdit game = new MapEdit();
		game.GameStart();
		
		game.elapsedTime = 0.0f;
	}
}


