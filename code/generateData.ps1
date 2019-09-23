for ($i = 0; $i -lt 10; $i++) {
    Write-Host $i
    Invoke-Expression "tsbs_generate_data -use-case=`"devops`" -seed=$i -scale=10 -timestamp-start=`"2019-01-01T00:00:00Z`" -timestamp-end=`"2019-01-01T02:00:00Z`" -log-interval=`"60s`" -format=`"cassandra`" > ..\data\devops_$i.txt"
    Write-Host after
}
Exit