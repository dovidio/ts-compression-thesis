const fs = require('fs');
const dynatraceMetrics = require('./dynatraceMetrics').dynatraceMetrics;
const readLine = require('readline');
const process = require('process');

let totalDevopsValues = 0;
let totalRepeatedDevopsValues = 0;
let totalDynatraceValues = 0;
let totalRepeatedDynatraceValues = 0;

const computeDevopsRepeatedValues = () => {
    const compressionTimeframes = [2, 4, 8, 16, 48];
    for (let timeframe of compressionTimeframes) {
        for (let i = 0; i < 50; i++) {
            const currentFile = `../../data/devops_${timeframe}h_60s_${i}.csv`;

            const readInterface = createReadInterface(currentFile);

            let repeatedValues = 0;
            let totalValues = 0;
            let numberOfSeries = 0;
            const timeSeriesContainer = {};
            readInterface.on('line', (line) => {
                totalValues++;
                const values = line.split(",");
                const timeSeriesKey = values.slice(1, values.length - 3).join('');
                const currentValue = values[values.length - 1];

                if (timeSeriesContainer[timeSeriesKey] === undefined) {
                    timeSeriesContainer[timeSeriesKey] = null;
                    numberOfSeries++;
                }

                if (currentValue != null) {
                    if (timeSeriesContainer[timeSeriesKey] !== null && timeSeriesContainer[timeSeriesKey] === currentValue) {
                        repeatedValues++;
                    } else {
                        timeSeriesContainer[timeSeriesKey]= currentValue;
                    }
                }
            });

            readInterface.on('close', () => {
                totalDevopsValues += totalValues;
                totalRepeatedDevopsValues += repeatedValues;
                // console.log(`statistics for file devops_${timeframe}h_60s_${i}.csv`);
                // console.log('number of hosts', numberOfSeries);
                // console.log('repeated values', repeatedValues);
                // console.log('total values', totalValues);
                // console.log('percentage of repeated values', repeatedValues / totalValues * 100);

                if (repeatedValues / totalValues * 100 > 30) {
                    console.log(`statistics for file devops_${timeframe}h_60s_${i}.csv`, 'crazy amount of repeated values');
                }
            });
        }
    }
};

const computeDynatraceRepeatedValues = () => {
    for (let metric of dynatraceMetrics) {
        const currentFileSuffix = metric.replace(':', '_');
        const currentFile = `../../data/dynatrace_${currentFileSuffix}.csv`;
    
        const readInterface = createReadInterface(currentFile);
        let repeatedValues = 0;
        let totalValues = 0;
        let numberOfHosts = 0;
        const timeSeriesContainer = {};
        readInterface.on('line', (line) => {
            totalValues++;
            const values = line.split(',');
            
            const hostName = values[0];
            const currentValue = values[values.length - 1];
            
            if (timeSeriesContainer[hostName] === undefined) {
                timeSeriesContainer[hostName] = null;
                numberOfHosts++;
            }
    
            if (currentValue !== null) {
                if (timeSeriesContainer[hostName] !== null && timeSeriesContainer[hostName] === currentValue) {
                    repeatedValues++;
                } else {
                    timeSeriesContainer[hostName] = currentValue;
                }
            }
        });
    
        readInterface.on('close', () => {
            totalDynatraceValues += totalValues;
            totalRepeatedDynatraceValues += repeatedValues;
            console.log('statistics for metric', metric);
            console.log('number of hosts', numberOfHosts);
            console.log('repeated values', repeatedValues);
            console.log('total values', totalValues);
            console.log('percentage of repeated values', repeatedValues / totalValues * 100);
        });
    }
};

const createReadInterface = (currentFile) => {
    return readLine.createInterface({
        input: fs.createReadStream(currentFile),
        output: null,
        console: false
    });
}

computeDevopsRepeatedValues();
computeDynatraceRepeatedValues();

process.on('exit', () => {
    console.log('='.repeat(50), ' DEVOPS STATISTICS ', '='.repeat(50));
    console.log('total values', totalDevopsValues);
    console.log('total repeated values', totalRepeatedDevopsValues);
    console.log('percentage of devops repeated values', totalRepeatedDevopsValues / totalDevopsValues * 100);

    console.log('='.repeat(50), ' DYNATRACE STATISTICS ', '='.repeat(50));
    console.log('total values', totalDynatraceValues);
    console.log('total repeated values', totalRepeatedDynatraceValues);
    console.log('percentage of repeated values', totalRepeatedDynatraceValues / totalDynatraceValues * 100);
})