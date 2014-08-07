#include "InputManager.h"
#include <algorithm>
#include "SFLogger.h"


InputManager::InputManager(void)
{
	mKeysDown = new int[SDLK_LAST];

	for(int i = 0; i < SDLK_LAST; i++)
	{
		mKeysDown[i] = false;
	}

	mMouseX = 0;
	mMouseY = 0;
	mMouse = SFMouse::CreateMouse(&mMouseX, &mMouseY);
	mLBLastState = false;
	mLBState = true;
}


InputManager::~InputManager(void)
{
}

void InputManager::AddKeyboardListener(IKeyboardListener* listener)
{
	mKeyListeners.push_back(listener);
}

void InputManager::AddMouseListener(IMouseListener* listener)
{
	mMouseListeners.push_back(listener);
}

void InputManager::RemoveMouseListener(IMouseListener* listener)
{
	for(int i = 0; i < mMouseListeners.size(); i++)
	{
		if(mMouseListeners[i]->MouseListenerId() == listener->MouseListenerId())
		{
			SFLogger::getInstance()->Print("MouseRemoved");
			SFLogger::getInstance()->Print(listener->MouseListenerId());
			mMouseListeners.erase(mMouseListeners.begin()+i);
		}
	}
}

void InputManager::RemoveKeyboardListener(IKeyboardListener* listener)
{
	for(int i = 0; i < mKeyListeners.size(); i++)
	{
		if(mKeyListeners[i]->KeyListenerId() == listener->KeyListenerId())
		{
			mKeyListeners.erase(mKeyListeners.begin() + i);
			return;
		}
	}
}

const std::string InputManager::GetInputCharacter(SDL_Event& evt)
{
	std::string character("");

	if(evt.type == SDL_KEYDOWN)
	{
		if(mLastKeyDown != (char)evt.key.keysym.unicode)
		{
			mLastKeyDown = evt.key.keysym.unicode;
			return std::string(&mLastKeyDown);
		}
	}

	return character;
}

bool InputManager::IsKeyDown(SDLKey key)
{
	return mKeysDown[key];
}

void InputManager::UpdateMouseSinceLastFrame(SDL_Event& evt)
{
	if(mMouse->LeftButton())
		mLBLastState = true;
	else
		mLBLastState = false;

	mMouseX = evt.motion.x;
	mMouseY = evt.motion.y;
}

void InputManager::ProcessInput(SDL_Event& evt)
{

	/*for(auto listener = mMouseListeners.rbegin(); listener != mMouseListeners.rend(); listener++)
	{
		ProcessMouseClicked(evt, (*listener));
		ProcessMouseDown(evt, (*listener));
		ProcessMouseDrag(evt, (*listener));
		ProcessMouseReleased(evt,(*listener));
	}*/

	for(int i = 0; i < mMouseListeners.size(); i++)
	{
		IMouseListener* listener = mMouseListeners[i];
		ProcessMouseClicked(evt, listener);
		ProcessMouseDown(evt, listener);
		ProcessMouseDrag(evt, listener);
		ProcessMouseReleased(evt,listener);
		ProcessMouseMove(evt,listener);
		ProcessMouseOver(evt,listener);
	}

	for(int i = 0; i < mKeyListeners.size(); i++)
	{
		ProcessKeyDown(evt,mKeyListeners[i]);
	}

	UpdateMouseSinceLastFrame(evt);
	UpdateKeyboardSinceLastFrame(evt);

}

void InputManager::ProcessMouseOver(SDL_Event& evt, IMouseListener* listener)
{
	if(listener->CheckMouseOver(mMouse))
	{
		listener->OnMouseOver(mMouse);
	}
}

void InputManager::ProcessMouseMove(SDL_Event& evt, IMouseListener* listener)
{
	if(evt.type == SDL_MOUSEMOTION)
	{
		listener->OnMouseMove(mMouse);
	}
}

void InputManager::ProcessMouseDrag(SDL_Event& evt, IMouseListener* listener)
{
	if(evt.type == SDL_MOUSEMOTION)
	{

		mMouse->SetDistanceSinceLastFrame(float(evt.motion.x - mMouseX), float(evt.motion.y - mMouseY));

		if(mMouse->LeftButton() || mMouse->RightButton())
		{
			listener->OnMouseDrag(mMouse);
		}
	}
}


void InputManager::ProcessMouseClicked(SDL_Event& evt, IMouseListener* listener)
{
	if(evt.type == SDL_MOUSEBUTTONDOWN)
	{
		mLBState = true;
		
		if(evt.button.button == SDL_BUTTON_LEFT)
			mMouse->SetLeftMouseButton(true);
		if(evt.button.button == SDL_BUTTON_RIGHT)
			mMouse->SetRightMouseButton(true);

		listener->OnMouseDown(mMouse);

		if(!mLBLastState && listener->CheckMouseOver(mMouse))
		{
			listener->OnClicked(mMouse);
		}
	}
}

void InputManager::ProcessMouseReleased(SDL_Event& evt, IMouseListener* listener)
{
	if(evt.type == SDL_MOUSEBUTTONUP)
	{
		mLBState = false;
		mLBLastState = false;

		if(evt.button.button == SDL_BUTTON_LEFT)
			mMouse->SetLeftMouseButton(false);
		if(evt.button.button == SDL_BUTTON_RIGHT)
			mMouse->SetRightMouseButton(false);

		listener->OnMouseReleased(mMouse);
	}
}

void InputManager::ProcessKeyDown(SDL_Event& evt, IKeyboardListener* listener)
{
	bool keyDown = false;
	bool keyReleased = false;

	if(evt.type == SDL_KEYDOWN)
	{
		if(!mKeysDown[evt.key.keysym.sym])
			listener->OnKeyClicked(evt);

		//mKeysDown[evt.key.keysym.sym] = true;
		keyDown = true;
		//mLastKeyDown = evt.key.keysym.unicode;

		listener->OnKeyDown(evt);
	} 

	if(evt.type == SDL_KEYUP)
	{
		if(mKeysDown[evt.key.keysym.sym])
			keyReleased = true;

		mKeysDown[evt.key.keysym.sym] = false;
		mLastKeyDown = 'ws';

		listener->OnKeyUp(evt);
	}
}

void InputManager::UpdateKeyboardSinceLastFrame(SDL_Event& evt)
{
	if(evt.type == SDL_KEYDOWN)
	{
		mKeysDown[evt.key.keysym.sym] = true;
		mLastKeyDown = evt.key.keysym.unicode;
	}
}

void InputManager::ProcessMouseDown(SDL_Event& evt, IMouseListener* listener)
{
}

