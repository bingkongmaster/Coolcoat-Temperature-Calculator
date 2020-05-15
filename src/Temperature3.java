import java.math.BigDecimal;
import java.util.*;

public class Temperature3
{
	static double temperature = 25.0 + 273.16; //K
	static double atmospheric_pressure = 101.325; //kPa
	static double humidity = 30.0; //relative humidity
	static double air_velocity = 1.0; //m/s
	static double surface_area = 1.0; //Large cotton T-shirt * 2 m^2
	
	static double area_density = 0.100; //kg/m^2
	static double thickness = 0.006; //m
	static double hygroscopy = 50; //%
	
	static double intensity = 1;
	static double albedo = 0.4; //
	static double altitude = 52;//12AM:70 6PM:20http://aa.usno.navy.mil/cgi-bin/aa_altazw.pl?form=2&body=10&year=2016&month=8&day=7&intv_mag=10&place=&lon_sign=1&lon_deg=128&lon_min=&lat_sign=1&lat_deg=36&lat_min=&tz=9&tz_sign=1
	static double body_temperature = 36.5 + 273.16; //K
	
	public static void main(String args[])
	{
		System.out.println("\nIndoor");
		set_indoor();
		print_data(temperature, atmospheric_pressure, humidity, air_velocity, surface_area, intensity, albedo, thickness, altitude, area_density, hygroscopy);

		System.out.println("\nOutdoor");
		set_outdoor();
		print_data(temperature, atmospheric_pressure, humidity, air_velocity, surface_area, intensity, albedo, thickness, altitude, area_density, hygroscopy);
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
		altitude = 52;
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
		altitude = 52;
		body_temperature = 36.5 + 273.16; //K
	}
	
	//public static double calculate_temperature
	
	public static double calculate_saturation_pressure(double temperature)
	{
		double saturation_pressure = 
				0.6112 * Math.exp((17.67 * (temperature - 273.16)) / (temperature - 29.66));
		
		return saturation_pressure;
	}
	
	public static double calculate_partial_pressure(double temperature, double humidity)
	{
		double saturation_pressure =
				calculate_saturation_pressure(temperature);
		
		double partial_pressure =
				saturation_pressure * humidity / 100;
		
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
		
		return evaporation_rate;
	}
	
	public static double calculate_evaporation_heat_rate(double temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area)
	{
		double water_evaporation_heat = //J/kg
				-2257000;
		
		double evaporation_rate = //kg/s
				calculate_evaporation_rate(temperature, atmospheric_pressure, humidity, air_velocity, surface_area);
	
		double evaporation_heat_rate = 
				water_evaporation_heat * evaporation_rate;
		
		return evaporation_heat_rate;
	}
	
	public static double calculate_radiation_heat_rate(double intensity, double albedo, double altitude)
	{
		double radiation_heat_rate = 
				intensity * albedo / Math.sin(altitude/180*Math.PI) * Math.sin((90-altitude)/180*Math.PI);
		
		return radiation_heat_rate;
	}
	
	public static double calculate_water_conduction_heat_rate(double temperature, double surface_area, double thickness, double humidity)
	{
		double skin_temperature = 36.5 + 273.16;//K
		double water_thermal_conductivity = 0.63;//W/mK fiber-reinforced plastic can have similar thermal conductivity to water
		
		double wetbulb_temperature = calculate_wetbulb_temperature(temperature, humidity);//;
		
		double water_conduction_heat_rate = water_thermal_conductivity * surface_area * (wetbulb_temperature - skin_temperature) / thickness;
		
		return water_conduction_heat_rate;
	}
	
	public static double calculate_air_conduction_heat_rate(double temperature, double surface_area, double thickness, double humidity)
	{
		double skin_temperature = 36.5 + 273.16;//K
		double water_thermal_conductivity = 0.63;//W/mK laminate can have similar thermal conductivity to water
		
		double wetbulb_temperature = calculate_wetbulb_temperature(temperature, humidity);//;
		
		double air_conduction_heat_rate = water_thermal_conductivity * surface_area * (temperature - wetbulb_temperature) / thickness;
		
		return air_conduction_heat_rate;
	}
	
	public static double calculate_skin_conduction_heat_rate(double surface_area, double thickness, double humidity)
	{	
		double skin_conduction_heat_rate = 85; //average men powers 100W heat

		return skin_conduction_heat_rate;
	}
	
	public static double calculate_conduction_heat_rate(double temperature, double surface_area, double thickness, double humidity)
	{	
		double air_conduction_heat_rate = calculate_air_conduction_heat_rate(temperature, surface_area, thickness, humidity);
		double skin_conduction_heat_rate = calculate_skin_conduction_heat_rate(surface_area, thickness, humidity);
		
		double conduction_heat_rate = air_conduction_heat_rate + skin_conduction_heat_rate;
		
		return conduction_heat_rate;
	}
	
	public static double calculate_heat_rate(double temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double intensity, double albedo, double thickness, double altitude)
	{	
		double evaporation_heat_rate = 
				calculate_evaporation_heat_rate(temperature, atmospheric_pressure, humidity, air_velocity, surface_area);
		
		double radiation_heat_rate = 
				calculate_radiation_heat_rate(intensity, albedo, altitude);
		
		double conduction_heat_rate =
				calculate_conduction_heat_rate(temperature, surface_area, thickness, humidity);
		
		double total_heat_rate = 
				evaporation_heat_rate + radiation_heat_rate + conduction_heat_rate;
		
		return total_heat_rate;
	}
	
	public static double calculate_temperature_rate(double temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double intensity, double albedo, double thickness, double altitude, double area_density, double hygroscopy)
	{
		double cotton_specific_heat = 
				1100; //J/kgC
		
		double water_specific_heat = 
				4186; //J/kgC
		
		double heat_capacity = //J/C
				cotton_specific_heat * surface_area * area_density + hygroscopy * water_specific_heat * surface_area * area_density;
		
		double total_heat_rate = 
				calculate_heat_rate(temperature, atmospheric_pressure, humidity, air_velocity, surface_area, intensity, albedo, thickness, altitude);
		
		double temperature_rate = 
				total_heat_rate / heat_capacity;
		
		return temperature_rate;
	}
	
	////////////////////////////////////////////////////
	public static double calculate_dewpoint_temperature(double temperature, double humidity)
	{
		double partial_pressure = 
				calculate_partial_pressure(temperature, humidity);
		
		double dewpoint_temperature = 
				(243.5 * Math.log(partial_pressure/0.6122))/(17.67 - Math.log(partial_pressure/0.6122));
		
		return dewpoint_temperature;
	}
	
	public static double calculate_wetbulb_temperature(double temperature, double humidity)
	{
		double dewpoint_temperature = 
				calculate_dewpoint_temperature(temperature, humidity);
		
		double wetbulb_temperature = 
				(temperature-273.16) * Math.atan(0.151977 * Math.pow(humidity + 8.313659, 0.5)) 
				+ Math.atan((temperature-273.16) + humidity) - Math.atan(humidity - 1.676331)
				+ 0.00391838 * Math.pow(humidity, 1.5) * Math.atan(0.023101 * humidity) - 4.686035 + 273.16;

		return wetbulb_temperature;
	}
	
	///////////////////////////////////////////////////Data
	
	public static void print_data(double temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double intensity, double albedo, double thickness, double altitude, double area_density, double hygroscopy)
	{	
		double temperature2 = temperature + +0.0000000000001;
		double atmospheric_pressure2 = atmospheric_pressure + 0.0000000000001;
		double humidity2 = humidity + 0.0000000000001;
		double air_velocity2 = air_velocity + 0.00001;
		double surface_area2 = surface_area + 0.0000000000001;
		double intensity2 = intensity + 0.000001;
		double albedo2 = albedo + 0.0000000000000001;
		double thickness2 = thickness + 0.00000000000000001;
		double area_density2 = area_density + 0.000000000000001;
		double hygroscopy2 = hygroscopy + 0.0000000000001;
		double altitude2 = altitude + 0.0000000000001;
		
		System.out.println("\n#Variables\n");
		
		String information1 = String.format("%30s  %.15s %s", "Temperature:", new BigDecimal(temperature2 - 273.16), "(¡ÆC)");
		System.out.println(information1);
		
		String information2 = String.format("%30s  %.15s %s", "Atmospheric Pressure:", new BigDecimal(atmospheric_pressure2), "(kPa)");
		System.out.println(information2);
		
		String information3 = String.format("%30s  %.15s %s", "Humidity:", new BigDecimal(humidity2), "(%)");
		System.out.println(information3);
		
		String information4 = String.format("%30s  %.15s %s", "Air Velocity:", new BigDecimal(air_velocity2), "(m/s)");
		System.out.println(information4);
		
		String information5 = String.format("%30s  %.15s %s", "Surface Area:", new BigDecimal(surface_area2), "(m^2)");
		System.out.println(information5);
		
		String information6 = String.format("%30s  %.15s %s", "Intensity:", new BigDecimal(intensity2), "(W)");
		System.out.println(information6);
		
		String information7 = String.format("%30s  %.15s %s", "Albedo:", new BigDecimal(albedo2), "(.%)");
		System.out.println(information7);
		
		String information8 = String.format("%30s  %.15s %s", "Thickness:", new BigDecimal(thickness2 * 1000), "(mm)");
		System.out.println(information8);
		
		String information9 = String.format("%30s  %.15s %s", "Area Density:", new BigDecimal(area_density2 * 1000), "(g/m^2)");
		System.out.println(information9);
		
		String information10 = String.format("%30s  %.15s %s", "Hygroscopy:", new BigDecimal(hygroscopy2), "(%)");
		System.out.println(information10);
		
		String information11 = String.format("%30s  %.15s %s", "Altitude:", new BigDecimal(altitude2), "(¡Æ)");
		System.out.println(information11);
		
		//double saturation_pressure = calculate_saturation_pressure(temperature);
		//double partial_pressure = calculate_partial_pressure(temperature, humidity);
		//double humidity_ratio = calculate_humidity_ratio(temperature, atmospheric_pressure, humidity);
		//double saturation_humidity_ratio = calculate_saturation_humidity_ratio(temperature, atmospheric_pressure, humidity);
		double evaporation_rate = calculate_evaporation_rate(temperature, atmospheric_pressure, humidity, air_velocity, surface_area);
		double evaporation_heat_rate = calculate_evaporation_heat_rate(temperature, atmospheric_pressure, humidity, air_velocity, surface_area);
		double radiation_heat_rate = calculate_radiation_heat_rate(intensity, albedo, altitude);
		double water_conduction_heat_rate = calculate_water_conduction_heat_rate(temperature, surface_area, thickness, humidity);
		double air_conduction_heat_rate = calculate_air_conduction_heat_rate(temperature, surface_area, thickness, humidity);
		double skin_conduction_heat_rate = calculate_skin_conduction_heat_rate(surface_area, thickness, humidity);
		double conduction_heat_rate = calculate_conduction_heat_rate(temperature, surface_area, thickness, humidity);
		double heat_rate = calculate_heat_rate(temperature, atmospheric_pressure, humidity, air_velocity, surface_area, intensity, albedo, thickness, albedo);
		double wetbulb_temperature = calculate_wetbulb_temperature(temperature, humidity);
		
		System.out.println("\n.....................................................................\n\n#Data\n");
		
		String information12 = String.format("%30s  %.15s %s", "Evaporation Rate:", new BigDecimal(evaporation_rate * 1000), "(g/s)");
		System.out.println(information12);
		
		String information13 = String.format("%30s  %.15s %s", "Evaporation Heat Rate:", new BigDecimal(evaporation_heat_rate), "(W)");
		System.out.println(information13);
		
		String information14 = String.format("%30s  %.15s %s", "Radiation Heat Rate:", new BigDecimal(radiation_heat_rate), "(W)");
		System.out.println(information14);
		
		String information15 = String.format("%30s  %.15s %s", "Water Conduction Heat Rate:", new BigDecimal(water_conduction_heat_rate), "(W)");
		System.out.println(information15);
		
		String information16 = String.format("%30s  %.15s %s", "Air Conduction Heat Rate:", new BigDecimal(air_conduction_heat_rate), "(W)");
		System.out.println(information16);
		
		String information17 = String.format("%30s  %.15s %s", "Skin Conduction Heat Rate:", new BigDecimal(skin_conduction_heat_rate), "(W)");
		System.out.println(information17);
		
		String information18 = String.format("%30s  %.15s %s", "Conduction Heat Rate:", new BigDecimal(conduction_heat_rate), "(W)");
		System.out.println(information18);
		
		String information19 = String.format("%30s  %.15s %s", "Heat Rate:", new BigDecimal(heat_rate), "(W)");
		System.out.println(information19);
		
		String information20 = String.format("%30s  %.15s %s", "Wetbulb Temperature:", new BigDecimal(wetbulb_temperature - 273.16), "(¡ÆC)");
		System.out.println(information20);
		
		System.out.println("______________________________________________________________________");
	}
}
