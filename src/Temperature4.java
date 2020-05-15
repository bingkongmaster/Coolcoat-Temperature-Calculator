import java.math.BigDecimal;
import java.util.*;

public class Temperature4
{
	static double ambient_temperature = 25.0 + 273.16; //K http://www.kma.go.kr/weather/observation/past_table.jsp?stn=108&yy=2016&obs=08&x=11&y=18
	static double atmospheric_pressure = 101.325; //kPa
	static double humidity = 60.0; //relative humidity
	static double air_velocity = 1.0; //m/s
	static double surface_area = 1.0; //Large cotton T-shirt * 2 m^2
	
	static double absorption_area_density = 0.080; //kg/m^2
	static double nonabsorption_area_density = 0.250; //kg/m^2 mine was 0.104
	static double thickness = 0.001; //m thickness of water. mine was 0.0011, jacket is 0.001
	static double hygroscopy = 6.0; //.%
	
	static double albedo = 0.5; //
	static double altitude = 63;//12AM:72 2PM: 63 6PM:20http://aa.usno.navy.mil/cgi-bin/aa_altazw.pl?form=2&body=10&year=2016&month=8&day=7&intv_mag=10&place=&lon_sign=1&lon_deg=128&lon_min=&lat_sign=1&lat_deg=36&lat_min=&tz=9&tz_sign=1
	static double body_temperature = 36.5 + 273.16; //K
	static double skin_conduction_heat_rate = 75.055; //average men powers 100W heat
	
	public static void main(String args[])
	{
		System.out.println("\nIndoor");
		set_indoor();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
	
		System.out.println("\nOutdoor12PM");
		set_outdoor12PM();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
	
		System.out.println("\nOutdoor3PM");
		set_outdoor3PM();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);

		System.out.println("\nOutdoor6PM");
		set_outdoor6PM();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);

		System.out.println("\nAverage");
		set_average();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);

		/*
		System.out.println("\nTest1");
		set_test1();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
		
		System.out.println("\nTest2");
		set_test2();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
		*/

	}
	
	public static void set_indoor()
	{
		ambient_temperature = 34.0 + 273.16; //K
		atmospheric_pressure = 101.3; //kPa
		humidity = 50.0; //relative humidity
		air_velocity = 0.0; //m/s
		
		altitude = 0.0000001;
		
		skin_conduction_heat_rate = 75.001;
	}

	public static void set_outdoor12PM()
	{
		ambient_temperature = 32.0 + 273.16; //K
		atmospheric_pressure = 101.1; //kPa
		humidity = 55.0; //%relative humidity
		air_velocity = 0.0; //2.5m/s
		
		altitude = 72;
		
		skin_conduction_heat_rate = 75.055;
	}
	
	public static void set_outdoor3PM()
	{
		ambient_temperature = 34.0 + 273.16; //K
		atmospheric_pressure = 100.6; //kPa
		humidity = 45.0; //%relative humidity
		air_velocity = 0.0; //2.5m/s
		
		altitude = 64;
	
		skin_conduction_heat_rate = 75.055;
	}
	
	public static void set_outdoor6PM()
	{
		ambient_temperature = 28.0 + 273.16; //K
		atmospheric_pressure = 100.3; //kPa
		humidity = 60.0; //%relative humidity
		air_velocity = 0.0; //2.5m/s
		
		altitude = 20;
	
		skin_conduction_heat_rate = 75.055;
	}
	
	public static void set_average()
	{
		ambient_temperature = 28.0 + 273.16; //K
		atmospheric_pressure = 101.3; //kPa
		humidity = 50.0; //relative humidity
		air_velocity = 0.0; //m/s
		
		altitude = 85;
		
		skin_conduction_heat_rate = 75.001;
	}
	
	public static void set_test1()
	{

		ambient_temperature = 28.5 + 273.16; //K
		atmospheric_pressure = 101.325; //kPa
		humidity = 76.0; //%relative humidity
		air_velocity = 0.0; //2.5m/s
		
		altitude = 0.0001;
		surface_area = 1;//0.415 * 0.415;
		
		skin_conduction_heat_rate = 0.01;
	}
	
	public static void set_test2()
	{

		ambient_temperature = 27 + 273.16; //K
		atmospheric_pressure = 101.325; //kPa
		humidity = 70.0; //%relative humidity
		air_velocity = 0.0; //2.5m/s
		
		altitude = 0.0001;
		surface_area = 0.415 * 0.415;
		
		skin_conduction_heat_rate = 0.01;
	}
	
	public static double calculate_saturation_pressure(double ambient_temperature)
	{
		double saturation_pressure = 
				0.6112 * Math.exp((17.67 * (ambient_temperature - 273.16)) / (ambient_temperature - 29.66));
		
		return saturation_pressure;
	}
	
	public static double calculate_partial_pressure(double ambient_temperature, double humidity)
	{
		double saturation_pressure =
				calculate_saturation_pressure(ambient_temperature);
		
		double partial_pressure =
				saturation_pressure * humidity / 100;
		
		return partial_pressure;
	}
	
	public static double calculate_humidity_ratio(double ambient_temperature, double atmospheric_pressure, double humidity)
	{
		double vapor_constant = 
				0.62199;
		
		double partial_pressure = 
				calculate_partial_pressure(ambient_temperature, humidity);
		
		double humidity_ratio = 
				vapor_constant * partial_pressure / (atmospheric_pressure - partial_pressure);
		
		return humidity_ratio;
	}
	
	public static double calculate_saturation_humidity_ratio(double wetbulb_temperature, double atmospheric_pressure, double humidity)
	{
		double vapor_constant = 
				0.62199;

		double saturation_pressure =
				calculate_saturation_pressure(wetbulb_temperature);
		
		double saturation_humidity_ratio = 
				vapor_constant * saturation_pressure / (atmospheric_pressure - saturation_pressure);
		
		return saturation_humidity_ratio;
	}
	
	public static double calculate_evaporation_rate(double ambient_temperature, double water_temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area)
	{
		double evaporation_coefficient = 
				25 + 19*air_velocity;
		
		double saturation_humidity_ratio = 
				calculate_saturation_humidity_ratio(water_temperature, atmospheric_pressure, humidity);
		
		double humidity_ratio = 
				calculate_humidity_ratio(ambient_temperature, atmospheric_pressure, humidity);
		
		double evaporation_rate = //kg/s
				evaporation_coefficient * surface_area * (saturation_humidity_ratio - humidity_ratio) / 3600;
		
		return evaporation_rate;
	}
	
	public static double calculate_evaporation_heat_rate(double ambient_temperature, double water_temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area)
	{
		double water_evaporation_heat = //J/kg
				-2257000;
		
		double evaporation_rate = //kg/s
				calculate_evaporation_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area);
	
		double evaporation_heat_rate = //W
				water_evaporation_heat * evaporation_rate;
		
		return evaporation_heat_rate;
	}
	
	public static double calculate_direct_insolation(double altitude)
	{//https://en.wikipedia.org/wiki/Direct_insolation
		double direct_insolation = //solar insolation measured at a given location on Earth with a surface element perpendicular to the Sun's rays, excluding diffuse insolation 
				1353 * Math.pow(0.7,Math.pow(1/Math.cos((90-altitude)/180*Math.PI), 0.678)); //Only direct insolation with no clouds: diffuse insolation is ignored because cloth angle is 90%
		//System.out.println(Math.cos(Math.PI));
		return direct_insolation;
	}
	
	public static double calculate_direct_irradiance(double altitude)
	{//https://en.wikipedia.org/wiki/Solar_irradiance
		double direct_insolation = //solar power per unit area incident on the Earth's surface
				calculate_direct_insolation(altitude);
		
		double direct_irradiance = 
				direct_insolation * Math.sin(altitude/180*Math.PI);
		
		return direct_irradiance;
	}
	
	public static double calculate_radiation_heat_rate(double surface_area, double albedo, double altitude)
	{
		double direct_irradiance = 
				calculate_direct_irradiance(altitude);
		
		double radiation_heat_rate = 
			surface_area * direct_irradiance * (1-albedo) / Math.tan(altitude/180*Math.PI) * 0.35;//angle of sun, only one side is shined upon
			//surface_area * intensity * (1-albedo) / Math.tan(altitude/180*Math.PI) * 0.35;//angle of sun, only one side is shined upon
		//System.out.println(radiation_heat_rate);
		return radiation_heat_rate;
	}
	
	public static double calculate_water_conduction_heat_rate(double ambient_temperature, double water_temperature, double surface_area, double thickness, double humidity)
	{
		double skin_temperature = 32.5 + 273.16;//K
		double water_thermal_conductivity = 0.63;//W/mK fiber-reinforced plastic can have similar thermal conductivity to water
		
		double water_conduction_heat_rate = water_thermal_conductivity * surface_area/2 * (skin_temperature - water_temperature) / thickness;
		
		return water_conduction_heat_rate;
	}
	
	public static double calculate_air_conduction_heat_rate(double ambient_temperature, double water_temperature, double atmospheric_pressure, double air_velocity, double surface_area, double thickness, double humidity)
	{
		double wetbulb_temperature = calculate_wetbulb_temperature(ambient_temperature, humidity);
		double base_air_conduction_heat_rate = calculate_evaporation_heat_rate(ambient_temperature, wetbulb_temperature, atmospheric_pressure, humidity, air_velocity, surface_area);
		
		
		double air_conduction_heat_rate = 
				-base_air_conduction_heat_rate / (ambient_temperature - wetbulb_temperature) * (ambient_temperature - water_temperature);
				//58/9.2*(ambient_temperature - water_temperature);
				
		double humidity_test = humidity;
		double wetbulb_temperature_test = wetbulb_temperature;
		while (wetbulb_temperature_test < water_temperature)
		{
			humidity_test += 0.1;
			wetbulb_temperature_test = calculate_wetbulb_temperature(ambient_temperature, humidity_test);
		}
		air_conduction_heat_rate = -calculate_evaporation_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity_test, air_velocity, surface_area);
		//System.out.println(humidity_test);
		return air_conduction_heat_rate;
	}
	
	public static double calculate_skin_conduction_heat_rate(double skin_conduction_heat_rate)
	{//https://en.wikipedia.org/wiki/Basal_metabolic_rate	
		return skin_conduction_heat_rate;
	}
	
	public static double calculate_conduction_heat_rate(double ambient_temperature, double water_temperature, double atmospheric_pressure, double air_velocity, double surface_area, double thickness, double humidity, double skin_conduction_heat_rate)
	{	
		double air_conduction_heat_rate = calculate_air_conduction_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, air_velocity, surface_area, thickness, humidity);
		double conduction_heat_rate = air_conduction_heat_rate + skin_conduction_heat_rate;
		
		return conduction_heat_rate;
	}
	
	public static double calculate_heat_rate(double ambient_temperature, double water_temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double albedo, double thickness, double altitude, double skin_conduction_heat_rate)
	{	
		double evaporation_heat_rate = 
				calculate_evaporation_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area);
		
		double radiation_heat_rate = 
				calculate_radiation_heat_rate(surface_area, albedo, altitude);
		
		double conduction_heat_rate =
				calculate_conduction_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, air_velocity, surface_area, thickness, humidity, skin_conduction_heat_rate);
		
		double total_heat_rate = 
				evaporation_heat_rate + radiation_heat_rate + conduction_heat_rate;
		
		return total_heat_rate;
	}
	
	public static double calculate_temperature_rate(double ambient_temperature, double water_temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double albedo, double thickness, double altitude, double absorption_area_density, double hygroscopy, double skin_conduction_heat_rate)
	{
		double cotton_specific_heat = 
				1100; //J/kgC
		
		double water_specific_heat = 
				4186; //J/kgC
		
		double heat_capacity = //J/C
				cotton_specific_heat * surface_area * absorption_area_density + hygroscopy * water_specific_heat * surface_area * absorption_area_density;
		
		double total_heat_rate = 
				calculate_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, skin_conduction_heat_rate);
		
		double temperature_rate = 
				total_heat_rate / heat_capacity;
		
		return temperature_rate;
	}
	
	////////////////////////////////////////////////////
	public static double calculate_dewpoint_temperature(double ambient_temperature, double humidity)
	{
		double partial_pressure = 
				calculate_partial_pressure(ambient_temperature, humidity);
		
		double dewpoint_temperature = 
				(243.5 * Math.log(partial_pressure/0.6122))/(17.67 - Math.log(partial_pressure/0.6122));
		
		return dewpoint_temperature;
	}
	
	public static double calculate_wetbulb_temperature(double ambient_temperature, double humidity)
	{
		double dewpoint_temperature = 
				calculate_dewpoint_temperature(ambient_temperature, humidity);
		
		double wetbulb_temperature = 
				(ambient_temperature-273.16) * Math.atan(0.151977 * Math.pow(humidity + 8.313659, 0.5)) 
				+ Math.atan((ambient_temperature-273.16) + humidity) - Math.atan(humidity - 1.676331)
				+ 0.00391838 * Math.pow(humidity, 1.5) * Math.atan(0.023101 * humidity) - 4.686035 + 273.16;

		return wetbulb_temperature;
	}
	
	public static double calculate_water_temperature(double ambient_temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double albedo, double thickness, double altitude, double skin_conduction_heat_rate)
	{
		double wetbulb_temperature = calculate_wetbulb_temperature(ambient_temperature, humidity);
		
		double water_temperature = wetbulb_temperature;

	    while (0 < calculate_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, skin_conduction_heat_rate))
	    {
	    	water_temperature += 0.03;
	    }

	    return water_temperature;
	}
	
	public static double calculate_water_volume(double surface_area, double absorption_area_density, double hygroscopy)
	{
		double water_volume = surface_area * absorption_area_density * hygroscopy;
		
		return water_volume;
	}
	
	public static double calculate_cloth_weight(double surface_area, double absorbtion_area_density, double nonabsorption_area_density)
	{
		double cloth_weight = surface_area * (absorbtion_area_density + nonabsorption_area_density);
		
		return cloth_weight;
	}
	
	public static double calculate_duration(double surface_area, double absorption_area_density, double hygroscopy, double evaporation_rate)
	{
		double duration = surface_area * absorption_area_density * hygroscopy / evaporation_rate;
		
		return duration;
	}
	///////////////////////////////////////////////////Data
	
	public static void print_data(double ambient_temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double albedo, double thickness, double altitude, double absorption_area_density, double nonabsorption_area_density, double hygroscopy, double skin_conduction_heat_rate)
	{	
		double ambient_temperature2 = ambient_temperature + +0.0000000000001;
		double atmospheric_pressure2 = atmospheric_pressure + 0.0000000000001;
		double humidity2 = humidity + 0.00000001;
		double air_velocity2 = air_velocity + 0.00001;
		double surface_area2 = surface_area + 0.0000000000001;
		double albedo2 = albedo + 0.0000000000000001;
		double thickness2 = thickness + 0.00000000000000001;
		double absorption_area_density2 = absorption_area_density + 0.000000000000001;
		double nonabsorption_area_density2 = absorption_area_density + 0.000000000000001;
		double hygroscopy2 = hygroscopy + 0.0000000000001;
		double altitude2 = altitude + 0.0000000000001;
		double water_volume2 = calculate_water_volume(surface_area, absorption_area_density, hygroscopy);
		double cloth_weight2 = calculate_cloth_weight(surface_area, absorption_area_density, nonabsorption_area_density) + 0.0000000000001;
		double total_weight2 = cloth_weight2 + water_volume2;
		double direct_insolation2 = calculate_direct_insolation(altitude);
		double direct_irradiance2 = calculate_direct_irradiance(altitude);
				
		System.out.println("\n#Variables\n");
		
		String information1 = String.format("%30s  %.15s %s", "Ambient Temperature:", new BigDecimal(ambient_temperature2 - 273.16), "(¡ÆC)");
		System.out.println(information1);
		
		String information2 = String.format("%30s  %.15s %s", "Atmospheric Pressure:", new BigDecimal(atmospheric_pressure2), "(kPa)");
		System.out.println(information2);
		
		String information3 = String.format("%30s  %.15s %s", "Humidity:", new BigDecimal(humidity2), "(%)");
		System.out.println(information3);
		
		String information4 = String.format("%30s  %.15s %s", "Air Velocity:", new BigDecimal(air_velocity2), "(m/s)");
		System.out.println(information4);
		
		String information11 = String.format("%30s  %.15s %s", "Altitude:", new BigDecimal(altitude2), "(¡Æ)");
		System.out.println(information11);
		
		String information211 = String.format("%30s  %.15s %s", "Direct Insolation:", new BigDecimal(direct_insolation2), "(W)");
		System.out.println(information211);
		
		String information212 = String.format("%30s  %.15s %s", "Direct Irradiance:", new BigDecimal(direct_irradiance2), "(W)");
		System.out.println(information212);
		
		System.out.println("");//cloth information
		
		String information5 = String.format("%30s  %.15s %s", "Surface Area:", new BigDecimal(surface_area2), "(m^2)");
		System.out.println(information5);
		
		String information8 = String.format("%30s  %.15s %s", "Thickness:", new BigDecimal(thickness2 * 1000), "(mm)");
		System.out.println(information8);
		
		String information9 = String.format("%30s  %.15s %s", "Absorption Area Density:", new BigDecimal(absorption_area_density2 * 1000), "(g/m^2)");
		System.out.println(information9);
		
		String information99 = String.format("%30s  %.15s %s", "Non Absorption Area Density:", new BigDecimal(nonabsorption_area_density2 * 1000), "(g/m^2)");
		System.out.println(information99);
		
		String information10 = String.format("%30s  %.15s %s", "Hygroscopy:", new BigDecimal(hygroscopy2), "(.%)");
		System.out.println(information10);
		
		String information7 = String.format("%30s  %.15s %s", "Albedo:", new BigDecimal(albedo2), "(.%)");
		System.out.println(information7);
		
		String information111 = String.format("%30s  %.15s %s", "Water Volume:", new BigDecimal(water_volume2 * 1000), "(ml)");
		System.out.println(information111);
		
		String information112 = String.format("%30s  %.15s %s", "Cloth Weight:", new BigDecimal(cloth_weight2 * 1000), "(g)");
		System.out.println(information112);
		
		String information113 = String.format("%30s  %.15s %s", "Total Weight:", new BigDecimal(total_weight2 * 1000), "(g)");
		System.out.println(information113);
		
		
		//double saturation_pressure = calculate_saturation_pressure(temperature);
		//double partial_pressure = calculate_partial_pressure(temperature, humidity);
		//double humidity_ratio = calculate_humidity_ratio(temperature, atmospheric_pressure, humidity);
		//double saturation_humidity_ratio = calculate_saturation_humidity_ratio(temperature, atmospheric_pressure, humidity);

		double water_temperature = calculate_water_temperature(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, skin_conduction_heat_rate);
		double evaporation_rate = calculate_evaporation_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area);
		double evaporation_heat_rate = calculate_evaporation_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area);
		double radiation_heat_rate = calculate_radiation_heat_rate(surface_area, albedo, altitude);
		double water_conduction_heat_rate = calculate_water_conduction_heat_rate(ambient_temperature, water_temperature, surface_area, thickness, humidity);
		double air_conduction_heat_rate = calculate_air_conduction_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, air_velocity, surface_area, thickness, humidity);
		double skin_conduction_heat_rate2 = skin_conduction_heat_rate+0.001;
		double conduction_heat_rate = calculate_conduction_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, air_velocity, surface_area, thickness, humidity, skin_conduction_heat_rate);
		double heat_rate = calculate_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, skin_conduction_heat_rate);
		double wetbulb_temperature = calculate_wetbulb_temperature(ambient_temperature, humidity);
		int duration = (int)calculate_duration(surface_area, absorption_area_density, hygroscopy, evaporation_rate);
		
		System.out.println("\n.....................................................................\n\n#Data\n");
		
		String information12 = String.format("%30s  %.15s %s", "Evaporation Rate:", new BigDecimal(evaporation_rate * 1000), "(g/s)");
		System.out.println(information12);
		
		String information23 = String.format("%30s  %.15s %s", "Evaporation Rate:", new BigDecimal(evaporation_rate * 1000 * 60 * 60), "(g/hr)");
		System.out.println(information23);
		
		String information20 = String.format("%30s  %.15s %s", "Wetbulb Temperature:", new BigDecimal(wetbulb_temperature - 273.16), "(¡ÆC)");
		System.out.println(information20);
		
		String information21 = String.format("%30s  %.15s %s", "Water Temperature:", new BigDecimal(water_temperature - 273.16), "(¡ÆC)");
		System.out.println(information21);
		
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
		
		String information22 = String.format("%30s  %.15s %s", "Duration:", duration/60/60+":"+duration/60%60+":"+duration%60%60, "");
		System.out.println(information22);
		
		System.out.println("______________________________________________________________________");
	}
}
