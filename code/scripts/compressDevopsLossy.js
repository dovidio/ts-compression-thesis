const fs = require('fs');
const child_process = require('child_process');

const compressionTimeframes = [2, 4, 8, 16, 48];

const fileName = '../../data/devops_statistics.csv';

fs.writeFileSync(fileName, 'host_id,initialNumberOfDataPoints,finalNumberOfDataPoints,compression_ratio,timeframe,datapoints_number\n');
    for (let timeframe of compressionTimeframes) {
        for (let i = 0; i < 50; i++) {
            const readStream = fs.createReadStream(`../../data/devops_${timeframe}h_60s_${i}.txt`);
            const interval = 60 * 1000;
            const cmd = `java -jar ../TimeseriesCompressionBenchmarks/build/libs/TimeseriesCompressionBenchmarks-all.jar devops pmc_mr 0.01 ${interval}`;

            readStream.on('open', () => {
                const spawned = child_process.spawnSync(cmd, [], {
                    shell: true,
                    stdio: [readStream, 'pipe', process.stderr]
                });
                if (spawned.error) {
                    console.log(spawned.error);
                    process.exit(-1);
                }
                const number_of_data_points = timeframe * 60;
                fs.appendFileSync(fileName, `${i},${spawned.stdout},${timeframe},${number_of_data_points}\n`);
            });
        }
    }    
