#include "SDLWrapper.h"
#include <iostream>
#include "InputManager.h"

#include <stdlib.h>

SDLWrapper::SDLWrapper(void)
	:mRenderer(NULL)
{
	mKeysDown = new int[SDLK_LAST];

	for(int i = 0; i < SDLK_LAST; i++)
	{
		mKeysDown[i] = false;
	}

	mRender3D = false;
	mElapsedTime = 1.0f;
}


SDLWrapper::~SDLWrapper(void)
{
}

void SDLWrapper::Initialize()
{
	if ( SDL_Init(SDL_INIT_AUDIO|SDL_INIT_VIDEO) < 0 ) {
		fprintf(stderr, "Unable to init SDL: %s\n", SDL_GetError());
		exit(1);
	}


	SDL_EnableUNICODE(SDL_ENABLE);
	mScreen = SDL_SetVideoMode(640,480,32,SDL_SWSURFACE | SDL_DOUBLEBUF);
	mIsRunning = true;

	mRenderer = Renderer(mScreen);
	mRenderer.AddSurface(SDL_CreateRGBSurface(SDL_SWSURFACE, 640,840,32,255,0,255,0));
	mRenderer.SetActiveScreen(mScreen);

	mRender3D = false;
}

//
//Inactive function
//
void SDLWrapper::InitOpelnGL()
{
	/*if ( SDL_Init(SDL_INIT_AUDIO|SDL_INIT_VIDEO) < 0 ) {
		fprintf(stderr, "Unable to init SDL: %s\n", SDL_GetError());
		exit(1);
	}


	SDL_EnableUNICODE(SDL_ENABLE);
	SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER,1);
	mScreen = SDL_SetVideoMode(640,480,32, SDL_OPENGL);
	mIsRunning = true;
	mRenderer = Renderer(mScreen);


//	Room* ent = new Room("room1","./media/xml/testRoom.xml");
	//Entity* ent2 = new Entity("ent2");

//	SFMessage mess = SFMessage::GlobalMessage("Message Text", ent->GetID(), 1.0f);
	//ent->SendGlobalMessage("IM GLOBAL MESS", 3.0f);

	//mEntitys.push_back(ent);

	//mRenderer.AddSurface(mScreen);
	mRenderer.AddSurface(SDL_CreateRGBSurface(SDL_SWSURFACE, 640,840,32,255,0,255,0));
	mRenderer.SetActiveScreen(mScreen);
	//mCurrentState = new EditorState();

	glEnable( GL_TEXTURE_2D );

	glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );

	glViewport( 0, 0, 640, 480 );

	glClear( GL_COLOR_BUFFER_BIT );

	glMatrixMode( GL_PROJECTION );
	glLoadIdentity();

	glOrtho(0.0f, 640, 480, 0.0f, -1.0f, 1.0f);

	glMatrixMode( GL_MODELVIEW );
	glLoadIdentity();

	mRender3D = true;*/
}

void SDLWrapper::RenderString(const std::string& str, int x, int y, int incolor)
{
	/*if(!TTF_WasInit())
	{
		if(TTF_Init() != -1)
		{
				mFontTTF = TTF_OpenFont("./media/fonts/arial.ttf",14);
		}
	}

	SDL_Surface* text;
	SDL_Color color,colorBG;
	color.r = 255; color.g = 0; color.b = 255;
	colorBG.r = 100; colorBG.g = 100; colorBG.b = 100;
	SDL_Rect rect, srcRect;
	rect.x = x; rect.y = y;
	int w = NULL;
	int h = NULL;

	color.b = (char)incolor;
	color.g = (int)incolor >> 8;
	color.r = (int)incolor >> 16;

	mTextSurface = TTF_RenderText_Solid(mFontTTF, str.c_str(), color);
	w = mTextSurface->w; h = mTextSurface->h;
	rect.w = w; rect.h = h;

	srcRect.x = 0; srcRect.y = 0; srcRect.w = w; srcRect.h = h;
	SDL_BlitSurface(mTextSurface, &srcRect, mScreen, &rect);

	SDL_FreeSurface(mTextSurface);*/

}

void SDLWrapper::Render()
{

	mRenderer.RenderToScreen();

	if(!mRender3D)
	{
		SDL_Flip(mScreen);
		//SDL_GL_SwapBuffers();
		ClearScreen();
	}
}

bool SDLWrapper::Update()
{
	SDL_Event evt;

	static Uint32 lastTime = 0;
	Uint32 now = SDL_GetTicks();
	float elapsed = (now - lastTime)/1000.0f;
	mElapsedTime = elapsed;
	//mCurrentState->UpdateState(elapsed);

	while(SDL_PollEvent(&evt))
	{
//		mInputMgr->ProcessInput(evt);

		if(evt.type == SDL_QUIT)
			mIsRunning = false;
	}

	lastTime = now;

	return mIsRunning;
}

void SDLWrapper::DrawPixel(SDL_Surface* screen, int x, int y, ColorRGB* rgb)
{
	Uint32 col = SDL_MapRGB(screen->format, rgb->r,rgb->g,rgb->b);
	if(SDL_MUSTLOCK(screen))
	{
		if(SDL_LockSurface(screen) < 0)
			return;
	}

	Uint32* pixel =	(Uint32*) screen->pixels + y * screen->pitch/4 +x;
	*pixel = col;

	if(SDL_MUSTLOCK(screen))
	{
		SDL_UnlockSurface(screen);
	}
}

void SDLWrapper::ClearScreen()
{
	SDL_Surface* screen = Screen();
	ColorRGB rgb = ColorRGB(0,0,0);

	Uint32 col = SDL_MapRGB(screen->format, rgb.r,rgb.g,rgb.b);
	//rgb = col;
	if(SDL_MUSTLOCK(screen))
	{
		if(SDL_LockSurface(screen) < 0)
			return;
	}

	for(int x = 0; x < mScreen->w; x++)
	{
		for(int y = 0; y < mScreen->h; y++)
		{
			Uint32* pixel =	(Uint32*) screen->pixels + y * screen->pitch/4 +x;
			*pixel = col;
		}
	}

	if(SDL_MUSTLOCK(screen))
	{
		SDL_UnlockSurface(screen);
	}

	//SDL_UpdateRect(screen, 0, 0, screen->w,screen->h);
}

bool SDLWrapper::IsKeyDown(SDLKey key)
{
	return mKeysDown[key];
}

float SDLWrapper::GetElapsedTime()
{
	static float lastTime = 0;
	Uint32 now = SDL_GetTicks();

	float elapsedTime = (now - lastTime)/1000.0f;

	lastTime = now;

	return elapsedTime;
}

