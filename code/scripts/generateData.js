const child_process = require('child_process');
const fs = require('fs');

const configs = [
    {
        end: '2019-01-01T02:00:00Z',
        hours: 2,
    },
    {
        end: '2019-01-01T04:00:00Z',
        hours: 4,
    },
    {
        end: '2019-01-01T08:00:00Z',
        hours: 8,
    },
    {
        end: '2019-01-01T16:00:00Z',
        hours: 16,
    },
    {
        end: '2019-01-03T00:00:00Z',
        hours: 48,
    }
];

const dataDirectory = '../../data';

const stat = fs.lstatSync(dataDirectory);
if (!stat.isDirectory(dataDirectory)) {
    fs.mkdirSync(dataDirectory);
}

for (config of configs) {
    const start = '2019-01-01T00:00:00Z';
    for (let i = 0; i < 50; i++) {
        child_process.execSync(`tsbs_generate_data --use-case="devops" --seed=${i} --scale=1 --timestamp-start="${start}" --timestamp-end="${config.end}" --log-interval="60s" --format="cassandra" > ${dataDirectory}/devops_${config.hours}h_60s_${i}.csv`);
    }
}