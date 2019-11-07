# Compression Algorithms for Time Series Data

The project is divided in three folder:
- **code**: contains code for benchmarking and data analysis purposes
- **thesis**: contains the thesis written in latex
- **data**: contains data used for compressing

## Prerequisites

- NodeJs for running the scripts that generate/collect/compress data
- Java (version 8) for running the project under code/TimeseriesCompressionBenchmarks
- [Time Series Benchmark Suite](https://github.com/timescale/tsbs) to generate "DevOps data"
- python3 for the data analysis 

## Generating data
To generate data, use the following command
```
cd code/scripts
node generatedData.js
```
This will invoke the Time Series Benchmark Suite command-line utility. Make sure to have
installed it before (refer to the [documentation](https://github.com/timescale/tsbs/blob/master/README.md))
250 (50 hosts x 5 data frames) files will be created under the data directory 

## Collecting data from Dynatrace
For this step, you need access to a dynatrace tenant and a valid token.
Once you have that information, create a .env file under the folder code/scripts
In this file, add the following information
```
DYNATRACE_URL= url of your dynatrace tenant
DYNATRACE_API_TOKEN= api token with metric read permissions
```
You can then run the following command to collect timeseries from dynatrace.
```
cd code/scripts
node collectDynatraceTimeseries.js
```
8 different metrics will be stored in 8 different files under the data folder.

## Collecting "Taxi Data"
You can find taxi data for January 2019 at the following [link](https://s3.amazonaws.com/nyc-tlc/trip+data/yellow_tripdata_2019-01.csv)
This data will need some preprocessing to be correctly compressed by the gorilla algorithms.
To do so, after having placed the data under the code directory, switch to that directory and run the following

```
python3 taxi_data_preprocessing.py
```
This will save the preprocessed data under the data directory

## Compressing data
All the following compression steps assume that you have built the TimeSeriesCompressionBenchmark project already
If you have not done so, go to code/TimeseriesCompressionBenchmarks and run
the following
```
./gradlew shadowJar
```
a jar file will be generated under code/TimeseriesCompressionBenchmarks/build/libs

## Compressing generated data
Simply run the following
```
cd code/scripts
node compressDevopsData.js
```
a file with the compression stats will be generated under the data directory

## Compressing Dynatrace data
Simply run the following
```
cd code/scripts
node compressDynatraceData.js
```
a file with the compression stats will be generated under the data directory

## Compressing Taxi Data
Simply run the following
```
cd code/scripts
node compressTaxiData.js
```
a file with the compression stats will be generated under the data directory

## Data Analysis
The files devops_data_analysis.py, dynatrace_data_analysis.py, taxi_data_analysis.py
contain some interesting commands for data analysis. They will also generate figures to the folder thesis/figure.