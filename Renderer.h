#pragma once

#include "IRenderable.h"
#include "Vector2D.h"
#include "SDL.h"
#include <vector>
#include "Rect.h"
#include <string>
#include <vector>

class Renderer
{
public:
	Renderer(SDL_Surface* screen);
	~Renderer(void);

	void InitFontEngine();
	void InitRenderer();

	void Render( IRenderable* r);
	void Render(const IRenderable* r, SFMath::Vector2Df pos);
	void RenderString(const std::string& str, SFMath::Vector2Df* pos, int color = 0xFFFFFF);

	void RemoveFromRenderList(IRenderable* r);
	void AddSurface(SDL_Surface* surface);
	void SetActiveScreen(SDL_Surface* surface);

	void RenderToScreen();

	void RenderTextureToScreen(SDL_Surface* screen);

	static const int LAYER_SIZE = 7;
	static const int LAYER_DEBUG = 6;
	static const int LAYER_HUD = 5;
	static const int LAYER_HUD1 = 4;
	static const int LAYVER_ITEM = 3;
	static const int LAYER_ITEM2 = 2;
	static const int LAYER_BKG = 1;
	static const int LAYER_BKG2 = 0;

private:

	void SortRenderObjects();

	void RenderToLayer(IRenderable* r, int l);
	SDL_Surface* GetLayer(int num);
	SDL_Surface* mActiveScreen;

	SDL_Surface* mScreen;
	std::vector<SDL_Surface*> mScreens;

	//typedef std::shared_ptr<SDL_Surface> SurfacePtr;
	//std::vector<SurfacePtr> mVecSurfaces;

	std::vector< std::vector<IRenderable*> > mRenderObjects;
	typedef std::vector< std::vector<IRenderable*> >::iterator RenderListIter;
	typedef std::vector<IRenderable*>::iterator RenderObjIter;
};

