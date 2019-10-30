import numpy as np
import pandas as pd

# import dataset
# contains compression performance data of 4 different compression algorithms:
# deflate, gorilla, lz4 and zstandard
# for different timeframes: 2 hours, 4 hours, 8 hours, 16 hours, 48 hours
df = pd.read_csv('../data/devops_statistics.csv')
df.head()
# check that we have the same amount of observations for each algorithm
df['compression_method'].value_counts()

# mean and std for compression methods
df.groupby(['compression_method']).mean()['compression_ratio']
df.groupby(['compression_method']).std()['compression_ratio']

# group by compression method and timeframe and get means
groupedMeans = df.groupby(['timeframe', 'compression_method']).mean()['compression_ratio'].unstack()
plt = groupedMeans.plot.bar()
plt.legend(bbox_to_anchor=(1.2, 0.5))
plt.set_ylabel('Compression ratio')
plt.set_xlabel('Timeframe (hours)')
plt.legend()
figure = plt.get_figure()
figure.savefig('../thesis/figure/devops_bar_chart.png')