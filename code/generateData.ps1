for ($i = 0; $i -lt 50; $i++) {
    Invoke-Expression "tsbs_generate_data -use-case=`"devops`" -seed=$i -scale=1 -timestamp-start=`"2019-01-01T00:00:00Z`" -timestamp-end=`"2019-01-01T02:00:00Z`" -log-interval=`"60s`" -format=`"cassandra`" > ..\data\devops_2h_60s_$i.txt"
}

for ($i = 0; $i -lt 50; $i++) {
    Invoke-Expression "tsbs_generate_data -use-case=`"devops`" -seed=$i -scale=1 -timestamp-start=`"2019-01-01T00:00:00Z`" -timestamp-end=`"2019-01-01T04:00:00Z`" -log-interval=`"60s`" -format=`"cassandra`" > ..\data\devops_4h_60s_$i.txt"
}

for ($i = 0; $i -lt 50; $i++) {
    Invoke-Expression "tsbs_generate_data -use-case=`"devops`" -seed=$i -scale=1 -timestamp-start=`"2019-01-01T00:00:00Z`" -timestamp-end=`"2019-01-01T08:00:00Z`" -log-interval=`"60s`" -format=`"cassandra`" > ..\data\devops_8h_60s_$i.txt"
}

for ($i = 0; $i -lt 50; $i++) {
    Invoke-Expression "tsbs_generate_data -use-case=`"devops`" -seed=$i -scale=1 -timestamp-start=`"2019-01-01T00:00:00Z`" -timestamp-end=`"2019-01-01T16:00:00Z`" -log-interval=`"60s`" -format=`"cassandra`" > ..\data\devops_16h_60s_$i.txt"
}

for ($i = 0; $i -lt 50; $i++) {
    Invoke-Expression "tsbs_generate_data -use-case=`"devops`" -seed=$i -scale=1 -timestamp-start=`"2019-01-01T00:00:00Z`" -timestamp-end=`"2019-01-03T00:00:00Z`" -log-interval=`"60s`" -format=`"cassandra`" > ..\data\devops_48h_60s_$i.txt"
}

Exit