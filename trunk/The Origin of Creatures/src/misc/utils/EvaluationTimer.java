package misc.utils;

import configuration.FitnessConfiguration;

public class EvaluationTimer
{

	public EvaluationTimer()
	{
	}

	public void reset()
	{
		evaluationTime = 0;
	}

	public void update(float tpf)
	{
		evaluationTime += tpf;;
	}

	public boolean isEndReached()
	{
		return (FitnessConfiguration.FITNESS_EVALUATION_TIME < evaluationTime);
	}

	private float evaluationTime;
}
