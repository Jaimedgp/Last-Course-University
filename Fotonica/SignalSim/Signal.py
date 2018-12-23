import numpy as np
import matplotlib.pyplot as plt

Imed = [10000, 100]
M = [0.1, 0.5, 1]
T = 0.001
P = 0.1
N = 1000
fr = 1
numK = 100

I = lambda t, Mr: Imed[0]*(Mr*np.cos(fr*t))
g = lambda t, tau, Mr: (I(t, Mr)*I(t+tau, Mr))

k = np.linspace(1, numK, numK)
time = np.linspace(0, 99, N)
WL = np.linspace(0, 1/fr, 100)
tauM = k*T
fig = plt.figure(figsize=(15, 5))
ax = fig.add_subplot(121)
bx = fig.add_subplot(122)

for Mr in M:
    iSum = 0
    i2Sum = 0
    iMed = np.zeros(numK)
    i2Med = np.zeros(numK)
    index = 0
    for tau in tauM:
        for ts in WL:
            iSum += g(ts, tau, Mr)
            i2Sum += I(ts, Mr)
        iMed[index] = (iSum/len(time))
        i2Med[index] = (i2Sum/len(time))
        index +=1

    G = iMed/(i2Med**2.0)


    ax.plot(tauM, G)
    bx.plot(I(time, Mr))
plt.show()