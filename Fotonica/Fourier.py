import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

from scipy.misc import imread, imresize, imrotate
import numpy as np

def filtroPasoAlto(xSize, ySize):
	filtro =  np.zeros((xSize, ySize))
	CeroX = xSize/2
	CeroY = ySize/2
	for x in range(0,xSize):
		rX = CeroX-x
		for y in range(0,ySize):
			rY = y-CeroY
			if rX**2+rY**2 >= 10**2:
				filtro[x][y] = 1
	return filtro

def filtroPasoBajo(xSize, ySize):
	filtro =  np.zeros((xSize, ySize))
	CeroX = xSize/2
	CeroY = ySize/2
	for x in range(0,xSize):
		rX = CeroX-x
		for y in range(0,ySize):
			rY = y-CeroY
			if rX**2+rY**2 <= 60**2:
				filtro[x][y] = 1
	return filtro

numReflec = 50
Pupila = filtroPasoAlto(600, 600)
M1 = Pupila

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

x = np.arange(0, len(M4))
y = np.arange(0, len(M4))
x, y = np.meshgrid(x, y)

# create the figure
fig = plt.figure()
ax1 = fig.add_subplot(2, 2, 1)
ax2 = fig.add_subplot(2, 2, 2, projection='3d')
ax3 = fig.add_subplot(2, 2, 3)
ax4 = fig.add_subplot(2, 2, 4, projection='3d')

# Mostrar la imagen rotandola
ax1.imshow(imrotate(abs(M2), 180), cmap=plt.cm.jet, interpolation='nearest',
               origin='lower', extent=[0,1,0,1])

# show the 3D rotated projection
ax2.plot_surface(x, y, abs(M2), cmap=plt.cm.jet,
                                       linewidth=0, antialiased=False, shade=False)

# Mostrar la imagen rotandola
ax3.imshow(imrotate(abs(M4), 180), cmap=plt.cm.jet, interpolation='nearest',
               origin='lower', extent=[0,1,0,1])

# show the 3D rotated projection
ax4.plot_surface(x, y, abs(M4), rstride=1, cstride=1, cmap=plt.cm.jet,
                 linewidth=0, antialiased=False, shade=False)

plt.show()
