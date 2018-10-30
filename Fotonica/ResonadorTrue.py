from Pupila import EspejoResonador
import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
from scipy.misc import imread, imresize, imrotate


Espejo1 = EspejoResonador()
Espejo2 = EspejoResonador()
"""
M1 = Espejo1.lente
for i in range(0, 5):
    rebote1 = Espejo1.rebotes(M1)

    M1 = Espejo2.rebotes(rebote1)
"""

###################

numReflec = 50
Pupila = Espejo1.lente
"""M1 = Pupila

# Primera lente
fourier = np.fft.fft2(M1)
M2 = np.fft.fftshift(fourier)

#primera reflexion
M3 = M2 * Pupila

M4 = np.fft.fft2(M3)

for i in range(0, numReflec-1):
	M1 = M4 * Pupila

	# Primera lente
	M2 = np.fft.fft2(M1)

	#primera reflexion
	M3 = M2 * Pupila

	M4 = np.fft.fft2(M3)

#####################
"""
x = np.arange(0, len(Pupila))
y = np.arange(0, len(Pupila))
x, y = np.meshgrid(x, y)

# create the figure
fig = plt.figure()
ax1 = fig.add_subplot(2, 2, 1)
ax2 = fig.add_subplot(2, 2, 2, projection='3d')
ax3 = fig.add_subplot(2, 2, 3)
ax4 = fig.add_subplot(2, 2, 4, projection='3d')

# Mostrar la imagen rotandola
ax1.imshow(imrotate(Pupila.real, 180), cmap=plt.cm.jet, interpolation='nearest',
               origin='lower', extent=[0,1,0,1])

# show the 3D rotated projection
ax2.plot_surface(x, y, Pupila.real, cmap=plt.cm.jet,
                                       linewidth=0, antialiased=False, shade=False)
"""
# Mostrar la imagen rotandola
ax3.imshow(imrotate(abs(M1.real), 180), cmap=plt.cm.jet, interpolation='nearest',
               origin='lower', extent=[0,1,0,1])

# show the 3D rotated projection
ax4.plot_surface(x, y, abs(M1.real), rstride=1, cstride=1, cmap=plt.cm.jet,
                 linewidth=0, antialiased=False, shade=False)
"""
plt.show()
