import numpy as np
import matplotlib.pyplot as plt

AmpI = [10000, 100]
M = [0.1, 0.5, 1]
T = 0.001
P = 0.1
N = 10000
fr = 1/P
numK = 100


k = np.linspace(1, numK, numK)
time = np.linspace(0, 10, N)
tauM = k*T

tMed = T / 2

g = [[ [] for i in M ] for i in AmpI ]
indexI = 0
for Imed in AmpI:
    indexM = 0
    for m in M:
        I = lambda t: Imed*(m*np.cos(fr*t))

        n = np.zeros(len(time))
        poiss = np.zeros(len(time))

        for i in range(len(time)):
            n[i] = tMed*abs(I(time[i]+tMed)/2)
            poiss[i] = np.random.poisson(n[i])

        for ks in k:
            ks = int(ks)
            nProd = np.zeros(len(time)-ks)
            for i in range(ks, len(time)):
                nProd[i-ks] = poiss[i-ks]*poiss[i]
            g[indexI][indexM].append(np.mean(nProd)/(np.mean(poiss)**2))
        indexM += 1
    indexI += 1

colors = ['r', 'b', 'g']
for i in range(len(AmpI)):
    title = "Intensidad de "+str(AmpI[i])
    for j in range(len(M)):
        label = "M = "+str(M[j])
        plt.plot(tauM, g[i][j] ,colors[j], label=label)
    plt.title(title)
    plt.legend()
    plt.show()

