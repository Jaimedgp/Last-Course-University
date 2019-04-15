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
ax = fig.add_subplot(121, projection='3d')
bx = fig.add_subplot(122)

ax.plot_surface(kx, ky, energiaMas,  rstride=1, cstride=1,
                    cmap=plt.cm.jet, linewidth=0, antialiased=False, shade=False)
ax.plot_surface(kx, ky, energiaMenos,  rstride=1, cstride=1,
                    cmap=plt.cm.jet, linewidth=0, antialiased=False, shade=False)
bx.imshow(energiaMenos,cmap=plt.cm.jet)
plt.show()
