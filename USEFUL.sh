more transcript_analysis.txt | awk 'BEGIN {FS="\t"} {print $1, $7 }' | grep TRUMP | awk 'BEGIN {n=0; e=0;} {n+=$2; e+=1} END {print n, e, n/e}'

more transcript_analysis.txt | awk 'BEGIN {FS="\t"} {print $1, $7 }' | grep TRUMP | awk '{print $2}' | sort -n | tail -20
