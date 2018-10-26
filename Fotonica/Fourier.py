import matplotlib.pyplot as plt
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
			if rX**2+rY**2 <= 50**2:
				filtro[x][y] = 1
	return filtro

numReflec = 20
Pupila = filtroPasoBajo(600, 600)
M1 = Pupila

# Primera lente
fourier = np.fft.fft2(M1)
M2 = np.fft.fftshift(fourier)

#primera reflexion
M3 = M2 * Pupila

M4 = np.fft.fft2(M3)

M1 = M4*Pupila

for i in range(0, numReflec-1):
	# Primera lente
	M2 = np.fft.fft2(M1)
	#M2 = np.fft.fftshift(fourier)

	#primera reflexion
	M3 = M2 * Pupila

	M4 = np.fft.fft2(M3)

	M1 = M4*Pupila

# Mostrar la imagen rotandola
plt.imshow(imrotate(abs(M1), 180), cmap=plt.cm.BrBG, interpolation='nearest',
           origin='lower', extent=[0,1,0,1])
plt.show()
