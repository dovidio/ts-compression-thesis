# in order for the gorilla algorithms to work as expected, we need to process datapoints
# with increasing timestamp values. However, the taxi data file does not have the datapoints
# sorted by the start of the taxi driver, therefore we will do it here.
# Moreover we need to clean up some values which are outside the time range, and we want
# to drop columns that have string data types
# the file that we expect in input is https://s3.amazonaws.com/nyc-tlc/trip+data/yellow_tripdata_2019-01.csv
import numpy as np
import pandas as pd

df = pd.read_csv('../data/yellow_tripdata_2019-01.csv')
# sort by pickup time
df.sort_values(by=['tpep_pickup_datetime'], inplace=True)
# drop values with pickup time before 1 january 2019
indexes = df[df['tpep_pickup_datetime'] < '2019-01-01 00:00:00'].index
df.drop(indexes, inplace=True)
# drop column containing string values and column with NaN
df.drop(columns=['store_and_fwd_flag', 'congestion_surcharge'], inplace=True)
# drop last 3 million columns, to make in memory computation possible
df.drop(df.tail(3000000).index, inplace=True)
df.to_csv('../data/taxi_cleaned.csv', header=False, index=False)
