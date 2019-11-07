const fs = require('fs');
const child_process = require('child_process');
const dynatraceMetrics = require('./dynatraceMetrics').dynatraceMetrics;
const countFileLines = require('./countFileLines').countFileLines;

const compressionMethods = ['gorilla', 'deflate', 'lz4', 'zstandard'];
const fileName = '../../data/dynatrace_statistics.csv';

fs.writeFileSync(fileName, 'metric,initial_size,compressed_size,compression_ratio,compression_method,timeframe,datapoints_number\n');
for (let method of compressionMethods) {
    for (let metric of dynatraceMetrics) {
        const currentFileSuffix = metric.replace(':', '_');
        const currentFile = `../../data/dynatrace_${currentFileSuffix}.csv`;
        const readStream = fs.createReadStream(currentFile);
        const cmd = `java -jar ../TimeseriesCompressionBenchmarks/build/libs/TimeseriesCompressionBenchmarks-all.jar dynatrace ${method}`;

        readStream.on('open', () => {
            const spawned = child_process.spawnSync(cmd, [], {
                shell: true,
                stdio: [readStream, 'pipe', process.stderr]
            });
            if (spawned.error) {
                console.log(spawned.error);
                process.exit(-1);
            }

            countFileLines(currentFile).then(numberOfLines => {
                fs.appendFileSync(fileName, `${metric},${spawned.stdout},${method},2,${numberOfLines}\n`);
            });
        });
    }
}