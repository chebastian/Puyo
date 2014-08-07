#pragma once

#include "IMouseListener.h"
#include "IkeyboardListener.h"

#include <vector>
#include "SDLWrapper.h"

class SFMouse
{
public:
	static SFMouse *CreateMouse(int* px, int* py)
	{
		return new SFMouse(px,py);
	}

	void RemoveMouse(SFMouse* mouse)
	{
		delete mouse;
	}

	const int X() const {return *m_pX;}
	const int Y() const {return *m_pY;}
	const float* DistX() const {return &mDistX;}
	const float* DistY() const {return &mDistY;}
	const bool LeftButton() const {return mLeftButton;}
	const bool RightButton() const {return mRightButton;}

	void SetDistanceSinceLastFrame(const float& x,const float& y)
	{
		mDistX = x; mDistY = y;
	}

	void SetLeftMouseButton(bool down)
	{
		mLeftButton = down;
	}

	void SetRightMouseButton(bool down)
	{
		mRightButton = down;
	}


protected:
	int* m_pX;
	int* m_pY;

	float mDistX, mDistY;
	bool mLeftButton, mRightButton;

	SFMouse(int* mx, int* my)
	{
		m_pX = mx;
		m_pY = my;

		mDistX = 0; mDistY = 0;
		mLeftButton = false; mRightButton = false;
	}

	~SFMouse()
	{
		m_pX = NULL;
		m_pY = NULL;
	}
};

class InputManager
{

public:
	InputManager(void);
	~InputManager(void);

	void AddMouseListener(IMouseListener* listener);
	void AddKeyboardListener(IKeyboardListener* listener);

	void RemoveMouseListener(IMouseListener* listener);
	void RemoveKeyboardListener(IKeyboardListener* listener);

	void ProcessInput(SDL_Event& evt);

	bool IsKeyDown(SDLKey key);

	const std::string GetInputCharacter(SDL_Event& evt);


private:
	void UpdateMouseSinceLastFrame(SDL_Event& evt);
	void UpdateKeyboardSinceLastFrame(SDL_Event& evt);
	void ProcessKeyDown(SDL_Event& evt, IKeyboardListener* listener);
	void ProcessMouseDown(SDL_Event& evt, IMouseListener* listener);
	void ProcessMouseReleased(SDL_Event& evt, IMouseListener* listener);
	void ProcessMouseMotion(SDL_Event& evt, IMouseListener* listener);
	void ProcessMouseClicked(SDL_Event& evt, IMouseListener* listener);
	void ProcessMouseDrag(SDL_Event& evt, IMouseListener* listener);
	void ProcessMouseOver(SDL_Event& evt, IMouseListener* listener);
	void ProcessMouseMove(SDL_Event& evt, IMouseListener* listener);

	std::vector<IMouseListener*> mMouseListeners;
	std::vector<IKeyboardListener*> mKeyListeners;

	int* mKeysDown;

	int mMouseX, mMouseY;
	SFMouse* mMouse;

	bool mLBLastState;
	bool mLBState;
	char mLastKeyDown;

	typedef std::vector<IMouseListener*>::iterator MouseListenerIter;
	typedef std::vector<IKeyboardListener*>::iterator KeyListenerIter;

};

