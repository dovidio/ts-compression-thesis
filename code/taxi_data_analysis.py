import numpy as np
import pandas as pd
from matplotlib import rcParams

# import dataset
# contains compression performance data of 4 different compression algorithms:
# deflate, gorilla, lz4 and zstandard
# for taxi data
df = pd.read_csv('../data/taxi_statistics.csv')
# check that we have the same amount of observations for each algorithm
df['compression_method'].value_counts()

# group by compression method and timeframe and get means
groupedMeans = df.groupby(['compression_method']).mean()['compression_ratio']
rcParams.update({'figure.autolayout': True})
plt = groupedMeans.plot.bar()
plt.set_ylabel('Compression ratio')
plt.set_xlabel('Compression algorithm')

figure = plt.get_figure()
figure.savefig('../thesis/figure/taxi_bar_chart.png')