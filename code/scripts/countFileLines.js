const fs = require('fs');
const LINE_FEED = '\n'.charCodeAt(0);

const countFileLines = (filePath) => {
    return new Promise((resolve, reject) => {
        let lineCount = 0;
        fs.createReadStream(filePath)
            .on("data", (buffer) => {
                let idx = -1;
                lineCount--; // Because the loop will run once for idx=-1
                do {
                    idx = buffer.indexOf(LINE_FEED, idx + 1);
                    lineCount++;
                } while (idx !== -1);
            }).on("end", () => {
                resolve(lineCount);
            }).on("error", reject);
    });
};

module.exports = {
    countFileLines: countFileLines
}