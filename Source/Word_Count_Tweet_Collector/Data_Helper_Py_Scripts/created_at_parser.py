import json
import re

# build array of JSON objects containing tweet
tweets = []
count = 0
for line in open('COMBINED_Twitter_Debate_Data.json', 'r', encoding="utf-8"):
    if line.strip() != '' and line.strip() != '\n':        
        tweets.append(json.loads(line))
        count += 1
print("Tweets processed: ", count)
print("Writing data to file...")
# create output file of text from tweets
outFile = open("CLEAN_CREATED_AT_ONLY_1.txt", "w", encoding="utf-8")

outCount = 0
percent_done = 0

for twt in tweets:
    str_twt = twt["created_at"].split()[3].replace(':', '')

    outCount += 1
    if(outCount % 10000 == 0):
        if(percent_done == 0):
            print(percent_done, "% done")
            percent_done = 10
        else:
            print(percent_done, "% done")
            percent_done += 10

    print(str_twt, file=outFile)

print("total output", outCount)
outFile.close()   
