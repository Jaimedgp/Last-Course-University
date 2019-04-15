import numpy as np
import matplotlib.pyplot as plt

namefile = "EnergiaDistancia.txt"

x = []
y = []

with open(namefile, 'r') as fr:
    for i in range(0,7):
        x.append(float(fr.readline()))
    for i in range(0,7):
        y.append(float(fr.readline()))

param = np.polyfit(x, y, 3)

xTh = np.linspace(min(x), max(x))
yTh = param[3] + param[2]*xTh + param[1]*xTh**2 + param[0]*xTh**3

plt.plot(xTh, yTh, 'r')
plt.plot(x, y, 'ro')
plt.show()

