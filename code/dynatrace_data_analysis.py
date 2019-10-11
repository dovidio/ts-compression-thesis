import numpy as np
import pandas as pd
from matplotlib import rcParams


# import dataset
# contains compression performance data of 4 different compression algorithms:
# deflate, gorilla, lz4 and zstandard
# for different metrics
df = pd.read_csv('../data/dynatrace_statistics.csv')
df.head()
# check that we have the same amount of observations for each algorithm
df['compression_method'].value_counts()

# group by compression method and timeframe and get means
groupedMeans = df.groupby(['metric', 'compression_method']).mean()['compression_ratio'].unstack()
rcParams.update({'figure.autolayout': True})
plt = groupedMeans.plot.bar()
plt.legend(bbox_to_anchor=(1.2, 0.5))
plt.set_ylabel('Compression ratio')
plt.set_xlabel('Metrics')
plt.set_xticklabels(['CPU I/O Wait', 'CPU System', 'CPU Usage', 'CPU User', 'Disk Read Time', 'Disk Write Time', 'Memory Available', 'Memory Used'])
# plt.adjust(bottom=.3)
plt.autoscale(tight=True)
plt.legend()
figure = plt.get_figure()
figure.savefig('../thesis/figure/dynatrace_bar_chart.png')