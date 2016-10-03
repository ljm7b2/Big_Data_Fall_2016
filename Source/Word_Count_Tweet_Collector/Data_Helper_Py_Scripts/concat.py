# Concats Two text files, and checks that correct number of lines desired are present

import shutil
with open('COMBINED_Twitter_Debate_Data.json','w', encoding="utf-8") as wfd:
    for f in ['Debate_Data_9_26_2016_Final_1.txt','Debate_Data_9_26_2016_Final_1_2.txt']:
        with open(f,'r', encoding="utf-8") as fd:
            shutil.copyfileobj(fd, wfd, 1024*1024*10)
            #10MB per writing chunk to avoid reading big file into memory.



count = 0
for line in open('COMBINED_Twitter_Debate_Data.json','r', encoding="utf-8"):
    count+=1

print(count)
