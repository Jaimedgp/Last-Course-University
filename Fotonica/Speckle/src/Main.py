from Speckle import Speckle
from Image import Image
from Espejo import EspejoResonador
from scipy.misc import imrotate
import numpy as np
import matplotlib.pyplot as plt
import cmath

def fourierTrans(matrix):
    fourier = np.fft.fft2(matrix)
    return np.fft.fftshift(fourier)

def createImage(nameImagen, tmnhPeque, tmnhExtnd):

    imagen = Image(nameImagen)
    imagen.resizeImage(2*tmnhPeque, 2*tmnhPeque)

    extndImage = np.zeros((tmnhExtnd, tmnhExtnd), dtype=np.complex_)

    posicion = tmnhExtnd/2 - tmnhPeque
    extndImage[posicion:-posicion, posicion:-posicion] = imagen.image[:, :]

    return extndImage

def aberraciones(matrix):
    intervalo = np.deg2rad(90)
    xSize = len(matrix)
    ySize = len(matrix[0])
    for x in range(0, xSize):
        rX = x-(xSize/2)
        for y in range(0,ySize):
            tilt = np.random.randint(-10,10)*(intervalo/10)
            tip = np.random.randint(-10,10)*(intervalo/10)

            rY = (ySize/2)-y
            r2 = rX**2+rY**2

            matrix[x][y] = (matrix[x][y]*cmath.exp(1j*(tip*rX + tilt*rY)))

    return matrix

def speckleAleatorio(Speckle):
    """---------------------------------------"""
    """            SPECKLE ALEATORIO          """
    """---------------------------------------"""

    image = createImage('../images.png', int(tmnhGrand*ratePupilas)/2, tmnhGrand)

    image = aberraciones(image) * lenteEspejo

    patron = Speckle.getSpeckle(0, False)
    modificar = np.zeros((tmnhGrand, tmnhGrand), dtype=np.complex_)

    for x in range(len(patron)):
        for y in range(len(patron[0])):
            if patron[x][y] != 0 and image[x][y] != 0:
                phase = -(cmath.phase(image[x][y]))
                modificar[x][y] = 1*cmath.exp(1j*(phase))

            else:
                modificar[x][y] = 0

    result = modificar * image

    resultFFT = fourierTrans(result)
    screen = np.fft.fft2(resultFFT)

    fig = plt.figure()
    fig.add_subplot(131).imshow(abs(image.imag), cmap=plt.cm.jet)
    fig.add_subplot(132).imshow(abs(modificar.imag), cmap=plt.cm.jet)
    fig.add_subplot(133).imshow(imrotate(abs(screen), 180), cmap=plt.cm.jet)
    plt.show()

    return None

#########################################
#####       MAIN DEL PROGRAMA       #####
#########################################

#---------------------------------------
#   APROXIMACION MANCHA AIRY CON FASE
#---------------------------------------

tmnhPeque = 64
tmnhGrand = 512

ratePupilas = 0.125
telescopeSize = int(tmnhGrand*ratePupilas)

SpeckleAiryn = Speckle(tmnhPeque, telescopeSize, tmnhGrand)

imagePhase = SpeckleAiryn.getSpeckle(0,  True)

imageFFT = fourierTrans(imagePhase)

fig = plt.figure()
fig.add_subplot(122).imshow(abs(imageFFT.real), cmap=plt.cm.jet)
fig.add_subplot(122).set_title("Mancha de Airy")
fig.add_subplot(121).imshow(abs(imagePhase.real), cmap=plt.cm.jet)
fig.add_subplot(121).set_title("Pupila")
fig.savefig('../Graficas/Airy_Fase.png', dpi=600)

#---------------------------------------
#   APROXIMACION MANCHA AIRY SIN FASE
#---------------------------------------

tmnhPeque = 64
tmnhGrand = 512

ratePupilas = 0.125
telescopeSize = int(tmnhGrand*ratePupilas)

SpeckleAiryn = Speckle(tmnhPeque, telescopeSize, tmnhGrand)

imagePhase = SpeckleAiryn.getSpeckle(0,  False)

imageFFT = fourierTrans(imagePhase)

fig = plt.figure()
fig.add_subplot(122).imshow(abs(imageFFT.real), cmap=plt.cm.jet)
fig.add_subplot(122).set_title("Mancha de Airy")
fig.add_subplot(121).imshow(abs(imagePhase.real), cmap=plt.cm.jet)
fig.add_subplot(121).set_title("Pupila")
fig.savefig('../Graficas/Airy.png', dpi=600)

#---------------------------------------
#   Extremo MANCHA AIRY SIN FASE
#---------------------------------------

tmnhPeque = 8
tmnhGrand = 512

ratePupilas = 0.125
telescopeSize = int(tmnhGrand*ratePupilas)

SpeckleAiryn = Speckle(tmnhPeque, telescopeSize, tmnhGrand)

imagePhase = SpeckleAiryn.getSpeckle(0,  True)

imageFFT = fourierTrans(imagePhase)

fig = plt.figure()
fig.add_subplot(122).imshow(abs(imageFFT.real), cmap=plt.cm.jet)
fig.add_subplot(122).set_title("Mancha de Airy")
fig.add_subplot(121).imshow(abs(imagePhase.real), cmap=plt.cm.jet)
fig.add_subplot(121).set_title("Pupila")
fig.savefig('../Graficas/SpeckleTotal.png', dpi=600)

#---------------------------------------
#          OBTENCION DE IMAGEN
#---------------------------------------

tmnhPeque = 16
tmnhGrand = 512

ratePupilas = 0.5
telescopeSize = int(tmnhGrand*ratePupilas)

Speckle = Speckle(tmnhPeque, telescopeSize, tmnhGrand)

imagePhase = Speckle.getSpeckle(0,  True)

image = createImage('../images.png', int(tmnhGrand*ratePupilas)/2, tmnhGrand)

image = image * imagePhase
imageFFT = fourierTrans(image)

fig = plt.figure()

#---------------------------------------
#            SIN SPECKLE
#---------------------------------------

espejo = EspejoResonador(tmnhGrand, tmnhGrand, tmnhGrand*ratePupilas)
espejo.lenteCircular()
lenteEspejo = espejo.lente

resultFFT = imageFFT * lenteEspejo
screen = np.fft.fft2(resultFFT)

fig.add_subplot(231).imshow(abs(image.imag), cmap=plt.cm.jet)
fig.add_subplot(231).set_title("Imagen Fuente")
fig.add_subplot(232).imshow(abs(lenteEspejo), cmap=plt.cm.jet)
fig.add_subplot(232).set_title("Pupila")
fig.add_subplot(233).imshow(imrotate(abs(screen), 180), cmap=plt.cm.jet)
fig.add_subplot(233).set_title("Imagen Obtenida")

#---------------------------------------
#            CON SPECKLE
#---------------------------------------

patron = Speckle.getSpeckle(0, False)
modificar = np.zeros((tmnhGrand, tmnhGrand), dtype=np.complex_)

for x in range(len(patron)):
    for y in range(len(patron[0])):
        if patron[x][y] != 0 and image[x][y] != 0:
            phase = -(cmath.phase(image[x][y]))
            modificar[x][y] = 1*cmath.exp(1j*(phase))

        else:
            modificar[x][y] = 0

result = modificar * image

resultFFT = fourierTrans(result)
screen = np.fft.fft2(resultFFT)

fig.add_subplot(234).imshow(abs(image.imag), cmap=plt.cm.jet)
fig.add_subplot(234).set_title("Imagen Fuente")
fig.add_subplot(235).imshow(abs(modificar.imag), cmap=plt.cm.jet)
fig.add_subplot(235).set_title("Pupila con Speckle")
fig.add_subplot(236).imshow(imrotate(abs(screen), 180), cmap=plt.cm.jet)
fig.add_subplot(236).set_title("Imagen Obtenida")
fig.savefig('../Graficas/Nasa.png', dpi=600)
plt.show()
