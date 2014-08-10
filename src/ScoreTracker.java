
public class ScoreTracker {

	int mScore;
	
	public ScoreTracker()
	{
		mScore = 0;
	}
	
	public void increaseScore(int amount)
	{
		mScore += amount;
	}
	
	public void increaseScore(int clusterSize, int chain)
	{
		int sc = clusterSize * 100;
		sc += sc * (chain/2);
		mScore += sc;
	}
	
	public int getScore()
	{
		return mScore;
	}
	
	

}
