const fs = require('fs');
const child_process = require('child_process');
const countFileLines = require('./countFileLines').countFileLines;

const compressionMethods = ['gorilla', 'deflate', 'lz4', 'zstandard'];
const fileName = '../../data/taxi_statistics.csv';

fs.writeFileSync(fileName, 'initial_size,compressed_size,compression_ratio,compression_method,timeframe,datapoints_number\n');
for (let method of compressionMethods) {
        const currentFile = `../../data/taxi_cleaned.csv`;
        const readStream = fs.createReadStream(currentFile);
        const cmd = `java -jar ../TimeseriesCompressionBenchmarks/build/libs/TimeseriesCompressionBenchmarks-all.jar taxi ${method}`;

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
                fs.appendFileSync(fileName, `${spawned.stdout},${method},2,${numberOfLines}\n`);
            });
        });
}    
