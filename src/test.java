
public class test 
{
	public static void main(String args[])
	{
		double air_velocity = 0;
		
		double output = 10.45 - air_velocity + 10 * Math.pow(air_velocity, 1.0/2);
		System.out.println(output);
	
		output = Math.pow(air_velocity, 1.0/2);
		System.out.println(output);
		
		air_velocity = 2.5;
		
		output = 10.45 - air_velocity + 10 * Math.pow(air_velocity, 1.0/2);
		System.out.println(output);
	
		output = Math.pow(air_velocity, 1.0/2);
		System.out.println(output);
		
		air_velocity = 0;
		
		output = 10.45 - air_velocity + 10 * Math.pow(air_velocity, 1.0/2);
		System.out.println(output);
	
		output = Math.pow(air_velocity, 1.0/2);
		System.out.println(output);
	}
}
