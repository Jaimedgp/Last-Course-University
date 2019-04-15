from hexagon import hexagono
import numpy as np
import matplotlib.pyplot as plt
#import matplotlib
#matplotlib.use('Agg')
from mpl_toolkits.mplot3d import Axes3D

es = -13.6
a = 2
g = 2

kx = np.linspace(-np.pi, np.pi, 40)
ky = np.linspace(-np.pi, np.pi, 40)
kx, ky = np.meshgrid(kx, ky)

energiaMas = np.zeros((40, 40))
energiaMas = es - a - 2*g*(np.cos(kx) + np.cos(ky))

energiaMenos = np.zeros((40, 40))
energiaMenos = es + a + 2*g*(np.cos(kx) + np.cos(ky))

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')
ax.plot_surface(kx, ky, energiaMas,  rstride=1, cstride=1,
                    cmap=plt.cm.jet, linewidth=0, antialiased=False, shade=False)
ax.plot_surface(kx, ky, energiaMenos,  rstride=1, cstride=1,
                    cmap=plt.cm.jet, linewidth=0, antialiased=False, shade=False)
plt.show()
