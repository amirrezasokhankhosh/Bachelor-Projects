import os
import re
from matplotlib import pyplot as plt

os.system("./main");

output_main = open("./output_main.txt" , "r");

output_lines = output_main.readlines()
configsFirst = []
configsSecond = []
times = []
j = 0
for i in output_lines:
    i = re.sub('\\n$' , '' , i)
    if(j % 3 == 0):
        configsFirst.append(i)
    elif(j % 3 == 1):
        configsSecond.append(i)
    else:
        times.append(i)
    j = j + 1

plt.scatter([1 , 2 , 3 , 4] , times)
plt.suptitle("1 is (" + configsFirst[0] + " , " + configsSecond[0] + " ) -  2 is ( " + configsFirst[1] + " , " + configsSecond[1] + " ) - 3 is ( " + configsFirst[2] + " , " + configsSecond[2] + " ) - 4 is ( " + configsFirst[3] + " , " + configsSecond[3] + " ).")
plt.show()



