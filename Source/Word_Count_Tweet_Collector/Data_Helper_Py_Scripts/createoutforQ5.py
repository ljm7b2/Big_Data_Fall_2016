t = open("Q5_Output.txt", "r")

k1 = open("outer1.txt", "w")
k2 = open("outer2.txt", "w")

count = 0

for line in t:
    s = line.strip()
    s = s.split(',')
    s1 = s[0].replace('(', '')
    s2 = s[1].replace(')', '')

    print(s1 +",", file=k1)
    print(s2 + ",", file=k2)

    if count == 100:
        

k1.close()
k2.close()
