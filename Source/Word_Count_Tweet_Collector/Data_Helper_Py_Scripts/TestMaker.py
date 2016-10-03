


with open("Debate_Data_9_26_2016_Final_1.json", "r", encoding="utf-8") as line:
    with open("SMALL_Debate_data.json", "w", encoding="utf-8") as outFile:
        count = 0

        for l in line:
            print(l, file=outFile, end="")
            count += 1
            if count == 20:
                break
        
    
