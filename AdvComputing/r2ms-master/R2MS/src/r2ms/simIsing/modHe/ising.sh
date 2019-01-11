#!/bin/sh
#
for size in 5 10 15 20 25 30 40 50; do

for red_temp in  0.7 0.8 0.9 0.93 0.97 1.03 1.07  1.1 1.2 1.3 ; do

java r2ms.simIsing.modHe.HeNew <<EOF >> ising.${size}.${red_temp}.out
$size
$red_temp
1000000 1000 50000
0.0
EOF

done

done
