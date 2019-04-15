import numpy as np
import matplotlib.pyplot as plt
es = -13.6
a = 2
g = 2
nP = 21
x = np.zeros(nP)
y = np.zeros(nP)
for n in range(0, nP):
    x[n] = n*np.pi/nP
    y[n] = es-a-2*g*(np.cos(x[n])+1)

plt.plot(x, y, 'r')


for n in range(0, nP):
    x[n] = n*np.pi/nP
    y[n] = es-a-2*g*(-1+np.cos(x[n]))

plt.plot(np.pi+x, y, 'b')

for n in range(0, nP):
    x[n] = np.pi*np.sqrt(2)-n*np.pi*np.sqrt(2)/nP
    y[n] = es-a-4*g*np.cos(np.pi-x[n]/np.sqrt(2))

plt.plot(2*np.pi+x, y, 'g')
plt.show()
