import java.math.BigDecimal;
import java.util.*;

public class Temperature 
{
	static double temperature = 25.0 + 273.16; //K
	static double atmospheric_pressure = 101.325; //kPa
	static double humidity = 30.0; //relative humidity
	static double air_velocity = 0.0; //m/s
	static double surface_area = 1.0; //Large cotton T-shirt * 2 m^2
	
	static double area_density = 0.100; //kg/m^2
	static double thickness = 0.006; //m
	static double hygroscopy = 50; //%
	
	static double intensity = 0;
	static double albedo = 0.4; //
	static double altitude = 50;//12AM:70 6PM:20http://aa.usno.navy.mil/cgi-bin/aa_altazw.pl?form=2&body=10&year=2016&month=8&day=7&intv_mag=10&place=&lon_sign=1&lon_deg=128&lon_min=&lat_sign=1&lat_deg=36&lat_min=&tz=9&tz_sign=1
	static double body_temperature = 36.5 + 273.16; //K
	
	public static void main(String args[])
	{
		set_indoor();
		calculate_temperature_rate(temperature, atmospheric_pressure, humidity, air_velocity, surface_area, intensity, albedo, thickness, area_density, hygroscopy);
		System.out.println("");
		
		set_outdoor();
		calculate_temperature_rate(temperature, atmospheric_pressure, humidity, air_velocity, surface_area, intensity, albedo, thickness, area_density, hygroscopy);
		System.out.println("");
		
		System.out.println(calculate_wetbulb_temperature(40 + 273.16, 50));
	}
	
	public static void set_indoor()
	{
		temperature = 20.0 + 273.16; //K
		atmospheric_pressure = 101.325; //kPa
		humidity = 30.0; //relative humidity
		air_velocity = 0.0; //m/s
		surface_area = 1.0; //Large cotton T-shirt * 2 m^2
		
		area_density = 0.500; //kg/m^2
		hygroscopy = 50; //%
		
		intensity = 0;
		albedo = 0.6;
		altitude = 50;
		body_temperature = 36.5 + 273.16; //K
	}

	public static void set_outdoor()
	{
		temperature = 30.0 + 273.16; //K
		atmospheric_pressure = 101.325; //kPa
		humidity = 30.0; //relative humidity
		air_velocity = 2.5; //m/s
		surface_area = 1.0; //Large cotton T-shirt * 2 m^2
		
		area_density = 0.500; //kg/m^2
		hygroscopy = 50; //%
		
		intensity = 800; //
		albedo = 0.6;
		altitude = 50;
		body_temperature = 36.5 + 273.16; //K
	}
	
	//public static double calculate_temperature
	
	public static double calculate_saturation_pressure(double temperature)
	{
		double saturation_pressure = 
				0.6112 * Math.exp((17.67 * (temperature - 273.16)) / (temperature - 29.66));
		
		String information = String.format("%30s  %.15s %s", "saturation pressure:", new BigDecimal(saturation_pressure), "(kPa)");
		System.out.println(information);
		
		return saturation_pressure;
	}
	
	public static double calculate_partial_pressure(double temperature, double humidity)
	{
		double saturation_pressure =
				calculate_saturation_pressure(temperature);
		
		double partial_pressure =
				saturation_pressure * humidity / 100;
		
		String information = String.format("%30s  %.15s %s", "partial pressure:", new BigDecimal(partial_pressure), "(kPa)");
		System.out.println(information);
		
		return partial_pressure;
	}
	
	public static double calculate_humidity_ratio(double temperature, double atmospheric_pressure, double humidity)
	{
		double vapor_constant = 
				0.62199;
		
		double partial_pressure = 
				calculate_partial_pressure(temperature, humidity);
		
		double humidity_ratio = 
				vapor_constant * partial_pressure / (atmospheric_pressure - partial_pressure);
		
		String information = String.format("%30s  %.15s", "humidity ratio:", new BigDecimal(humidity_ratio));
		System.out.println(information);
		
		return humidity_ratio;
	}
	
	public static double calculate_saturation_humidity_ratio(double temperature, double atmospheric_pressure, double humidity)
	{
		double vapor_constant = 
				0.62199;

		double saturation_pressure =
				calculate_saturation_pressure(temperature);
		
		double saturation_humidity_ratio = 
				vapor_constant * saturation_pressure / (atmospheric_pressure - saturation_pressure);
		
		String information = String.format("%30s  %.15s", "saturation humidity ratio:", new BigDecimal(saturation_humidity_ratio));
		System.out.println(information);
		
		return saturation_humidity_ratio;
	}
	
	public static double calculate_evaporation_rate(double temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area)
	{
		double evaporation_coefficient = 
				25 + 19*air_velocity;
		
		double saturation_humidity_ratio = 
				calculate_saturation_humidity_ratio(temperature, atmospheric_pressure, humidity);
		
		double humidity_ratio = 
				calculate_humidity_ratio(temperature, atmospheric_pressure, humidity);
		
		double evaporation_rate = //kg/s
				evaporation_coefficient * surface_area * (saturation_humidity_ratio - humidity_ratio) / 3600;
		
		String information = String.format("%30s  %.15s %s", "evaporation rate:", new BigDecimal(evaporation_rate*1000), "(g/s)");
		System.out.println(information);
		
		return evaporation_rate;
	}
	
	public static double calculate_heat_rate(double temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double intensity, double albedo, double thickness)
	{
		double water_evaporation_heat = //J/kg
				2257;
		
		double evaporation_rate = //kg/s
				calculate_evaporation_rate(temperature, atmospheric_pressure, humidity, air_velocity, surface_area);
	
		double evaporation_heat_rate = 
				water_evaporation_heat * evaporation_rate * -1000;
		
		double radiation_heat_rate = 
				intensity * albedo / Math.sin(altitude/180*Math.PI) * Math.sin((90-altitude)/180*Math.PI);
		
		double conduction_heat_rate =
				calculate_conduction_heat_rate(temperature, surface_area, thickness);
		
		double total_heat_rate = 
				evaporation_heat_rate + radiation_heat_rate + conduction_heat_rate;
		
		String information1 = String.format("%30s %.15s %s", "evaporation heat rate:", new BigDecimal(evaporation_heat_rate), "(W)");
		System.out.println(information1);
		
		String information2 = String.format("%30s %.15s %s", "radiation heat rate:", new BigDecimal(radiation_heat_rate), "(W)");
		System.out.println(information2);
		
		String information3 = String.format("%30s %.15s %s", "conduction heat rate:", new BigDecimal(conduction_heat_rate), "(W)");
		System.out.println(information3);
		
		String information4 = String.format("%30s %.15s %s", "total heat rate:", new BigDecimal(total_heat_rate), "(W)");
		System.out.println(information4);
		
		return total_heat_rate;
	}
	
	public static double calculate_temperature_rate(double temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double intensity, double albedo, double thickness, double area_density, double hygroscopy)
	{
		double cotton_specific_heat = 
				1100; //J/kgC
		
		double water_specific_heat = 
				4186; //J/kgC
		
		double heat_capacity = //J/C
				cotton_specific_heat * surface_area * area_density + hygroscopy * water_specific_heat * surface_area * area_density;
		
		double total_heat_rate = 
				calculate_heat_rate(temperature, atmospheric_pressure, humidity, air_velocity, surface_area, intensity, albedo, thickness);
		
		double temperature_rate = 
				total_heat_rate / heat_capacity;
				
		String information = String.format("%30s %.16s %s", "temperature rate:", new BigDecimal(temperature_rate), "(C/s)");
		System.out.println(information);
		
		return temperature_rate;
	}
	
	////////////////////////////////////////////////////
	public static double calculate_dewpoint_temperature(double temperature, double humidity)
	{
		double partial_pressure = 
				calculate_partial_pressure(temperature, humidity);
		
		double dewpoint_temperature = 
				(243.5 * Math.log(partial_pressure/0.6122))/(17.67 - Math.log(partial_pressure/0.6122));
		
		String information = String.format("%30s  %.15s", "dewpoint temperature:", new BigDecimal(dewpoint_temperature));
		System.out.println(information);
		
		return dewpoint_temperature;
	}
	
	public static double calculate_wetbulb_temperature(double temperature, double humidity)
	{
		double dewpoint_temperature = 
				calculate_dewpoint_temperature(temperature, humidity);
		
		double wetbulb_temperature = 
				(temperature-273.16) * Math.atan(0.151977 * Math.pow(humidity + 8.313659, 0.5)) 
				+ Math.atan((temperature-273.16) + humidity) - Math.atan(humidity - 1.676331)
				+ 0.00391838 * Math.pow(humidity, 1.5) * Math.atan(0.023101 * humidity) - 4.686035;
		
		String information = String.format("%30s  %.15s", "wetbulb temperature:", new BigDecimal(wetbulb_temperature));
		System.out.println(information);

		return wetbulb_temperature;
	}
	
	///////////////////////////////////////////////////conduction
	
	public static double calculate_conduction_heat_rate(double temperature, double surface_area, double thickness)
	{
		double water_thermal_conductivity = 0.63;//W/mK
		double body_temperature = 36.5 + 273.16;//K
		double air_heat_capacity = 1004;//J/kgK
		double skin_heat_capacity = 3391;
		double water_heat_capacity = 4178;
		
		double wetbulb_temperature = calculate_wetbulb_temperature(temperature, humidity);
		
		double air_conduction_heat_rate = water_thermal_conductivity * surface_area * (temperature - wetbulb_temperature) / thickness * (air_heat_capacity / water_heat_capacity);
		double skin_conduction_heat_rate = water_thermal_conductivity * surface_area * (body_temperature - wetbulb_temperature) / thickness * (skin_heat_capacity / water_heat_capacity);
		
		double conduction_heat_rate = air_conduction_heat_rate + skin_conduction_heat_rate;
		
		return conduction_heat_rate;
	}
	
	//public static double calculate_temperature_transfer_rate(double temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double area_density, double hygroscopy, double body_temperature)
	{
		
	}
}
