import java.math.BigDecimal;
import java.util.*;

//2
//added variables air_wetbulb_temperature, water_wetbulb_temperature, and breathability

//*decreasing both ambient temperature and water temperature decreases evaporation rate
//*decreasing ambient temperature only actually increases evaporation rate
//*but usually ambient temperature and water temperature decrease together

//3
//changed output format
//added variables in_radiation, out_radiation, and convection

//4
//added functions calculate_water_temperature and calculte_surface_temperature

//5
//organized variables

//7
//added Aerogel/Polyurethane option

public class TemperatureAerogel7
{
	//Climate Setting
	static double ambient_temperature = 25.0 + 273.16; //K http://www.kma.go.kr/weather/observation/past_table.jsp?stn=108&yy=2016&obs=08&x=11&y=18
	static double atmospheric_pressure = 101.325; //kPa
	static double humidity = 60.0; //relative humidity
	static double air_velocity = 1.0; //m/s
	static double altitude = 63;//12AM:72 2PM: 63 6PM:20http://aa.usno.navy.mil/cgi-bin/aa_altazw.pl?form=2&body=10&year=2016&month=8&day=7&intv_mag=10&place=&lon_sign=1&lon_deg=128&lon_min=&lat_sign=1&lat_deg=36&lat_min=&tz=9&tz_sign=1
	static double direct_insolation = 0;
	
	static double direct_irradiance = 0;

	//Cloth Setting
	static double surface_area = 0.9; //Large cotton T-shirt * 2 m^2xstatic double absorption_area_density = 0.080; //kg/m^2
	static double nonabsorption_thickness = 0.01; //m thickness of outer layer 0.015
	static double absorption_thickness = 0.0007; //m thickness of water. mine was 0.0011, jacket is 0.001
	static double insulator_thermal_conductivity = 0.02; //Polyurethane foam //0.014; //Aerogel blanket
	static double breathability = 0.4;//.% lower breathability slightly increases duration
	static double albedo = 0.8; //
	static double emissivity_coefficient = 0.85;
	static double water_volume = 0;
	static double cloth_weight = 0;
	static double total_weight = cloth_weight + water_volume;

	static double absorption_area_density = 0.080 * absorption_thickness / 0.0011; //0.096g/m^2 for neoshell
	static double nonabsorption_area_density = 20 * nonabsorption_thickness + 0.104; //kg/m^2 mine was 0.104 aerogel was 150 //http://www.thermablok.co.uk/wp/wp-content/uploads/2015/11/thermablokaerogelblanketdatasheet2.pdf
	static double hygroscopy = 6.0; //.%
	
	//Temperature
	static double water_temperature = 0;//this has to be calculated first and foremost!
	static double surface_temperature = 0;//this must be done second!
	static double air_wetbulb_temperature = 0;
	static double water_wetbulb_temperature = 0;
	static double ambient_temperature2 = ambient_temperature;
	
	static double body_temperature = 36.5 + 273.16; //K
	
	//Heat Rate
	static double in_radiation_heat_rate = 0;
	static double out_radiation_heat_rate = 0;
	static double convection_heat_rate = 0;
	static double insulated_conduction_heat_rate = 0;
	static double skin_conduction_heat_rate = 100 * 0.4001;//average men powers 100W heat percentage: http://files.forensicmed.webnode.com/200000116-a70bfa8060/lund_and_browder_chart.jpg //heat produced: https://en.wikipedia.org/wiki/Basal_metabolic_rate http://www.engineeringtoolbox.com/met-metabolic-rate-d_733.html //body heat loss is proportional to surface area: http://www.livescience.com/34411-body-heat-loss-head.html
	static double evaporation_heat_rate = 0;
	static double conduction_heat_rate = 0;
	static double heat_rate = 0;

	static double water_volume2 = water_volume;
	static double evaporation_rate = 0;
	static int duration = 0;
	
	public static void main(String args[])
	{
		/*
		System.out.println("\nSpaceloft Aerogel");
		set_worst();
		set_spaceloft_aerogel();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability, albedo, emissivity_coefficient, absorption_thickness, nonabsorption_thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
		*/
		/*
		System.out.println("\nThermal Aerogel1");
		set_worst();
		set_thermal_aerogel1();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability, albedo, emissivity_coefficient, absorption_thickness, nonabsorption_thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
		
		System.out.println("\nThermal Aerogel2");
		set_worst();
		set_thermal_aerogel2();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability, albedo, emissivity_coefficient, absorption_thickness, nonabsorption_thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
		*/
		/*
		System.out.println("\nWorst Polyurethane Foam");
		set_worst();
		set_puform();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability, albedo, emissivity_coefficient, absorption_thickness, nonabsorption_thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
		*/
		System.out.println(0.2*0.4);
		System.out.println("\nIndoor");
		set_fleece();
		set_indoor();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability, albedo, emissivity_coefficient, absorption_thickness, nonabsorption_thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
	
		System.out.println("\nOutdoor12PM");
		set_fleece();
		set_outdoor12PM();
		print_data(ambient_temperature=1, atmospheric_pressure, humidity, air_velocity, surface_area, breathability, albedo, emissivity_coefficient, absorption_thickness, nonabsorption_thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
		
		System.out.println("\nOutdoor3PM");
		set_fleece();
		set_outdoor3PM();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability, albedo, emissivity_coefficient, absorption_thickness, nonabsorption_thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
		
		System.out.println("\nOutdoor6PM");
		set_fleece();
		set_outdoor6PM();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability, albedo, emissivity_coefficient, absorption_thickness, nonabsorption_thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);

		System.out.println("\nAverage");
		set_primaloft1();
		set_average();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability, albedo, emissivity_coefficient, absorption_thickness, nonabsorption_thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
		//System.out.println(air_velocity);
		//System.out.println("\nEngineeringtoolboxtest");
		//set_engineeringtoolboxtest();
		//print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability, albedo, absorption_thickness, nonabsorption_thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
		
		/*
		System.out.println("\nTest1");
		set_test1();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
		
		System.out.println("\nTest2");
		set_test2();
		print_data(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, thickness, altitude, absorption_area_density, nonabsorption_area_density, hygroscopy, skin_conduction_heat_rate);
		*/

	}
	
	public static void set_worst()
	{
		ambient_temperature = 32.0 + 273.16; //K http://www.kma.go.kr/weather/observation/past_table.jsp?stn=108&yy=2016&obs=08&x=11&y=18
		atmospheric_pressure = 100.6; //kPa
		humidity = 60.0; //relative humidity
		air_velocity = 2.5; //m/s
		altitude = 64;
	}
	
	public static void set_spaceloft_aerogel()
	{
		surface_area = 0.9; //Large cotton T-shirt * 2 m^2xstatic double absorption_area_density = 0.080; //kg/m^2
		nonabsorption_thickness = 0.003; //m thickness of outer layer 0.015
		absorption_thickness = 0.00060; //m thickness of water. mine was 0.0011, jacket is 0.001
		insulator_thermal_conductivity = 0.014; //Polyurethane foam //0.014; //Aerogel blanket
		breathability = 0.3;//.% lower breathability slightly increases duration
		albedo = 0.80; //
		emissivity_coefficient = 0.85;
		water_volume = 0;
		cloth_weight = 0;
		total_weight = cloth_weight + water_volume;

		absorption_area_density = 0.104 * absorption_thickness / 0.0011;//kg/m^3
		nonabsorption_area_density = 150 * nonabsorption_thickness + 0.100; //0.80kg/m^3 because aerogel itself is 방수
		//aerogel was 150 //http://www.thermablok.co.uk/wp/wp-content/uploads/2015/11/thermablokaerogelblanketdatasheet2.pdf
		//https://patentimages.storage.googleapis.com/WO2008054551A2/imgf000020_0001.png density: 94.39
		hygroscopy = 6.0; //.%
	}
	
	public static void set_thermal_aerogel1()
	{
		surface_area = 0.9; //Large cotton T-shirt * 2 m^2xstatic double absorption_area_density = 0.080; //kg/m^2
		nonabsorption_thickness = 0.004; //m thickness of outer layer 0.015
		absorption_thickness = 0.00077; //m thickness of water. mine was 0.0011, jacket is 0.001
		insulator_thermal_conductivity = 0.023; //Polyurethane foam //0.014; //Aerogel blanket
		breathability = 0.39;//.% lower breathability slightly increases duration
		albedo = 0.80; //
		emissivity_coefficient = 0.85;
		water_volume = 0;
		cloth_weight = 0;
		total_weight = cloth_weight + water_volume;

		absorption_area_density = 0.104 * absorption_thickness / 0.0011;//kg/m^3
		nonabsorption_area_density = 70 * nonabsorption_thickness + 0.100; //80kg/m^3 because aerogel itself is 방수
		//aerogel was 150 //http://www.thermablok.co.uk/wp/wp-content/uploads/2015/11/thermablokaerogelblanketdatasheet2.pdf
		//https://patentimages.storage.googleapis.com/WO2008054551A2/imgf000020_0001.png density: 94.39
		hygroscopy = 6.0; //.%
	}
	
	public static void set_thermal_aerogel2()
	{
		surface_area = 0.9; //Large cotton T-shirt * 2 m^2xstatic double absorption_area_density = 0.080; //kg/m^2
		nonabsorption_thickness = 0.006; //m thickness of outer layer 0.015
		absorption_thickness = 0.0006; //m thickness of water. mine was 0.0011, jacket is 0.001
		insulator_thermal_conductivity = 0.023; //Polyurethane foam //0.014; //Aerogel blanket
		breathability = 0.39;//.% lower breathability slightly increases duration
		albedo = 0.80; //0.80
		emissivity_coefficient = 0.85;
		water_volume = 0;
		cloth_weight = 0;
		total_weight = cloth_weight + water_volume;

		absorption_area_density = 0.104 * absorption_thickness / 0.0011;//kg/m^3
		nonabsorption_area_density = 70 * nonabsorption_thickness + 0.100; //80kg/m^3 because aerogel itself is 방수
		//aerogel was 150 //http://www.thermablok.co.uk/wp/wp-content/uploads/2015/11/thermablokaerogelblanketdatasheet2.pdf
		//https://patentimages.storage.googleapis.com/WO2008054551A2/imgf000020_0001.png density: 94.39
		hygroscopy = 6.0; //.%
	}
	
	public static void set_primaloft1()//100g/m^2 is just retarded measure to deceive customers so inaccurate
	{
		surface_area = 0.9; //Large cotton T-shirt * 2 m^2xstatic double absorption_area_density = 0.080; //kg/m^2
		nonabsorption_thickness = 0.006; //m thickness of outer layer 0.015
		absorption_thickness = 0.0006; //m thickness of water. mine was 0.0011, jacket is 0.001
		insulator_thermal_conductivity = 0.017;
		breathability = 0.39;//.% lower breathability slightly increases duration
		albedo = 0.8; //0.80
		emissivity_coefficient = 0.85;
		water_volume = 0;
		cloth_weight = 0;
		total_weight = cloth_weight + water_volume;

		absorption_area_density = 0.104 * absorption_thickness / 0.0011;//kg/m^3
		nonabsorption_area_density = 70 * nonabsorption_thickness + 0.100; //
		hygroscopy = 6.0; //.%
	}
	
	public static void set_fleece()
	{
		surface_area = 0.9; //Large cotton T-shirt * 2 m^2xstatic double absorption_area_density = 0.080; //kg/m^2
		nonabsorption_thickness = 0.003; //m thickness of outer layer 0.015
		absorption_thickness = 0.0006; //m thickness of water. mine was 0.0011, jacket is 0.001
		insulator_thermal_conductivity = 0.053; //Polyurethane foam //0.014; //Aerogel blanket
		breathability = 0.39;//.% lower breathability slightly increases duration
		albedo = 0.8; //0.80
		emissivity_coefficient = 0.85;
		water_volume = 0;
		cloth_weight = 0;
		total_weight = cloth_weight + water_volume;

		absorption_area_density = 94.5 * absorption_thickness;//g/m^2
		nonabsorption_area_density = 179 * nonabsorption_thickness + 0.100; //g/m^2
		hygroscopy = 6.0; //.%
	}
	
	public static void set_puform()
	{
		surface_area = 0.9; //Large cotton T-shirt * 2 m^2xstatic double absorption_area_density = 0.080; //kg/m^2
		nonabsorption_thickness = 0.004; //m thickness of outer layer 0.015
		absorption_thickness = 0.00060; //m thickness of water. mine was 0.0011, jacket is 0.001
		insulator_thermal_conductivity = 0.020; //Polyurethane foam //0.014; //Aerogel blanket
		breathability = 0.30;//.% lower breathability slightly increases duration
		albedo = 0.8; //
		emissivity_coefficient = 0.85;
		water_volume = 0;
		cloth_weight = 0;
		total_weight = cloth_weight + water_volume;

		absorption_area_density = 0.104 * absorption_thickness / 0.0011; //kg/m^2 mine was 0.104 
		nonabsorption_area_density = 10 * nonabsorption_thickness + 0.180; //aerogel was 150 //http://www.thermablok.co.uk/wp/wp-content/uploads/2015/11/thermablokaerogelblanketdatasheet2.pdf
		hygroscopy = 6.0; //.%
	}
	
	public static void set_indoor()
	{
		ambient_temperature = 34.0 + 273.16; //K
		atmospheric_pressure = 101.3; //kPa
		humidity = 50.0; //relative humidity
		air_velocity = 0.0; //m/s
		
		altitude = 0.0000001;
	}

	public static void set_outdoor12PM()
	{
		ambient_temperature = 32.0 + 273.16; //K
		atmospheric_pressure = 101.1; //kPa
		humidity = 55.0; //%relative humidity
		air_velocity = 2.2; //2.5m/s
		
		altitude = 72;
	}
	
	public static void set_outdoor3PM()
	{
		ambient_temperature = 34.0 + 273.16; //K http://www.kma.go.kr/weather/observation/past_table.jsp?stn=108&yy=2016&obs=08&x=11&y=18
		atmospheric_pressure = 100.6; //kPa
		humidity = 45.0; //relative humidity
		air_velocity = 2.5; //m/s
		altitude = 64;
	}
	
	public static void set_outdoor6PM()
	{
		ambient_temperature = 28.0 + 273.16; //K
		atmospheric_pressure = 100.3; //kPa
		humidity = 60.0; //%relative humidity
		air_velocity = 2.0; //2.5m/s
		
		altitude = 20;
	}
	
	public static void set_average()
	{
		ambient_temperature = 28.0 + 273.16; //K
		atmospheric_pressure = 101.3; //kPa
		humidity = 50.0; //relative humidity
		air_velocity = 1; //m/s
		
		altitude = 85;
	}
	
	public static void set_engineeringtoolboxtest()
	{

		ambient_temperature = 25.0 + 273.16; //K
		atmospheric_pressure = 101.325; //kPa
		humidity = 50.0; //%relative humidity
		air_velocity = 0.5; //2.5m/s
		
		altitude = 0.0001;
		surface_area = 500;//0.415 * 0.415;
		
		skin_conduction_heat_rate = 0.01;
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
	
	public static double calculate_evaporation_rate(double ambient_temperature, double water_temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double breathability)
	{//www.engineeringtoolbox.com/evaporation-water-surface-d_690.html
		air_velocity = 0;
		double evaporation_coefficient = //25;
				25 + 19*air_velocity;//blocks all air
		
		double saturation_humidity_ratio = 
				calculate_saturation_humidity_ratio(water_temperature, atmospheric_pressure, humidity);
		
		double humidity_ratio = 
				calculate_humidity_ratio(water_temperature, atmospheric_pressure, humidity);
		//ambient temperature = water temperature
		
		double evaporation_rate = //kg/s
				breathability * evaporation_coefficient * surface_area * (saturation_humidity_ratio - humidity_ratio) / 3600;
		//breathability added
		
		return evaporation_rate;
	}
	
	public static double calculate_evaporation_heat_rate(double ambient_temperature, double water_temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double breathability)
	{
		double water_evaporation_heat = //J/kg
				-2257000;
		
		double evaporation_rate = //kg/s
				calculate_evaporation_rate(water_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability);
	//ambient temperature = water temperature
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
	
	public static double calculate_skin_conduction_heat_rate(double skin_conduction_heat_rate)
	{//https://en.wikipedia.org/wiki/Basal_metabolic_rate	
		return skin_conduction_heat_rate;
	}
	
	public static double calculate_in_radiation_heat_rate(double surface_area, double albedo, double altitude)
	{
		double direct_irradiance = 
				calculate_direct_irradiance(altitude);
		
		double radiation_heat_rate = 
			surface_area * direct_irradiance * (1-albedo) / Math.tan(altitude/180*Math.PI) * 0.35;//angle of sun, only one side is shined upon
			//surface_area * intensity * (1-albedo) / Math.tan(altitude/180*Math.PI) * 0.35;//angle of sun, only one side is shined upon
		//System.out.println(radiation_heat_rate);
		return radiation_heat_rate;
	}
	
	public static double calculate_out_radiation_heat_rate(double ambient_temperature, double surface_temperature, double air_velocity, double surface_area, double emissivity_coefficient)
	{//http://www.engineeringtoolbox.com/radiation-heat-transfer-d_431.html
		
		double stefan_boltzmann_constant = //(W/m2K4)
				5.6703 * Math.pow(10, -8);
		
		double out_radiation_heat_rate = //q = hc A dT
				-emissivity_coefficient * stefan_boltzmann_constant * surface_area * (Math.pow(surface_temperature,4) - Math.pow(ambient_temperature,4));
	
		return out_radiation_heat_rate;
	}
	
	public static double calculate_convection_heat_rate(double ambient_temperature, double surface_temperature, double air_velocity, double surface_area)
	{//http://www.engineeringtoolbox.com/convective-heat-transfer-d_430.html
		double convective_heat_transfer_coefficient = //of air (W/(m2K))
				10.45 - air_velocity + 10 * Math.pow(air_velocity, 1.0/2);
		
		double convection_heat_rate = //q = hc A dT
				-convective_heat_transfer_coefficient * surface_area * (surface_temperature - ambient_temperature);

		return convection_heat_rate;
	}

	public static double calculate_insulated_conduction_heat_rate(double surface_temperature, double water_temperature, double surface_area, double nonabsorption_thickness)
	{		
		double insulated_conduction_heat_rate = insulator_thermal_conductivity * surface_area * (surface_temperature - water_temperature) / nonabsorption_thickness;
		
		return insulated_conduction_heat_rate;
	}
	
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
	
	public static double calculate_surface_temperature(double ambient_temperature, double water_temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double albedo, double emissivity_coefficient, double altitude, double breathability, double skin_conduction_heat_rate)
	{
		double surface_temperature = 0;
		
		double evaporation_heat_rate =
				calculate_evaporation_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability);

		double insulated_conduction_heat_rate = 
				-skin_conduction_heat_rate - evaporation_heat_rate;
		
		double in_radiation_heat_rate =
				calculate_in_radiation_heat_rate(surface_area, albedo, altitude);
		
		double out_radiation_heat_rate = 
				calculate_out_radiation_heat_rate(ambient_temperature, surface_temperature, air_velocity, surface_area, emissivity_coefficient);
		
		double convection_heat_rate =
				calculate_convection_heat_rate(ambient_temperature, surface_temperature, air_velocity, surface_area);
		
		while (insulated_conduction_heat_rate > -(in_radiation_heat_rate + out_radiation_heat_rate + convection_heat_rate))
		{
			surface_temperature += 0.03;
			
			evaporation_heat_rate =
					calculate_evaporation_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability);
			
			insulated_conduction_heat_rate = 
					calculate_insulated_conduction_heat_rate(surface_temperature, water_temperature, surface_area, nonabsorption_thickness);
			
			out_radiation_heat_rate = 
					calculate_out_radiation_heat_rate(ambient_temperature, surface_temperature, air_velocity, surface_area, emissivity_coefficient);
			
			convection_heat_rate =
					calculate_convection_heat_rate(ambient_temperature, surface_temperature, air_velocity, surface_area);
		}
				
		return surface_temperature;
	}
	
	public static double calculate_water_temperature(double ambient_temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double breathability, double albedo, double emissivity_coefficient, double absorbtion_thickness, double nonabsorbtion_thickness, double altitude, double skin_conduction_heat_rate)
	{
		double water_temperature = 15.0 + 273.16;
		
		double surface_temperature = 
				calculate_surface_temperature(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, emissivity_coefficient, altitude, breathability, skin_conduction_heat_rate);

		double insulated_heat_rate =
				calculate_insulated_conduction_heat_rate(surface_temperature, water_temperature, surface_area, nonabsorbtion_thickness);
		
		double evaporation_heat_rate =
				calculate_evaporation_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability);
				

	    while (insulated_heat_rate > -skin_conduction_heat_rate - evaporation_heat_rate)
	    {
	    	water_temperature += 0.03;
	    	
	    	surface_temperature = 
	    			calculate_surface_temperature(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, emissivity_coefficient, altitude, breathability, skin_conduction_heat_rate);
	    	
			insulated_heat_rate =
					calculate_insulated_conduction_heat_rate(surface_temperature, water_temperature, surface_area, nonabsorbtion_thickness);

			evaporation_heat_rate =
					calculate_evaporation_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability);

	    }
	    //water_temperature -= 0.03;
	    
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
	
	public static void print_data(double ambient_temperature, double atmospheric_pressure, double humidity, double air_velocity, double surface_area, double breathability, double albedo, double emissivity_coefficient, double absorption_thickness, double nonabsorption_thickness, double altitude, double absorption_area_density, double nonabsorption_area_density, double hygroscopy, double skin_conduction_heat_rate)
	{	
		double ambient_temperature2 = ambient_temperature + +0.0000000000001;
		double atmospheric_pressure2 = atmospheric_pressure + 0.0000000000001;
		double humidity2 = humidity + 0.00000001;
		double air_velocity2 = air_velocity + 0.00001;
		double surface_area2 = surface_area + 0.0000000000001;
		double albedo2 = albedo + 0.0000000000000001;
		double emissivity_coefficient2 = emissivity_coefficient + 0.00000000000000001;
		double absorption_thickness2 = absorption_thickness + 0.00000000000000001;
		double nonabsorption_thickness2 = nonabsorption_thickness + 0.00000000000000001;
		double absorption_area_density2 = absorption_area_density + 0.000000000000001;
		double nonabsorption_area_density2 = nonabsorption_area_density + 0.000000000000001;
		double hygroscopy2 = hygroscopy + 0.0000000000001;
		double altitude2 = altitude + 0.0000000000001;
		double water_volume2 = calculate_water_volume(surface_area, absorption_area_density, hygroscopy);
		double cloth_weight2 = calculate_cloth_weight(surface_area, absorption_area_density, nonabsorption_area_density) + 0.0000000000001;
		double total_weight2 = cloth_weight2 + water_volume2;
		double direct_insolation2 = calculate_direct_insolation(altitude);
		double direct_irradiance2 = calculate_direct_irradiance(altitude);
				
		System.out.println("\n#Climate Setting\n");
		
		String information11 = String.format("%35s  %.15s %s", "Ambient Temperature:", new BigDecimal(ambient_temperature2 - 273.16), "(캜)");
		System.out.println(information11);
		
		String information12 = String.format("%35s  %.15s %s", "Atmospheric Pressure:", new BigDecimal(atmospheric_pressure2), "(kPa)");
		System.out.println(information12);
		
		String information13 = String.format("%35s  %.15s %s", "Humidity:", new BigDecimal(humidity2), "(%)");
		System.out.println(information13);
		
		String information14 = String.format("%35s  %.15s %s", "Air Velocity:", new BigDecimal(air_velocity2), "(m/s)");
		System.out.println(information14);
		
		String information15 = String.format("%35s  %.15s %s", "Altitude:", new BigDecimal(altitude2), "(�)");
		System.out.println(information15);
		
		String information16 = String.format("%35s  %.15s %s", "Direct Insolation:", new BigDecimal(direct_insolation2), "(W)");
		System.out.println(information16);
		
		System.out.println("\n#Cloth Setting\n");
		
		String information21 = String.format("%35s  %.15s %s", "Surface Area:", new BigDecimal(surface_area2), "(m^2)");
		System.out.println(information21);
		
		String information22 = String.format("%35s  %.15s %s", "Thickness:", new BigDecimal((absorption_thickness2 + nonabsorption_thickness2) * 1000), "(mm)");
		System.out.println(information22);
		
		String information28 = String.format("%35s  %.15s %s", "Breathability:", new BigDecimal(breathability), "(.%)");
		System.out.println(information28);
		
		String information23 = String.format("%35s  %.15s %s", "Albedo:", new BigDecimal(albedo2), "(.%)");
		System.out.println(information23);
		
		String information24 = String.format("%35s  %.15s %s", "Emissivity:", new BigDecimal(emissivity_coefficient2), "(.%)");
		System.out.println(information24);
		
		String information25 = String.format("%35s  %.15s %s", "Water Volume:", new BigDecimal(water_volume2 * 1000.001), "(ml)");
		System.out.println(information25);
		
		String information26 = String.format("%35s  %.15s %s", "Cloth Weight:", new BigDecimal(cloth_weight2 * 1000), "(g)");
		System.out.println(information26);
		
		String information27 = String.format("%35s  %.15s %s", "Total Weight:", new BigDecimal(total_weight2 * 1000), "(g)");
		System.out.println(information27);
		
				
		
		double water_temperature = //20 + 273.16;
				calculate_water_temperature(ambient_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability, albedo, emissivity_coefficient, absorption_thickness, nonabsorption_thickness, altitude, skin_conduction_heat_rate);
		//this has to be calculated first and foremost!
		double surface_temperature = //30 + 273.16;
				calculate_surface_temperature(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, albedo, emissivity_coefficient, altitude, breathability, skin_conduction_heat_rate);
		
		double evaporation_rate = calculate_evaporation_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability);
		double evaporation_heat_rate = calculate_evaporation_heat_rate(ambient_temperature, water_temperature, atmospheric_pressure, humidity, air_velocity, surface_area, breathability);
		double in_radiation_heat_rate = calculate_in_radiation_heat_rate(surface_area, albedo, altitude);
		//double out_radiation_heat_rate = calculate_out_radiation_heat_rate(ambient_temperature, surface_temperature, air_velocity, surface_area, emissivity_coefficient);
		double insulated_conduction_heat_rate = calculate_insulated_conduction_heat_rate(surface_temperature, water_temperature, surface_area, nonabsorption_thickness);
		double skin_conduction_heat_rate2 = skin_conduction_heat_rate+0.001;
		double out_radiation_heat_rate = calculate_out_radiation_heat_rate(ambient_temperature, surface_temperature, air_velocity, surface_area, emissivity_coefficient);
		double convection_heat_rate = calculate_convection_heat_rate(ambient_temperature, surface_temperature, air_velocity, surface_area);
		double air_wetbulb_temperature = calculate_wetbulb_temperature(ambient_temperature, humidity);
		double water_wetbulb_temperature = calculate_wetbulb_temperature(water_temperature, humidity);
		int duration = (int)calculate_duration(surface_area, absorption_area_density, hygroscopy, evaporation_rate);
		
		System.out.println("\n#Temperature\n");

		
		String information31 = String.format("%35s  %.15s %s", "Air Wetbulb Temperature:", new BigDecimal(air_wetbulb_temperature - 273.16), "(캜)");
		System.out.println(information31);
		
		String information32 = String.format("%35s  %.15s %s", "Water Wetbulb Temperature:", new BigDecimal(water_wetbulb_temperature - 273.16), "(캜)");
		System.out.println(information32);
		
		String information33 = String.format("%35s  %.15s %s", "Ambient Temperature:", new BigDecimal(ambient_temperature2 - 273.16), "(캜)");
		System.out.println(information33);
		
		String information34 = String.format("%35s  %.15s %s", "Water Temperature:", new BigDecimal(water_temperature - 273.16), "(캜)");
		System.out.println(information34);
		
		String information35 = String.format("%35s  %.15s %s", "Surface Temperature:", new BigDecimal(surface_temperature - 273.16), "(캜)");
		System.out.println(information35);
		
		System.out.println("\n#Heat Rate\n");
		
		String information41 = String.format("%35s  %.15s %s", "In Radiation Heat Rate:", new BigDecimal(in_radiation_heat_rate), "(W)");
		System.out.println(information41);
		
		String information42 = String.format("%35s  %.15s %s", "Out Radiation Heat Rate:", new BigDecimal(out_radiation_heat_rate), "(W)");
		System.out.println(information42);
		
		String information43 = String.format("%35s  %.15s %s", "Convection Heat Rate:", new BigDecimal(convection_heat_rate), "(W)");
		System.out.println(information43);

		String information46 = String.format("%35s  %.15s %s", "Insulated Conduction Heat Rate:", new BigDecimal(insulated_conduction_heat_rate), "(W)");
		System.out.println(information46);

		String information45 = String.format("%35s  %.15s %s", "Skin Conduction Heat Rate:", new BigDecimal(skin_conduction_heat_rate), "(W)");
		System.out.println(information45);
		
		String information44 = String.format("%35s  %.15s %s", "Evaporation Heat Rate:", new BigDecimal(evaporation_heat_rate), "(W)");
		System.out.println(information44);
		
		
		//String information47 = String.format("%35s  %.15s %s", "Conduction Heat Rate:", new BigDecimal(conduction_heat_rate), "(W)");
		//System.out.println(information47);
		
		System.out.println("\n#Evaporation\n");
		
		String information51 = String.format("%35s  %.15s %s", "Water Volume:", new BigDecimal(water_volume2 * 1000.001), "(ml)");
		System.out.println(information51);
		
		String information52 = String.format("%35s  %.15s %s", "Evaporation Rate:", new BigDecimal(evaporation_rate * 1000), "(g/s)");
		System.out.println(information52);
		
		String information53 = String.format("%35s  %.15s %s", "Evaporation Rate:", new BigDecimal(evaporation_rate * 1000 * 60 * 60), "(g/hr)");
		System.out.println(information53);
		
		String information54 = String.format("%35s  %.15s %s", "Duration:", duration/60/60+":"+duration/60%60+":"+duration%60%60, "        (hr:min:sec)");
		System.out.println(information54);
		
		System.out.println("______________________________________________________________________");
	}
}
