#include "Renderer.h"
#include <algorithm>

Renderer::Renderer(SDL_Surface* screen)
{
	mScreen = screen;
	mActiveScreen = screen;

	mScreens.push_back(screen);
	for(int i = 0; i < LAYER_SIZE; i++)
	{
		SDL_Surface* sr = SDL_CreateRGBSurface(SDL_HWSURFACE, 640,480,32,255,0,255,255);
		SDL_FillRect(sr,NULL, 0xFF00FFFF);
		mScreens.push_back(sr);
	}


	for(int i = 0; i < LAYER_SIZE; i++)
	{
		std::vector<IRenderable*> vec;
		mRenderObjects.push_back(vec);
	}

	//InitFontEngine();
}

Renderer::~Renderer(void)
{
}

void Renderer::InitFontEngine()
{
}

void Renderer::InitRenderer()
{

}

void Renderer::RemoveFromRenderList(IRenderable* r)
{
	IRenderable* ren = r;

	for(int i = 0; i < mRenderObjects.size(); i++)
	{
        for(int j = 0; j < mRenderObjects[i].size(); j++)
        {
            if(mRenderObjects[i][j] == r)
            {
                mRenderObjects[i].erase(mRenderObjects[i].begin() + j);
            }
        }
	}

/*	for(auto iter = mRenderObjects.begin(); iter != mRenderObjects.end(); iter++)
	{
		for(auto objIter = (*iter).begin(); objIter != (*iter).end(); objIter++)
		{
			if( (*objIter) == r)
			{
				(*iter).erase(objIter);
				return;
			}
		}
	}*/
}

void Renderer::Render(IRenderable* r)
{
	mRenderObjects.at(r->GetLayer()).push_back(r);
	//RenderToLayer(r,r->GetLayer());
}

void Renderer::AddSurface(SDL_Surface* surface)
{
	mScreens.push_back(surface);
}

void Renderer::SetActiveScreen(SDL_Surface* surface)
{
	mActiveScreen = surface;
}

void Renderer::RenderToScreen()
{
	SDL_Surface* screen = mScreen;

	for(RenderListIter iter = mRenderObjects.begin(); iter != mRenderObjects.end(); iter++)
	{
	    for(RenderObjIter i = (*iter).begin(); i != (*iter).end(); i++)
		{
			(*i)->Render(screen);
		}
	}

	for(RenderListIter i = mRenderObjects.begin(); i != mRenderObjects.end(); i++)
	{
		(*i).clear();
	}

}

void Renderer::RenderString(const std::string& str, SFMath::Vector2Df* pos,int color)
{

}

SFMath::iRect* MeasureString(const std::string& str, const std::string& fontName)
{
	SFMath::iRect rect;
	return &rect;
}

void Renderer::RenderTextureToScreen(SDL_Surface* screen)
{
	/*screen = mScreen;
	 int nOfColors = screen->format->BytesPerPixel;
	 GLenum texture_format = GL_RGB;
	 SDL_Surface* surface = screen;
	 GLuint texture;
        if (nOfColors == 4)     // contains an alpha channel
        {
                if (surface->format->Rmask == 0x000000ff)
                        texture_format = GL_RGBA;
        } else if (nOfColors == 3)     // no alpha channel
        {
                if (surface->format->Rmask == 0x000000ff)
                        texture_format = GL_RGB;
        } else {
                printf("warning: the image is not truecolor..  this will probably break\n");
                // this error should not go unhandled
        }

	// Have OpenGL generate a texture object handle for us
	glGenTextures( 1, &texture );

	// Bind the texture object
	glBindTexture( GL_TEXTURE_2D, texture );

	// Set the texture's stretching properties
        glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR );
        glTexParameteri( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR );

	// Edit the texture object's image data using the information SDL_Surface gives us
	glTexImage2D( GL_TEXTURE_2D, 0, nOfColors, surface->w, surface->h, 0,
                      texture_format, GL_UNSIGNED_BYTE, surface->pixels );

	glBindTexture( GL_TEXTURE_2D, texture );

	glBegin( GL_QUADS );
	//Bottom-left vertex (corner)
	glTexCoord2i( 0, 0 );
	glVertex3f( 100.f, 100.f, 0.0f );

	//Bottom-right vertex (corner)
	glTexCoord2i( 1, 0 );
	glVertex3f( 228.f, 100.f, 0.f );

	//Top-right vertex (corner)
	glTexCoord2i( 1, 1 );
	glVertex3f( 228.f, 228.f, 0.f );

	//Top-left vertex (corner)
	glTexCoord2i( 0, 1 );
	glVertex3f( 100.f, 228.f, 0.f );
	glEnd();

	SDL_GL_SwapBuffers();
	glDeleteTextures( 1, &texture );*/
}
