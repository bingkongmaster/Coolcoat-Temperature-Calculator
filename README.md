# Coolcoat Temperature Calculator
Calculate temperature of Coolcoat with &lt;0.1°C accurracy

---------------------------------------------------------------------------
Input/Output Example

Outdoor3PM

#Climate Setting

               Ambient Temperature:  34.000000000000 (°C)
              Atmospheric Pressure:  100.60000000000 (kPa)
                          Humidity:  45.000000010000 (%)
                      Air Velocity:  2.5000100000000 (m/s)
                          Altitude:  64.000000000000 (°)
                 Direct Insolation:  922.09227262844 (W)

#Cloth Setting

                      Surface Area:  0.9000000000001 (m^2)
                         Thickness:  3.6000000000000 (mm)
                     Breathability:  0.3900000000000 (.%)
                            Albedo:  0.8000000000000 (.%)
                        Emissivity:  0.8499999999999 (.%)
                      Water Volume:  306.18030617999 (ml)
                      Cloth Weight:  624.33000000010 (g)
                      Total Weight:  930.51000000010 (g)

#Temperature

           Air Wetbulb Temperature:  24.745861607669 (°C)
         Water Wetbulb Temperature:  24.234985974271 (°C)
               Ambient Temperature:  34.000000000000 (°C)
                 Water Temperature:  33.389999999983 (°C)
               Surface Temperature:  37.339999999959 (°C)

#Heat Rate

            In Radiation Heat Rate:  25.465774760841 (W)
           Out Radiation Heat Rate:  -17.07045407381 (W)
              Convection Heat Rate:  -71.42673323146 (W)
    Insulated Conduction Heat Rate:  62.804999999619 (W)
         Skin Conduction Heat Rate:  40.009999999999 (W)
             Evaporation Heat Rate:  -103.8808030969 (W)

#Evaporation

                      Water Volume:  306.18030617999 (ml)
                  Evaporation Rate:  0.0460260536539 (g/s)
                  Evaporation Rate:  165.69379315420 (g/hr)
                          Duration:  1:50:52         (hr:min:sec)

---------------------------------------------------------------------------
Versions

2
added variables air_wetbulb_temperature, water_wetbulb_temperature, and breathability

*decreasing both ambient temperature and water temperature decreases evaporation rate
*decreasing ambient temperature only actually increases evaporation rate
*but usually ambient temperature and water temperature decrease together

3
changed output format
added variables in_radiation, out_radiation, and convection

4
added functions calculate_water_temperature and calculte_surface_temperature

5
organized variables

7
added Aerogel/Polyurethane option