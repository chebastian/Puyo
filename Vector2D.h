#pragma once

namespace SFMath
{
	template <class T>
	class Point2D
	{
	public:

        T x, y;

		Point2D()
			:x(0),y(0)
		{
		}

		Point2D(T px, T py)
			:x(px),y(py)
		{
		}

		void operator += (const Point2D& p)
		{
			x += p.x;
			y += p.y;
		}

		void operator -= (const Point2D& p)
		{
			x -= p.x;
			y -= p.y;
		}
	};

	template <class V>
	class Vector2D : public Point2D<V>
	{
	public:

		Vector2D()
		{
		    this.x = 0;
			this.y = 0;
		}

		V Length()
		{
			return this.x+this.x*this.y+this.y;
		}

		Vector2D(V px, V py)
			:Point2D<V>(px,py)
		{

		}


		void operator *= (const float& p)
		{
			this.x *= p;
			this.y *= p;
		}

		Vector2D operator * (const float& p) const
		{
			Vector2D<V> temp;
			temp.x = this.x * p;
			temp.y = this.y * p;
			return temp;
		}
	};

	typedef Vector2D<float> Vector2Df;
	typedef Vector2D<int> Vector2Di;
};
