# Concats Two text files, and checks that correct number of lines desired are present

import shutil
with open('All_User_Names.txt','w', encoding="utf-8") as wfd:
    for f in ['Twitter_Screen_Names_1.txt', 'Twitter_Screen_Names_2.txt']:
        with open(f,'r', encoding="utf-8") as fd:
            shutil.copyfileobj(fd, wfd, 1024*1024*10)
            #10MB per writing chunk to avoid reading big file into memory.



count = 0
for line in open('All_User_Names.txt','r', encoding="utf-8"):
    count+=1

print(count)
