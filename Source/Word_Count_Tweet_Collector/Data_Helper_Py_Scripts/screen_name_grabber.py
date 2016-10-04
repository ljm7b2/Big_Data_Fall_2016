import json
import re

# build array of JSON objects containing tweet
tweets = []
count = 0
inFile = open('Debate_Data_9_26_2016_Final_1_2.json', 'r', encoding="utf-8")
for line in inFile:
    if line.strip() != '' and line.strip() != '\n':        
        tweets.append(json.loads(line))
        count += 1
print("Tweets processed: ", count)
print("Writing data to file...")
inFile.close()
# create output file of text from tweets
outFile = open("Twitter_Screen_Names_2.txt", "w")

outCount = 0
percent_done = 0

for twt in tweets:
    str_twt = twt["user"]["screen_name"].replace('\n', '')


    outCount += 1
    if(outCount % 10000 == 0):
        if(percent_done == 0):
            print(percent_done, "% done")
            percent_done = 10
        else:
            print(percent_done, "% done")
            percent_done += 10

    print(str_twt, file=outFile)

outFile.close()   
