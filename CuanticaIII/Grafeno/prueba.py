from hexagon import hexagono
import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.gridspec as gridspec

e0 = -13.6
g = 2

kx = np.linspace(-2*np.pi, 2*np.pi, 100)
ky = np.linspace(-2*np.pi, 2*np.pi, 100)
kx, ky = np.meshgrid(kx, ky)

energiaMas = - g*np.sqrt(3+2*np.cos(kx) + 2*np.cos(kx/2.0 +
                            np.sqrt(3)*ky/2.0) +
                            2*np.cos(kx/2.0-np.sqrt(3)*ky/2.0))

energiaMenos = g*np.sqrt(3+2*np.cos(kx) + 2*np.cos(kx/2.0 +
                            np.sqrt(3)*ky/2.0) +
                            2*np.cos(kx/2.0-np.sqrt(3)*ky/2.0))

fig = plt.figure()
ax = fig.add_subplot(223, projection='3d')
bx = fig.add_subplot(224)
cx = fig.add_subplot(211)

ax.plot_surface(kx, ky, energiaMas,  rstride=1, cstride=1,
                    cmap=plt.cm.coolwarm, linewidth=0, antialiased=False, shade=False)
ax.plot_surface(kx, ky, energiaMenos,  rstride=1, cstride=1,
                    cmap=plt.cm.coolwarm, linewidth=0, antialiased=False, shade=False)
bx.imshow(energiaMenos,cmap=plt.cm.coolwarm)

x1 = np.linspace(0, np.sqrt(3)*np.pi/2.0, int(np.sqrt(3)*25/2))
x2 = np.linspace(np.sqrt(3)*np.pi/2.0, np.pi, int((np.sqrt(3)-1)*25/2))
x3 = np.linspace(np.pi, 0, 25)
x = np.concatenate((x1, x2, x3))

y1 = np.zeros(len(x1))
y2 = np.linspace(0, np.pi/2.0, len(x2))
y3 = np.linspace(np.pi/2.0, 0, len(x3))
y = np.concatenate((y1, y2, y3))

enerMs = np.zeros(len(x))
enerMns = np.zeros(len(x))

for i in range(0, len(x)):
    enerMs[i] = - g*np.sqrt(3+2*np.cos(x[i]) + 2*np.cos(x[i]/2.0 +
                                np.sqrt(3)*y[i]/2.0) +
                                2*np.cos(x[i]/2.0-np.sqrt(3)*y[i]/2.0))

    enerMns[i] = g*np.sqrt(3+2*np.cos(x[i]) + 2*np.cos(x[i]/2.0 +
                                np.sqrt(3)*y[i]/2.0) +
                                2*np.cos(x[i]/2.0-np.sqrt(3)*y[i]/2.0))

cx.plot(enerMs, 'r', enerMns, 'b')

plt.show()
