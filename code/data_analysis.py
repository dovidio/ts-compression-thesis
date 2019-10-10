import numpy as np
import pandas as pd

# import dataset
# contains compression performance data of 4 different compression algorithms:
# deflate, gorilla, lz4 and zstandard
df = pd.read_csv('devops_statistics.txt')
df.head()
# check that we have the same amount of observations for each algorithm
df['compression_method'].value_counts()

# group by compression method and timeframe and get means
groupedMeans = df.groupby(['timeframe', 'compression_method']).mean()['compression_ratio'].unstack()
plt = groupedMeans.plot.bar()
plt.legend(bbox_to_anchor=(1.2, 0.5))
plt.set_ylabel('Compression ratio')
plt.set_xlabel('Timeframe (hours)')
plt.legend()
figure = plt.get_figure()
figure.savefig('../thesis/figure/devops_bar_chart.png')