#pragma once
#include "SDL.h"

class IKeyboardListener
{
public:
	virtual void OnKeyClicked(SDL_Event& evt) = 0;
	virtual void OnKeyDown(SDL_Event& evt) = 0;
	virtual void OnKeyUp(SDL_Event& evt) = 0;

	virtual const std::string& KeyListenerId() = 0;
};