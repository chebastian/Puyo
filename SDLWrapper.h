#pragma once

#include "SDL.h"
#include <vector>
#include "Renderer.h"
#include "IState.h"

class InputManager;

typedef struct ColorRGB
{
	unsigned int r;
	unsigned int g;
	unsigned int b;

	ColorRGB(int red, int green, int blue)
		:r(red),g(green),b(blue)
	{}
} ColorRGB;

class SDLWrapper
{
public:

	static SDLWrapper* GetInstance()
	{
		static SDLWrapper instance;
		return &instance;
	}

	void Initialize();

	void InitOpelnGL();
	bool Update();
	void Render();

	void RenderString(const std::string& str, int x, int y, int incolor = 0xFFFFFF);
	float GetElapsedTime();
	const bool IsRunning() const {return mIsRunning;}
	SDL_Surface* Screen() {return mScreen;}
	const float& ElapsedTime() {return mElapsedTime;}

	void DrawPixel(SDL_Surface* screen, int x, int y, ColorRGB* rgb);
	void GetKeysDown(int& keys);
	bool IsKeyDown(SDLKey key);

	Renderer* GetRenderer() {return &mRenderer;}
	InputManager* GetInputManager() {return mInputMgr;}

	void ShutDown()
	{
		mIsRunning = false;
	}

	bool Render3D()
	{
		return mRender3D;
	}
	/*static Renderer* Renderer()
	{
		return SDLWrapper::GetInstance()->GetRenderer();
	}*/

private:

	SDLWrapper(void);
	~SDLWrapper(void);

	void ClearScreen();

	bool mIsRunning;
	SDL_Surface* mScreen, mOffScreen;
	int* mKeysDown;
	std::vector<IRenderable*> mEntitys;

	SDL_Surface* mTextSurface;
	InputManager* mInputMgr;

	IState* mCurrentState;
	Renderer mRenderer;

	float mElapsedTime;
	bool mRender3D;
};

#define SDLWRAPPER SDLWrapper::GetInstance()
#define INPUTMANAGER SDLWrapper::GetInstance()->GetInputManager()
#define RENDERER SDLWrapper::GetInstance()->GetRenderer()
