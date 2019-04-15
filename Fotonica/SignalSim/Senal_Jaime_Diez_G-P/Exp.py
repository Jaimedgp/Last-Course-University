import numpy as np
import matplotlib.pyplot as plt

AmpI = [10000, 100]
M = [0.1, 0.5, 1]
T = 0.001
P = 0.1
N = 10000
fr = 2*np.pi/P
numK = 100

k = np.linspace(1, numK, numK)
time = np.linspace(0, 10, N)
tauM = k*T

tMed = T / 2

colors = ['r', 'b', 'g']

def analitica():
    gTh = [[ 0 for i in tauM ] for j in M ]
    for m in range(3):
        summ = np.cos(20*np.pi*T*k)*(M[m]*M[m])/2
        gTh[m] = 1 + summ

    figure = plt.figure()
    for m in range(3):
        label = "M = "+str(M[m])
        plt.plot(tauM, gTh[m] ,colors[m], label=label)
    plt.legend()
    plt.ylabel("$g^{2}(\\tau)$", fontsize=15)
    plt.xlabel("$\\tau$", fontsize=15)
    figure.savefig("TheoInt")
    plt.show()

def numerica():
    g = [[ [] for i in M ] for i in AmpI ]

    indexI = 0
    for Imed in AmpI:
        indexM = 0
        for m in M:
            # Definimos la funcion Intensidad con la M y la Imed dada
            I = lambda t: Imed*(1+ m*np.cos(fr*t))

            n = np.zeros(len(time))
            poiss = np.zeros(len(time))

            for i in range(len(time)):
                #n[i] = tMed*abs(I(time[i]+tMed)/2)
                n[i] = T*abs(I(time[i]+tMed))
                poiss[i] = np.random.poisson(n[i])

            for ks in k:
                ks = int(ks)
                nProd = np.zeros(len(time)-ks)
                for i in range(ks, len(time)):
                    nProd[i-ks] = poiss[i-ks]*poiss[i]
                g[indexI][indexM].append(np.mean(nProd)/(np.mean(poiss)**2))
            indexM += 1
        indexI += 1

    for i in range(len(AmpI)):
        figure = plt.figure()
        for j in range(len(M)):
            label = "M = "+str(M[j])
            plt.plot(tauM, g[i][j] ,colors[j], label=label)
        plt.ylabel("$g^{2}(\\tau)$", fontsize=15)
        plt.xlabel("$\\tau$", fontsize=15)
        plt.legend()
        figure.savefig("Int"+str(AmpI[i]))
        plt.show()

analitica()

numerica()

