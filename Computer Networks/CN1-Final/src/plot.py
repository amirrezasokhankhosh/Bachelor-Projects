import os
import time
from matplotlib import pyplot as plt

inputs = [1, 2, 4, 8, 16]
times_SRA = []

os.system('javac *.java')

print("Selective Repeat ARQ")
for input in inputs:
    f = open("./input.txt", "w")
    f.write(str(input) + "\nfalse")
    f.close()

    start_time = time.time()
    os.system('java App')
    end_time = time.time()  
    times_SRA.append(end_time - start_time)

# plt.scatter(inputs , times)
# plt.title("Selective Repeat ARQ")
# plt.show()

times_GBN = []
print("GBN")
for input in inputs:
    f = open("./input.txt", "w")
    f.write(str(input) + "\ntrue")
    f.close()

    start_time = time.time()
    os.system('java App')
    end_time = time.time()  
    times_GBN.append(end_time - start_time)

# plt.scatter(inputs , times)
# plt.title("GBN")
# plt.show()


fig, axs = plt.subplots(2)
fig.suptitle('Selective Repeat ARQ vs GBN')
axs[0].scatter(inputs, times_SRA)
axs[1].scatter(inputs, times_GBN)
axs[0].plot(inputs, times_SRA)
axs[1].plot(inputs, times_GBN)
plt.show()
