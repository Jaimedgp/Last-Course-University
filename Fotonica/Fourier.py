#from Resonador import SistemaOptico
import matplotlib.pyplot as plt
from scipy.misc import imrotate

"""
Sistema = SistemaOptico("Imagen2.jpg")

f, plots = plt.subplots(2, 2)
plots[0,0].imshow(Sistema.image, cmap="gray")
plots[1,0].imshow(Sistema.image, cmap="gray")

plots[0,1].imshow(imrotate(abs(Sistema.filtroPasoAlto()), 180), cmap="gray")
plots[1,1].imshow(imrotate(abs(Sistema.filtroPasoBajo()), 180), cmap="gray")

plt.show()
"""

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

"""
# Se abre la imagen
imagenPath = 'Imagen1.jpg'
image = imread(imagenPath, False, 'L')
xSize, ySize = len(image), len(image[1])

# Primera lente
fourier = np.fft.fft2(image)
fourierS = np.fft.fftshift(fourier)

##########################################
###      PREPARAR GRAFICAS             ###
##########################################

f, plots = plt.subplots(2, 3)
plots[0,0].imshow(image, cmap="gray")
plots[1,0].imshow(image, cmap="gray")

##########################################
###      PROGRAMA CON PASO BAJO        ###
##########################################

# tipo de filtro en el espacio de Fourier
filtro = filtroPasoBajo(xSize, ySize)
plots[0,1].imshow(filtro, cmap="gray")
# Ultima lente 
screen = np.fft.fft2(fourierS*filtro)

# Mostrar la imagen rotandola
plots[0,2].imshow(imrotate(abs(screen), 180), cmap="gray")

##########################################
###      PROGRAMA CON PASO ALTO        ###
##########################################

# tipo de filtro en el espacio de Fourier
filtro = filtroPasoAlto(xSize, ySize)
plots[1,1].imshow(filtro, cmap="gray")

# Ultima lente 
screen = np.fft.fft2(fourierS*filtro)

# Mostrar la imagen rotandola
plots[1,2].imshow(imrotate(abs(screen), 180), cmap="gray")

plt.show()

"""

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
plt.imshow(imrotate(abs(M1), 180), cmap=plt.cm.BrBG, interpolation='nearest', origin='lower', extent=[0,1,0,1])
plt.show()