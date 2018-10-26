import matplotlib.pyplot as plt
from scipy.misc import imread, imresize, imrotate
import numpy as np

##########################################
###      PREPARAR GRAFICAS             ###
##########################################

def filtroPasoAlto(self, imagenToProcess=0):
    if imagenToProcess == 0:
        imagenToProcess = self.image
    filtro =  np.zeros((self.xSize, self.ySize))
    CeroX = self.xSize/2
    CeroY = self.ySize/2
    for x in range(0,self.xSize):
        rX = CeroX-x
        for y in range(0,self.ySize):
            rY = y-CeroY
            if rX**2+rY**2 >= 10**2:
                filtro[x][y] = 1
    return filtro

def filtroPasoBajo(self, imagenToProcess=0):
    if imagenToProcess == 0:
        imagenToProcess = self.image
    filtro =  np.zeros((self.xSize, self.ySize))
    CeroX = self.xSize/2
    CeroY = self.ySize/2
    for x in range(0,self.xSize):
        rX = CeroX-x
        for y in range(0,self.ySize):
            rY = y-CeroY
            if rX**2+rY**2 <= 50**2:
                filtro[x][y] = 1
    return filtro

##########################################
###      PREPARAR GRAFICAS             ###
##########################################

# Se abre la imagen
imagenPath = 'Imagen2.jpg'
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
