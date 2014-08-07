#include "SDL.h"
#include "SDLWrapper.h"
#include <iostream>
#include <time.h>
#include "Vector2D.h"

int main(int argc, char* argv[])
{
	SDLWrapper* sdl = SDLWrapper::GetInstance();

	time_t seconds = time(NULL);
	float s = seconds;

	sdl->Initialize();
	//sdl->InitOpelnGL();

	ColorRGB c = ColorRGB(0,255,05);
	float x = 10;

	while(sdl->IsRunning())
	{
		sdl->Update();
		sdl->Render();
	}

	return 0;
}
