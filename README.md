# Compression Algorithms for Time Series Data

run the benchmark, do the following

```
cd code/TimeseriesCompressionBenchmarks
gradlew shadowJar
data.txt > java -jar build/libs/TimeseriesCompressionBenchmarks-all.jar [gorilla|lz4|zstandard|deflate]
```
