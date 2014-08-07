#pragma once

namespace SFMath
{
	template <class T>
	class Rect
	{
	public:

		Rect(void)
		{
		}

		Rect(const T& x, const T& y, const T& w, const T& h)
			:left(x),top(y),width(w),height(h)
		{
			right = left + width;
			bottom = top + height;
		}

		~Rect(void)
		{
		}

		void TranslateTo(const T& x, const T& y)
		{
			left = x; right = left + width;
			top = y; bottom = top + height;
		}

		void SetSize(const T& w, const T& h)
		{
			width = w; height = h;

			right = left + width;
			bottom = top + height;
		}

		const T& Left() const {return left;}
		const T& Right() const {return right;}
		const T& Bottom() const {return bottom;}
		const T& Top() const {return top;}
		const T& Width() const {return width;}
		const T& Height() const {return height;}
		const T& CenterX() const
		{
			return right - width*0.2f;
		}
		const T& CenterY() const {return this.bottom - this.height*0.2f;}

	private:
		T left, right, top, bottom;
		T width, height;
	};

	typedef Rect<int> iRect;
	typedef Rect<float> fRect;

}
