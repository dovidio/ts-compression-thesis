const fs = require('fs');
const child_process = require('child_process');

const metrics = [
    'builtin:host.cpu.usage',
    'builtin:host.cpu.user',
    'builtin:host.cpu.steal',
    'builtin:host.cpu.load',
    'builtin:host.mem.used',
    'builtin:host.mem.usage',
    'builtin:host.mem.recl',
    'builtin:host.mem.swap.avail'
];
const compressionMethods = ['gorilla', 'deflate', 'lz4', 'zstandard'];
const fileName = '../../data/dynatrace_statistics.txt';

fs.writeFileSync(fileName, 'host_id,initial_size,compressed_size,compression_ratio,compression_method,timeframe,datapoints_number\n');
for (let method of compressionMethods) {
    for (let metric of metrics) {
        const currentFile = `../../data/dynatrace_${metric}.csv`;
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

            // TODO: find solution that works only for windows
            const number_of_data_points = child_process.spawnSync(`wc -l < ${currentFile}`, [], {shell: true});
            if (number_of_data_points.error) {
                console.log(number_of_data_points.error);
            }

            fs.appendFileSync(fileName, `${metric},${spawned.stdout},${method},2,${number_of_data_points.stdout}`);
        });
    }
}    
