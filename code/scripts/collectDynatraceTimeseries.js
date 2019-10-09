const https = require('https');
const fs = require('fs');
// load environmental variables from .env file
require('dotenv').config();

const append = (metric, data) => {
    const series = data.metrics[metric].series;

    
    for (let s of series) {
        const dimensionName = s.dimensions[0];
        for (value of s.values) {
            const row = dimensionName + ',' + value.timestamp + ',' + value.value;
            fs.appendFileSync(`../dynatrace_${metric}.csv`, row + '\n');
        }    
    }
};

const makeRequest = (metric, args, nextPageKey) => {
    
    let queryURL = baseTimeseriesURL + '/' + metric;

    if (nextPageKey) {
        queryURL += '?nextPageKey=' + nextPageKey;
    } else {
        console.log('start fetching data for ' + metric);
    }
    
    https.get(queryURL, args, (res) => {

        if (res.statusCode !== 200) {
            console.error('request for ' + metric + ' was not succesful');
        } else {
            let data = '';
            res.on('data', (d) => {
                data += d;
            });
        
            res.on('end', () => {
                try {            
                    const response = JSON.parse(data);
                    append(metric, response);
                    
                    if (response.nextPageKey) {
                        makeRequest(metric, args, response.nextPageKey);
                    } else {
                        console.log('finished querying data for ' + metric);
                    }
                } catch(error) {
                    console.error('could not parse json ' + error);
                }
            });
        }
    }).on('error', console.error);
}

const DYNATRACE_URL = process.env.DYNATRACE_URL;
const DYNATRACE_API_TOKEN = process.env.DYNATRACE_API_TOKEN;
if (!DYNATRACE_URL) {
    console.log('DYNATRACE URL was not defined, please add it to your .env file');
} 
if (!DYNATRACE_API_TOKEN) {
    console.log('DYNATRACE API TOKEN was not defined, please add it to your .env file');
}

const baseTimeseriesURL = `${DYNATRACE_URL}/api/v2/metrics/series`;
const args = {
    headers: {
        Authorization: 'Api-token ' + DYNATRACE_API_TOKEN,
    }
};

const metricsToQuery = [
    'builtin:host.cpu.usage',
    'builtin:host.cpu.user',
    'builtin:host.cpu.steal',
    'builtin:host.cpu.load',
    'builtin:host.mem.used',
    'builtin:host.mem.usage',
    'builtin:host.mem.recl',
    'builtin:host.mem.swap.avail'
];

// create files
for (let metric of metricsToQuery) {
    const fileHeader = 'host,timestamp,' + metric + '\n';
    fs.writeFileSync(`../dynatrace_${metric}.csv`, fileHeader);
}

// query dynatrace for each metric
for (let metric of metricsToQuery) {
    makeRequest(metric, args, undefined);
}

