import json
import re

# build array of JSON objects containing tweet
tweets = []
count = 0
for line in open('TwitterZikka.txt', 'r', encoding="utf-8"):
    if line.strip() != '' and line.strip() != '\n':        
        tweets.append(json.loads(line))
        count += 1
print("Tweets processed: ", count)

# create output file of text from tweets
outFile = open("output_twitter_text.txt", "w")

for twt in tweets:
    str_twt = twt["text"].replace('\n', ' ')
    str_twt = re.sub("[^a-zA-Z]+", " ", str_twt)
    str_twt = str_twt.lower()
    
    print(str_twt, file=outFile)

outFile.close()   
