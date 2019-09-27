$compressionMethods = "gorilla", "deflate", "lz4", "zstandard"
$compressionTimeframes = 2, 4, 8, 16, 48

Clear-Content statistics.txt
Add-Content statistics.txt "host_id,initial_size,compressed_size,compression_ratio,compression_method,timeframe"
foreach ($method in $compressionMethods) {
    foreach ($timeframe in $compressionTimeframes) {
        for ($i = 0; $i -lt 50; $i++) {
            Write-Host "Compressing file devops_${timeframe}h_60s_${i}.txt with compression method ${method}"
            $getContent = "Get-Content ..\data\devops_${timeframe}h_60s_${i}.txt"
            $statistics = Invoke-Expression "$getContent | Out-String -Stream | java -jar .\TimeseriesCompressionBenchmarks\build\libs\TimeseriesCompressionBenchmarks-all.jar $method"
            $number_of_data_points = $timeframe * 60
            Add-Content statistics.txt "${i},${statistics},${method},${timeframe},${number_of_data_points}"
        }
    }    
}